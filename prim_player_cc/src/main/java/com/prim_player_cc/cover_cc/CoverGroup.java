package com.prim_player_cc.cover_cc;

import android.text.TextUtils;

import com.prim_player_cc.cover_cc.listener.OnCoverGroupChangeListener;
import com.prim_player_cc.log.PrimLog;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc 覆盖视图组 主要存储不同功能的视图组件
 * 同时实现了对组件对热插拔
 * @time 2018/7/26 - 上午10:57
 */
public class CoverGroup implements ICoverGroup {

    /**
     * 存储视图的仓库
     */
    private Map<String, ICover> coverMap;

    /**
     * 存储视图的优先级队列，用于对视图排序
     */
//    private Deque<ICover> coverDeque;

    private WeakReference<OnCoverGroupChangeListener> onCoverGroupChangeListener;

    public CoverGroup() {
        //HashMap 在多线程的环境下会出现各种各样的问题，ConcurrentHashMap 可以支持16个线程并发操作
        coverMap = new ConcurrentHashMap<>(16);
//        coverDeque = new ArrayDeque<>();
    }

    /**
     * 添加视图组件
     *
     * @param key   组件标示key
     * @param cover 具体的组件
     */
    @Override
    public void addCover(String key, ICover cover) {
        addCover(key, cover, true);
    }

    @Override
    public void addCover(String key, ICover cover, boolean isListener) {
        if (isListener) {
            if (coverMap != null && cover != null && !TextUtils.isEmpty(key)) {
                cover.setCoverKey(key);
                coverMap.put(key, cover);
                bindAddCoverListener(key, cover);
            }
        } else {
            if (coverMap != null && cover != null && !TextUtils.isEmpty(key)) {
                cover.setCoverKey(key);
                coverMap.put(key, cover);
            }
        }
    }

    /**
     * 移除视图组件
     *
     * @param key 要移除的视图组件标示key
     * @return 移除的视图组件
     */
    @Override
    public ICover removeCover(String key) {
        return removeCover(key, true);
    }

    @Override
    public ICover removeCover(String key, boolean isListener) {
        if (isListener) {
            if (coverMap != null && !TextUtils.isEmpty(key)) {
                ICover remove = coverMap.remove(key);
                bindRemoveCoverListener(key, remove);
                return remove;
            }
        } else {
            if (coverMap != null && !TextUtils.isEmpty(key)) {
                return coverMap.remove(key);
            }
        }
        return null;
    }

    @Override
    public <T extends ICover> T getCover(String key) {
        if (coverMap != null) {
            return (T) coverMap.get(key);
        }
        return null;
    }

    @Override
    public void clearCovers() {
        for (Map.Entry<String, ICover> coverEntry : coverMap.entrySet()) {
            bindRemoveCoverListener(coverEntry.getKey(), coverEntry.getValue());
        }
        if (coverMap != null) {
            coverMap.clear();
        }

        if (coverList != null) {
            coverList.clear();
        }
    }

    @Override
    public int getCoverCount() {
        return coverMap.size();
    }

    @Override
    public Map<String, ICover> getCovers() {
        return coverMap;
    }

    @Override
    public boolean containsCover(String key) {
        return coverMap.containsKey(key);
    }

    @Override
    public void loopCovers(OnLoopCoverListener loopCoverListener) {
        for (Map.Entry<String, ICover> coverEntry : coverList) {
            loopCoverListener.getCover(coverEntry.getValue());
        }
    }

    private void bindAddCoverListener(String key, ICover cover) {
        if (onCoverGroupChangeListener != null && onCoverGroupChangeListener.get() != null) {
            onCoverGroupChangeListener.get().onAddCover(key, cover);
        }
    }

    private void bindRemoveCoverListener(String key, ICover cover) {
        if (onCoverGroupChangeListener != null && onCoverGroupChangeListener.get() != null) {
            onCoverGroupChangeListener.get().onRemoveCover(key, cover);
        }
    }

    @Override
    public void bindCoverGroupChangeListener(OnCoverGroupChangeListener onCoverGroupChangeListener) {
        this.onCoverGroupChangeListener = new WeakReference<>(onCoverGroupChangeListener);
    }

    @Override
    public void unBindCoverGroupChangeListener() {
        if (onCoverGroupChangeListener != null) {
            onCoverGroupChangeListener.clear();
            onCoverGroupChangeListener = null;
        }
    }

    private List<Map.Entry<String, ICover>> coverList;

    private static final String TAG = "CoverGroup";

    /**
     * 对覆盖视图组件排序 级别从低到高
     * 的顺序list.add 视图
     */
    @Override
    public void coverSort() {
        PrimLog.d(TAG, "排序-coverSort");
        coverList = new ArrayList<>(coverMap.entrySet());

        Collections.sort(coverList, new Comparator<Map.Entry<String, ICover>>() {
            @Override
            public int compare(Map.Entry<String, ICover> o1, Map.Entry<String, ICover> o2) {
                Integer x = 0;
                Integer y = 0;
                if (o1.getValue() instanceof BaseCover) {
                    x = o1.getValue().getCoverLevel();
                }
                if (o2.getValue() instanceof BaseCover) {
                    y = o2.getValue().getCoverLevel();
                }
                return x.compareTo(y);//从小到大排序
            }
        });

        for (int i = 0; i < coverList.size(); i++) {
            PrimLog.d(TAG, "排序后的值：" + coverList.get(i).getValue().getCoverLevel());
        }
    }
}
