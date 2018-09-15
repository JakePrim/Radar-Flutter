package com.prim_player_cc.render_cc;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/30 - 上午10:33
 */
public class RenderSurfaceView extends SurfaceView implements IRender {

    private WeakReference<IRenderCallback> weakRenderCallback;

    public RenderSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(new SurfaceHolderCallback());
    }

    @Override
    public View getRenderView() {
        return this;
    }

    @Override
    public void updateRenderSize(int width, int height) {
        if (width != 0 && height != 0) {
            getHolder().setFixedSize(width, height);
            requestLayout();
        }
    }

    @Override
    public void setRenderCallback(IRenderCallback renderCallback) {
        weakRenderCallback = new WeakReference<>(renderCallback);
    }

    class SurfaceHolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (weakRenderCallback != null && weakRenderCallback.get() != null) {
                weakRenderCallback.get().surfaceCreated(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (weakRenderCallback != null && weakRenderCallback.get() != null) {
                weakRenderCallback.get().surfaceChanged(holder, format, width, height);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (weakRenderCallback != null && weakRenderCallback.get() != null) {
                weakRenderCallback.get().surfaceDestroyed(holder);
            }
        }
    }
}
