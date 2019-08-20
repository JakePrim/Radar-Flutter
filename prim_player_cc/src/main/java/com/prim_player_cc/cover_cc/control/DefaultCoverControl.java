package com.prim_player_cc.cover_cc.control;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.log.PrimLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认实现到视图组件控制器
 * @time 2018/7/26 - 下午5:17
 */
public class DefaultCoverControl extends BaseCoverControl {

    private static final String TAG = "DefaultCoverControl";

    private FrameLayout mLevelLowCoverLayout;

    private FrameLayout mLevelMiddleCoverLayout;

    private FrameLayout mLevelHeightCoverLayout;

    public DefaultCoverControl(Context context) {
        super(context);
        //低级别视图组件的 root view 添加到控制视图组件到root view
        mLevelLowCoverLayout = new FrameLayout(context);
        mLevelLowCoverLayout.setBackgroundColor(Color.TRANSPARENT);
        addLevelCoverView(mLevelLowCoverLayout, null);

        //中级别视图组件到 root view 添加到控制视图组件到root view
        mLevelMiddleCoverLayout = new FrameLayout(context);
        mLevelMiddleCoverLayout.setBackgroundColor(Color.TRANSPARENT);
        addLevelCoverView(mLevelMiddleCoverLayout, null);

        //高级别视图组件到 root view 添加到控制视图组件到root view
        mLevelHeightCoverLayout = new FrameLayout(context);
        mLevelHeightCoverLayout.setBackgroundColor(Color.TRANSPARENT);
        addLevelCoverView(mLevelHeightCoverLayout, null);
    }

    @Override
    protected void onCoverRemoveAll() {
        PrimLog.d(TAG, "cover remove all");
        mLevelLowCoverLayout.removeAllViews();
        mLevelHeightCoverLayout.removeAllViews();
        mLevelMiddleCoverLayout.removeAllViews();
    }

    @Override
    protected void onCoverAdd(BaseCover cover) {
        int coverLevel = cover.getCoverLevel();
        if (coverLevel > ICover.LEVEL_HEIGHT) {
            PrimLog.d(TAG, "coverView: " + cover.getCoverView() + " | 添加到LEVEL_HEIGHT：" + coverLevel);
            ViewParent parent = cover.getCoverView().getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewgroup = (ViewGroup) parent;
                viewgroup.removeView(cover.getCoverView());
            }
            mLevelHeightCoverLayout.addView(cover.getCoverView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else if (coverLevel > ICover.LEVEL_MIDDLE) {
            PrimLog.d(TAG, "coverView: " + cover.getCoverView() + " | 添加到LEVEL_MIDDLE：" + coverLevel);
            ViewParent parent = cover.getCoverView().getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewgroup = (ViewGroup) parent;
                viewgroup.removeView(cover.getCoverView());
            }
            mLevelMiddleCoverLayout.addView(cover.getCoverView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            PrimLog.d(TAG, "coverView: " + cover.getCoverView() + " | 添加到LEVEL_LOW：" + coverLevel);
            ViewParent parent = cover.getCoverView().getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewgroup = (ViewGroup) parent;
                viewgroup.removeView(cover.getCoverView());
            }
            mLevelLowCoverLayout.addView(cover.getCoverView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    protected void onCoverRemove(BaseCover cover) {
        PrimLog.d(TAG, "remove Cover:" + cover.getCoverView());
        mLevelLowCoverLayout.removeView(cover.getCoverView());
        mLevelMiddleCoverLayout.removeView(cover.getCoverView());
        mLevelHeightCoverLayout.removeView(cover.getCoverView());
    }

    @Override
    protected ViewGroup initCoverControl() {
        FrameLayout frameLayout = new FrameLayout(mContext.get());
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
        return frameLayout;
    }

    private void addLevelCoverView(ViewGroup viewGroup, ViewGroup.LayoutParams layoutParams) {
        if (getCoverRootView() != null) {
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            getCoverRootView().addView(viewGroup, layoutParams);
        }
    }
}
