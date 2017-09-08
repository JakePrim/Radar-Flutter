Welcome to the VideoFeed wiki!
中文版()
# VideoFeed
## update and modify BUG
1. Modify the crash problem

2. Optimize the sliding experience and animation effects

## implementation of the function

1. Scroll when not playing, but to light up, the current screen, item view shows the largest percentage of one.
2. Stop scrolling and play automatically when the finger is raised.
3. Play the current video, automatically scroll to the next and automatically play.
4. is playing the current video, about to play when finished, pop up TextView prompt to play the next one, click TextView automatically scroll to the next.
5. Activity in the foreground play, enter the background to suspend playback, and then enter the front of the automatic playback of video.
6. Activity in the foreground pause playback, enter the background, and then enter the foreground or pause before playing the video state.
7. When the activity finish, stop playing the destruction.
8. Play the video, when the controller is hidden, the bottom of the player to play the progress bar display.
9. Play the list to the last time to determine the operation.

### Implement ideas
Look at the following article from the realization to the optimization of all the pit filling process
Video List Scrolling and Broadcasting Technology Research Series http://www.jianshu.com/nb/15022458

## [video list rolling broadcast technology exploration series] (http://www.jianshu.com/nb/15022458)
1. [imitation NetEase / QQ space video list rolling continuous broadcast cool effect (V1.0 digging the road)] (http://www.jianshu.com/p/f89fc875ac14).
2. [imitation Net easy / QQ space video list rolling continuous broadcast cool effect (V2.0 fill pit road)] (http://www.jianshu.com/p/55e95eb37197).
3. Optimize and make changes in thought. () - Optimized and thoughtful changes (v3.0 stable version - thought change and optimization).
4. [RecyclerView smooth rolling can control the rolling speed of the ultimate solution] (http://www.jianshu.com/p/bae9e516aace).
5. [imitation NetEase video list continuous broadcast cool effect - v3.1 upgrade version - details optimization (network state switch, item click event, etc.)] (http://www.jianshu.com/p/a4b82d9c3218).
Continued update .....

> Read the above two of the two articles of the students, to achieve the basic idea has been understood, not familiar with the students can see these two articles, through the 1 to achieve the effect, there are some problems.

### What are the problems?
1. Each item has a player, how to destroy it? Memory will be very large?
2. When there is a full-screen video playback, will there be a problem with the switch?
3. Each item player is not easy to control?
4. When the full-screen playback, the progress of how to deal with

### How do I fix the problem?
##### Ideology change
> Dynamically add the player: not the layout of each item has a player, video list page Activity to initialize a player view, dynamically added to the list item, that is to play when the current item will play Add to the ViewGroup container reserved for the item. While the list is more fluid, easy to play, handle and destroy and stop the player.

> Full-screen playback processing: the video list page in the Activity layout file to reserve a ViewGroup container, when clicked full-screen playback, hide the list, and the list of players to remove the list, display the layout file in the container, The player is added to this container so that the video will continue to play from the current progress, without having to deal with the complicated logic at all. This method needs to be reserved in the Activity of a player placed in the width and height are match_parent ViewGroup, the size of the switch is to add the player to the original small container and added to the full-screen ViewGroup switch back and forth, the player's listener is not used Too much intervention.

As shown in the following figure, the global operation of only one player
! [Logic diagram] (http://upload-images.jianshu.io/upload_images/2005932-4466424a84ce3f36.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Is not suddenly seemingly Dayton, NetEase news or Tencent news video list should be so to achieve.

#### 1. How to dynamically add the player to the item
- in the video list Activity global initialization of a player's view
```
  lVideoView = new LVideoView (this); // initialize the player
```
- stop rolling the finger when you dynamically add the player, start playing the video, and get the previous playback progress
```
    private void aoutPlayVideo (final RecyclerView recyclerView) {
        if (! lVideoView.isPlayer ()) {
            VideoFeedHolder childViewHolder = (VideoFeedHolder) recyclerView.findViewHolderForAdapterPosition (itemPosition);
            if (childViewHolder! = null) {
                // register listen and hide the mask
                childViewHolder.registerVideoPlayerListener (this);
                childViewHolder.goneMasked ();
                childViewHolder.playerWifi ();
// int netType = NetChangeManager.getInstance (). getNetType ();
// if (netType == 1 || Constants.VIDEO_FEED_WIFI) {// WiFi, or if it is not allowed to continue playing in WiFi
                // Add the player dynamically
                View itemView = childViewHolder.itemView;
                FrameLayout frameLayout = (FrameLayout) itemView.findViewById (R.id.ll_video);
                frameLayout.removeAllViews ();
                ViewGroup last = (ViewGroup) lVideoView.getParent (); // find the parent class of videoitemview, and then remove
                if (last! = null && last.getChildCount ()> 0) {
                    last.removeAllViews ();
                }
                frameLayout.addView (lVideoView);
                // Get the progress of the play
                TabFragMainBeanItemBean itemBean = itemBeens.get (itemPosition);
                long videoProgress = itemBean.videoProgress;
                long duration = itemBean.mDuration;
                if (videoProgress! = 0 && videoProgress! = duration) {// jump to the previous progress, continue to play
                    lVideoView.startLive (itemBean.video_url);
                    lVideoView.setSeekTo (videoProgress);
                } else {// play from scratch
                    lVideoView.startLive (itemBean.video_url);
                }
//}
            }
        }
    }
```
From the above code we can see that you are going to play the video in the item container and add the player to the container. If there is a play before, take the progress of the play and jump to the previous progress Play.
- When you play the next video while slipping, stop playing the previous video and remove the player from the item. Write down the progress of the current item, add it to the next item, and play the video.

```
   private void stopPlayer (int position) {
        VideoFeedHolder childViewHolder = (VideoFeedHolder) rl_video.findViewHolderForAdapterPosition (position);
        if (childViewHolder! = null) {
            if (lVideoView.isPlayer ()) {// If you are playing, stop and record the progress of the play, otherwise do not call this method
                lVideoView.stopVideoPlay ();
                TabFragMainBeanItemBean itemBean = itemBeens.get (position);
                itemBean.videoProgress = currentPosition;
                itemBean.mDuration = mDuration;
                itemBeens.set (position, itemBean);
            }
            childViewHolder.visMasked (); // display the mask
            View itemView = childViewHolder.itemView;
            FrameLayout frameLayout = (FrameLayout) itemView.findViewById (R.id.ll_video);
            frameLayout.removeAllViews ();
            childViewHolder.unRegisterVideoPlayerListener (); / / Note that we need to lift the last item of the monitor, or will register multiple monitoring
        }
    }
```

- vertical and horizontal screen when the switch, in accordance with the above realization of ideas, see the following code
```
    @Override
    public void onConfigurationChanged (Configuration newConfig) {
        super.onConfigurationChanged (newConfig);
        lVideoView.onConfigurationChanged (newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {// vertical screen
            orientation = false;
            full_screen.setVisibility (View.VISIBLE);
            addPlayer (itemPosition);
            int mShowFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            full_screen.setSystemUiVisibility (mShowFlags);
        } else {// horizontal screen
            orientation = true;
            rl_video_feed.setVisibility (View.GONE);
            ViewGroup viewGroup = (ViewGroup) lVideoView.getParent ();
            if (viewGroup == null)
                return;
            viewGroup.removeAllViews ();
            full_screen.addView (lVideoView);
            full_screen.setVisibility (View.VISIBLE);
            int mHideFlags =
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            full_screen.setSystemUiVisibility (mHideFlags);
        }
    }

  / **
     * Add player
     *
     * @param position
     * /
    private void addPlayer (int position) {
        VideoFeedHolder childViewHolder = (VideoFeedHolder) rl_video.findViewHolderForAdapterPosition (position);
        if (childViewHolder! = null) {
            View itemView = childViewHolder.itemView;
            FrameLayout frameLayout = (FrameLayout) itemView.findViewById (R.id.ll_video);
            frameLayout.removeAllViews ();
            ViewGroup last = (ViewGroup) lVideoView.getParent (); // find the parent class of videoitemview, and then remove
            if (last! = null && last.getChildCount ()> 0) {
                last.removeAllViews ();
            }
            frameLayout.addView (lVideoView);
        }
    }
```
In the Activity set aside a player placed in the width and height are match_parent ViewGroup, the size of the switch is to add the player to the original small container and added to the full-screen ViewGroup switch back and forth, the player's listener is not too much intervention The
> Note to change the size of the player view.
```
 / **
     * Size screen to switch the player's handling
     *
     * @param newConfig
     * /
    public void onConfigurationChanged (Configuration newConfig) {
        ViewGroup.LayoutParams layoutParams = fraVideoContainer.getLayoutParams ();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {// vertical screen
            layoutParams.height = (int) getResources (). getDimension (R.dimen.live_video_height);
            fraVideoContainer.setLayoutParams (layoutParams);
        } else {// horizontal screen
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            fraVideoContainer.setLayoutParams (layoutParams);
        }
    }
```
- Activity / Fragment the life cycle of the deal, the whole of us on a player, the life cycle can be a very good deal with this player
```
/ **
     * Activity is paused when it is not in the foreground
     * /
    @Override
    protected void onPause () {
        super.onPause ();
        if (! isThrumePause) {/ / If not manually pause, Activity into the background automatically suspended
            lVideoView.onPause ();
        }
    }

    / **
     * Activity re - enter the foreground play logic
     * /
    @Override
    protected void onResume () {
        super.onResume ();
        Log.e ("linksu",
                "onResume (VideoFeedDetailAct.java:558) isThrumePause" + isThrumePause);
        if (! isThrumePause) {// is not manually suspended and enters the foreground from the background
            lVideoView.currentPlayer ();
        } else {// Before entering the background is the state of the pause, again into the state or pause
            lVideoView.startThumb ();
        }
    }

    / **
     * Activity stops when you exit
     * /
    @Override
    public void finish () {
        super.finish ();
        lVideoView.stopVideoPlay ();
    }

    / **
     * Destroy the player when it is destroyed
     * /
    @Override
    protected void onDestroy () {
        super.onDestroy ();
        lVideoView.removeAllViews ();
    }
```
- the status of the player can be monitored in the Activity / Fragment to deal with, to deal with more convenient
```
@Override
    public void onVideoPrepared () {// ready to play
    }

    @Override
    public void onVideoCompletion () {// play is complete
        if (itemPosition! = lastItemPosition) {/ / If the play is not the last one, the playback is complete automatically play the next
            VideoFeedHolder childViewHolder = (VideoFeedHolder) rl_video.findViewHolderForAdapterPosition (itemPosition);
            if (childViewHolder! = null) {
                / / Playback is completed before the playback progress empty
                TabFragMainBeanItemBean itemBean = itemBeens.get (itemPosition);
                itemBean.videoProgress = 0;
                itemBean.mDuration = 0;
                itemBeens.set (itemPosition, itemBean);
                // Remove the player
                childViewHolder.visMasked ();
                View itemView = childViewHolder.itemView;
                FrameLayout frameLayout = (FrameLayout) itemView.findViewById (R.id.ll_video);
                frameLayout.removeAllViews ();
                childViewHolder.unRegisterVideoPlayerListener (); / / Note that we need to lift the last item of the monitor, or will register multiple monitoring
            }
            itemPosition = itemPosition + 1;
            playerPosition = itemPosition;
            ((LinearLayoutManager) rl_video.getLayoutManager ()). ScrollToPositionWithOffset (playerPosition, 20);
            aoutPlayVideo (rl_video);
        }
    }

    @Override
    public void onVideoError (int i, String error) {
    }

    @Override
public void onBufferingUpdate () {

    }

    @Override
    public void onVideoStopped () {// Stop video playback when recording video playback location

    }

    @Override
    public void onVideoPause () {// Pause video playback

    }

    @Override
    public void onVideoThumbPause () {// pause video playback manually
        isThrumePause = true;
    }

    @Override
    public void onVideoThumbStart () {// Manually start video playback
        isThrumePause = false;
    }

    @Override
    public void onVideoPlayingPro (long currentPosition, long mDuration, int mPlayStatus) {// Get play progress
        this.currentPosition = currentPosition;
        this.mDuration = mDuration;
        if (itemPosition! = lastItemPosition) {/ / If the play is not the last one, pop-up play the next prompt
            float percent = (float) ((double) currentPosition / (double) mDuration);
            DecimalFormat fnum = new DecimalFormat ("## 0.0");
            float c_percent = 0;
            c_percent = Float.parseFloat (fnum.format (percent));
            if (0.8 <= c_percent) {
                videoTips ();
            } else {
                missVideoTips ();
            }
        }
    }
```

> So we completed the entire optimization process, in fact, is a list of the map, the dynamic to add the player, so that processing is not only accounted for very little memory, and no complicated logic.

! [Finally, please do not polite, enjoy the issue or pull request come over! (https://github.com/susussa/VideoFeed)

## License

```
  MIT license
   
  Copyright (c) 2017 Sufu deer
   
  Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:
   
  The above copyright notice and this permission notice shall be included in all
   copies or gt copies of the Software.
   
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
```
