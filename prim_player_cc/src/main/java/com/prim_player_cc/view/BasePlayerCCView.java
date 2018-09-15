package com.prim_player_cc.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.prim_player_cc.cover_cc.CoverCCManager;
import com.prim_player_cc.cover_cc.CoverGroup;
import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.decoder_cc.ProxyDecoderCC;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.defualt.DefaultControlCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCompleteCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCoverKey;
import com.prim_player_cc.cover_cc.defualt.DefaultErrorCover;
import com.prim_player_cc.cover_cc.defualt.DefaultLoadCover;
import com.prim_player_cc.decoder_cc.listener.OnErrorListener;
import com.prim_player_cc.decoder_cc.listener.OnPreparedListener;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.render_cc.IRender;
import com.prim_player_cc.render_cc.RenderSurfaceView;
import com.prim_player_cc.render_cc.RenderTextureView;
import com.prim_player_cc.state.State;

/**
 * @author prim
 * @version 1.0.0
 * @desc playerCC base view 播放器组件的基础view
 * 主要承载了:
 * 1、加载覆盖视图组件
 * 2、加载呈现视频视图组件
 * 3、同时还有播放操作
 * 4、如有其他逻辑可在继承此类基础上扩展
 * @time 2018/7/24 - 下午5:08
 */
public abstract class BasePlayerCCView extends FrameLayout implements IPlayerCCView {

    private static final String TAG = "BasePlayerCCView";

    protected ProxyDecoderCC decoderCC;

    protected BusPlayerView busPlayerView;

    protected IRender render;

    protected View renderView;

    protected ICoverGroup coverGroup;

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
        PrimLog.d(TAG, "build player cc View");
        decoderCC = ProxyDecoderCC.getInstance();
        //初始化视图组
        coverGroup = new CoverGroup();
        CoverCCManager.getInstance().setCoverGroup(coverGroup);
        //初始化视图组件总线的view
        busPlayerView = new BusPlayerView(context);
        //将视图组件总线view 添加到 视频组件基类view中 在最底层
        addView(busPlayerView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initView();
        _initListener();
    }

    private void _initListener() {
        decoderCC.setPreparedListener(preparedListener);
        decoderCC.setOnErrorListener(errorListener);
    }

    protected abstract void initView();

    @Override
    public void setLooping(boolean loop) {
        decoderCC.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return decoderCC.isLooping();
    }

    /**
     * 添加覆盖视图分组
     *
     * @param coverGroup {@link ICoverGroup}
     */
    @Override
    public void setCoverGroup(ICoverGroup coverGroup) {
        if (busPlayerView != null) {
            busPlayerView.setCoverGroup(coverGroup);
        }
    }

    /**
     * 获取覆盖视图组
     *
     * @return {@link ICoverGroup}
     */
    @Override
    public ICoverGroup getCoverGroup() {
        if (busPlayerView != null) {
            return busPlayerView.getCoverGroup();
        }
        return null;
    }

    /**
     * 切换解码器组件
     *
     * @param decoderId 解码器组件ID
     */
    @Override
    public void switchDecoder(int decoderId) {
        decoderCC.switchDecoder(decoderId);
    }

    /**
     * 设置播放资源数据
     *
     * @param dataSource 资源数据
     */
    @Override
    public void setDataSource(PlayerSource dataSource) {
        decoderCC.setDataSource(dataSource);
    }

    /**
     * 如果自己已经实现了
     * SurfaceView 或 TextureView，
     * 便不需要 本库中的view ，将此view绑定到组件播放器view
     */
    private void bindVideoView() {
        decoderCC.bindVideoView(this);
    }

    /**
     * 获取视图总线组件view
     *
     * @return {@link BusPlayerView}
     */
    @Override
    public BusPlayerView getBusPlayerView() {
        return busPlayerView;
    }

