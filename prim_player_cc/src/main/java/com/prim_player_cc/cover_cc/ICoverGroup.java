package com.prim_player_cc.cover_cc;

import com.prim_player_cc.cover_cc.listener.OnCoverGroupChangeListener;

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

    boolean containsCover(String key);

    void loopCovers(OnLoopCoverListener loopCoverListener);

    void loopCovers(OnCoverFilter filter,OnLoopCoverListener loopCoverListener);

    interface OnLoopCoverListener {
        void getCover(ICover cover);
    }

    /**
     * 视图拦截器
     */
    interface OnCoverFilter{
        boolean filter(ICover cover);
    }

    void bindCoverGroupChangeListener(OnCoverGroupChangeListener onCoverGroupChangeListener);

    void unBindCoverGroupChangeListener();

    void coverSort();
}
