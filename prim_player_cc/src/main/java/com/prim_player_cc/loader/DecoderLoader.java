package com.prim_player_cc.loader;

import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.decoder_cc.DecoderWrapper;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.decoder_cc.BaseDecoderCC;

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
     * @return {@link BaseDecoderCC} 具体实现的解码器组件
     */
    public static BaseDecoderCC loadPlayer(int decoderId) {
        if (!PlayerCC_Config.checkDecoderId(decoderId)) {
            throw new RuntimeException("playerId :" + decoderId + " not exist,please check whether set player id.");
        }
        BaseDecoderCC basePlayerCC = null;
        try {
            DecoderWrapper wrapper = PlayerCC_Config.getDecoder(decoderId);
            Class<? extends BaseDecoderCC> playerClass = wrapper.getPlayerClass();
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
     * @return {@link BaseDecoderCC}
     */
    public static BaseDecoderCC getUsedDecoder() {
        BaseDecoderCC basePlayerCC = null;
        try {
            DecoderWrapper usedPlayer = PlayerCC_Config.getUsedDecoder();
            Class<? extends BaseDecoderCC> playerClass = usedPlayer.getPlayerClass();
            basePlayerCC = playerClass.getConstructor().newInstance();
        } catch (Exception e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
        }
        return basePlayerCC;
    }
}
