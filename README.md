## Welcome PrimPlayerCC

## This What?

> 这是一个为多媒体复杂业务解决方案框架，该框架采用组件化的思想。主要涉及到四个核心组件：解码器组件、视图组件、显示组件、数据资源组件。
  使各个组件间的业务分离互不干扰。

## 实现的功能

- **数据资源组件**:数据提供者,可自定义数据，存储到数据池中
- **解码器组件**:可自定义解码器,支持动态切换解码器
- **视频显示组件**:可自定义显示组件
- **呈现视频组件**:允许自动呈现视频view,灵活切换
- 高度可定制的视图组件
- 统一事件分发，各个视图组件的业务完全隔离
- 支持视图组件的热插拔，可动态的添加移除视图组件
- 可定制视图组，添加视图的拦截规则
- 可定制化的事件分发机制
- 支持各种场景下的全屏切换无缝续播，丝毫不入侵任何代码
- 支持手势滑动
- 支持自动播放下一条
- 支持手动播放下一条/上一条，以及播放index位置的资源
- 支持动态切换解码器
- 支持动态切换呈现view(SurfaceView/TextureView)
- 支持调整视频显示比例
- 支持倍速播放
- 支持视频旋转

## 更新版本说明
### 1.0.0-beta (开发版本)

- 基础功能完毕

### 1.0.1-beta (开发版本-框架架构调整)

- 列表播放
- 完善视频基础功能


## 基础功能

#### 1. 初始化

```
        //视频播放器组件配置初始化
        PrimPlayerCC.getInstance().init()
                .setLogEnable(true)
                .addDecoder(new DecoderWrapper(11, DefaultDecoder.class, "default player 11"))
                .addDecoder(new DecoderWrapper(12, DefaultDecoder.class,"default player 12"))
                .setUseDecoderId(11)
                .build(this);
```

#### 2. 使用

##### 自由切换解码器组件

```
       playerCCView = (DefaultPlayerCCView) findViewById(R.id.player_cc);
       playerCCView.switchDecoder(12);//切换解码器
```


##### 高度可完全定制的视图组件

```
        //使用自定义的视图组
        PrimPlayerCC.getCoverCCManager()
                .addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this))
                .addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(this))
                .addCover(DefaultCoverKey.DEFAULT_ERROR_COVER,new DefaultErrorCover(this))
                .addCover(DefaultCoverKey.DEFAULT_COMPLETE_COVER,new DefaultCompleteCover(this))
                .insertCoverGroup(playerCCView);

        //使用框架中默认的视图组
        playerCCView.usedDefaultCoverGroup();

```

##### 设置视图组拦截器，可定义拦截规则

```
        //只添加视图等级 > 0 的视图
        playerCCView.setCoverGroupFilter(new ICoverGroup.OnCoverFilter() {
            @Override
            public boolean filter(ICover cover) {
                return cover.getCoverLevel() > 0;
            }
        });
```

##### 设置呈显视频的组件，允许自定义

```
        //设置呈现视频的RenderView
        //使用框架默认的surfaceView
        playerCCView.setRenderView(IRender.SURFACE_VIEW);
        /**
          * 使用自定的view,从解码器中返回 {@link IDecoder#getRenderView()}
          */
        playerCCView.setRenderView(IRender.CUSTOM_VIEW);

        /**
          * 使用框架默认的textureView
          */
        playerCCView.setRenderView(IRender.TEXTURE_VIEW);
```

##### 设置数据提供者，适用于播放资源列表，方便播放资源的切换 详情看Demo

```

 //设置数据提供者 多个数据资源
        List<TestVideoBean> testVideoBeanList = new ArrayList<>(100);
        for (int i = 0; i < 2; i++) {
            TestVideoBean testVideoBean = new TestVideoBean();
            testVideoBean.setId(10 + i);
            testVideoBean.setCount(i * 100);
            testVideoBean.setImgUrl("http:www.baidu");
            testVideoBean.setTitle("测试标题:" + i);
            testVideoBean.setUrl("http://rmrbtest-image.peopleapp.com/upload/video/201809/153723951440062dcc54a0912f.mp4");
            testVideoBeanList.add(testVideoBean);
        }

        final TestDataProvider testDataProvider = new TestDataProvider(testVideoBeanList);

        playerCCView.setDataProvider(testDataProvider);
```

##### 设置单个的播放资源，如果设置了数据提供者，默认使用数据提供者的数据

```
 //设置播放资源
        playerCCView.setDataSource(
                new PlayerSource("http://rmrbtest-image.peopleapp.com/upload/video/201809/153723951440062dcc54a0912f.mp4"));
```

##### 如果为资源列表，可设置自动播放下一条

```
        //播放完成后的等待时间，然后自动播放下一条
        playerCCView.autoNextPlayWaitTime(3000);
         //允许自动播放下一条
        playerCCView.autoPlayNext(true);
        //支持播放资源列表中的某一个资源
        playerCCView.selectDataPlay(position);
```

##### 手势控制

```
//开启/关闭手势
playerCCView.setGesture(isChecked);
//开启/关闭滑动手势
playerCCView.setScrollGesture(isChecked);
```

##### 手动调用播放上一条或下一条

```
 playerCCView.playerForward();
 playerCCView.playerNext();

 //MedaiPlayer
 playerNext()
 playerNext()
```

