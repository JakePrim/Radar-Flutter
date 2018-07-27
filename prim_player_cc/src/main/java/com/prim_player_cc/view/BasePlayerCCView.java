package com.prim_player_cc.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.prim_player_cc.ProxyPlayerCC;
import com.prim_player_cc.cover_cc.CoverGroup;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.defualt.DefalutControlCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCompleteCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCoverKey;
import com.prim_player_cc.cover_cc.defualt.DefaultErrorCover;
import com.prim_player_cc.cover_cc.defualt.DefaultLoadCover;
import com.prim_player_cc.data.PlayerSource;
import com.prim_player_cc.log.PrimLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc playerCC base view 播放器组件的view
 * 主要加载视图组件，同时还有播放操作
 * 如有其他逻辑可在继承此类基础上扩展
 * @time 2018/7/24 - 下午5:08
 */
public abstract class BasePlayerCCView extends FrameLayout implements IPlayerCCView {

    private static final String TAG = "BasePlayerCCView";

    protected ProxyPlayerCC playerCC;

    protected BusPlayerView busCoverView;

    public BasePlayerCCView(@NonNull Context context) {
        this(context, null);
    }

    public BasePlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BasePlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BasePlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void _init(Context context, AttributeSet attrs, int defStyleAttr) {
        PrimLog.d(TAG, "init player cc View");
        playerCC = ProxyPlayerCC.getInstance();
        //初始化视图组件总线的view
        busCoverView = new BusPlayerView(context);
        //将视图组件总线view 添加到 视频组件基类view中 在最底层
        addView(busCoverView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initView();
    }

    protected abstract void initView();

    @Override
    public void setLooping(boolean loop) {
        playerCC.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return playerCC.isLooping();
    }

    /**
     * 添加覆盖视图分组
     *
     * @param coverGroup
     */
    public void setCoverGroup(ICoverGroup coverGroup) {
        if (busCoverView != null) {
            busCoverView.setCoverGroup(coverGroup);
        }
    }

    /**
     * 获取覆盖视图组
     * @return {@link ICoverGroup}
     */
    @Override
    public ICoverGroup getCoverGroup() {
        if (busCoverView != null) {
            return busCoverView.getCoverGroup();
        }
        return null;
    }

    /**
     * 切换播放器组件
     *
     * @param playerId 播放器组件ID
     */
    @Override
    public void switchPlayer(int playerId) {
        playerCC.switchPlayer(playerId);
    }

    /**
     * 设置播放资源数据
     *
     * @param dataSource 资源数据
     */
    @Override
    public void setDataSource(PlayerSource dataSource) {
        playerCC.setDataSource(dataSource);
    }

    /**
     * 如果自己已经实现了
     * SurfaceView 或 TextureView，
     * 便不需要 本库中的view ，将此view绑定到组件播放器view
     */
    private void bindVideoView() {
        playerCC.bindVideoView(this);
    }

    /**
     * 获取视图总线组件view
     *
     * @return
     */
    @Override
    public BusPlayerView getBusCoverView() {
        return busCoverView;
    }

    /**
     * 使用默认的覆盖视图组件
     */
    @Override
    public void usedDefaultCoverGroup() {
        CoverGroup coverGroup = new CoverGroup();
        coverGroup.addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(getContext()));
        coverGroup.addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefalutControlCover(getContext()));
        coverGroup.addCover(DefaultCoverKey.DEFAULT_ERROR_COVER, new DefaultErrorCover(getContext()));
        coverGroup.addCover(DefaultCoverKey.DEFAULT_COMPLETE_COVER, new DefaultCompleteCover(getContext()));
        setCoverGroup(coverGroup);
    }

    /**
     * 开始播放
     */
    @Override
    public void start() {
        playerCC.start();
    }

    /**
     * 开始从某个位置播放
     *
     * @param location
     */
    @Override
    public void start(long location) {
        playerCC.start(location);
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        playerCC.pause();
    }

    /**
     * 继续播放
     */
    @Override
    public void resume() {
        playerCC.resume();
    }

    /**
     * 重置播放
     */
    @Override
    public void reset() {
        playerCC.reset();
    }

    /**
     * 停止播放
     */
    @Override
    public void stop() {
        playerCC.stop();
    }

    /**
     * 销毁播放器
     */
    @Override
    public void destroy() {
        playerCC.destroy();
        busCoverView.destroy();
    }

    /**
     * 跳转到某个位置播放
     *
     * @param position
     */
    @Override
    public void seek(int position) {
        playerCC.seek(position);
    }

    /**
     * 获取播放状态
     *
     * @return {@link com.prim_player_cc.state.State}
     */
    @Override
    public int getState() {
        return playerCC.getState();
    }

    /**
     * 是否正在播放中
     *
     * @return true 正在播放 false 没有播放
     */
    @Override
    public boolean isPlaying() {
        return playerCC.isPlaying();
    }

    /**
     * 获取当前到播放进度
     *
     * @return long
     */
    @Override
    public long getCurrentPosition() {
        return playerCC.getCurrentPosition();
    }

    /**
     * 获取总到播放进度
     *
     * @return long
     */
    @Override
    public long getDuration() {
        return playerCC.getDuration();
    }

    /**
     * 获取缓存
     *
     * @return
     */
    @Override
    public int getBufferPercentage() {
        return playerCC.getBufferPercentage();
    }

    /**
     * 设置声音
     *
     * @param left
     * @param right
     */
    @Override
    public void setVolume(float left, float right) {
        playerCC.setVolume(left, right);
    }

    /**
     * 设置播放速度
     *
     * @param m
     */
    @Override
    public void setSpeed(float m) {
        playerCC.setSpeed(m);
    }
}
