package com.prim_player_cc.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 参数配置
 * @time 2018/10/15 - 下午3:31
 */
public class AVOptions {
    public static final int MEDIA_CODEC_SW_DECODE = 0;
    public static final int MEDIA_CODEC_HW_DECODE = 1;
    public static final int MEDIA_CODEC_AUTO = 2;
    public static final String KEY_BUFFER_TIME = "rtmp_buffer";
    public static final String KEY_START_ON_PREPARED = "start-on-prepared";


    public static final String KEY_PREPARE_TIMEOUT = "timeout";
    public static final String KEY_GET_AV_FRAME_TIMEOUT = "get-av-frame-timeout";
    public static final String KEY_LIVE_STREAMING = "live-streaming";
    public static final String KEY_MEDIACODEC = "mediacodec";
    public static final String KEY_DELAY_OPTIMIZATION = "delay-optimization";
    public static final String KEY_CACHE_BUFFER_DURATION = "cache-buffer-duration";
    public static final String KEY_MAX_CACHE_BUFFER_DURATION = "max-cache-buffer-duration";
    public static final String KEY_RECONNECT = "reconnect";
    public static final String KEY_PROBESIZE = "probesize";
    public static final String KEY_AUDIO_DATA_CB_ENABLE = "audio-data-cb-enable";
    public static final String KEY_VIDEO_DATA_CB_ENABLE = "video-data-cb-enable";
    public static final String KEY_AUDIO_RENDER_MSG = "audio-render-msg-cb";
    public static final String KEY_VIDEO_RENDER_MSG = "video-render-msg-cb";
    public static final String KEY_VIDEO_DISPLAY_DISABLE = "nodisp";
    public static final String KEY_RTMP_LIVE = "rtmp_live";
    public static final String KEY_ANALYZE_MAX_DURATION = "analyzemaxduration";
    public static final String KET_ANALYZE_DURATION = "analyzeduration";
    public static final String KEY_MAX_BUFFER_SIZE = "max-buffer-size";
    public static final String KEY_MEDIA_CODEC_AUTO_ROTATE = "mediacodec-auto-rotate";
    public static final String KEY_MEDIA_CODEC_HANDLE_RESOLUTION_CHANGE = "mediacodec-handle-resolution-change";
    public static final String KEY_OPEN_SLES = "opensles";
    public static final String KET_FRAMEDROP = "framedrop";
    public static final String KEY_SKIP_LOOP_FILTER = "skip_loop_filter";
    public static final String KEY_FLUSH_PACKETS = "flush_packets";
    public static final String KEY_PACKET_BUFFERING = "packet-buffering";
    public static final String KEY_HTTP_DETECT_RANGE_SUPPORT = "http-detect-range-support";
    public static final String KEY_OVERLAY_FORMAT = "overlay-format";
    public static final String KEY_DNS_CACHE_CLEAR = "dns_cache_clear";
    /**
     * @deprecated
     */
    public static final String KEY_FFLAGS = "fflags";
    /**
     * @deprecated
     */
    public static final String VALUE_FFLAGS_NOBUFFER = "nobuffer";

    private Map<String, Object> mOptions = new HashMap<>(20);

    public AVOptions() {
    }

    public final boolean containsKey(String var1) {
        return this.mOptions.containsKey(var1);
    }

    public final int getInteger(String var1) {
        return (Integer) this.mOptions.get(var1);
    }

    public final int getInteger(String var1, int var2) {
        try {
            return this.getInteger(var1);
        } catch (NullPointerException var4) {
            ;
        } catch (ClassCastException var5) {
            ;
        }

        return var2;
    }

    public final long getLong(String var1) {
        return (Long) this.mOptions.get(var1);
    }

    public final float getFloat(String var1) {
        return (Float) this.mOptions.get(var1);
    }

    public final String getString(String var1) {
        return (String) this.mOptions.get(var1);
    }

    public final void setInteger(String var1, int var2) {
        this.mOptions.put(var1, var2);
    }

    public final void setLong(String var1, long var2) {
        this.mOptions.put(var1, var2);
    }

    public final void setFloat(String var1, float var2) {
        this.mOptions.put(var1, var2);
    }

    public final void setString(String var1, String var2) {
        this.mOptions.put(var1, var2);
    }

    public Map<String, Object> getMap() {
        return this.mOptions;
    }
}
