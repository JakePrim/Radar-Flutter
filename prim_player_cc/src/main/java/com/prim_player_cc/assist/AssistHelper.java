package com.prim_player_cc.assist;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

/**
 * @author prim
 * @version 1.0.0
 * @desc 续播助手辅助类
 * @time 2018/12/19 - 4:27 PM
 */
public class AssistHelper {
    private ImageView ivVideoTemp;

    private AssistPlayer mAssistPlayer;

    //temp image 10ms 变动一次来制造一个视频播放的假象
    private static final int LOOP_TIME = 10;

    private final int UPDATE_VIDEO_TEMP = 0x12;

    private int accumulate = 0;

    private Handler H = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VIDEO_TEMP:
                    if (ivVideoTemp != null) {
                        accumulate += LOOP_TIME;
                        if (accumulate >= 500) {
                            H.removeCallbacksAndMessages(null);
                            return;
                        }
                        ivVideoTemp.setImageBitmap(mAssistPlayer.getShortcut());
                        H.sendEmptyMessageDelayed(UPDATE_VIDEO_TEMP, LOOP_TIME);
                    } else {
                        H.removeCallbacksAndMessages(null);
                    }
                    break;
            }
        }
    };

    public AssistHelper(AssistPlayer assistPlayer) {
        mAssistPlayer = assistPlayer;
    }

    /**
     * 跳转Activity 共享元素无缝续播辅助方法
     *
     * @param imageView
     */
    public void jumpAssist(ImageView imageView) {
        if (imageView != null) {
            this.ivVideoTemp = imageView;
            if (ivVideoTemp.getVisibility() == View.GONE) {
                ivVideoTemp.setVisibility(View.VISIBLE);
            }
            if (H != null) {
                H.removeCallbacksAndMessages(null);
                H.sendEmptyMessage(UPDATE_VIDEO_TEMP);
            }
        }
    }

    public void jumpFinish() {
        H.removeCallbacksAndMessages(null);
    }
}
