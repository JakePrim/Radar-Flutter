package com.prim_player_cc.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.MutableLong;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.helper.AudioHelper;
import com.prim_player_cc.cover_cc.helper.BrightnessHelper;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.utils.Tools;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author prim
 * @version 1.0.0
 * @desc 手势控制view
 * @time 2018/11/1 - 11:06 AM
 */
public class GestureControllerView extends FrameLayout {

    private View inflate;

    private LinearLayout mLlChangeBrightness, mLlChangeVolume, mLlChangePosition;

    private TextView mTvChangePosition, mTvAllPosition;

    private ProgressBar mChangeBrightnessProgress, mChangeVolumeProgress, mChangePositionProgress;

    private ImageView mChangeForwardOrRewind;

    public static final int NONE = 0, VOLUME = 1, BRIGHTNESS = 2, POSITION = 3;

    private @ScrollMode
    int mScrollMode = NONE;
    private boolean isPositionGesture = false;


    @IntDef({NONE, VOLUME, BRIGHTNESS, POSITION})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ScrollMode {
    }

    private int offX = 10;
    private int offInX = 10;

    private int oldVolume = 0;

    private int maxVolume = 0;

    private float brightness = 1;

    private static final String TAG = "ControllerGestureLayout";

    private BrightnessHelper brightnessHelper;

    private AudioHelper audioHelper;

    private long currentPosition, duration;

    private float oldPosition = 0;

    private boolean isGesture = true;

    private float recordBrightness;//record first screen brightness

    private boolean isRecordBrightness = false;

    public GestureControllerView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public GestureControllerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GestureControllerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GestureControllerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        brightnessHelper = new BrightnessHelper(context);
        audioHelper = new AudioHelper(context);
        inflate = LayoutInflater.from(context).inflate(R.layout.default_gesture_controller_layout, null, false);
        mLlChangePosition = (LinearLayout) inflate.findViewById(R.id.ll_change_position);
        mLlChangeVolume = (LinearLayout) inflate.findViewById(R.id.ll_change_volume);
        mLlChangeBrightness = (LinearLayout) inflate.findViewById(R.id.ll_change_brightness);
        mTvChangePosition = (TextView) inflate.findViewById(R.id.tv_change_position);
        mChangePositionProgress = (ProgressBar) inflate.findViewById(R.id.change_position_progress);
        mChangeVolumeProgress = (ProgressBar) inflate.findViewById(R.id.change_volume_progress);
        mChangeBrightnessProgress = (ProgressBar) inflate.findViewById(R.id.change_brightness_progress);
        mChangeForwardOrRewind = (ImageView) inflate.findViewById(R.id.iv_change_forwardOrRewind);
        mTvAllPosition = (TextView) inflate.findViewById(R.id.tv_all_position);
        addView(inflate);
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setCurrentPosition(long currentPosition) {
        this.currentPosition = currentPosition;
    }

    private boolean isBrightness = false;

    private boolean isVolumeGesture = false;

    public void setChangeBrightness(boolean isBrightness) {
        this.isBrightness = isBrightness;
    }

    public void setChangeVolume(boolean isVolumeGesture) {
        this.isVolumeGesture = isVolumeGesture;
    }

    public void setchangePosition(boolean isPositionGesture) {
        this.isPositionGesture = isPositionGesture;
    }

    private long getVideoDuration() {
        return duration;
    }

    private long getVideoCurrentPosition() {
        return currentPosition;
    }

