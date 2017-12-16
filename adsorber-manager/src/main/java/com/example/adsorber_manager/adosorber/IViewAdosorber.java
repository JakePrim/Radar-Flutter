package com.example.adsorber_manager.adosorber;

import android.view.View;

/**
 * Created by suful on 2017/12/16.
 * Helper class that bind a view rect area to another one,
 * And Adosorber origin location changed
 */

public interface IViewAdosorber {

    /**
     * Attach a follower View
     * to DecorView {@link android.view.Window#ID_ANDROID_CONTENT}
     */
    IViewAdosorber attach();

    /**
     * detach a follower View
     * to DecorView {@link android.view.Window#ID_ANDROID_CONTENT}
     */
    IViewAdosorber detach();

    /**
     * just hide current{@link #getFloatView()} ,no delete it,and pause video if exit
     */
    IViewAdosorber hide();

    /**
     * just show current{@link #getFloatView()} ,no delete it,and start video if exit
     */
    IViewAdosorber show();

    /**
     * Normally call in Activity or Fragment destroyed.
     */
    IViewAdosorber destory();

    /**
     * Offer a tracker view for follower view to track
     * Need Detach the old one and attach new one
     *
     * @param adosorberView
     */
    IViewAdosorber adosorberView(View adosorberView);

    /**
     * Just simple bind current {@link #getFloatView()} to new trackerView
     * Only {@link FloatView} changed. don't re-attach to screen
     *
     * @param adosorberView new tracker view
     */
    IViewAdosorber changeAdosorberView(View adosorberView);

    IViewAdosorber into(View view);

    /**
     * Check if the follower view is attach into DecorView {@link android.view.Window#ID_ANDROID_CONTENT}
     */
    boolean isAttach();

    /**
     * The view that call scroll vertical and hold the tracker view
     * like {@link android.widget.ListView},{@link android.support.v7.widget.RecyclerView}
     */
    View getVerticalScrollView();

    /**
     * The view that position changed will be tracked
     * or need bind follower view on it.
     */
    View getAdosorberView();

    /**
     * The unique id of {@link #getAdosorberView()}, use this to find next  {@link #getAdosorberView()}
     * inside  {@link #getVerticalScrollView()}
     */
    int getAdosorberViewId();

    /**
     * The view need bind onto {@link #getAdosorberView()},and scroll follow it.
     */
    View getFollowerView();

    /**
     * The root view of {@link #getFollowerView()}, need add into
     * DecorView {@link android.view.Window#ID_ANDROID_CONTENT}
     */
    FloatView getFloatView();
}
