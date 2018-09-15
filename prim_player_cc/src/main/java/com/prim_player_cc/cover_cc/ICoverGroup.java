package com.prim_player_cc.cover_cc;

import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/26 - 上午11:00
 */
public interface ICoverGroup {
    void addCover(String key, ICover cover);

    void addCover(String key, ICover cover, boolean isListener);

    ICover removeCover(String key);

    ICover removeCover(String key, boolean isListener);

    <T extends ICover> T getCover(String key);

    void clearCovers();

    int getCoverCount();

    Map<String, ICover> getCovers();

    void loopCovers(OnLoopCoverListener loopCoverListener);

    public interface OnLoopCoverListener {
        void getCover(ICover cover);
    }

    void bindCoverGroupChangeListener(OnCoverGroupChangeListener onCoverGroupChangeListener);

    void unBindCoverGroupChangeListener();

    void coverSort();
}
