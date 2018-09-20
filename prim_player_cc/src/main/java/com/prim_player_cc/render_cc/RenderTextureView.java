package com.prim_player_cc.render_cc;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.TextureView;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc 框架默认实现的TextureView
 * @time 2018/7/30 - 上午10:34
 */
public class RenderTextureView extends TextureView implements IRender {

    private WeakReference<IRenderCallback> weakRenderCallback;

    public RenderTextureView(Context context) {
        super(context);
        setSurfaceTextureListener(new SurfaceTextureListener());
    }

    @Override
    public View getRenderView() {
        return this;
    }

    @Override
    public void updateRenderSize(int width, int height) {
        if (width != 0 && height != 0) {//TODO

        }
    }

    @Override
    public void setRenderCallback(IRenderCallback renderCallback) {
        weakRenderCallback = new WeakReference<>(renderCallback);
    }

    class SurfaceTextureListener implements TextureView.SurfaceTextureListener {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    }
}
