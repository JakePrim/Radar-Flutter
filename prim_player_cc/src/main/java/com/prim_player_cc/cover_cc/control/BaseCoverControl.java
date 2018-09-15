package com.prim_player_cc.cover_cc.control;

import android.content.Context;
import android.view.ViewGroup;

import com.prim_player_cc.cover_cc.BaseCover;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 覆盖视频组件控制器的基类
 * @time 2018/7/26 - 下午4:31
 */
public abstract class BaseCoverControl implements ICoverControl {
    protected WeakReference<Context> mContext;

    private List<BaseCover> coverList;

    private ViewGroup viewGroup;

    public BaseCoverControl(Context context) {
        this.mContext = new WeakReference<>(context);
        this.coverList = new ArrayList<>();
        this.viewGroup = initCoverControl();
    }

    @Override
    public void addCover(BaseCover cover) {
        if (isRule(cover)) {
            coverList.add(cover);
            onCoverAdd(cover);
        }
    }

    @Override
    public void removeCover(BaseCover cover) {
        if (isRule(cover)) {
            coverList.remove(cover);
            onCoverRemove(cover);
        }
    }

    @Override
    public void removeAllCovers() {
        coverList.clear();
        onCoverRemoveAll();
    }

    @Override
    public int getCoverCount() {
        return coverList.size();
    }

    @Override
    public ViewGroup getCoverRootView() {
        return viewGroup;
    }

    private boolean isRule(BaseCover cover) {
        if (cover != null && cover.getCoverView() != null) {
            return true;
        }
        return false;
    }

    protected abstract void onCoverRemoveAll();

    protected abstract void onCoverAdd(BaseCover cover);

    protected abstract void onCoverRemove(BaseCover cover);

    protected abstract ViewGroup initCoverControl();
}
