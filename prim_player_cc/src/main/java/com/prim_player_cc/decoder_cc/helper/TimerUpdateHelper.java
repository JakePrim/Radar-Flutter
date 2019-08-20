package com.prim_player_cc.decoder_cc.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.prim_player_cc.decoder_cc.event_code.EventCode;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.log.PrimLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc 更新视频的播放进度
 * @time 2018/9/20 - 下午2:32
 */
public class TimerUpdateHelper {
    private final int UPDATE_TIMER = 0x12;

    /**
     * 循环一次的时间
     */
    private int loopTime = 1000;

    private boolean isStart = false;

    public TimerUpdateHelper(int loopTime) {
        this.loopTime = loopTime;
    }

    private Handler H = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIMER:
                    if (!isStart) {
                        return;
                    }
                    if (onTimerUpdateHandleListener != null) {
                        onTimerUpdateHandleListener.onUpdate();
                    }
                    loopNext();
                    break;
            }
        }
    };

    public void setUpdatePlayEvent(@EventCode int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
            case PlayerEventCode.PRIM_PLAYER_EVENT_START:
            case PlayerEventCode.PRIM_PLAYER_EVENT_RESUME:
            case PlayerEventCode.PRIM_PLAYER_EVENT_PANDERING_START:
                startH();
                break;
            case PlayerEventCode.PRIM_PLAYER_EVENT_PAUSE:
            case PlayerEventCode.PRIM_PLAYER_EVENT_RESET:
            case PlayerEventCode.PRIM_PLAYER_EVENT_RELEASE:
            case PlayerEventCode.PRIM_PLAYER_EVENT_STOP:
            case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
            case PlayerEventCode.PRIM_PLAYER_EVENT_DESTROY:
                cancleH();
                break;
            default:
                break;
        }
    }

    public void setPlayEventError() {
        cancleH();
    }

    private void startH() {
        isStart = true;
        removeAllMessageH();
        if (H != null) {
            H.sendEmptyMessage(UPDATE_TIMER);
        }
    }

    public void cancleH() {
        isStart = false;
        removeAllMessageH();
    }

    private static final String TAG = "TimerUpdateHelper";

    private void loopNext() {
        if (H != null) {
//            PrimLog.e(TAG, "查看handler是否一直执行");
            H.sendEmptyMessageDelayed(UPDATE_TIMER, loopTime);
        }
    }

    private void removeAllMessageH() {
        if (H != null) {
            H.removeCallbacksAndMessages(null);
        }
    }

    private OnTimerUpdateHandleListener onTimerUpdateHandleListener;

    public void setOnTimerUpdateHandleListener(OnTimerUpdateHandleListener onTimerUpdateHandleListener) {
        this.onTimerUpdateHandleListener = onTimerUpdateHandleListener;
    }

    public interface OnTimerUpdateHandleListener {
        void onUpdate();
    }
}
