package com.prim_player_cc.transitions;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionPropagation;
import android.util.Pair;
import android.view.View;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/15 - 5:38 PM
 */
public class TransitionBuilder {
    private Transition transition;

    public TransitionBuilder(Transition transition) {
        this.transition = transition;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public TransitionBuilder duration(long duration) {
        transition.setDuration(duration);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public TransitionBuilder target(View view) {
        transition.addTarget(view);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TransitionBuilder target(Class clazz) {
        transition.addTarget(clazz);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TransitionBuilder target(String target) {
        transition.addTarget(target);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public TransitionBuilder target(int targetId) {
        transition.addTarget(targetId);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public TransitionBuilder delay(long delay) {
        transition.setStartDelay(delay);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TransitionBuilder pathMotion(PathMotion motion) {
        transition.setPathMotion(motion);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TransitionBuilder propagation(TransitionPropagation propagation) {
        transition.setPropagation(propagation);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TransitionBuilder pair(Pair<View, String> pair) {
        pair.first.setTransitionName(pair.second);
        transition.addTarget(pair.second);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public TransitionBuilder excludeTarget(final View view, final boolean exclude){
        transition.excludeTarget(view, exclude);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TransitionBuilder excludeTarget(final String targetName, final boolean exclude) {
        transition.excludeTarget(targetName, exclude);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TransitionBuilder link(final View from, final View to, final String transitionName) {
        from.setTransitionName(transitionName);
        to.setTransitionName(transitionName);
        transition.addTarget(transitionName);
        return this;
    }

    public Transition build() {
        return transition;
    }
}
