package com.prim_player_cc.source_cc;

/**
 * @author prim
 * @version 1.0.0
 * @desc 数据提供者，因为每个项目对Java bean 都是不一样的
 * 数据提供者的意义就是在于解决这个问题，将Java bean的数据 于播放相关的
 * 在{@link #startBindSourceData} 方法中去做相应的处理。
 * 数据提供者处理单个数据或多个数据，数据提供者与数据池相连接，并且操作数据池，数据提供者负责将数据加入数据池和从数据池中取出数据。
 * @time 2018/10/19 - 下午6:19
 */
public interface IDataProvider<T> {

    /**
     * 获取原始数据
     *
     * @return 泛型 T
     */
    T getOriginalData();

    /**
     * 绑定资源数据
     */
    void startBindSourceData();

    /**
     * 取消绑定数据
     *
     * @return true 表示取消成功 false 表示取消失败
     */
    boolean cancleBind();

    /**
     * 销毁数据提供者
     */
    void destory();

    /**
     * 数据提供者绑定资源数据监听
     *
     * @param onDateProviderListener
     */
    void setOnDateProviderListener(OnDataProviderListener onDateProviderListener);

    void removeOnDataProviderListener();

    /**
     * 绑定资源数据监听
     */
    interface OnDataProviderListener {
        void onBindDataStart();

        void onBindDataCancle();

        void onBindDataSuccess(PlayerSource source);

        void onBindDataError(int code);

        void onBindDataFinish();
    }
}
