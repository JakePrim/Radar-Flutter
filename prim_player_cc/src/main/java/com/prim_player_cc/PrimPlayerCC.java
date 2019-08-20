package com.prim_player_cc;

import android.os.Handler;
import android.os.Looper;

import com.prim_player_cc.config.PrimPlayerConfig;
import com.prim_player_cc.cover_cc.CoverCCManager;
import com.prim_player_cc.decoder_cc.DecoderWrapper;
import com.prim_player_cc.loader.ImageEngine;

/**
 * @author prim
 * @version 1.0.0
 * @desc prim player cc total entrance
 * 多媒体组件库的总入口
 * @time 2018/7/30 - 上午10:22
 */
public class PrimPlayerCC {

    private static PrimPlayerCC instance;

    private Handler handler;

    private ImageEngine imageLoader;

    //是否允许在数据流量下播放
    public static boolean ALLOW_NETDATA_PLAY = false;

    public static PrimPlayerCC getInstance() {
        if (instance == null) {
            synchronized (PrimPlayerCC.class) {
                if (instance == null) {
                    instance = new PrimPlayerCC();
                }
            }
        }
        return instance;
    }


    PrimPlayerCC() {
        handler = new Handler(Looper.getMainLooper());
    }

    //----------------------------- 初始化相关 API -----------------------------//

    public PrimPlayerConfig.Builder init() {
        return PrimPlayerConfig.configBuild();
    }

    /**
     * 设置是否显示日志信息
     *
     * @param enable true 显示 false 隐藏
     */
    public void setLogEnable(boolean enable) {
        PrimPlayerConfig.configBuild().setLogEnable(enable);
    }

    //----------------------------- 解码器相关 API ---------------------------//

    /**
     * 添加解码器
     * {@link PrimPlayerConfig#configBuild()#addDecoder(DecoderWrapper)}
     *
     * @param decoderWrapper 解码器包装类{@link DecoderWrapper}
     */
    public void addDecoder(DecoderWrapper decoderWrapper) {
        PrimPlayerConfig.configBuild().addDecoder(decoderWrapper);
    }


    /**
     * 设置要使用的解码器
     *
     * @param decoderId 解码器ID
     */
    public void setUseDecoderId(int decoderId) {
        PrimPlayerConfig.setUseDecoderId(decoderId);
    }

    /**
     * 获取正在使用的解码器ID
     *
     * @return ID
     */
    public int getUseDecoderId() {
        return PrimPlayerConfig.getUseDecoderId();
    }

    /**
     * get decoder
     * 根据ID获取解码器
     *
     * @return decoder ID
     */
    public DecoderWrapper getDecoder(int decoderId) {
        return PrimPlayerConfig.getDecoder(decoderId);
    }

    /**
     * get used decoder
     * 获取正在使用的解码器
     *
     * @return {@link DecoderWrapper}
     */
    public DecoderWrapper getUsedDecoder() {
        return getDecoder(getUseDecoderId());
    }

    //--------------------------------- 视图组件相关 API 视图的热插拔 动态的添加和删除视图 --------------------------------//

    public static CoverCCManager getCoverCCManager() {
        return CoverCCManager.getInstance();
    }

    //--------------------------------- 图片加载引擎 ------------------------------------//

    /**
     * 设置图片加载器 用于加载缩率图
     *
     * @param imageLoader
     * @return
     */
    public PrimPlayerCC setImageLoader(ImageEngine imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public ImageEngine getImageLoader() {
        return imageLoader;
    }
}
