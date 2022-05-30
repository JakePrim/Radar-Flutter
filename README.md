# Abstergo
Android MVVM 架构基于JetPack框架快速搭建高质量、高效率的APP应用,作为应用的基础组件化脚手架.

宿主层 -> 业务层 -> 核心基础业务 + 公共服务层 -> 基础库层

基础库层：

- abstergo-core  : 是核心基础库，提供了MVVM Base基类和组件化架构、路由、图片加载器、权限、依赖注入、日志等
- abstergo-http  : 网络请求库，底层Retrofit+协程的网络请求封装：统一异常处理、返回结果统一处理
- abstergo-cache : 缓存库：数据库+文件缓存
- abstergo-utils : 常用工具库，推荐使用：https://github.com/Blankj/AndroidUtilCode 工具非常齐全
- abstergo-ui    : UI库，包括常用的UI和自定义View、屏幕适配
- abstergo-player: 媒体库，视频、音频、直播的封装处理
- abstergo-share : 分享库，统一处理分享逻辑
- abstergo-pay   : 支付基础库，封装支付的逻辑
- abstergo-web   : WebView的封装处理,简化WebView的操作，支持多进程、复用池等功能

基础Module 业务层

- module-common  : 提供所有Module的核心基础业务,依赖了api module-service
- module-service : 提供所有Module的公共服务接口依赖库，这里实现module的bean和service 依赖了api abstergo-core

其他的lib库，根据使用情况进行依赖，业务Module必须要依赖module-common -> module-service -> abstergo-core