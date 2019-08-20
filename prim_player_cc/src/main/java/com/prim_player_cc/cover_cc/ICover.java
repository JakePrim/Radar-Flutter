package com.prim_player_cc.cover_cc;

import android.os.Bundle;
import android.view.View;

import com.prim_player_cc.cover_cc.listener.OnCoverEventListener;
import com.prim_player_cc.decoder_cc.IMediaController;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图要实现的接口
 * @time 2018/7/26 - 上午10:58
 */
public interface ICover extends IMediaController {
    void setCoverKey(String key);

    View getCoverView();

    void coverVisibility(int visibility);

    int getCoverLevel();

    String getCoverKey();

    ICoverGroup getCoverGroup();

    <T extends View> T findViewById(int id);

    void bindCoverEventListener(OnCoverEventListener onCoverEventListener);

    void unBindCoverEventListener();

    void bindCoverGroup(ICoverGroup coverGroup);

    void onPlayEvent(int eventCode, Bundle bundle);

    void onErrorEvent(int eventCode, Bundle bundle);

    void onCoverNativeEvent(int eventCode, Bundle bundle);

    void onExpandEvent(int eventCode, Bundle bundle);

    void onCoverBind();

    void onCoverUnBind();

    int LEVEL_LOW = 0;

    int LEVEL_MIDDLE = 31;

    int LEVEL_HEIGHT = 61;
}
