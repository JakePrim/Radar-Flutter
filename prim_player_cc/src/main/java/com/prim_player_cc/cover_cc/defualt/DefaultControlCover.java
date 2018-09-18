package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.cover_cc.listener.OnCoverGestureListener;
import com.prim_player_cc.state.State;

import java.util.ArrayList;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认控制器视图
 * @time 2018/7/27 - 下午3:04
 */
public class DefaultControlCover extends BaseCover implements OnCoverGestureListener{
    public DefaultControlCover(Context context) {
        super(context);
        setCoverLevelHeight(5);
        coverVisibility(View.VISIBLE);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_control_layout, null);
    }

    @Override
    public void onPlayEvent(int state, Bundle bundle) {
        switch (state) {
            case State.STATE_PREPARED:
                coverVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dX, float dY) {
        return false;
    }
}
