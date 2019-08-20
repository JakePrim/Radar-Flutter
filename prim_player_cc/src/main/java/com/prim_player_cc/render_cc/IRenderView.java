package com.prim_player_cc.render_cc;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.decoder_cc.IDecoder;

import java.util.LinkedHashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 下午6:12
 */
public interface IRenderView {

    int SURFACE_VIEW = 0;

    int TEXTURE_VIEW = 1;

    int CUSTOM_VIEW = 2;

    int RENDER_NONE = -1;

    int ASSIST_VIEW = -2;

    int AR_ASPECT_FIT_PARENT = 0;//自适应屏幕

    int AR_ASPECT_FILL_PARENT = 1;//全屏截取中间

    int AR_ASPECT_WRAP_CONTENT = 2;//原始视频

    int AR_MATCH_PARENT = 3;//铺满屏幕

    int AR_16_9_FIT_PARENT = 4;//16:9

    int AR_4_3_FIT_PARENT = 5;//4:3

    void release();

    void postRenderView();

    View getRenderView();

    void updateRenderSize(int width, int height);

    void setAspectRatio(int aspectRatio);

    void setVideoRotation(int degree);

    void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen);

    boolean shouldWaitForResize();

    void setRenderCallback(@NonNull IRenderCallback renderCallback);

    void removeRenderCallback(@NonNull IRenderCallback renderCallback);

    Bitmap getShortcut();

    interface ISurfaceHolder {
        void bindToMediaPlayer(IDecoder decoder);

        @NonNull
        IRenderView getRenderView();

        @NonNull
        SurfaceHolder getSurfaceHolder();

        @NonNull
        Surface openSurface();

        @NonNull
        SurfaceTexture getSurfaceTexture();
    }

    interface IRenderCallback {
        void surfaceCreated(@NonNull ISurfaceHolder holder, int width, int height);

        void surfaceChanged(@NonNull ISurfaceHolder holder, int format, int width, int height);

        void surfaceDestroyed(@NonNull ISurfaceHolder holder);
    }
}
