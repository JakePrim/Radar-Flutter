package com.prim_player_cc.cover_cc;

import android.view.View;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/26 - 上午10:58
 */
public interface ICover {
    void setCoverKey(String key);

    View getCoverView();

    void coverVisibility(int visibility);

    int getCoverLevel();

    ICoverGroup getCoverGroup();

    <T extends View> T findViewById(int id);

    void bindCoverEventListener(OnCoverEventListener onCoverEventListener);

    void unBindCoverEventListener();

    void bindCoverGroup(ICoverGroup coverGroup);

    int LEVEL_LOW = 0;

    int LEVEL_MIDDLE = 31;

    int LEVEL_HEIGHT = 61;
}
