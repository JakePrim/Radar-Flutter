package com.linksu.videofeed.demo.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/16 - 3:15 PM
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ShowDetailsTransition extends Transition {

    private static String TOP = "top";

    private static String HEIGHT = "height";

    private static final String TAG = "ShowDetailsTransition";

    private long mPositionDuration = 500;
    private long mSizeDuration = 500;

    private TimeInterpolator mPositionInterpolator;
    private TimeInterpolator mSizeInterpolator;

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Rect rect = new Rect();
        view.getHitRect(rect);
        transitionValues.values.put(TOP, rect.top);
        transitionValues.values.put(HEIGHT, view.getHeight());

        Log.e(TAG, "captureStartValues: top --> " + rect.top + " height --> " + view.getHeight());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(TOP, 0);
        transitionValues.values.put(HEIGHT, transitionValues.view.getHeight());

        Log.e(TAG, "captureStartValues: top --> 0" + " height --> " + transitionValues.view.getHeight());

    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

        final View endView = endValues.view;

        final int startTop = startValues == null ? 0 : (int) startValues.values.get(TOP);
        final int startHeight = startValues == null ? 0 : (int) startValues.values.get(HEIGHT);
        final int endTop = endValues == null ? 0 : (int) endValues.values.get(TOP);
        final int endHeight = endValues == null ? 0 : (int) endValues.values.get(HEIGHT);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewCompat.setTranslationY(endView, (Float) animation.getAnimatedValue());
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        return set;
    }
}