    /**
     * 使用默认的覆盖视图组件
     */
    @Override
    public void usedDefaultCoverGroup() {
        CoverCCManager.getInstance()
                .addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(getContext()))
                .addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(getContext()))
                .addCover(DefaultCoverKey.DEFAULT_ERROR_COVER, new DefaultErrorCover(getContext()))
                .addCover(DefaultCoverKey.DEFAULT_COMPLETE_COVER, new DefaultCompleteCover(getContext()));
        setCoverGroup(CoverCCManager.getInstance().getCoverGroup());
    }


    @Override
    public void setRenderView(int type) {
        switch (type) {
            case IRender.SURFACE_VIEW:
                render = new RenderSurfaceView(getContext());
                addDefaultRenderView();
                break;
            case IRender.CUSTOM_VIEW:
                renderView = decoderCC.getRenderView();
                addCustomRenderView();
                break;
            default:
                render = new RenderTextureView(getContext());
                addDefaultRenderView();
                break;
        }
    }

    private void addCustomRenderView() {
        decoderCC.setDisplay(null);
        decoderCC.setSurface(null);
        busPlayerView.setRenderView(renderView);
    }

    private void addDefaultRenderView() {
        decoderCC.setDisplay(null);
        decoderCC.setSurface(null);
        render.updateRenderSize(decoderCC.getVideoWidth(), decoderCC.getVideoHeight());
        render.setRenderCallback(new RenderCallBack());
        renderView = render.getRenderView();
        busPlayerView.setRenderView(renderView);
    }

    class RenderCallBack implements IRender.IRenderCallback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (holder != null) {
                decoderCC.setDisplay(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    //----------------- 播放监听相关 -------------------//
    OnPreparedListener preparedListener = new OnPreparedListener() {
        @Override
        public void onPrepared(Bundle bundle, int i) {
            if (busPlayerView != null) {
                busPlayerView.dispatchPlayEvent(i, bundle);
            }
        }
    };

    OnErrorListener errorListener = new OnErrorListener() {
        @Override
        public boolean onError(Bundle bundle, int errorCode) {
            return false;
        }
    };


    //------------------ 播放控制相关 -------------------//

    /**
     * 开始播放
     */
    @Override
    public void start() {
        decoderCC.start();
    }

    /**
     * 开始从某个位置播放
     *
     * @param location
     */
    @Override
    public void start(long location) {
        decoderCC.start(location);
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        decoderCC.pause();
    }

    /**
     * 继续播放
     */
    @Override
    public void resume() {
        decoderCC.resume();
    }

    /**
     * 重置播放
     */
    @Override
    public void reset() {
        decoderCC.reset();
    }

    /**
     * 停止播放
     */
    @Override
    public void stop() {
        decoderCC.stop();
    }

    /**
     * 销毁播放器
     */
    @Override
    public void destroy() {
        decoderCC.destroy();
        busPlayerView.destroy();
    }

    /**
     * 跳转到某个位置播放
     *
     * @param position
     */
    @Override
    public void seek(int position) {
        decoderCC.seek(position);
    }

    /**
     * 获取播放状态
     *
     * @return {@link com.prim_player_cc.state.State}
     */
    @Override
    public int getState() {
        return decoderCC.getState();
    }

    /**
     * 是否正在播放中
     *
     * @return true 正在播放 false 没有播放
     */
    @Override
    public boolean isPlaying() {
        return decoderCC.isPlaying();
    }

    /**
     * 获取当前到播放进度
     *
     * @return long
     */
    @Override
    public long getCurrentPosition() {
        return decoderCC.getCurrentPosition();
    }

    /**
     * 获取总到播放进度
     *
     * @return long
     */
    @Override
    public long getDuration() {
        return decoderCC.getDuration();
    }

    /**
     * 获取缓存进度
     *
     * @return long
     */
    @Override
    public int getBufferPercentage() {
        return decoderCC.getBufferPercentage();
    }

    /**
     * 设置声音
     *
     * @param left
     * @param right
     */
    @Override
    public void setVolume(float left, float right) {
        decoderCC.setVolume(left, right);
    }

    /**
     * 设置播放速度
     *
     * @param m
     */
    @Override
    public void setSpeed(float m) {
        decoderCC.setSpeed(m);
    }
}
