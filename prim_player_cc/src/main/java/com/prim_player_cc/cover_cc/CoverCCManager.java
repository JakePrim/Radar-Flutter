package com.prim_player_cc.cover_cc;

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

    }

    public ICoverGroup getCoverGroup() {
        return coverGroup;
    }

    public CoverCCManager setCoverGroup(ICoverGroup coverGroup) {
        this.coverGroup = coverGroup;
        return this;
    }

    public CoverCCManager setCoverGroup() {
        this.coverGroup = new CoverGroup();
        return this;
    }

    public CoverCCManager addCover(String key, ICover cover) {
        if (this.coverGroup == null) {
            throw new RuntimeException("coverGroup must to be null,please setCoverGroup");
        }
        this.coverGroup.addCover(key, cover);
        return this;
    }

    public <T extends ICover> T getCover(String key) {
        if (this.coverGroup == null) {
            throw new RuntimeException("coverGroup must to be null,please setCoverGroup");
        }
        return coverGroup.getCover(key);
    }

    public void removeCover(String key) {
        if (this.coverGroup == null) {
            throw new RuntimeException("coverGroup must to be null,please setCoverGroup");
        }
        this.coverGroup.removeCover(key);
    }

    public void removeAllCover() {
        if (this.coverGroup == null) {
            throw new RuntimeException("coverGroup must to be null,please setCoverGroup");
        }
        this.coverGroup.clearCovers();
    }
}
