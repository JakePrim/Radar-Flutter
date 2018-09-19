package com.prim_player_cc.decoder_cc;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.loader.DecoderLoader;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * 本类使用代理模式
 * ProxyDecoderCC 也实现 IDecoder 接口，同时代理了{@link BaseDecoderCC}的具体实现者, 方便切换播放器组件和添加其他逻辑
 * @time 2018/7/24 - 上午11:26
 */
public class ProxyDecoderCC implements IDecoder {

    private static final String TAG = "ProxyDecoderCC";

    private static ProxyDecoderCC ourInstance;

    private BaseDecoderCC decoderCC;

    private int decoderId;

    public static ProxyDecoderCC getInstance() {
        if (ourInstance == null) {
            synchronized (ProxyDecoderCC.class) {
                if (ourInstance == null) {
                    ourInstance = new ProxyDecoderCC();
                }
            }
        }
        return ourInstance;
    }

    /**
     * 默认加载配置的 player ID
     * {@link PlayerCC_Config#usedDecoderId}
     */
    public ProxyDecoderCC() {
        loadDecoder(PlayerCC_Config.getUseDecoderId());
    }

    /**
     * load player 根据播放器ID 加载具体的播放器组件
     * {@link DecoderLoader#loadPlayer(int)}
     * @param decoderId player id
     */
    private void loadDecoder(int decoderId) {
        this.decoderId = decoderId;
        decoderCC = DecoderLoader.loadPlayer(this.decoderId);
        if (decoderCC == null) {
            throw new RuntimeException("build decoder failed,please check you config,decoderId:" + this.decoderId + " class not found");
        }
        DecoderWrapper decoder = PlayerCC_Config.getDecoder(this.decoderId);
        PrimLog.d(TAG, "load decoder :" + this.decoderId);
        PrimLog.d(TAG, "decoder class:" + decoder.getPlayerClass().getSimpleName());
        PrimLog.d(TAG, "decoder desc:" + decoder.getPlayerDec());
    }

    /**
     * 动态选择解码器组件
     *
     * @param decoderId player ID
     */
    public boolean switchDecoder(int decoderId) {
        if (this.decoderId == decoderId) {
            PrimLog.d(TAG, "current decoderId == switch decoderId");
            return false;
        }
        //检查播放器组件ID 是否存在
        if (PlayerCC_Config.checkDecoderId(decoderId)) {
            destroy();//将之前的解码器销毁掉，防止出现内存泄漏出现其他问题等
            PlayerCC_Config.setUseDecoderId(decoderId);//更换用户使用的解码器组件ID
            loadDecoder(decoderId);//加载播放器组件
            return true;
        } else {
            throw new RuntimeException("build decoder failed,please check you config,decoderId: " + decoderId + " not found");
        }
    }


    @Override
    public void start() {
        decoderCC.start();
    }

    @Override
    public void start(long location) {
        decoderCC.start(location);
    }

    @Override
    public void pause() {
        decoderCC.pause();
    }

    @Override
    public void resume() {
        decoderCC.resume();
    }

    @Override
    public void reset() {
        decoderCC.reset();
    }

    @Override
    public void stop() {
        decoderCC.stop();
    }

    @Override
    public void destroy() {
        decoderCC.destroy();
    }

    @Override
    public void seek(int position) {
        decoderCC.seek(position);
    }

    /**
     * 设置是否循环播放
     *
     * @param loop true 循环播放 false 不循环播放
     */
    @Override
    public void setLooping(boolean loop) {
        decoderCC.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return decoderCC.isLooping();
    }

    @Override
    public int getState() {
        return decoderCC.getState();
    }

    @Override
    public boolean isPlaying() {
        return decoderCC.isPlaying();
    }

    @Override
    public long getCurrentPosition() {
        return decoderCC.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return decoderCC.getDuration();
    }

    @Override
    public int getBufferPercentage() {
        return decoderCC.getBufferPercentage();
    }

    @Override
    public void setVolume(float left, float right) {
        decoderCC.setVolume(left, right);
    }

    @Override
    public void setSpeed(float m) {
        decoderCC.setSpeed(m);
    }

    @Override
    public void setDataSource(PlayerSource source) {
        decoderCC.setDataSource(source);
    }

    @Override
    public void setPlayerEventListener(OnPlayerEventListener onPlayingListener) {
          decoderCC.setPlayerEventListener(onPlayingListener);
    }

    @Override
    public void setOnErrorEventListener(OnErrorEventListener onErrorListener) {
          decoderCC.setOnErrorEventListener(onErrorListener);
    }

    @Override
    public void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
          decoderCC.setBufferingUpdateListener(onBufferingUpdateListener);
    }

    @Override
    public void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
        decoderCC.setOnTimerUpdateListener(onTimerUpdateListener);
    }

    @Override
    public void setSurface(Surface surface) {
        decoderCC.setSurface(surface);
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        decoderCC.setDisplay(surfaceHolder);
    }

    @Override
    public void bindVideoView(View videoView) {
        decoderCC.bindVideoView(videoView);
    }

    @Override
    public View getRenderView() {
        return decoderCC.getRenderView();
    }

    @Override
    public int getVideoWidth() {
        return decoderCC.getVideoWidth();
    }

    @Override
    public int getVideoHeight() {
        return decoderCC.getVideoHeight();
    }
}
