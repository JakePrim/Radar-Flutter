package com.prim_player_cc.expand.producer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.config.ApplicationAttach;
import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.cover_cc.event.CoverEventKey;
import com.prim_player_cc.expand.AbsEventProducer;
import com.prim_player_cc.expand.CoverEventSender;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.utils.NetworkTools;

import java.lang.ref.WeakReference;

import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_NET_WORK_CHANGED;

/**
 * @author prim
 * @version 1.0.0
 * @desc 监听网络改变事件的生产者
 * @time 2018/11/27 - 12:37 PM
 */
public class NetworkEventProducer extends AbsEventProducer {
    private static final String TAG = "NetworkEventProducer";

    private Context mContext;

    private NetChangeBroadcastReceiver mBroadcastReceiver;

    private int mState;

    private Handler H = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CODE_NETWORK_CHANGE:
                    int state = (int) msg.obj;
                    if (mState == state) return;
                    mState = state;
                    sendEvent(state);
                    break;
            }
        }
    };

    public void sendEvent(int state) {
        PrimLog.e(TAG, "sendEvent:" + state);
        CoverEventSender sender = getSender();
        if (sender != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(CoverEventKey.DEFAULT_NET_STATE, state);
            sender.sendEvent(PRIM_PLAYER_EVENT_NET_WORK_CHANGED, bundle);
        }
    }

    public NetworkEventProducer() {
        mContext = ApplicationAttach.getApplicationContext();
    }

    @Override
    public void onAdded() {
        mState = NetworkTools.getNetworkState(mContext);
        sendEvent(mState);
        registerNetChangeReceiver();
    }

    @Override
    public void onRemoved() {
        onDestroy();
    }

    @Override
    public void onDestroy() {
        if (mBroadcastReceiver != null) {
            mBroadcastReceiver.destory();
        }
        unRegisterNetChangeReceiver();
        H.removeMessages(MSG_CODE_NETWORK_CHANGE);
    }

    private void registerNetChangeReceiver() {
        unRegisterNetChangeReceiver();
        if (mContext != null) {
            mBroadcastReceiver = new NetChangeBroadcastReceiver(mContext, H);
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            mContext.registerReceiver(mBroadcastReceiver, filter);
        }

    }

    private void unRegisterNetChangeReceiver() {
        try {
            if (mContext != null && mBroadcastReceiver != null) {
                mContext.unregisterReceiver(mBroadcastReceiver);
                mBroadcastReceiver = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class NetChangeBroadcastReceiver extends BroadcastReceiver {

        private Handler handler;

        private WeakReference<Context> mContextWeak;

        public NetChangeBroadcastReceiver(Context context, Handler handler) {
            this.mContextWeak = new WeakReference<>(context);
            this.handler = handler;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            PrimLog.e(TAG, "action -> " + action);
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                handler.removeCallbacks(mRunnable);
                handler.postDelayed(mRunnable, 1000);
            }
        }

        private Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mContextWeak != null && mContextWeak.get() != null) {
                    int networkState = NetworkTools.getNetworkState(mContextWeak.get());
                    PrimLog.e(TAG, "networkState -> " + networkState);
                    Message message = Message.obtain();
                    message.what = MSG_CODE_NETWORK_CHANGE;
                    message.obj = networkState;
                    handler.sendMessage(message);
                }
            }
        };

        public void destory() {
            handler.removeCallbacks(mRunnable);
        }
    }

    private static final int MSG_CODE_NETWORK_CHANGE = 491;
}
