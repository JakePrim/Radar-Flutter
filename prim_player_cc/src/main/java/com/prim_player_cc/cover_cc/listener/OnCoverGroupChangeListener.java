package com.prim_player_cc.cover_cc.listener;

import com.prim_player_cc.cover_cc.ICover;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图组中的视图 动态添加或删除的监听
 * @time 2018/7/26 - 上午11:04
 */
public interface OnCoverGroupChangeListener {
    void onAddCover(String key, ICover cover);

    void onRemoveCover(String key, ICover cover);
}
