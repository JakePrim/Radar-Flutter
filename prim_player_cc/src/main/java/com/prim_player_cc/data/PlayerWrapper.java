package com.prim_player_cc.data;

import com.prim_player_cc.player_cc.BasePlayerCC;

/**
 * @author prim
 * @version 1.0.0
 * @desc player wrapper,播放器的包装类，主要记录了播放器的ID 实现类class 说明desc
 * @time 2018/7/24 - 下午4:18
 */
public class PlayerWrapper {

    private int playerId;

    private Class<? extends BasePlayerCC> playerClass;

    private String playerDec;

    public PlayerWrapper(int playerId, Class<? extends BasePlayerCC> playerClass) {
        this.playerId = playerId;
        this.playerClass = playerClass;
    }

    public PlayerWrapper(int playerId, Class<? extends BasePlayerCC> playerClass, String playerDec) {
        this.playerId = playerId;
        this.playerClass = playerClass;
        this.playerDec = playerDec;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Class<? extends BasePlayerCC> getPlayerClass() {
        return playerClass;
    }

    public String getPlayerDec() {
        return playerDec;
    }

    @Override
    public String toString() {
        return "PlayerWrapper{" +
                "playerId=" + playerId +
                ", playerClassName='" + playerClass.getSimpleName() + '\'' +
                ", playerDec='" + playerDec + '\'' +
                '}';
    }
}
