package com.prim_player_cc.cover_cc.helper;

import android.content.Context;
import android.media.AudioManager;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/9 0009
 * 描    述：音量调节的辅助类
 * 修订历史：
 * ================================================
 */
public class AudioHelper {
    protected AudioManager mAM;

    public AudioHelper(Context context) {
        mAM = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public int getCurrentVolume() {
        return mAM.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public int getMaxVolume() {
        return mAM.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public void setVolume(int volume) {
        mAM.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);// AudioManager.FLAG_PLAY_SOUND
    }

    public void lower() {
        //减少音量
        mAM.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
    }

    public void raise() {
        //增加yin音量
        mAM.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
    }
}
