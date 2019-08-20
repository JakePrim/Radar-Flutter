package com.prim_player_cc.service;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.log.PrimLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/27 - 6:47 PM
 */
public class CustomPhoneStateListener extends PhoneStateListener {
    private Context mContext;

    private static final String TAG = "CustomPhoneStateListene";

    public CustomPhoneStateListener(Context context) {
        mContext = context;
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        PrimLog.d(TAG, "CustomPhoneStateListener state: " + state + " incomingNumber: " + incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:      // 电话挂断
                if (AssistPlayer.defaultPlayer().isPause()) {
                    AssistPlayer.defaultPlayer().pause();
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING:   // 电话响铃
                if (AssistPlayer.defaultPlayer().isPlaying()) {
                    AssistPlayer.defaultPlayer().pause();
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:   // 来电接通 或者 去电  但是没法区分
                break;
        }
    }
}
