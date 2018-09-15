package com.prim_player_cc;

import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.cover_cc.CoverCCManager;
import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.cover_cc.ICoverGroup;

/**
 * @author prim
 * @version 1.0.0
 * @desc prim player cc total entrance
 * 播放器组件库的总入口
 * @time 2018/7/30 - 上午10:22
 */
public class PrimPlayerCC {

    private static PrimPlayerCC instance;

    public static PrimPlayerCC getInstance() {
        if (instance == null) {
            synchronized (PrimPlayerCC.class) {
                if (instance == null) {
                    instance = new PrimPlayerCC();
                }
            }
        }
        return instance;
    }


    public PrimPlayerCC() {
    }

    //----------------------------- 初始化相关 -----------------------------//

    public PlayerCC_Config.Builder init() {
        return PlayerCC_Config.configBuild();
    }

    //--------------------------------- 视图组件相关 视图的热插拔 --------------------------------//

    public CoverCCManager addCover(String key, ICover cover) {
        return CoverCCManager.getInstance().addCover(key, cover);
    }

    public <T extends ICover> T getCover(String key) {
        return CoverCCManager.getInstance().getCover(key);
    }

    public void removeCover(String key) {
        CoverCCManager.getInstance().removeCover(key);
    }

    public void removeAllCover() {
        CoverCCManager.getInstance().removeAllCover();
    }

    public ICoverGroup getCoverGroup(){
        return CoverCCManager.getInstance().getCoverGroup();
    }

    //-------------------------------- 播放相关 ------------------------------------//



}
