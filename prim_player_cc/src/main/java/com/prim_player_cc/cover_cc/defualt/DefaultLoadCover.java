package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.utils.NetSpeed;
import com.prim_player_cc.utils.NetSpeedRate;
import com.prim_player_cc.utils.NetSpeedTimer;

import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_SEEK_LOADING_START;
import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_SEEK_RENDERING_START;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认的加载视图
 * @time 2018/7/26 - 上午11:21
 */
public class DefaultLoadCover extends BaseCover {

    private Handler mH = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NetSpeedTimer.NET_SPEED_TIMER_DEFAULT:
                    String s_kb = (String) msg.obj;
                    PrimLog.e(TAG, "加载速度 -> " + s_kb);
                    tv_net_speed.setText(s_kb);
                    break;
                default:
                    break;
            }
        }
    };
    private static final String TAG = "DefaultLoadCover";

    private TextView tv_net_speed;

//    private NetSpeedTimer mNetSpeedTimer;

    public DefaultLoadCover(Context context) {
        super(context);
        coverVisibility(View.GONE);
        tv_net_speed = findViewById(R.id.tv_net_speed);
//        mNetSpeedTimer = new NetSpeedTimer(context, new NetSpeed(), mH).setDelayTime(10).setPeriodTime(200);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_load_layout, null);
    }

    @Override
    protected int[] setCoverLevel() {
        return new int[]{LEVEL_HEIGHT, 20};
    }

    //接收播放事件
    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
                PrimLog.e(TAG, "播放准备完毕");
                coverVisibility(View.GONE);
//                mNetSpeedTimer.stopSpeedTimer();
                break;
            case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARING:
                PrimLog.e(TAG, "播放准备中");
//                mNetSpeedTimer.startSpeedTimer();
                coverVisibility(View.VISIBLE);
                break;
            case PlayerEventCode.PRIM_PLAYER_EVENT_PANDERING_START:
                PrimLog.e(TAG, "视频渲染完毕");
                coverVisibility(View.GONE);

//                mNetSpeedTimer.stopSpeedTimer();
                break;
            case PlayerEventCode.PRIM_PLAYER_EVENT_BUFFERING_START:
                PrimLog.e(TAG, "开始缓冲");
//                mNetSpeedTimer.startSpeedTimer();
                coverVisibility(View.VISIBLE);
                break;
            case PlayerEventCode.PRIM_PLAYER_EVENT_BUFFERING_END:
                PrimLog.e(TAG, "缓冲结束");
//                mNetSpeedTimer.stopSpeedTimer();
                coverVisibility(View.GONE);
                break;
            case PlayerEventCode.PRIM_PLAYER_EVENT_STOP:
//                mNetSpeedTimer.stopSpeedTimer();
                coverVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        coverVisibility(View.GONE);
//        mNetSpeedTimer.stopSpeedTimer();
//        forceStopTimer();
    }

    @Override
    public void onCoverNativeEvent(int eventCode, Bundle bundle) {
        super.onCoverNativeEvent(eventCode, bundle);
        switch (eventCode) {
            case COVER_EVENT_SEEK_RENDERING_START:
                coverVisibility(View.GONE);
                break;
            case COVER_EVENT_SEEK_LOADING_START:
//                coverVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void onCoverUnBind() {
        super.onCoverUnBind();
//        mNetSpeedTimer.stopSpeedTimer();
        //强制使其停止
//        forceStopTimer();
    }

    private void forceStopTimer() {
//        mH.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                if (mNetSpeedTimer != null)
////                    mNetSpeedTimer.stopSpeedTimer();
//            }
//        }, 1000);
    }

    @Override
    public void onCoverBind() {
        super.onCoverBind();
    }
}
