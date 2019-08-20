package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prim_player_cc.R;
import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.log.PrimLog;

import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_ERROR;
import static com.prim_player_cc.decoder_cc.event_code.ErrorCode.PLAYER_EVENT_ERROR_UNKNOWN;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_PANDERING_START;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_START;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_STOP;

/**
 * @author prim
 * @version 1.0.0
 * @desc 错误视图
 * @time 2018/7/27 - 下午3:08
 */
public class DefaultErrorCover extends BaseCover {

    private static final String TAG = "DefaultErrorCover";

    private TextView tv_video_error_cover;

    private LinearLayout ll_live_video_error_cover;

    public DefaultErrorCover(Context context) {
        super(context);
        coverVisibility(View.GONE);
        tv_video_error_cover = findViewById(R.id.tv_video_error_cover);
        ll_live_video_error_cover = findViewById(R.id.ll_live_video_error_cover);
        ll_live_video_error_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replay();
            }
        });
    }

    private void replay() {
        mediaPlayerControl.releaseSurface();
        mediaPlayerControl.rePlay();
        boolean voice = AssistPlayer.defaultPlayer().isVoice();
        if (voice) {
            mediaPlayerControl.setVolume(1, 1);
        } else {
            mediaPlayerControl.setVolume(0, 0);
        }
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_error_layout, null);
    }

    @Override
    protected int[] setCoverLevel() {
        return new int[]{LEVEL_LOW, 2};
    }

    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PRIM_PLAYER_EVENT_PANDERING_START:
            case PRIM_PLAYER_EVENT_PREPARED:
            case PRIM_PLAYER_EVENT_START:
                coverVisibility(View.GONE);
                break;
            case PRIM_PLAYER_EVENT_STOP:
                coverVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onCoverNativeEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case COVER_EVENT_ERROR:
                coverVisibility(View.VISIBLE);
                mediaPlayerControl.setDataSource(null);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        PrimLog.e(TAG, "eventCode -> " + eventCode);
        coverVisibility(View.VISIBLE);
//        if (bundle != null) {
//            String toast;
//            int what = bundle.getInt("framework_err");
//            PrimLog.e(TAG, "framework_err -> " + what);
//            switch (what) {
//                case -5:
//                    toast = "网络异常";
//                    break;
//                case -11:
//                    toast = "与服务器连接断开";
//                    break;
//                case -110:
//                    toast = "网络不稳定";
//                    break;
//                default:
//                    toast = "网络异常";
//                    break;
//            }
//            tv_video_error_cover.setText(toast);
//        } else {
//            switch (eventCode) {
//                case -5:
//                    tv_video_error_cover.setText("网络异常");
//                    break;
//            }
//        }
    }
}
