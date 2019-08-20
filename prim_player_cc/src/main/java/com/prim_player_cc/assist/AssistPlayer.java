package com.prim_player_cc.assist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.R;
import com.prim_player_cc.config.AVOptions;
import com.prim_player_cc.config.ApplicationAttach;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.decoder_cc.ProxyDecoderCC;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.expand.producer.NetworkEventProducer;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.service.CustomPhoneStateListener;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.status.Status;
import com.prim_player_cc.utils.NetworkTools;
import com.prim_player_cc.utils.Tools;
import com.prim_player_cc.view.AssistPlayerView;
import com.prim_player_cc.view.BusPlayerView;
import com.prim_player_cc.view.OnCoverNativePlayerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_MANUAL_PAUSE;
import static com.prim_player_cc.cover_cc.event.CoverEventCode.COVER_EVENT_START;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_DISPOSABLE;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_FULL_VERTICAL;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_STOP;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_VERTICAL_FULL;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视频无缝续播助手
 * @time 2018/11/7 - 3:18 PM
 */
public class AssistPlayer implements IAssistPlay {

    private volatile static AssistPlayer assistPlayer;//DCL

    private ProxyDecoderCC proxyDecoderCC;

    private AssistPlayerView mPlayerView;

    private List<OnPlayerEventListener> onPlayerEventListeners;

    private List<OnErrorEventListener> onErrorEventListeners;

    private List<OnTimerUpdateListener> onTimerUpdateListeners;

    private List<OnCoverNativePlayerListener> onCoverNativePlayerListeners;

    private PlayerSource mCurrentSource;

    private NetworkEventProducer networkEventProducer;

    //默认的视频客户端
    private static final String DEFAULT_CLIENT_NAME = "_player_client_name_default";

    //小视频客户端
    public static final String SMALL_CLENT_NAME = "_player_small_client_name";

    public static final String PLAYER_VOICE = "_player_voice";

    //ConcurrentHashMap 线程安全的HashMap,对桶节点加锁
    private static final ConcurrentHashMap<String, AssistPlayer> CLIENT_MAP = new ConcurrentHashMap<>();

    private String clientKey;

    private ICoverGroup mCoverGroup;

    public int videoFeedPosition = 0;

    private Activity mActivity;

    private boolean isVoice = false;

    private SharedPreferences sharedPreferences;

    private int mOriginHeight, mOriginWidth;

    public boolean isVerticalFull = false;

    private AssistHelper mAssistHelper;

    private boolean isAllowStopRemove = true;

    public static boolean IS_VIDEO_BACK = false;

    private BusPlayerView busPlayerView;

