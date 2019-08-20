package com.prim_player_cc.assist;

import android.graphics.Bitmap;
import android.view.ViewGroup;

import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.render_cc.RenderTextureView;
import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.IDataProvider;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.view.OnCoverNativePlayerListener;

/**
 * @author prim
 * @version 1.0.0
 * @desc 播放助手 用于无缝续播 此代码参考了{playerbase}
 * @time 2018/11/5 - 7:08 PM
 */
public interface IAssistPlay {
    //是否允许播放完成后 自动移除绑定的view
    AssistPlayer allowCompleteRemove(boolean isAllowStopRemove);

    Bitmap getShortcut();

}
