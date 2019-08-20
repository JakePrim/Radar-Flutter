package com.prim_player_cc.loader;

import com.prim_player_cc.config.PrimPlayerConfig;
import com.prim_player_cc.decoder_cc.DecoderWrapper;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.decoder_cc.AbsDecoderCC;

/**
 * @author prim
 * @version 1.0.0
 * @desc load decoder 加载解码器
 * @time 2018/7/24 - 下午4:52
 */
public class DecoderLoader {

    /**
     * 解码器ID加载 仓库中存储的具体解码器组件
     *
     * @param decoderId 仓库中存储的解码器组件ID 标示
     * @return {@link AbsDecoderCC} 具体实现的解码器组件
     */
    public static AbsDecoderCC loadPlayer(int decoderId) {
        if (!PrimPlayerConfig.checkDecoderId(decoderId)) {
            throw new RuntimeException("playerId :" + decoderId + " not exist,please check whether set player id.");
        }
        AbsDecoderCC basePlayerCC = null;
        try {
            DecoderWrapper wrapper = PrimPlayerConfig.getDecoder(decoderId);
            Class<? extends AbsDecoderCC> playerClass = wrapper.getPlayerClass();
            basePlayerCC = playerClass.getConstructor().newInstance();
        } catch (Exception e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
        }
        return basePlayerCC;
    }

    /**
     * 获取正在使用的 解码器组件
     * @return {@link AbsDecoderCC}
     */
    public static AbsDecoderCC getUsedDecoder() {
        AbsDecoderCC basePlayerCC = null;
        try {
            DecoderWrapper usedPlayer = PrimPlayerConfig.getUsedDecoder();
            Class<? extends AbsDecoderCC> playerClass = usedPlayer.getPlayerClass();
            basePlayerCC = playerClass.getConstructor().newInstance();
        } catch (Exception e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
        }
        return basePlayerCC;
    }
}
