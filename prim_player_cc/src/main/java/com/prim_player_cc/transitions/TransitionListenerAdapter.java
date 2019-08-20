package com.prim_player_cc.transitions;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;

/**
 * @author prim
 * @version 1.0.0
 * @desc Transition 监听
 * @time 2018/11/15 - 5:36 PM
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class TransitionListenerAdapter implements Transition.TransitionListener {
    @Override
    public void onTransitionStart(Transition transition) {

    }

    @Override
    public void onTransitionEnd(Transition transition) {

    }

    @Override
    public void onTransitionCancel(Transition transition) {

    }

    @Override
    public void onTransitionPause(Transition transition) {

    }

    @Override
    public void onTransitionResume(Transition transition) {

    }
}
