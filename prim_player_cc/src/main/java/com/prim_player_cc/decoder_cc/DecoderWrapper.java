package com.prim_player_cc.decoder_cc;

/**
 * @author prim
 * @version 1.0.0
 * @desc player wrapper,解码器的包装类，主要记录了解码器的ID 实现类class 说明desc
 * @time 2018/7/24 - 下午4:18
 */
public class DecoderWrapper {

    private int playerId;

    private Class<? extends AbsDecoderCC> playerClass;

    private String playerDec;

    public DecoderWrapper(int playerId, Class<? extends AbsDecoderCC> playerClass) {
        this.playerId = playerId;
        this.playerClass = playerClass;
    }

    public DecoderWrapper(int playerId, Class<? extends AbsDecoderCC> playerClass, String playerDec) {
        this.playerId = playerId;
        this.playerClass = playerClass;
        this.playerDec = playerDec;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Class<? extends AbsDecoderCC> getPlayerClass() {
        return playerClass;
    }

    public String getPlayerDec() {
        return playerDec;
    }

    @Override
    public String toString() {
        return "DecoderWrapper{" +
                "playerId=" + playerId +
                ", playerClassName='" + playerClass.getSimpleName() + '\'' +
                ", playerDec='" + playerDec + '\'' +
                '}';
    }
}