    //获取不同的实例播放器 防止冲突 不同的功能可以做区分
    @NonNull
    public static AssistPlayer get(@NonNull String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("client key can not be null");
        }
        AssistPlayer player = CLIENT_MAP.get(key);
        if (player == null) {
            synchronized (CLIENT_MAP) {
                player = CLIENT_MAP.get(key);
                if (player == null) {
                    player = new AssistPlayer(key);
                    CLIENT_MAP.put(key, player);
                    assistPlayer = player;
                }
                return player;
            }
        } else {
            assistPlayer = player;
        }
        return player;
    }

    @NonNull
    public static AssistPlayer defaultPlayer() {
        return get(DEFAULT_CLIENT_NAME);
    }

    private AssistPlayer(String key) {
        clientKey = key;
        mAssistHelper = new AssistHelper(this);
        init();
    }


    public void init() {
        //初始化解码器代理类
        proxyDecoderCC = new ProxyDecoderCC();
        //初始化回调监听
        onPlayerEventListeners = new ArrayList<>();
        onErrorEventListeners = new ArrayList<>();
        onTimerUpdateListeners = new ArrayList<>();
        onCoverNativePlayerListeners = new ArrayList<>();
        //网络监听事件生产者
        networkEventProducer = new NetworkEventProducer();
        sharedPreferences = ApplicationAttach.getApplicationContext().getSharedPreferences("com.prim.player", 0);
        //创建一个播放器view
        mPlayerView = new AssistPlayerView(ApplicationAttach.getApplicationContext());
        //默认禁止手势事件
        mPlayerView.setGesture(false);
        //为播放容器添加解码器
        mPlayerView.setDecoderCC(proxyDecoderCC);
        //添加扩展事件发生者 监听网络变化
        mPlayerView.addEventProducer(networkEventProducer);
        //bus view
        busPlayerView = mPlayerView.getBusPlayerView();
        //参数设置
        setAVOption();

    }

    private void setAVOption() {
        AVOptions options = new AVOptions();
        // 解码方式，codec＝1，硬解; codec=0, 软解 2 自动
        options.setInteger(AVOptions.KEY_MEDIACODEC, 0);
        // 读取视频流超时时间10 * 1000
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        // 默认的缓存大小2000
        options.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 2000);
        // 最大的缓存大小4000
        options.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 4000);
        // 不启用自动播放
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 1);
        // 播放前最大探测流的字节数，单位是 byte
        options.setInteger(AVOptions.KEY_PROBESIZE, 10 * 1024);
        // 设置
        proxyDecoderCC.setAVOptions(options);
    }

    /**
     * 监听电话来电
     */
    private void registerPhoneStateListener() {
        CustomPhoneStateListener customPhoneStateListener = new CustomPhoneStateListener(ApplicationAttach.getApplicationContext());
        TelephonyManager telephonyManager = (TelephonyManager) ApplicationAttach.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            telephonyManager.listen(customPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    /**
     * 如果需要用Activity 必须要of一个Activity
     *
     * @param activity Activity
     * @return AssistPlayer
     */
    public AssistPlayer of(Activity activity) {
        this.mActivity = activity;
        return this;
    }


    /**
     * //阻断自动播放下一条视频
     *
     * @return
     */
    public AssistPlayer dispose() {
//        senderCover(PRIM_PLAYER_EVENT_DISPOSABLE, null);
        return this;
    }

    /**
     * 这种想法 无法实现 暂时抛弃
     *
     * @param imageView
     * @hide
     * @deprecated
     */
    public void jumpAssist(ImageView imageView) {
        if (mAssistHelper != null) {
            mAssistHelper.jumpAssist(imageView);
        }
    }

    /**
     * 是否为全屏播放
     *
     * @param activity 上下文
     * @return
     */
    public boolean isFullScreen(Activity activity) {
        int requestedOrientation = Tools.scanForActivity(activity).getRequestedOrientation();
        return requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
    }

    /**
     * 获取当前视频的截图
     *
     * @return Bitmap
     */
    @Override
    public Bitmap getShortcut() {
        return mPlayerView.getShortcut();
    }

    @Override
    public AssistPlayer allowCompleteRemove(boolean isAllowStopRemove) {
        this.isAllowStopRemove = isAllowStopRemove;
        PrimLog.e("PRIM!!", "isAllowStopRemove:" + isAllowStopRemove);
        return this;
    }

    /**
     * 动态绑定播放器
     */
    public void resumePlay(@NonNull ViewGroup container) {
        resumePlay(container, null, false);
    }

    public void resumePlay(@NonNull ViewGroup container, boolean addBottom) {
        resumePlay(container, null, addBottom);
    }

    public void resumePlay(@NonNull ViewGroup container, @NonNull PlayerSource source) {
        resumePlay(container, source, false);
    }

    public void resumePlay(@NonNull ViewGroup container, PlayerSource source, boolean addBottom) {
        //当前的播放资源等于传入的播放资源
        if (source != null) {
            this.mCurrentSource = source;
        }
        mAssistHelper.jumpFinish();
        attachListener();
        attachContainer(container, addBottom);
        if (source != null) {
            if (TextUtils.isEmpty(source.getUrl())) {
                senderNativeCover(CoverEventCode.COVER_EVENT_ERROR, null);
            } else {
                mPlayerView.setDataSource(source);
                setVoice(isVoice());
            }

        }
    }

    private ViewGroup container;

    public void attachContainer(ViewGroup container, boolean addBottom) {
        detachPlayerContainer();
        updateRender();
        if (container != null) {
            this.container = container;
            if (addBottom) {
                container.addView(mPlayerView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                container.addView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
    }

    public ViewGroup getContainer() {
        return container;
    }

    private void updateRender() {

    }

    public void detachPlayerContainer() {
        Log.e(TAG, "detachPlayerContainer");
        if (mPlayerView == null) return;
        ViewParent parent = mPlayerView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mPlayerView);
        }
    }

    public ViewGroup getPlayerContainer() {
        return mPlayerView;
    }


    public AssistPlayer setPlaySource(PlayerSource source) {
        attachListener();
        if (source != null) {
            mPlayerView.setDataSource(source);
            start();
        }
        return this;
    }

    public void setVideoSource(PlayerSource source) {
        this.mCurrentSource = source;
    }

    public ProxyDecoderCC getProxyDecoderCC() {
        return proxyDecoderCC;
    }

    /**
     * 切换解码器
     */
    public AssistPlayer switchDecoder(int id) {
        proxyDecoderCC.switchDecoder(id);
        return this;
    }

    /**
     * 解码器设置
     */
    public AssistPlayer setAVOption(AVOptions avOption) {
        proxyDecoderCC.setAVOptions(avOption);
        return this;
    }

    /**
     * 设置一组视图
     */
    public AssistPlayer setCoverGroup(ICoverGroup coverGroup) {
        if (coverGroup == null) {
            return this;
        }
        this.mCoverGroup = coverGroup;
        mPlayerView.setCoverGroup(coverGroup);
        return this;
    }

    /**
     * 获取当前的视图组
     */
    public ICoverGroup getCoverGroup() {
        return mCoverGroup;
    }

    /**
     * 设置是否开启手势
     */
    public AssistPlayer setGesture(boolean isGesture) {
        mPlayerView.setGesture(isGesture);
        return this;
    }

    /**
     * 是否开启滑动手势
     *
     * @param isScrollGesture
     * @return
     */
    public AssistPlayer setScrollGesture(boolean isScrollGesture) {
        mPlayerView.setScrollGesture(isScrollGesture);
        return this;
    }

    /**
     * 根据唯一key移除某个视图
     */
    public AssistPlayer removeCover(String key) {
        if (mCoverGroup != null) {
            mCoverGroup.removeCover(key);
        }
        return this;
    }

    /**
     * 添加某个视图
     */
    public AssistPlayer addCover(String key, BaseCover cover) {
        if (mCoverGroup != null) {
            mCoverGroup.addCover(key, cover);
        }
        return this;
    }

    /**
     * 获取当前播放资源
     */
    public PlayerSource getCurrentSource() {
        return mCurrentSource;
    }

    /**
     * 一直存在的播放资源
     */
    public PlayerSource getRealSource() {
        return mPlayerView.getPlayerSource();
    }

    private static final String TAG = "AssistPlayer";

    public AssistPlayerView getPlayerView() {
        return mPlayerView;
    }

    public boolean isPlaying() {
        return proxyDecoderCC.isPlaying();
    }

    public boolean isPause() {
        return proxyDecoderCC.getState() == Status.STATE_PAUSE;
    }

    public int getState() {
        return proxyDecoderCC.getState();
    }

    public void start() {
        PrimLog.e("AssistPlayer", "start1");
        PrimLog.printStackTrace(SMALL_CLENT_NAME + ".");
        if (!isManualPause) {
            proxyDecoderCC.start();
        }
    }

    /**
     * 开始播放时 绑定到某个viewGroup 上
     *
     * @param viewGroup 要绑定到viewGroup
     */
    public void start(ViewGroup viewGroup) {
        PrimLog.e("AssistPlayer", "start2");
        resumePlay(viewGroup);
        proxyDecoderCC.start();
    }

    public void start(long position) {
        if (!isManualPause) {
            proxyDecoderCC.start(position);
        }
    }

    public void pause() {
//        PrimLog.printStackTrace("AssistPlayer-pause");
        proxyDecoderCC.pause();
    }

    public void stop() {
//        PrimLog.printStackTrace("AssistPlayer-stop");
        senderCover(PRIM_PLAYER_EVENT_STOP, null);
        detachPlayerContainer();
        proxyDecoderCC.stop();
    }

    public boolean isVoice() {
        isVoice = sharedPreferences.getBoolean(PLAYER_VOICE, false);
        return isVoice;
    }

    public void setVoice(boolean isVoice) {
        sharedPreferences.edit().putBoolean(PLAYER_VOICE, isVoice).apply();
        this.isVoice = isVoice;
        if (isVoice) {
            proxyDecoderCC.setVolume(1, 1);
        } else {
            proxyDecoderCC.setVolume(0, 0);
        }
    }

    private void attachListener() {
        if (mPlayerView != null) {
            mPlayerView.setOnPlayerEventListener(mInternalPlayerEventListener);
            mPlayerView.setOnErrorEventListener(mInternalErrorEventListener);
            mPlayerView.setOnTimerUpdateListener(mInternalTimerUpdateListener);
            mPlayerView.setOnCoverNativePlayerListener(mInternalCoverNativePlayerListener);
        }
    }

    private void detachListener() {
        if (mPlayerView != null) {
            mPlayerView.setOnPlayerEventListener(null);
            mPlayerView.setOnErrorEventListener(null);
            mPlayerView.setOnTimerUpdateListener(null);
            mPlayerView.setOnCoverNativePlayerListener(null);
        }
    }

    /**
     * 视频停止播放时，清除屏幕常亮设置
     */
    public void clear_keep_screen_on() {
        if (mActivity != null) {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * 视频播放时保持屏幕常亮
     */
    public void keep_screen_on() {
        if (mActivity != null) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//不锁屏
        }
    }

    private Handler H = new Handler(Looper.getMainLooper());

    /**
     * 暂停切换防止黑屏 调用此方法
     */
    public void configurationPauseOrigin() {
        if (AssistPlayer.defaultPlayer().getState() == Status.STATE_PAUSE) {
            final boolean voice = AssistPlayer.defaultPlayer().isVoice();
            final AssistPlayerView playerView = AssistPlayer.defaultPlayer().getPlayerView();
            if (voice) {
                playerView.setVolume(0, 0);
            }
            AssistPlayer.defaultPlayer().start();
            H.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AssistPlayer.defaultPlayer().pause();
                    H.removeCallbacksAndMessages(null);
                    if (voice) {
                        playerView.setVolume(1, 1);
                    }
                }
            }, 200);
        }
    }

    /**
     * 向视图发送一个事件
     *
     * @param code   事件码
     * @param bundle 传递的值
     */
    public AssistPlayer senderCover(int code, Bundle bundle) {
        if (null != mPlayerView) {
            BusPlayerView busPlayerView = mPlayerView.getBusPlayerView();
            busPlayerView.dispatchPlayEvent(code, bundle);
        }
        return this;
    }

    /**
     * 向视图发送一个事件
     *
     * @param code   事件码
     * @param bundle 传递的值
     */
    public AssistPlayer senderNativeCover(int code, Bundle bundle) {
        if (null != mPlayerView) {
            BusPlayerView busPlayerView = mPlayerView.getBusPlayerView();
            busPlayerView.dispatchCoverNativeEvent(code, bundle);
        }
        return this;
    }

    /**
     * 设置是否循环播放
     *
     * @param loop
     */
    public void setLooping(boolean loop) {
        if (null != mPlayerView) {
            mPlayerView.setLooping(loop);
        }
    }

    /**
     * 从全屏切换为竖屏
     *
     * @param activity 上下文
     */
    public void openVerticalScreen(Activity activity) {
        if (null == activity) {
            return;
        }
        BusPlayerView busPlayerView = mPlayerView.getBusPlayerView();
        Window window = Tools.scanForActivity(activity).getWindow();
        if (window != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
            viewGroup.removeView(busPlayerView);
        }
        ViewParent parent = busPlayerView.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup viewgroup = (ViewGroup) parent;
            viewgroup.removeView(busPlayerView);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPlayerView.addView(busPlayerView, params);
        //通知视图已经变为竖屏了
        senderCover(PRIM_PLAYER_EVENT_FULL_VERTICAL, null);
    }

    /**
     * 从竖屏切换为全屏
     *
     * @param activity 上下文
     */
    public void openFulScreen(Activity activity) {
        if (null == activity) {
            return;
        }
        BusPlayerView busPlayerView = mPlayerView.getBusPlayerView();
        Window window = activity.getWindow();
        if (window != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ViewGroup viewGroup = (ViewGroup) window.getDecorView().findViewById(android.R.id.content);
            changeScreen(busPlayerView, viewGroup, PRIM_PLAYER_EVENT_VERTICAL_FULL);
        }
    }

    private void changeScreen(BusPlayerView busPlayerView, ViewGroup viewGroup, int key) {
        try {
            ViewParent parent = busPlayerView.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewgroup = (ViewGroup) parent;
                viewgroup.removeView(busPlayerView);
            }
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            viewGroup.addView(busPlayerView, lp);
            //通知视图已经变为全屏了
            senderCover(key, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换为竖屏全屏
     *
     * @param activity
     */
    public void openVerticalFullScreen(Activity activity) {
        isVerticalFull = true;
        mOriginHeight = mPlayerView.getHeight();
        mOriginWidth = mPlayerView.getWidth();
        final BusPlayerView busPlayerView = mPlayerView.getBusPlayerView();
        final ViewGroup content = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        final int width = content.getWidth();
        final int height = content.getHeight();
        final int detalH = height - mOriginHeight;
        //将播放器添加到content容器中
        ViewParent parent = busPlayerView.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup viewgroup = (ViewGroup) parent;
            viewgroup.removeView(busPlayerView);
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(mOriginWidth, mOriginHeight);
        content.addView(busPlayerView, lp);
        //开始移动动画
        com.prim_player_cc.view.ViewAnimator.putOn(busPlayerView).translation(0, 0);
        //动画更新监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                int moveWidth = (int) (width);
                int moveHeight = (int) (mOriginHeight + detalH * value);
                busPlayerView.getLayoutParams().width = moveWidth;
                busPlayerView.getLayoutParams().height = moveHeight;
                busPlayerView.requestLayout();
                mPlayerView.updateRenderSize();
            }
        });
        animator.setDuration(500);
        animator.start();
        senderCover(PRIM_PLAYER_EVENT_VERTICAL_FULL, null);
    }

    /**
     * 退出竖屏全屏
     *
     * @param activity
     */
    public void exitVerticalFullScreen(Activity activity) {
        isVerticalFull = false;
        final BusPlayerView busPlayerView = mPlayerView.getBusPlayerView();
        final ViewGroup content = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        int height = content.getMeasuredHeight();
        final int detalH = height - mOriginHeight;
        //动画更新监听
        final int finalHeight = height;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                int moveWidth = (int) (mOriginWidth);
                int moveHeight = (int) (finalHeight - detalH * value);
                busPlayerView.getLayoutParams().width = moveWidth;
                busPlayerView.getLayoutParams().height = moveHeight;
                busPlayerView.requestLayout();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ViewParent parent = busPlayerView.getParent();
                if (parent instanceof ViewGroup) {
                    ViewGroup viewgroup = (ViewGroup) parent;
                    viewgroup.removeView(busPlayerView);
                }
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                mPlayerView.addView(busPlayerView, params);
            }
        });
        animator.setDuration(500);
        animator.start();
        senderCover(PRIM_PLAYER_EVENT_FULL_VERTICAL, null);
    }

    private OnPlayerEventListener mInternalPlayerEventListener = new OnPlayerEventListener() {
        @Override
        public void onPlayerEvent(int eventCode, Bundle bundle) {
            switch (eventCode) {
                case PlayerEventCode.PRIM_PLAYER_EVENT_START:
                case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
                    IS_VIDEO_BACK = false;
                    isManualPause = false;
                    break;
                case PRIM_PLAYER_EVENT_STOP:
                    detachPlayerContainer();
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    bundle.putInt("position", mCurrentSource != null ? mCurrentSource.getPosition() : 0);
                    mCurrentSource = null;
                    isManualPause = false;
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
                    Log.e(TAG, "isAllowStopRemove: PRIM_PLAYER_EVENT_COMPLETION:" + isAllowStopRemove);
                    if (isAllowStopRemove) {
                        detachPlayerContainer();
                        mCurrentSource = null;
                    } else {
                        //source 继续存在
                    }
                    isManualPause = false;
                    break;
            }
            for (OnPlayerEventListener listener : onPlayerEventListeners) {
                listener.onPlayerEvent(eventCode, bundle);
            }
        }
    };

    private boolean isManualPause = false;

    private OnErrorEventListener mInternalErrorEventListener = new OnErrorEventListener() {
        @Override
        public boolean onError(Bundle bundle, int errorCode) {
            for (OnErrorEventListener listener : onErrorEventListeners) {
                listener.onError(bundle, errorCode);
            }
            return true;
        }
    };

    private OnTimerUpdateListener mInternalTimerUpdateListener = new OnTimerUpdateListener() {
        @Override
        public void onUpdate(long current, long duration, int bufferPercentage) {
            for (OnTimerUpdateListener onTimerUpdateListener : onTimerUpdateListeners) {
                onTimerUpdateListener.onUpdate(current, duration, bufferPercentage);
            }
        }
    };

    private OnCoverNativePlayerListener mInternalCoverNativePlayerListener = new OnCoverNativePlayerListener() {
        @Override
        public void onEvent(int eventCode, Bundle bundle) {
            switch (eventCode) {
                case COVER_EVENT_MANUAL_PAUSE:
                    isManualPause = true;
                    break;
                case COVER_EVENT_START:
                    //检测手动触发开始
                    isManualPause = false;
                    break;
            }
            for (OnCoverNativePlayerListener listener : onCoverNativePlayerListeners) {
                listener.onEvent(eventCode, bundle);
            }
        }
    };

    public AssistPlayer addOnCoverNativePlayerListener(OnCoverNativePlayerListener nativePlayerListener) {
        if (onCoverNativePlayerListeners != null) {
            if (onCoverNativePlayerListeners.contains(nativePlayerListener)) {
                return this;
            }
            onCoverNativePlayerListeners.add(nativePlayerListener);
        }
        return this;
    }

    public AssistPlayer removeOnCoverNativePlayerListener(OnCoverNativePlayerListener nativePlayerListener) {
        if (onCoverNativePlayerListeners != null) {
            onCoverNativePlayerListeners.remove(nativePlayerListener);
        }
        return this;
    }

    public AssistPlayer addOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
        if (onTimerUpdateListeners != null) {
            if (onTimerUpdateListeners.contains(onTimerUpdateListener)) {
                return this;
            }
            onTimerUpdateListeners.add(onTimerUpdateListener);
        }
        return this;
    }

    public AssistPlayer removeOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
        if (onTimerUpdateListeners != null) {
            onTimerUpdateListeners.remove(onTimerUpdateListener);
        }
        return this;
    }

    public AssistPlayer addOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener) {
        if (onPlayerEventListeners != null) {
            if (onPlayerEventListeners.contains(onPlayerEventListener)) {
                return this;
            }
            onPlayerEventListeners.add(onPlayerEventListener);
        }
        return this;
    }

    public void addOnErrorEventListener(OnErrorEventListener onErrorEventListener) {
        if (onErrorEventListeners != null) {
            if (onErrorEventListeners.contains(onErrorEventListener)) {
                return;
            }
            onErrorEventListeners.add(onErrorEventListener);
        }
    }

    public AssistPlayer removeOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener) {
        if (onPlayerEventListeners != null) {
            onPlayerEventListeners.remove(onPlayerEventListener);
        }
        return this;
    }

    public void removeOnErrorEventListener(OnErrorEventListener onErrorEventListener) {
        if (onErrorEventListeners != null) {
            onErrorEventListeners.remove(onErrorEventListener);
        }
    }

    public int getCurrentProgress() {
        if (proxyDecoderCC.getDuration() == 0) {
            return 0;
        }
        return (int) (1000L * proxyDecoderCC.getCurrentPosition() / proxyDecoderCC.getDuration());
    }

    public void destroy() {
        detachListener();
        isAllowStopRemove = true;
        onPlayerEventListeners.clear();
        onErrorEventListeners.clear();
        if (mPlayerView != null) {
            mPlayerView.destroy();
            mPlayerView.removeEventProducer(networkEventProducer);
        }
        if (proxyDecoderCC != null) {
            proxyDecoderCC.destroy();
        }
        //移除某个声明的播放器客户端
        if (clientKey != null) {
            CLIENT_MAP.remove(clientKey);
        } else {
            CLIENT_MAP.clear();
        }
    }
}
