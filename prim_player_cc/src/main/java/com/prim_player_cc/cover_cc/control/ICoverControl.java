package com.prim_player_cc.cover_cc.control;

import android.view.ViewGroup;

import com.prim_player_cc.cover_cc.BaseCover;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/26 - 下午4:21
 */
public interface ICoverControl {
    void addCover(BaseCover cover);

    void removeCover(BaseCover cover);

    void removeAllCovers();

    int getCoverCount();

    ViewGroup getCoverRootView();
}
