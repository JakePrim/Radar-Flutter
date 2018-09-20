## Welcome PrimPlayerCC

### This What ?

> 这是一个为多媒体复杂业务姐姐方案框架，该框架采用组件化的思想。主要涉及到三个核心组件：解码器组件、视图组件、显示组件。使各个组件间的业务分离互不干扰。

### TODO

- 功能细节还有好多未完成

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

自由切换解码器组件

```
playerCCView = (DefaultPlayerCCView) findViewById(R.id.player_cc);



        playerCCView.switchDecoder(12);//切换解码器
```


高度定制的视图组件

```
        //使用自定义的视图组
        PrimPlayerCC.getCoverCCManager()
                .addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this))
                .addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(this))
                .addCover(DefaultCoverKey.DEFAULT_ERROR_COVER,new DefaultErrorCover(this))
                .addCover(DefaultCoverKey.DEFAULT_COMPLETE_COVER,new DefaultCompleteCover(this))
                .insertCoverGroup(playerCCView);

        //      playerCCView.usedDefaultCoverGroup();//使用默认的视图组

```

设置呈显视频的组件

```
        //设置呈现视频的RenderView 使用系统默认的view
        playerCCView.setRenderView(IRender.SURFACE_VIEW);

        //设置数据提供者

        //设置播放资源
        playerCCView.setDataSource(
                new PlayerSource("http://rmrbtest-image.peopleapp.com/upload/video/201809/153723951440062dcc54a0912f.mp4"));
```