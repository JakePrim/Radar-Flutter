package com.example.adsorber_manager.adosorber;

import android.app.Activity;
import android.support.v4.util.ArrayMap;
import android.view.View;

/**
 * Created by suful on 2017/12/16.
 * 吸附view器
 */

public class Adosorber {
    private static ArrayMap<Activity, IViewAdosorber> mViewAdosorbers = new ArrayMap<>();

    /**
     * 在Activity 中创建的悬浮view 存到ArrayMap 中
     * Attach a activity with single {@link IViewAdosorber},and bind
     * follower view into DecorView, so we can handle it
     *
     * @param context
     * @return
     */
    public static IViewAdosorber attach(Activity context) {
        IViewAdosorber iViewAdosorber = mViewAdosorbers.get(context);
        if (iViewAdosorber != null) {
            return iViewAdosorber.attach();
        }
        IViewAdosorber adosorber = new ViewAdosorber(context).attach();
        mViewAdosorbers.put(context, adosorber);
        return adosorber;
    }

    public static IViewAdosorber detach(Activity context) {
        IViewAdosorber iViewAdosorber = mViewAdosorbers.get(context);
        if (iViewAdosorber != null) {
            return iViewAdosorber.detach();
        }
        return null;
    }

    public static IViewAdosorber destroy(Activity context) {
        IViewAdosorber iViewAdosorber = mViewAdosorbers.remove(context);
        if (iViewAdosorber != null) {
            return iViewAdosorber.destory();
        }
        return null;
    }

    public static boolean isAttach(Activity context) {
        IViewAdosorber iViewAdosorber = mViewAdosorbers.get(context);
        if (iViewAdosorber != null) {
            return iViewAdosorber.isAttach();
        }
        return false;
    }

    public static View getFloatView(Activity context) {
        IViewAdosorber iViewAdosorber = mViewAdosorbers.get(context);
        if (iViewAdosorber != null) {
            return iViewAdosorber.getFloatView();
        }
        return null;
    }

    /**
     * 判断当前吸附的view 和 新 吸附的view 是否是同一个view
     *
     * @param context
     * @return
     */
    public static boolean isSameAdosorberView(Activity context, View newAdosorberView) {
        IViewAdosorber iViewAdosorber = mViewAdosorbers.get(context);
        if (iViewAdosorber != null && iViewAdosorber.getAdosorberView() != null) {
            return iViewAdosorber.getAdosorberView().equals(newAdosorberView);
        }
        return false;
    }

    public static void stopPlayer(Activity context) {
        IViewAdosorber iViewAdosorber = mViewAdosorbers.get(context);
        if (iViewAdosorber != null) {
            iViewAdosorber.hide();
        } else {
            detach(context);
        }
    }

    /**
     * 开始播放，实际上只是将悬浮的view显示到固定的位置
     *
     * @param context
     * @param adosorberView
     */
    public static void startPlayer(Activity context, View adosorberView, View mScreenView) {
        IViewAdosorber iViewAdosorber = mViewAdosorbers.get(context);
        if (iViewAdosorber != null) {
            iViewAdosorber.show();
            if (iViewAdosorber.getAdosorberView() == null) {
                iViewAdosorber.adosorberView(adosorberView).into(mScreenView);
            } else {
                iViewAdosorber.changeAdosorberView(adosorberView).into(mScreenView);
            }
        } else {
            IViewAdosorber attach = attach(context);
            attach.adosorberView(adosorberView).show().into(mScreenView);
        }
    }

}
