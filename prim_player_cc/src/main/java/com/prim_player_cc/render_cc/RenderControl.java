package com.prim_player_cc.render_cc;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/30 - 下午2:56
 */
public class RenderControl implements IRenderControl {

    private WeakReference<Context> mContext;

    private FrameLayout frameLayout;

    public RenderControl(Context context) {
        mContext = new WeakReference<>(context);
        frameLayout = new FrameLayout(mContext.get());
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public ViewGroup getRenderRootView() {
        return frameLayout;
    }

    @Override
    public void addRenderView(View view) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        frameLayout.addView(view, layoutParams);
    }

    @Override
    public void removeRenderView() {
        frameLayout.removeAllViews();
    }
}
