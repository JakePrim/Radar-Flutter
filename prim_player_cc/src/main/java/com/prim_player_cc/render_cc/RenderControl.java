package com.prim_player_cc.render_cc;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc 渲染view的控制器
 * @time 2018/7/30 - 下午2:56
 */
public class RenderControl implements IRenderControl {

    private WeakReference<Context> mContext;

    private FrameLayout frameLayout;

    public RenderControl(Context context) {
        mContext = new WeakReference<>(context);
        frameLayout = new FrameLayout(mContext.get());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public ViewGroup getRenderRootView() {
        return frameLayout;
    }

    @Override
    public void addRenderView(IRenderView view) {
        if (view == null) {
            return;
        }
        View renderView = view.getRenderView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        renderView.setLayoutParams(layoutParams);
        frameLayout.addView(renderView, layoutParams);
    }

    @Override
    public void removeRenderView() {
        frameLayout.removeAllViews();
    }
}