    public void down() {
        brightness = brightnessHelper.getAppBrightness(getContext());
        if (brightness == -1) {
            brightness = brightnessHelper.getSystemBrightness() / 255;
        }
        if (!isRecordBrightness) {
            isRecordBrightness = true;
            recordBrightness = brightness;
        }
        mScrollMode = NONE;
        oldVolume = audioHelper.getCurrentVolume();
        maxVolume = audioHelper.getMaxVolume();
        currentPosition = getVideoCurrentPosition();
        duration = getVideoDuration();
        if (duration != 0) {
            oldPosition = 1000L * currentPosition / duration;
        }
    }


    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
        gestureMove(e1, e2, dx, dy);
        return false;
    }

    public void onTouchCancle() {
        if (mScrollMode == VOLUME) {
            mLlChangeVolume.setVisibility(GONE);
        }
        if (mScrollMode == BRIGHTNESS) {
            mLlChangeBrightness.setVisibility(GONE);
        }
        //手指抬起时 移动到当前的位置
        if (mScrollMode == POSITION && isPositionGesture) {
            mLlChangePosition.setVisibility(GONE);
            if (onGestureControllerListener != null) {
                onGestureControllerListener.seekTo((int) currentPosition);
            }
        }
    }

    public interface OnGestureControllerListener {
        void seekTo(int position);
    }

    private OnGestureControllerListener onGestureControllerListener;

    public void setOnGestureControllerListener(OnGestureControllerListener onGestureControllerListener) {
        this.onGestureControllerListener = onGestureControllerListener;
    }

    /**
     * 手势移动
     */
    private boolean gestureMove(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        switch (mScrollMode) {
            case NONE:
                return noneChange(e1, distanceX, distanceY);
            case VOLUME:
                return changeVolume(e1, e2);
            case BRIGHTNESS:
                return changeBrightness(e1, e2);
            case POSITION:
                return changePosition(e1, e2);
            default:
                return false;
        }
    }

    /**
     * 改变进度
     */
    private boolean changePosition(MotionEvent e1, MotionEvent e2) {
        if (!isPositionGesture) {
            return false;
        } else {
            float offX = e2.getX() - e1.getX();
            float movePercent = (1000L * (offX / getWidth()) * duration) / duration;
            float value = movePercent + oldPosition;
            if (value < 0) {
                value = 0;
            } else if (value > 1000) {
                value = 1000;
            }
            long position = (long) (duration * value / 1000);
            if (currentPosition > position) {
                mChangeForwardOrRewind.setImageResource(R.mipmap.icon_player_rewind);
            } else {
                mChangeForwardOrRewind.setImageResource(R.mipmap.icon_player_forward);
            }
            this.currentPosition = position;
            mLlChangePosition.setVisibility(VISIBLE);
            mChangePositionProgress.setMax(1000);
            mChangePositionProgress.setProgress((int) value);
            mTvChangePosition.setText(Tools.generateTime(position));
            mTvAllPosition.setText("/" + Tools.generateTime(duration));
            return true;
        }
    }

    /**
     * 改变亮度
     */
    private boolean changeBrightness(MotionEvent e1, MotionEvent e2) {
        PrimLog.e(TAG, "changeBrightness:" + isBrightness);
        if (!isBrightness) {
            return false;
        }
        float newBrightness = (e1.getY() - e2.getY()) / getHeight();
        newBrightness += brightness;
        if (newBrightness < 0) {
            newBrightness = 0;
        } else if (newBrightness > 1) {
            newBrightness = 1;
        }
        brightnessHelper.setAppBrightness(newBrightness, getContext());
        int position = (int) (newBrightness * 100);
        mLlChangeBrightness.setVisibility(VISIBLE);
        mChangeBrightnessProgress.setProgress(position);
        return true;

    }

    /**
     * 改变声音
     */
    private boolean changeVolume(MotionEvent e1, MotionEvent e2) {
        if (!isVolumeGesture) {
            return false;
        }
        getParent().requestDisallowInterceptTouchEvent(false);//touch hand over parent view doOauthVerify
        int value = getHeight() / maxVolume;
        int newVolume = (int) ((e1.getY() - e2.getY()) / value + oldVolume);
        int volumeProgress = (int) (newVolume / Float.valueOf(maxVolume) * 100);
        audioHelper.setVolume(newVolume);
        mLlChangeVolume.setVisibility(VISIBLE);
        mChangeVolumeProgress.setProgress(volumeProgress);
        return true;
    }

    /**
     * 没有改变
     */
    private boolean noneChange(MotionEvent e1, float distanceX, float distanceY) {
        if ((Math.abs(distanceX) - Math.abs(distanceY)) > offX) {
            mScrollMode = POSITION;
        } else {
            if (e1.getX() < getWidth() / 2) {
                mScrollMode = BRIGHTNESS;
            } else {
                mScrollMode = VOLUME;
            }
        }
        return false;
    }

    /**
     * What is changed is the brightness of the activity of the current window.
     * The brightness of other activities does not change,and it need to be reset
     * to the previous brightness when exiting.
     * 退出当前界面重置亮度
     */
    public void recoverBrightness() {
        if (isRecordBrightness) {
            brightnessHelper.setAppBrightness(WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE, getContext());
        }
    }
}
