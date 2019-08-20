package com.prim_player_cc.config;

import android.app.Application;
import android.util.SparseArray;

import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.decoder_cc.DecoderWrapper;
import com.prim_player_cc.loader.ImageEngine;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.decoder_cc.DefaultDecoder;

/**
 * @author prim
 * @version 1.0.0
 * @desc prim player cc config ,recommend application build config
 * 需要在application中初始化播放器组件配置
 * @time 2018/7/24 - 下午2:14
 */
public class PrimPlayerConfig {

    private static final String TAG = "PrimPlayerConfig";

    /**
     * 默认的解码器ID 使用的解码器是系统自带的
     */
    private static int DEFAULT_DECODER_ID = -1;

    /**
     * 记录当前使用解码器组件
     */
    public static int usedDecoderId = DEFAULT_DECODER_ID;

    /**
     * 存储播放器包装类的仓库
     */
    private static SparseArray<DecoderWrapper> mDecoders;

    static {
        mDecoders = new SparseArray<>();
        DecoderWrapper wrapper = new DecoderWrapper(DEFAULT_DECODER_ID, DefaultDecoder.class, "default decoder");
        mDecoders.put(DEFAULT_DECODER_ID, wrapper);
    }

    public static Builder configBuild() {
        return new Builder();
    }

    /**
     * get decoder
     * 根据ID获取解码器的包装类{@link DecoderWrapper}
     *
     * @return decoder ID
     */
    public static DecoderWrapper getDecoder(int decoderId) {
        return mDecoders.get(decoderId);
    }

    /**
     * get used decoder
     * 获取正在使用的解码器
     *
     * @return {@link DecoderWrapper}
     */
    public static DecoderWrapper getUsedDecoder() {
        return getDecoder(usedDecoderId);
    }

    /**
     * set used decoder id
     * 设置要使用的解码器ID
     *
     * @param id decoder id
     */
    public static void setUseDecoderId(int id) {
        usedDecoderId = id;
    }

    /**
     * 获取当前使用的解码器ID
     *
     * @return decoder id
     */
    public static int getUseDecoderId() {
        return usedDecoderId;
    }

    /**
     * 检查播放器ID是否存在仓库中
     *
     * @param id decoder id
     * @return boolean
     */
    public static boolean checkDecoderId(int id) {
        DecoderWrapper wrapper = getDecoder(id);
        return wrapper != null;
    }

    public static class Builder {

        /**
         * 添加解码器
         *
         * @param wrapper 解码器包装类
         * @return Builder
         */
        public Builder addDecoder(DecoderWrapper wrapper) {
            if (wrapper == null) {
                throw new NullPointerException("DecoderWrapper must not to be null");
            }
            mDecoders.put(wrapper.getPlayerId(), wrapper);
            return this;
        }

        /**
         * 设置使用的解码器ID
         *
         * @param id decoder id
         * @return Builder
         */
        public Builder setUseDecoderId(int id) {
            usedDecoderId = id;
            return this;
        }

        /**
         * 设置是否开启日志信息
         *
         * @param enable true 开启 false 关闭
         * @return Builder
         */
        public Builder setLogEnable(boolean enable) {
            PrimLog.LOG_OPEN = enable;
            return this;
        }

        /**
         * 设置图片加载引擎
         * @param imageLoader impl {@link ImageEngine}
         * @return Builder
         */
        public Builder setImageLoader(ImageEngine imageLoader) {
            PrimPlayerCC.getInstance().setImageLoader(imageLoader);
            return this;
        }

        /**
         * 调用此方法，初始化才完成
         *
         * @param application Application
         */
        public void build(Application application) {
            PrimLog.d(TAG, "build success, usedDecoderId:" + usedDecoderId + " | 日志是否开启：" + PrimLog.LOG_OPEN);
            ApplicationAttach.attach(application);
        }
    }
}
