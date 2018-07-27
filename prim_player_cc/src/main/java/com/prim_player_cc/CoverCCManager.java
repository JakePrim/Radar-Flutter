package com.prim_player_cc;

import com.prim_player_cc.cover_cc.CoverGroup;
import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.cover_cc.ICoverGroup;

/**
 * @author prim
 * @version 1.0.0
 * @desc 覆盖视图组件管理器, 确保全局只用一个coverGroup，来有效的添加和移除视图组件
 * @time 2018/7/26 - 下午6:21
 */
public class CoverCCManager {
    private ICoverGroup coverGroup;

    private static CoverCCManager ourInstance;

    public static CoverCCManager getInstance() {
        if (ourInstance == null) {
            synchronized (CoverCCManager.class) {
                if (ourInstance == null) {
                    ourInstance = new CoverCCManager();
                }
            }
        }
        return ourInstance;
    }

    private CoverCCManager() {
        coverGroup = new CoverGroup();
    }



    public ICoverGroup getCoverGroup() {
        return coverGroup;
    }

    public CoverCCManager setCoverGroup(ICoverGroup coverGroup) {
        this.coverGroup = coverGroup;
        return this;
    }

    public CoverCCManager addCover(String key, ICover cover) {
        this.coverGroup.addCover(key, cover);
        return this;
    }

    public <T extends ICover> T getCover(String key) {
        return coverGroup.getCover(key);
    }

    public void removeCover(String key) {
        this.coverGroup.removeCover(key);
    }

    public void removeAllCover() {
        this.coverGroup.clearCovers();
    }
}
