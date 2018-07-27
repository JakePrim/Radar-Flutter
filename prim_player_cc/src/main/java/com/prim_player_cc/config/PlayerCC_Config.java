package com.prim_player_cc.config;

import android.app.Application;
import android.util.SparseArray;

import com.prim_player_cc.data.PlayerWrapper;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.player_cc.DefaultPlayer;

/**
 * @author prim
 * @version 1.0.0
 * @desc prim player cc config ,recommend application init config
 * 需要在application中初始化播放器组件配置
 * @time 2018/7/24 - 下午2:14
 */
public class PlayerCC_Config {

    private static final String TAG = "PlayerCC_Config";

    /**
     * 默认的播放器ID 使用的播放器是系统自带的player
     */
    public static int DEFAULT_PLAYER_ID = -1;

    /**
     * 记录当前使用播放器组件
     */
    public static int usedPlayerId = DEFAULT_PLAYER_ID;

    /**
     * 存储播放器仓库
     */
    public static SparseArray<PlayerWrapper> mPlayers;

    static {
        mPlayers = new SparseArray<>();
        PlayerWrapper wrapper = new PlayerWrapper(DEFAULT_PLAYER_ID, DefaultPlayer.class, "default player");
        mPlayers.put(DEFAULT_PLAYER_ID, wrapper);
    }

    public static Builder configBuild() {
        return new Builder();
    }

    /**
     * get player
     * 根据ID获取播放器
     *
     * @return player ID
     */
    public static PlayerWrapper getPlayer(int playerId) {
        return mPlayers.get(playerId);
    }

    /**
     * get used player
     * 获取正在使用的播放器
     *
     * @return {@link PlayerWrapper}
     */
    public static PlayerWrapper getUsedPlayer() {
        return getPlayer(usedPlayerId);
    }

    /**
     * set used player id
     * 设置要使用的播放器ID
     *
     * @param id player id
     */
    public static void setUsePlayerId(int id) {
        usedPlayerId = id;
    }

    /**
     * 获取挡墙使用的播放器ID
     *
     * @return player id
     */
    public static int getUsePlayerId() {
        return usedPlayerId;
    }

    /**
     * 检查播放器ID是否存在仓库中
     *
     * @param id
     * @return
     */
    public static boolean checkPlayerId(int id) {
        PlayerWrapper wrapper = getPlayer(id);
        return wrapper != null;
    }

    public static class Builder {
        public Builder addPlayer(PlayerWrapper wrapper) {
            if (wrapper == null) {
                throw new NullPointerException("PlayerWrapper must not to be null");
            }
            mPlayers.put(wrapper.getPlayerId(), wrapper);
            return this;
        }

        public Builder setUsePlayerId(int id) {
            usedPlayerId = id;
            return this;
        }

        public Builder setLogEnable(boolean enable) {
            PrimLog.LOG_OPEN = enable;
            return this;
        }

        public void init(Application application) {
            PrimLog.d(TAG, "init success,usedPlayerId:" + usedPlayerId);
            ApplicationAttach.attach(application);
        }
    }
}