##### 调整视频显示比例
```
int AR_ASPECT_FIT_PARENT = 0;//自适应屏幕
int AR_ASPECT_FILL_PARENT = 1;//全屏截取中间
int AR_ASPECT_WRAP_CONTENT = 2;//原始视频
int AR_MATCH_PARENT = 3;//铺满屏幕
int AR_16_9_FIT_PARENT = 4;//16:9
int AR_4_3_FIT_PARENT = 5;//4:3
playerCCView.setDiaplayAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
```
#### 设置倍速播放

```
playerCCView.setSpeed(0.5f);
```

#### 调整视频角度
```
playerCCView.setVideoRotation(90);
```

### 事件分发码详解

#### PlayerEventCode
```
    /**
     * 当给解码器设置播放地址时，分发的事件码
     */
    public static final int PRIM_PLAYER_EVENT_DATA_SOURCE = -1010;

    /**
     * 当解码器处于准备中，分发的事件码
     */
    public static final int PRIM_PLAYER_EVENT_PREPARING = -2010;

    /**
     * 当解码器准备完毕，分发的事件码
     */
    public static final int PRIM_PLAYER_EVENT_PREPARED = -1011;

    /**
     * 缓冲结束的事件码
     */
    public static final int PRIM_PLAYER_EVENT_BUFFERING_END = -3000;

    /**
     * 开始缓冲的事件码
     */
    public static final int PRIM_PLAYER_EVENT_BUFFERING_START = -3001;

    /**
     * 视频角度改变
     */
    public static final int PRIM_PLAYER_EVENT_ROTATION_CHANGED = -3002;

    /**
     * 视频渲染完成，加载第一贞
     */
    public static final int PRIM_PLAYER_EVENT_PANDERING_START = -3003;

    /**
     * 视频大小改变监听
     */
    public static final int PRIM_PLAYER_EVENT_VIDEO_SIZE_CHANGE = -1021;

    /**
     * when decoder info listener
     */
    public static final int PRIM_PLAYER_EVENT_INFO = -1022;

    /**
     * when decoder info seek complete listener
     */
    public static final int PRIM_PLAYER_SEEK_COMPLETE = -1023;

    /**
     * when decoder timed text listener
     */
    public static final int PRIM_PLAYER_TIMED_TEXT = -1024;

    /**
     * when decoder completion listener
     */
    public static final int PRIM_PLAYER_EVENT_COMPLETION = -1019;

    /**
     * when you call {@link IDecoder#start()}
     */
    public static final int PRIM_PLAYER_EVENT_START = -1012;

    /**
     * when you call {@link IDecoder#pause()}
     */
    public static final int PRIM_PLAYER_EVENT_PAUSE = -1013;

    /**
     * when you call {@link IDecoder#resume()}
     */
    public static final int PRIM_PLAYER_EVENT_RESUME = -1014;

    /**
     * when you call {@link IDecoder#stop()}
     */
    public static final int PRIM_PLAYER_EVENT_STOP = -1015;

    /**
     * when you call {@link IDecoder#reset()}
     */
    public static final int PRIM_PLAYER_EVENT_RESET = -1016;

    /**
     * when you call {@link IDecoder#release()}
     */
    public static final int PRIM_PLAYER_EVENT_RELEASE = -1025;

    /**
     * when you call {@link com.prim_player_cc.decoder_cc.IDecoder#destroy()}
     */
    public static final int PRIM_PLAYER_EVENT_DESTROY = -1017;

    /**
     * on player timer progress update
     * {@link com.prim_player_cc.decoder_cc.helper.TimerUpdateHelper}
     */
    public static final int PRIM_PLAYER_EVENT_TIMER_UPDATE = -1018;

    /**
     * when player status change {@link com.prim_player_cc.status.Status}
     */
    public static final int PRIM_PLAYER_EVENT_STATUS_CHANGE = -1020;

    /**
     * when player advert prompt event
     */
    public static final int PRIM_PLAYER_EVENT_ADVERT_PROMPT = -2000;

    /**
     * when net/wifi --> net player prompt event
     */
    public static final int PRIM_PLAYER_EVENT_NET_DATA_PROMPT = -2001;

    /**
     * when net --> wifi player prompt event
     */
    public static final int PRIM_PLAYER_EVENT_WIFI_PROMPT = -2004;

    /**
     * when player completion auto player next
     */
    public static final int PRIM_PLAYER_EVENT_AUTO_PLAY_NEXT = -2002;

    /**
     * when player buffering update listener
     */
    public static final int PRIM_PLAYER_EVENT_BUFFER_UPDATE = -2003;

    /**
     * when {@link com.prim_player_cc.view.BasePlayerCCView} full --> vertical
     */
    public static final int PRIM_PLAYER_EVENT_FULL_VERTICAL = -2004;

    /**
     * when {@link com.prim_player_cc.view.BasePlayerCCView} vertical --> full
     */
    public static final int PRIM_PLAYER_EVENT_VERTICAL_FULL = -2005;

    /**
     * when event more info
     */
    public static final int PRIM_PLAYER_EVENT_MORE_INFO = -2006;
```



## 框架设计思想

![无标题绘图.png](https://upload-images.jianshu.io/upload_images/2005932-1a76a26bd023ff48.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)