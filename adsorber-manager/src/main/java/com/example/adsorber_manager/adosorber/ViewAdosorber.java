package com.example.adsorber_manager.adosorber;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by suful on 2017/12/16.
 * view 吸附器
 */

public class ViewAdosorber implements IViewAdosorber, ViewTreeObserver.OnScrollChangedListener {

    //Activity for find decor view
    private Activity mContext;

    //View that need be adosorber scroll changed ,Normally inside ListView or RecyclerView
    protected View mAdosorberView;

    /**
     * The root layer of video container,Normally contains video view
     */
    protected View mFollowerView;
    /**
     * A view that can scroll vertical,Normally indicate ListView or RecyclerView
     */
    protected View mVerticalScrollView;
    /**
     * The whole root view that be added in decor,we can add View inside if needed
     */
    protected FloatView mFloatView;

    protected boolean mIsAttach;
    /**
     * Origin activity flag and system ui visibility
     */
    protected int mOriginActivityFlag;

    protected int mOriginSystemUIVisibility;
    /**
     * Origin location and width/height params about {@link #mFollowerView}
     */
    protected int mOriginX;

    protected int mOriginY;

    protected int mOriginWidth;

    protected int mOriginHeight;

    private static final String TAG = "ViewAdosorber";

    public ViewAdosorber(Activity context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null in ViewAdosorber!");
        }
        this.mContext = context;
    }

    @Override
    public IViewAdosorber attach() {
        //remove FloatView from decorView if exits before add new one
        for (int i = 0; i < getDecorView().getChildCount(); i++) {
            View childAt = getDecorView().getChildAt(i);
            if (childAt instanceof FloatView) {
                getDecorView().removeView(childAt);
            }
        }
        if (mFloatView == null) {//first
            mFloatView = new FloatView(mContext);
            mFollowerView = mFloatView.getVideoRootView();
            restoreActivityFlag();
        }
        if (mFloatView.getParent() == null) {
            getDecorView().addView(mFloatView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        mIsAttach = true;
        Log.e(TAG, "attach: ");
        return this;
    }

    protected void restoreActivityFlag() {
        Window window = mContext.getWindow();
        mOriginActivityFlag = window.getAttributes().flags;
        mOriginSystemUIVisibility = window.getDecorView().getSystemUiVisibility();
    }

    private ViewGroup getDecorView() {
        return (ViewGroup) mContext.getWindow().getDecorView();
    }

    @Override
    public IViewAdosorber detach() {
        if (mAdosorberView != null) {
            mAdosorberView.getViewTreeObserver().removeOnScrollChangedListener(this);
        }

        if (mFloatView != null) {
            getDecorView().removeView(mFloatView);
        }
        mIsAttach = false;
        return this;
    }

    @Override
    public IViewAdosorber hide() {
        if (mFloatView != null) {
            mFloatView.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    @Override
    public IViewAdosorber show() {
        if (mFloatView != null) {
            mFloatView.setVisibility(View.VISIBLE);
        }
        return this;
    }

    @Override
    public IViewAdosorber destory() {
        detach();
        return this;
    }

    @Override
    public IViewAdosorber adosorberView(View adosorberView) {
        if (mAdosorberView != null) {
            detach();
            attach();
        }
        this.mAdosorberView = adosorberView;
        int id = mAdosorberView.getId();

        rebindViewToTracker(mFollowerView, adosorberView);
        adosorberView.getViewTreeObserver().addOnScrollChangedListener(this);
        Log.e(TAG, "adosorberView: ");
        return this;
    }

    @Override
    public IViewAdosorber changeAdosorberView(View adosorberView) {
        this.mAdosorberView.getViewTreeObserver().removeOnScrollChangedListener(this);
        this.mAdosorberView = adosorberView;
        rebindViewToTracker(mFollowerView, mAdosorberView);
        adosorberView.getViewTreeObserver().addOnScrollChangedListener(this);
        return null;
    }

    private void rebindViewToTracker(View fromView, View toView) {
        int[] locTo = new int[2];
        toView.getLocationOnScreen(locTo);
        View parent = (View) fromView.getParent();
        ViewAnimator.putOn(parent).translation(locTo[0], locTo[1])
                .andPutOn(fromView).translation(0, 0);
        mOriginX = locTo[0];
        mOriginY = locTo[1];
        mOriginWidth = toView.getWidth();
        mOriginHeight = toView.getHeight();
        fromView.getLayoutParams().width = mOriginWidth;
        fromView.getLayoutParams().height = mOriginHeight;
        fromView.requestLayout();
        Log.e(TAG, "rebindViewToTracker:mOriginX--> "
                + mOriginX
                + " mOriginY-->" + mOriginY
                + " mOriginWidth-->" + mOriginWidth
                + " mOriginHeight-->" + mOriginHeight);
    }

    @Override
    public IViewAdosorber into(View view) {
        this.mVerticalScrollView = view;
        return this;
    }

    @Override
    public boolean isAttach() {
        return mIsAttach;
    }

    @Override
    public View getVerticalScrollView() {
        return mVerticalScrollView;
    }

    @Override
    public View getAdosorberView() {
        return mAdosorberView;
    }

    @Override
    public int getAdosorberViewId() {
        return 0;
    }

    @Override
    public View getFollowerView() {
        return mFollowerView;
    }

    @Override
    public FloatView getFloatView() {
        return mFloatView;
    }

    @Override
    public void onScrollChanged() {

        moveCurrentView(mVerticalScrollView, mFollowerView, mAdosorberView);
    }

    /**
     * 移动view
     *
     * @param scrollParent
     * @param fromView
     * @param toView
     */
    private void moveCurrentView(View scrollParent, View fromView, View toView) {
        int[] locScroll = new int[2];
        scrollParent.getLocationOnScreen(locScroll);

        View parent = ((View) fromView.getParent());

        int[] locTo = new int[2];
        toView.getLocationOnScreen(locTo);

        int[] locFrom = new int[2];
        fromView.getLocationOnScreen(locFrom);

        Rect rect = new Rect();
        toView.getLocalVisibleRect(rect);

//        Logger.w(TAG, "moveCurrentView: rect.top -> " + rect.top
//                + " rect.bottom -> " + rect.bottom
//                + " rect.left -> " + rect.left
//                + " rect.right -> " + rect.right
//                + " locTo[0] -> " + locTo[0]
//                + " locTo[1] -> " + locTo[1]
//                + " locFrom[0] -> " + locFrom[0]
//                + " locFrom[1] -> " + locFrom[1]);

        if (rect.top != 0 || rect.bottom != toView.getHeight()
                || rect.left != 0 || rect.right != toView.getWidth()) { //reach top,bottom,left,right
            //move self
            float moveX = 0;
            float moveY = 0;

            //top
            if (rect.top > 0 && rect.top != 0) {
                moveX = -rect.left;
                moveY = -rect.top;
                //let the parent sticky to top
                ViewAnimator.putOn(parent).translation(getVerticalScrollView().getPaddingLeft() + getVerticalScrollView().getLeft(),
                        locScroll[1] + getVerticalScrollView().getPaddingTop());
//                mCurrentEdge = TOP_EDGE;
            }

            //bottom
            if (rect.bottom > 0 && rect.bottom != toView.getHeight()) {
                moveY = toView.getHeight() - rect.bottom;
                moveX = toView.getWidth() - rect.right;
                //let the parent sticky to bottom
                ViewAnimator.putOn(parent).translation(getVerticalScrollView().getPaddingLeft() + getVerticalScrollView().getLeft(),
                        locScroll[1] + scrollParent.getHeight() - toView.getHeight() - getVerticalScrollView().getPaddingBottom());
//                mCurrentEdge = BOTTOM_EDGE;
            }

            //left
            if (rect.left > 0 && rect.left != 0) {
                moveX = -rect.left;
                //let the parent sticky to left
                ViewAnimator.putOn(parent).translationX(0);
//                mCurrentEdge = LEFT_EDGE;
            }

            //right
            if (rect.right > 0 && rect.right != toView.getWidth()) {
                moveX = toView.getWidth() - rect.right;
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getVerticalScrollView().getLayoutParams();
                //let the parent sticky to right
                ViewAnimator.putOn(parent).translationX(getVerticalScrollView().getPaddingRight() + getVerticalScrollView().getPaddingLeft()
                        + layoutParams.leftMargin + layoutParams.rightMargin);
//                mCurrentEdge = RIGHT_EDGE;
            }

//            if (rect.left < 0 && rect.right < 0) {
//                mCurrentEdge = LEFT_EDGE;
//            }
//
//            if (rect.right >= Utils.getDeviceWidth(mContext)) {
//                mCurrentEdge = RIGHT_EDGE;
//            }


            ViewAnimator.putOn(fromView).translation(moveX, moveY);

            //if tracker view still in current screen

            //TODO when user scroll happened and tracker is be re-used,but user don't lift up finger
            // we can't notify onVisibleChange

            //TODO Need add ViewPager or another horizontal scroll detector ??? or ignore left <-> right ?
            if (rect.top >= 0) {
                float v1 = (rect.bottom - rect.top) * 1.0f / toView.getHeight();
                float v2 = (rect.right - rect.left) * 1.0f / toView.getWidth();
//                if (mVisibleChangeListener != null) {
//                    mVisibleChangeListener.onVisibleChange(v1 == 1 ? v2 : v1, this);
//                }
            }
        } else {

            if (locTo[0] != 0 || locTo[1] != 0) {
                //move parent
                ViewAnimator.putOn(parent).translation(locTo[0], locTo[1])
                        .andPutOn(fromView).translation(0, 0);
//                mCurrentEdge = NONE_EDGE;
            } else {
//                mCurrentEdge = TOP_EDGE;
            }
        }
    }

}
