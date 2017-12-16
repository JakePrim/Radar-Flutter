package com.example.adsorber_manager.adosorber;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.linksu.video_manager_library.ui.LVideoView;

/**
 * Created by suful on 2017/12/16.
 * this is FloatView in the view top
 */

public class FloatView extends FrameLayout {
    public FloatView(@NonNull Context context) {
        this(context, null);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //播放器容器
    private LVideoView mVideoPlayView;
    //控制器容器
    private FrameLayout mControllerView;

    private void init() {
        mVideoPlayView = new LVideoView(getContext());
        mControllerView = new FrameLayout(getContext());

        FrameLayout videoRoot = new FrameLayout(getContext());
        videoRoot.addView(mVideoPlayView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        videoRoot.addView(mControllerView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        FrameLayout rootLayout = new FrameLayout(getContext());
        rootLayout.addView(videoRoot, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //NOTE: root layout'heihgt must be WRAP_CONTENT
        addView(rootLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    /**
     * get mVideoPlayView getParent --> videoRoot
     *
     * @return
     */
    public View getVideoRootView() {
        return (View) mVideoPlayView.getParent();
    }

    public LVideoView getVideoPlayView() {
        return mVideoPlayView;
    }

    public View getControllerView() {
        return mControllerView;
    }
}
