package com.prim_player_cc.cover_cc;

import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.view.BasePlayerCCView;

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

    /**
     * 设置一个新的视图组
     *
     * @param coverGroup {@link ICoverGroup}
     * @return CoverCCManager
     */
    public CoverCCManager setCoverGroup(ICoverGroup coverGroup) {
        this.coverGroup = coverGroup;
        return this;
    }

    /**
     * 向视图组添加一个视图
     *
     * @param key   视图唯一key
     * @param cover 视图
     * @return CoverCCManager
     */
    public CoverCCManager addCover(String key, ICover cover) {
        if (this.coverGroup == null) {
            this.coverGroup = new CoverGroup();
        }
        this.coverGroup.addCover(key, cover);
        return this;
    }

    private static final String TAG = "CoverCCManager";

    /**
     * 动态添加视图
     * 在初始化视图时，必须将一个视图组{@link #getCoverGroup()} 赋给
     * {@link com.prim_player_cc.view.BasePlayerCCView#setCoverGroup(ICoverGroup)}
     * 方法，这样才能通过{@link com.prim_player_cc.view.BusPlayerView#dynamicAttachCover(String, ICover)}}
     * 完成视图的动态插入。
     *
     * @param key   视图的唯一key
     * @param cover 视图
     * @return {@link CoverCCManager}
     */
    public CoverCCManager dynamicInsertCover(String key, ICover cover) {
        if (!containsCover(key)) {
            addCover(key, cover);
        } else {
            PrimLog.e(TAG, "存在对应key的视图:" + key +" 禁止重复添加");
        }
        return this;
    }

    /**
     * 动态移除一个视图
     *
     * @param key 视图唯一key
     * @return CoverCCManager
     */
    public CoverCCManager dynamicDeleteCover(String key) {
        if (containsCover(key)) {
            removeCover(key);
        } else {
            PrimLog.e(TAG,"不存在对应key的视图:"+key+" 不用移除");
        }
        return this;
    }

    /**
     * 根据key获取相关的视图
     *
     * @param key 视图的唯一key
     * @param <T> 具体视图的范型
     * @return 具体视图
     */
    public <T extends ICover> T getCover(String key) {
        if (this.coverGroup == null) {
            throw new RuntimeException("coverGroup must to be null,please setCoverGroup");
        }
        return coverGroup.getCover(key);
    }

    /**
     * 查找是否存在对应key的视图
     *
     * @param key 视图的唯一key
     * @return true 存在视图 false 不存在视图
     */
    public boolean containsCover(String key) {
        if (this.coverGroup == null) {
            throw new RuntimeException("coverGroup must to be null,please setCoverGroup");
        }
        return coverGroup.containsCover(key);
    }

    /**
     * 移除某个视图
     *
     * @param key 视图的唯一key
     */
    public void removeCover(String key) {
        if (this.coverGroup == null) {
            throw new RuntimeException("coverGroup must to be null,please setCoverGroup");
        }
        this.coverGroup.removeCover(key);
    }

    /**
     * 移除所有视图
     */
    public void removeAllCover() {
        if (this.coverGroup == null) {
            throw new RuntimeException("coverGroup must to be null,please setCoverGroup");
        }
        this.coverGroup.clearCovers();
    }

    /**
     * 向{@link BasePlayerCCView#setCoverGroup(ICoverGroup)} 多媒体view中，插入一个视图组
     *
     * @param ccView     {@link BasePlayerCCView} 多媒体view
     * @param coverGroup 视图组
     */
    public void insertCoverGroup(BasePlayerCCView ccView, ICoverGroup coverGroup) {
        if (coverGroup == null) {
            throw new RuntimeException("coverGroup must not null.");
        }
        ccView.setCoverGroup(coverGroup);
    }

    public void insertCoverGroup(BasePlayerCCView ccView) {
        if (coverGroup == null) {
            throw new RuntimeException("coverGroup must not null.");
        }
        ccView.setCoverGroup(coverGroup);
    }

}
