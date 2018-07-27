package com.prim_player_cc.loader;

import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.data.PlayerWrapper;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.player_cc.BasePlayerCC;

/**
 * @author prim
 * @version 1.0.0
 * @desc load player - loader
 * @time 2018/7/24 - 下午4:52
 */
public class PlayerLoader {

    /**
     * 播放器ID加载 仓库中存储的具体播放器组件
     *
     * @param playerId 仓库中存储的播放器组件ID 标示
     * @return {@link BasePlayerCC} 具体实现的播放器组件
     */
    public static BasePlayerCC loadPlayer(int playerId) {
        if (!PlayerCC_Config.checkPlayerId(playerId)) {
            throw new RuntimeException("playerId :" + playerId + " not exist,please check whether set player id.");
        }
        BasePlayerCC basePlayerCC = null;
        try {
            PlayerWrapper wrapper = PlayerCC_Config.getPlayer(playerId);
            Class<? extends BasePlayerCC> playerClass = wrapper.getPlayerClass();
            basePlayerCC = playerClass.getConstructor().newInstance();
        } catch (Exception e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
        }
        return basePlayerCC;
    }

    public static BasePlayerCC getUsedPlayer() {
        BasePlayerCC basePlayerCC = null;
        try {
            PlayerWrapper usedPlayer = PlayerCC_Config.getUsedPlayer();
            Class<? extends BasePlayerCC> playerClass = usedPlayer.getPlayerClass();
            basePlayerCC = playerClass.getConstructor().newInstance();
        } catch (Exception e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
        }
        return basePlayerCC;
    }
}
