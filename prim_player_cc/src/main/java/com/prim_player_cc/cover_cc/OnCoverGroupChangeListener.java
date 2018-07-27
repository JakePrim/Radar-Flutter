package com.prim_player_cc.cover_cc;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/26 - 上午11:04
 */
public interface OnCoverGroupChangeListener {
    void onAddCover(String key, ICover cover);

    void onRemoveCover(String key, ICover cover);
}
