package com.prim_player_cc;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.data.PlayerSource;
import com.prim_player_cc.data.PlayerWrapper;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.player_cc.BasePlayerCC;
import com.prim_player_cc.player_cc.OnBufferingUpdateListener;
import com.prim_player_cc.player_cc.OnCompletionListener;
import com.prim_player_cc.player_cc.OnErrorListener;
import com.prim_player_cc.player_cc.OnInfoListener;
import com.prim_player_cc.player_cc.OnPlayingListener;
import com.prim_player_cc.player_cc.OnPreparedListener;
import com.prim_player_cc.loader.PlayerLoader;

/**
 * @author prim
 * @version 1.0.0
 * @desc prim player cc total entrance
 * 本类使用代理模式
 * ProxyPlayerCC 也实现 IPlayer 接口，同时代理了{@link BasePlayerCC}的具体实现者, 方便切换播放器组件和添加其他逻辑
 * @time 2018/7/24 - 上午11:26
 */
public class ProxyPlayerCC implements IPlayer {

    private static final String TAG = "ProxyPlayerCC";

    private static ProxyPlayerCC ourInstance;

    private BasePlayerCC playerCC;

    private int playerId;

    public static ProxyPlayerCC getInstance() {
        if (ourInstance == null) {
            synchronized (ProxyPlayerCC.class) {
                if (ourInstance == null) {
                    ourInstance = new ProxyPlayerCC();
                }
            }
        }
        return ourInstance;
    }

    /**
     * 默认加载配置的 player ID
     * {@link PlayerCC_Config#usedPlayerId}
     */
    public ProxyPlayerCC() {
        loadPlayer(PlayerCC_Config.getUsePlayerId());
    }

    /**
     * load player 根据播放器ID 加载具体的播放器组件
     *
     * @param playerId player id
     */
    private void loadPlayer(int playerId) {
        this.playerId = playerId;
        playerCC = PlayerLoader.loadPlayer(playerId);
        if (playerCC == null) {
            throw new RuntimeException("init player failed,please check you config,payerId:" + playerId + " class not found");
        }
        PlayerWrapper player = PlayerCC_Config.getPlayer(playerId);
        PrimLog.d(TAG, "load player :" + playerId);
        PrimLog.d(TAG, "player class:" + player.getPlayerClass().getSimpleName());
        PrimLog.d(TAG, "player desc:" + player.getPlayerDec());
    }

    /**
     * 动态选择播放器组件
     *
     * @param playerId player ID
     */
    public boolean switchPlayer(int playerId) {
        if (this.playerId == playerId) {
            PrimLog.d(TAG, "current playerId == switch playerId");
            return false;
        }
        //检查播放器组件ID 是否存在
        if (PlayerCC_Config.checkPlayerId(playerId)) {
            destroy();//将之前的播放器销毁掉，防止出现内存泄漏
            PlayerCC_Config.setUsePlayerId(playerId);//更换用户使用的播放器组件ID
            loadPlayer(playerId);//加载播放器组件
            return true;
        } else {
            throw new RuntimeException("init player failed,please check you config,payerId: " + playerId + " not found");
        }
    }


    @Override
    public void start() {
        playerCC.start();
    }

    @Override
    public void start(long location) {
        playerCC.start(location);
    }

    @Override
    public void pause() {
        playerCC.pause();
    }

    @Override
    public void resume() {
        playerCC.resume();
    }

    @Override
    public void reset() {
        playerCC.reset();
    }

    @Override
    public void stop() {
        playerCC.stop();
    }

    @Override
    public void destroy() {
        playerCC.destroy();
    }

    @Override
    public void seek(int position) {
        playerCC.seek(position);
    }

    /**
     * 设置是否循环播放
     *
     * @param loop true 循环播放 false 不循环播放
     */
    @Override
    public void setLooping(boolean loop) {
        playerCC.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return playerCC.isLooping();
    }

    @Override
    public int getState() {
        return playerCC.getState();
    }

    @Override
    public boolean isPlaying() {
        return playerCC.isPlaying();
    }

    @Override
    public long getCurrentPosition() {
        return playerCC.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return playerCC.getDuration();
    }

    @Override
    public int getBufferPercentage() {
        return playerCC.getBufferPercentage();
    }

    @Override
    public void setVolume(float left, float right) {
        playerCC.setVolume(left, right);
    }

    @Override
    public void setSpeed(float m) {
        playerCC.setSpeed(m);
    }

    @Override
    public void setDataSource(PlayerSource source) {
        playerCC.setDataSource(source);
    }

    @Override
    public void setPreparedListener(OnPreparedListener onPreparedListener) {

    }

    @Override
    public void setPlayingListener(OnPlayingListener onPlayingListener) {

    }

    @Override
    public void setOnInfoListener(OnInfoListener onInfoListener) {

    }

    @Override
    public void setOnErrorListener(OnErrorListener onErrorListener) {

    }

    @Override
    public void setCompletionListener(OnCompletionListener onCompletionListener) {

    }

    @Override
    public void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {

    }

    @Override
    public void setSurface(Surface surface) {
        playerCC.setSurface(surface);
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        playerCC.setDisplay(surfaceHolder);
    }

    @Override
    public void bindVideoView(View videoView) {
        playerCC.bindVideoView(videoView);
    }
}
