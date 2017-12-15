package com.linksu.videofeed.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/8 0008
 * 描    述：控制recyclerview 的抛掷滑动速度
 * 修订历史：
 * ================================================
 */
public class FlingRecyclerView extends RecyclerView {
    public FlingRecyclerView(Context context) {
        super(context);
    }

    public FlingRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityY *= 0.5;
        return super.fling(velocityX, velocityY);
    }

    private boolean isIntercept = false;

    public void setIntercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (isIntercept) {
            return true;
        } else {
            return super.onInterceptTouchEvent(e);
        }
    }
}
