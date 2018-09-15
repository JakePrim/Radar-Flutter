package com.prim_player_cc;

import com.prim_player_cc.config.PlayerCC_Config;

/**
 * @author prim
 * @version 1.0.0
 * @desc prim player cc total entrance
 * 播放器组件库的总入口
 * @time 2018/7/30 - 上午10:22
 */
public class PrimPlayerCC {

    private static PrimPlayerCC instance;

    public static PrimPlayerCC getInstance(){
        if (instance == null){
            synchronized (PrimPlayerCC.class){
                if (instance == null){
                    instance = new PrimPlayerCC();
                }
            }
        }
        return instance;
    }

    public PrimPlayerCC() {
    }

    public PlayerCC_Config.Builder init(){
        return PlayerCC_Config.configBuild();
    }


}
