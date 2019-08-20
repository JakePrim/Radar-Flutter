package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.R;
import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.config.ApplicationAttach;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.cover_cc.event.CoverEventKey;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.status.Status;

import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_NET_CHANGE;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_NET_WORK_CHANGED;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_PANDERING_START;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_STOP;

/**
 * @author prim
 * @version 1.0.0
 * @desc 4G 网络的提示视图
 * @time 2018/10/19 - 下午5:34
 */
public class DefaultNetDataPromptCover extends BaseCover {

    private TextView iv_video_feed_start, tv_content;

    private static final String TAG = "DefaultNetDataPromptCov";

    public DefaultNetDataPromptCover(Context context) {
        super(context);
        coverVisibility(View.GONE);
        iv_video_feed_start = findViewById(R.id.iv_video_feed_start);
        tv_content = findViewById(R.id.tv_content);
        iv_video_feed_start.setOnClickListener(this);
    }

    public DefaultNetDataPromptCover(Context context, String value) {
        this(context);
        tv_content.setText(value);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_network_layout, null);
    }

    @Override
    protected int[] setCoverLevel() {
        return new int[]{LEVEL_HEIGHT, 30};
    }

    @Override
    public void onExpandEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PRIM_PLAYER_EVENT_NET_WORK_CHANGED:
                if (bundle != null) {
                    int state = bundle.getInt(CoverEventKey.DEFAULT_NET_STATE);
                    PrimLog.e("resumePlay", "state --> " + state);
                    notifyUI(state);
                }
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        super.onErrorEvent(eventCode, bundle);
    }

    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case 0x102:
                tv_content.setText("正在使用非WiFi网络");
                break;
            case 0x103:
                tv_content.setText("正在使用非WiFi网络，播放将产生流量费用");
                break;
            case PRIM_PLAYER_EVENT_PANDERING_START:
            case PRIM_PLAYER_EVENT_STOP:
                coverVisibility(View.GONE);
                break;
            case PRIM_PLAYER_EVENT_NET_WORK_CHANGED:
                if (bundle != null) {
                    int state = bundle.getInt(CoverEventKey.DEFAULT_NET_STATE);
                    PrimLog.e("resumePlay", "state --> " + state);
                    notifyUI(state);
                }
                break;
        }
    }

    private void notifyUI(int state) {
        if (state == -1) {
            PrimLog.e("resumePlay", "当前没有网络");
            Toast.makeText(ApplicationAttach.getApplicationContext(), "当前没有网络", Toast.LENGTH_SHORT).show();
        } else if (state == 1) {//Wi-Fi网络
            PrimLog.e("resumePlay", "当前切换为WI-FI网络");
            if (mCoverView.getVisibility() == View.VISIBLE) {
                try {
                    //此处为安全检查判断，防止做多余的处理
                    ViewGroup container = AssistPlayer.defaultPlayer().getPlayerContainer();
                    if (container != null) {
                        if (container.getVisibility() != View.VISIBLE) {
                            PrimLog.e(TAG, "播放器没有显示，不做处理");
                            return;
                        }
                        ViewParent parent = container.getParent();
                        if (parent == null) {
                            PrimLog.e(TAG, "播放器的容器没有，不做处理");
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PrimLog.e("resumePlay", "当前切换为WI-FI网络 --> 继续播放");
                coverVisibility(View.GONE);
                if (mediaPlayerControl.getState() == Status.STATE_PAUSE) {
                    mediaPlayerControl.start();
                } else {
                    mediaPlayerControl.releaseSurface();
                    mediaPlayerControl.rePlay();
                    boolean voice = AssistPlayer.defaultPlayer().isVoice();
                    if (voice) {
                        mediaPlayerControl.setVolume(1, 1);
                    } else {
                        mediaPlayerControl.setVolume(0, 0);
                    }
                }
            } else {
                PrimLog.e(TAG, "网络提示view并没有显示，不做处理");
            }

        } else {//数据流量网络
            PrimLog.e("resumePlay", "当前切换为数据流量");
            if (!PrimPlayerCC.ALLOW_NETDATA_PLAY) {
                coverVisibility(View.VISIBLE);
                if (mediaPlayerControl.isPlaying()) {
                    mediaPlayerControl.pause();
                }
                //此处主要通知埋点视图，告知埋点视图 当前为数据流量提示需要埋点
                nativeCoverEvent(COVER_EVENT_NET_CHANGE, null);
            } else {
                PrimLog.e("resumePlay", "用户已经允许数据流量播放视频不做处理");
            }
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_video_feed_start) {
            coverVisibility(View.GONE);
            PrimPlayerCC.ALLOW_NETDATA_PLAY = true;
            PrimLog.e("resumePlay", "getState:" + mediaPlayerControl.getState());
            if (mediaPlayerControl.getState() == Status.STATE_PAUSE) {
                mediaPlayerControl.start();
            } else {
                mediaPlayerControl.releaseSurface();
                mediaPlayerControl.rePlay();
                //注意重置声音
                if (AssistPlayer.defaultPlayer().isVoice()) {
                    mediaPlayerControl.setVolume(1, 1);
                } else {
                    mediaPlayerControl.setVolume(0, 0);
                }
            }
        }
    }
}
