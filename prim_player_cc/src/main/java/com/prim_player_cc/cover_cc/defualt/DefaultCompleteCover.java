package com.prim_player_cc.cover_cc.defualt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prim_player_cc.R;
import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.source_cc.PlayerSource;

import static android.view.View.VISIBLE;
import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_CANCEL_AUTO_PLAY_NEXT;
import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_EXIT_VERTICAL_FULL_SCREEN;
import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_START_PLAY_NEXT;
import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_DATA_SOURCE;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_CANCEL_AUTO_PLAY_NEXT;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认的播放完成视图
 * @time 2018/7/27 - 下午3:08
 */
public class DefaultCompleteCover extends BaseCover implements View.OnClickListener {
    private TextView cover_complete_btn;

    private LinearLayout ll_auto_play_next;

    private TextView tv_replay;

    private static final String TAG = "DefaultCompleteCover";

    private CountDownTimer timer;

    private PlayerSource source;

    private ImageView tv_cancle_next, iv_controller_back;

    public DefaultCompleteCover(Context context) {
        super(context);
        cover_complete_btn = findViewById(R.id.cover_complete_btn);
        ll_auto_play_next = findViewById(R.id.ll_auto_play_next);
        tv_replay = findViewById(R.id.tv_replay);
        tv_cancle_next = findViewById(R.id.tv_cancle_next);
        iv_controller_back = findViewById(R.id.iv_controller_back);
        tv_replay.setOnClickListener(this);
        tv_cancle_next.setOnClickListener(this);
        cover_complete_btn.setOnClickListener(this);
        iv_controller_back.setOnClickListener(this);
        mCoverView.setOnClickListener(this);
        coverVisibility(View.GONE);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_complete_layout, null);
    }

    @Override
    protected int[] setCoverLevel() {
        return new int[]{LEVEL_LOW, 10};
    }

    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
                coverVisibility(View.VISIBLE);
                if (isFullScreen()) {
                    iv_controller_back.setVisibility(VISIBLE);
                } else {
                    iv_controller_back.setVisibility(View.GONE);
                }
                break;
            case PlayerEventCode.PRIM_PLAYER_EVENT_AUTO_PLAY_NEXT:
                autoPlayNext(bundle);
                break;
            case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
            case PlayerEventCode.PRIM_PLAYER_EVENT_START:
                coverVisibility(View.GONE);
                break;
            case PRIM_PLAYER_EVENT_CANCEL_AUTO_PLAY_NEXT:
                cancelTimer();
                ll_auto_play_next.setVisibility(View.GONE);
                break;
        }
    }

    private void autoPlayNext(Bundle bundle) {
        if (bundle != null) {
            source = bundle.getParcelable(PLAYER_DATA_SOURCE);
            if (source != null) {
                ll_auto_play_next.setVisibility(View.VISIBLE);
                ObjectAnimator translationX = ObjectAnimator.ofFloat(ll_auto_play_next, "translationX", ll_auto_play_next.getWidth(), 0);
                translationX.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startTimer();
                    }
                });
                translationX.setDuration(500);
                translationX.start();
            }
        }
    }

    public void startTimer() {
        timer = new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                transOut();
            }
        };
        timer.start();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void transOut() {
        transExit(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ll_auto_play_next.setVisibility(View.GONE);
                coverVisibility(View.GONE);
                nativeCoverEvent(COVER_EVENT_START_PLAY_NEXT, null);
                mediaPlayerControl.releaseSurface();
                mediaPlayerControl.setDataSource(source);
                boolean voice = AssistPlayer.defaultPlayer().isVoice();
                if (voice) {
                    mediaPlayerControl.setVolume(1, 1);
                } else {
                    mediaPlayerControl.setVolume(0, 0);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cover_complete_btn) {//重播
            replay();
        } else if (i == R.id.tv_replay) {//重播
            replay();
        } else if (i == R.id.tv_cancle_next) {//取消播放下一个
            cancelNext();
        } else if (i == R.id.iv_controller_back) {
            updateFull();
        }
    }

    private void updateFull() {
        if (isFullScreen()) {
            coverRequestVerticalScreen();
        }
    }

    private void replay() {
        cancelTimer();
        nativeCoverEvent(COVER_EVENT_CANCEL_AUTO_PLAY_NEXT, null);
        transExit(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ll_auto_play_next.setVisibility(View.GONE);
                coverVisibility(View.GONE);
                coverRequestStart();
                AssistPlayer.defaultPlayer().setVideoSource(mediaPlayerControl.getPlayerSource());
            }
        });
    }

    private void cancelNext() {
        nativeCoverEvent(COVER_EVENT_CANCEL_AUTO_PLAY_NEXT, null);
        transExit(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ll_auto_play_next.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cancelTimer();
            }
        });
    }

    private void transExit(AnimatorListenerAdapter adapter) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(ll_auto_play_next, "translationX", 0,
                ll_auto_play_next.getWidth());
        translationX.addListener(adapter);
        translationX.setDuration(500);
        translationX.start();
    }
}
