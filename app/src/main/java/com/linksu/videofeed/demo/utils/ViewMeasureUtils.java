package com.linksu.videofeed.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：12/13 0013
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ViewMeasureUtils {

    /**
     * 获取view的高度
     *
     * @param itemView
     *
     * @return
     */
    public static int getViewHeight(final View itemView) {
        final int[] height = {0};
        itemView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        itemView.getViewTreeObserver().removeOnPreDrawListener(this);
                        height[0] = itemView.getHeight();
                        return true;
                    }
                });

        return height[0];
    }

    public static int getHeight(View itemView) {
        return itemView.getHeight();
    }

    /**
     * 获取view在屏幕的位置 （注意这个算法加上了顶部的状态栏的高度）
     *
     * @param itemView
     *
     * @return [0] x  [1] y
     */
    public static int[] getViewLocation(View itemView) {
        int[] location = new int[2];
        itemView.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获取屏幕的宽和高
     *
     * @param context
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity context) {
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }
}
