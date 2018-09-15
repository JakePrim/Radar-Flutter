package com.prim_player_cc.render_cc;

import android.view.SurfaceHolder;
import android.view.View;

import java.util.LinkedHashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 下午6:12
 */
public interface IRender {

    public int SURFACE_VIEW = 0;

    public int TEXTURE_VIEW = 1;

    public int CUSTOM_VIEW = 2;

    View getRenderView();

    void updateRenderSize(int width, int height);

    void setRenderCallback(IRenderCallback renderCallback);

    interface IRenderCallback {
        public void surfaceCreated(SurfaceHolder holder);

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height);

        public void surfaceDestroyed(SurfaceHolder holder);
    }
}
