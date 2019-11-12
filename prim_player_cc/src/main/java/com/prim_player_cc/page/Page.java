package com.prim_player_cc.page;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.WindowCallbackWrapper;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

/**
 * @author prim
 * @version 1.0.0
 * @desc 作为一个单独的页面 作为一个单独的类似Activity 的页面
 * 1. 监听Activity的生命周期onCreate->onStart->onResume->onShow->onPause->onStop→onDestroy→onDismiss
 * 2. 拦截所有事件
 * @time 2018/11/6 - 5:05 PM
 */
public abstract class Page extends ContextWrapper implements LifeListener {

    private static final String TAG = "Page-Video";

    private LifeListenerFragment fragment;

    protected Activity activity;

    protected View mRootView;

    public Page(Activity base) {
        super(base);
        this.activity = base;
        //1 监听Activity的生命周期
        listenerLife(activity);
        //2 拦截事件自己处理
        Window.Callback wrapped = base.getWindow().getCallback();
        if (wrapped == null) {
            wrapped = base;
        }
        base.getWindow().setCallback(wrapperWindowCallback(wrapped));

    }

    private WindowCallbackWrapper wrapperWindowCallback(Window.Callback wrapped) {
        WindowCallbackWrapper windowCallbackWrapper = new WindowCallbackWrapper(wrapped) {
            @Override
            public boolean dispatchTouchEvent(MotionEvent event) {
                return Page.this.dispatchTouchEvent(event);
            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                return Page.this.dispatchKeyEvent(event);
            }
        };
        return windowCallbackWrapper;
    }

    protected boolean dispatchKeyEvent(KeyEvent event) {
        return mRootView.dispatchKeyEvent(event);
    }

    protected boolean dispatchTouchEvent(MotionEvent event) {
        return mRootView.dispatchTouchEvent(event);
    }

    protected void findViewById(int id) {

    }

    public boolean isShowing() {
        return false;
    }

    public void onBackPressed() {
        dismiss();
    }

    public void dismiss() {
        onDismiss();

    }

    public void show(Pair<Intent, ?> data) {
        onShow();


    }

    public void reset() {

    }

    public View getRootView() {
        return null;
    }

    /**
     * 监听Activity的生命周期，Page作出相应的变化
     *
     * @param activity
     */
    private void listenerLife(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragment = (LifeListenerFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new LifeListenerFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
        }
        fragment.addLifeListener(this);
    }

    /**
     * page显示
     */
    public void onShow() {

    }

    /**
     * page隐藏
     */
    public void onDismiss() {

    }
}
