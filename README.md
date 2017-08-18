# VideoFeed
## 实现的功能

1. 滚动时不播放，但是要亮起，当前屏幕内，item view显示百分比最大的一个。
2. 停止滚动且手指抬起时自动播放。
3. 播放完当前的视频，自动滚动到下一个并自动播放。
4. 正在播放的当前视频，快要播放完毕时，弹出TextView提示播放下一个，点击TextView自动滚动到下一个。
5. Activity 在前台播放时，进入后台暂停播放，再进入前台时 自动播放视频。
6. Activity 在前台暂停播放时，进入后台，再进入前台时 还是暂停播放之前的视频状态。
7. Activity finish 的时候，停止播放 销毁。
8. 播放视频，当控制器隐藏时，播放器底边播放进度条显示。
9. 播放列表到最后一个时的操作判断。

### 实现思路
看下面这篇文章 从实现到优化的所有填坑的过程
视频列表滚动连播技术探究系列 http://www.jianshu.com/nb/15022458

## [视频列表滚动连播技术探究系列](http://www.jianshu.com/nb/15022458)
1. [仿网易/QQ空间视频列表滚动连播炫酷效果(V1.0 挖坑之路)](http://www.jianshu.com/p/f89fc875ac14)。
2. [仿网易/QQ空间视频列表滚动连播炫酷效果(V2.0 填坑之路)](http://www.jianshu.com/p/55e95eb37197)。
3. [仿网易视频列表滚动连播炫酷效果(v3.0 稳定版-思想改变及优化)]() 稳定版-进行优化和思想上的改变。
4. [RecyclerView 平滑滚动可控制滚动速度的终极解决方案](http://www.jianshu.com/p/bae9e516aace)。
5. [仿网易视频列表连播炫酷效果 - v3.1 升级版-细节优化(网络状态切换、item点击事件等)](http://www.jianshu.com/p/a4b82d9c3218)。
持续更新中.....

> 看过1、2上面这两篇文章的同学，实现的思想基本上已经了解了，不熟悉的同学可以去看这两篇文章，通过1、2实现的效果，是存在一些问题的。

### 存在哪些问题？
1.  每个item中都有一个播放器，如何销毁呢？内存占用会很大？
2. 全屏播放视频时，切换会有问题？
3. 每个item的播放器都不容易控制？
4. 全屏播放时，进度如何处理

###  如何解决问题？
##### 思想上的转变
> 动态添加播放器：而不是每个item的布局都有一个播放器，视频列表页Activity 全局就初始化一个播放器view，动态添加到列表item中，也就是说当要播放当前的item时将播放器添加到item预留的ViewGroup容器中 。同时列表更加流畅，易方便于播放的处理，销毁和停止播放器。

> 全屏播放的处理: 在视频列表页的Activity 布局文件中预留一个ViewGroup容器，当点击全屏播放时，隐藏列表，并将列表的播放器移除列表，显示布局文件中预留的容器，将播放器添加到这个容器中，这样视频会继续从当前的进度播放，完全不用再去处理复杂的逻辑。这方法需要在Activity中预留一个放置播放器的宽高都match_parent的ViewGroup，大小切换就是把播放器添加到本来的小容器和添加到全屏的ViewGroup中来回切换，对于播放器的监听器也不用过多干预。

如看下图所示，全局只对一个播放器操作
![逻辑图](http://upload-images.jianshu.io/upload_images/2005932-4466424a84ce3f36.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
是不是忽然之间貌似顿开，网易新闻或者腾讯新闻的视频列表也应该是这样实现的。

#### 1. 如何动态的给item添加播放器
- 在视频列表Activity 全局初始化一个播放器的view
```
  lVideoView = new LVideoView(this);//初始化播放器
```
- 停止滚动手指抬起时 动态添加播放器，开始播放视频，并获取之前的播放进度
```
    private void aoutPlayVideo(final RecyclerView recyclerView) {
        if (!lVideoView.isPlayer()) {
            VideoFeedHolder childViewHolder = (VideoFeedHolder) recyclerView.findViewHolderForAdapterPosition(itemPosition);
            if (childViewHolder != null) {
                // 注册监听以及隐藏蒙层
                childViewHolder.registerVideoPlayerListener(this);
                childViewHolder.goneMasked();
                childViewHolder.playerWifi();
//                int netType = NetChangeManager.getInstance().getNetType();
//                if (netType == 1 || Constants.VIDEO_FEED_WIFI) { // WiFi的情况下，或者允许不是WiFi情况下继续播放
                // 动态添加播放器
                View itemView = childViewHolder.itemView;
                FrameLayout frameLayout = (FrameLayout) itemView.findViewById(R.id.ll_video);
                frameLayout.removeAllViews();
                ViewGroup last = (ViewGroup) lVideoView.getParent();//找到videoitemview的父类，然后remove
                if (last != null && last.getChildCount() > 0) {
                    last.removeAllViews();
                }
                frameLayout.addView(lVideoView);
                // 获取播放进度
                TabFragMainBeanItemBean itemBean = itemBeens.get(itemPosition);
                long videoProgress = itemBean.videoProgress;
                long duration = itemBean.mDuration;
                if (videoProgress != 0 && videoProgress != duration) { // 跳转到之前的进度，继续播放
                    lVideoView.startLive(itemBean.video_url);
                    lVideoView.setSeekTo(videoProgress);
                } else {//从头播放
                    lVideoView.startLive(itemBean.video_url);
                }
//                }
            }
        }
    }
```
从上面代码中我们可以看出，拿到当前正要播放视频的item中的容器，并将播放器添加到容器中，如果之前有播放过，拿取播放进度，并跳转到之前的进度继续播放。
- 滑动播放下一个视频时，停止播放上一个视频，并将播放器从item中移除记下当前item的播放进度，添加到下一个item的容器中，播放视频。

```
   private void stopPlayer(int position) {
        VideoFeedHolder childViewHolder = (VideoFeedHolder) rl_video.findViewHolderForAdapterPosition(position);
        if (childViewHolder != null) {
            if (lVideoView.isPlayer()) { // 如果正在播放，则停止并记录播放进度，否则不调用这个方法
                lVideoView.stopVideoPlay();
                TabFragMainBeanItemBean itemBean = itemBeens.get(position);
                itemBean.videoProgress = currentPosition;
                itemBean.mDuration = mDuration;
                itemBeens.set(position, itemBean);
            }
            childViewHolder.visMasked();//显示蒙层
            View itemView = childViewHolder.itemView;
            FrameLayout frameLayout = (FrameLayout) itemView.findViewById(R.id.ll_video);
            frameLayout.removeAllViews();
            childViewHolder.unRegisterVideoPlayerListener();// 注意我们需要解除上一个item的监听，不然会注册多个监听
        }
    }
```

- 横竖屏切换时的处理，按照上面的实现思路，看下面的代码
```
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        lVideoView.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {// 竖屏
            orientation = false;
            full_screen.setVisibility(View.GONE);
            full_screen.removeAllViews();
            rl_video_feed.setVisibility(View.VISIBLE);
            addPlayer(itemPosition);
            int mShowFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            full_screen.setSystemUiVisibility(mShowFlags);
        } else { // 横屏
            orientation = true;
            rl_video_feed.setVisibility(View.GONE);
            ViewGroup viewGroup = (ViewGroup) lVideoView.getParent();
            if (viewGroup == null)
                return;
            viewGroup.removeAllViews();
            full_screen.addView(lVideoView);
            full_screen.setVisibility(View.VISIBLE);
            int mHideFlags =
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            full_screen.setSystemUiVisibility(mHideFlags);
        }
    }

  /**
     * 添加播放器
     *
     * @param position
     */
    private void addPlayer(int position) {
        VideoFeedHolder childViewHolder = (VideoFeedHolder) rl_video.findViewHolderForAdapterPosition(position);
        if (childViewHolder != null) {
            View itemView = childViewHolder.itemView;
            FrameLayout frameLayout = (FrameLayout) itemView.findViewById(R.id.ll_video);
            frameLayout.removeAllViews();
            ViewGroup last = (ViewGroup) lVideoView.getParent();//找到videoitemview的父类，然后remove
            if (last != null && last.getChildCount() > 0) {
                last.removeAllViews();
            }
            frameLayout.addView(lVideoView);
        }
    }
```
在Activity中预留一个放置播放器的宽高都match_parent的ViewGroup，大小切换就是把播放器添加到本来的小容器和添加到全屏的ViewGroup中来回切换，对于播放器的监听器也不用过多干预。
> 注意改变播放器view的大小.
```
 /**
     * 大小屏切换播放器的处理
     *
     * @param newConfig
     */
    public void onConfigurationChanged(Configuration newConfig) {
        ViewGroup.LayoutParams layoutParams = fraVideoContainer.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) { //竖屏
            layoutParams.height = (int) getResources().getDimension(R.dimen.live_video_height);
            fraVideoContainer.setLayoutParams(layoutParams);
        } else {// 横屏
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            fraVideoContainer.setLayoutParams(layoutParams);
        }
    }
```
- Activity/Fragment 的生命周期中处理，整个全局我们就一个播放器，生命周期中就可以很好的处理这个播放器
```
/**
     * Activity 不在前台时 暂停播放
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (!isThrumePause) {//若不是手动暂停，Activity进入后台自动暂停
            lVideoView.onPause();
        }
    }

    /**
     * Activity 重新进入前台 播放逻辑
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("linksu",
                "onResume(VideoFeedDetailAct.java:558) isThrumePause" + isThrumePause);
        if (!isThrumePause) { //不是手动暂停且从后台进入前台
            lVideoView.currentPlayer();
        } else { //进入后台之前是暂停的状态,再次进入还是暂停的状态
            lVideoView.startThumb();
        }
    }

    /**
     * Activity 退出时 停止播放
     */
    @Override
    public void finish() {
        super.finish();
        lVideoView.stopVideoPlay();
    }

    /**
     * Activity 销毁时 销毁播放器
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        lVideoView.removeAllViews();
    }
```
- 播放器的状态监听就可以在Activity/Fragment 中去处理，处理起来更加方便
```
@Override
    public void onVideoPrepared() { //准备播放
    }

    @Override
    public void onVideoCompletion() {// 播放完成
        if (itemPosition != lastItemPosition) { //若播放的不是最后一个，播放完成自动播放下一个
            VideoFeedHolder childViewHolder = (VideoFeedHolder) rl_video.findViewHolderForAdapterPosition(itemPosition);
            if (childViewHolder != null) {
                // 播放完成将之前的播放进度清空
                TabFragMainBeanItemBean itemBean = itemBeens.get(itemPosition);
                itemBean.videoProgress = 0;
                itemBean.mDuration = 0;
                itemBeens.set(itemPosition, itemBean);
                // 移除播放器
                childViewHolder.visMasked();
                View itemView = childViewHolder.itemView;
                FrameLayout frameLayout = (FrameLayout) itemView.findViewById(R.id.ll_video);
                frameLayout.removeAllViews();
                childViewHolder.unRegisterVideoPlayerListener();// 注意我们需要解除上一个item的监听，不然会注册多个监听
            }
            itemPosition = itemPosition + 1;
            playerPosition = itemPosition;
            ((LinearLayoutManager) rl_video.getLayoutManager()).scrollToPositionWithOffset(playerPosition, 20);
            aoutPlayVideo(rl_video);
        }
    }

    @Override
    public void onVideoError(int i, String error) {
    }

    @Override
    public void onBufferingUpdate() {

    }

    @Override
    public void onVideoStopped() { // 停止视频播放时，记录视频的播放位置

    }

    @Override
    public void onVideoPause() { //暂停视频播放

    }

    @Override
    public void onVideoThumbPause() { // 手动暂停视频播放
        isThrumePause = true;
    }

    @Override
    public void onVideoThumbStart() { // 手动开始视频播放
        isThrumePause = false;
    }

    @Override
    public void onVideoPlayingPro(long currentPosition, long mDuration, int mPlayStatus) {//获取播放进度
        this.currentPosition = currentPosition;
        this.mDuration = mDuration;
        if (itemPosition != lastItemPosition) { //若播放的不是最后一个，弹出播放下一个的提示
            float percent = (float) ((double) currentPosition / (double) mDuration);
            DecimalFormat fnum = new DecimalFormat("##0.0");
            float c_percent = 0;
            c_percent = Float.parseFloat(fnum.format(percent));
            if (0.8 <= c_percent) {
                videoTips();
            } else {
                missVideoTips();
            }
        }
    }
```

> 这样我们就完成了整个优化过程，其实就是一个带图的列表，动态的添加播放器，这样处理不仅内存消耗占的很少而且，没有任何复杂的逻辑。

![最后的最后，请不要客气，尽情的砸issue或者pull request过来吧！(https://github.com/susussa/VideoFeed)

## License

```   
  MIT License
   
  Copyright (c) 2017 苏福鹿
   
  Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:
   
  The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.
   
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
```   

