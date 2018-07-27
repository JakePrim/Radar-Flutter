package com.prim_player_cc.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.cover_cc.IBusCover;
import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.OnCoverEventListener;
import com.prim_player_cc.cover_cc.OnCoverGroupChangeListener;
import com.prim_player_cc.cover_cc.control.DefaultCoverControl;
import com.prim_player_cc.cover_cc.control.ICoverControl;
import com.prim_player_cc.log.PrimLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图组件的总线view
 * @time 2018/7/25 - 下午2:47
 */
public class BusPlayerView extends FrameLayout implements IBusCover {

    private ICoverGroup coverGroup;

    private ICoverControl coverControl;

    private static final String TAG = "BusPlayerView";

    public BusPlayerView(@NonNull Context context) {
        super(context);
        _init(context);
    }

    private void _init(Context context) {
        PrimLog.d(TAG, "init Bus View");
        initRenderView(context);
        initCoverControl(context);
    }

    /**
     * 初始化video view
     *
     * @param context Context
     */
    private void initRenderView(Context context) {

    }

    /**
     * 初始化覆盖视图组件控制器,控制器必须在video view 上面
     * 并将控制器的root view 添加到 视图总线的view中
     *
     * @param context Context
     */
    private void initCoverControl(Context context) {
        coverControl = getCoverControl(context);
        if (coverControl.getCoverRootView() != null) {
            addView(coverControl.getCoverRootView(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 获取覆盖视图组件控制器
     *
     * @param context Context
     * @return {@link ICoverControl}
     */
    private ICoverControl getCoverControl(Context context) {
        return new DefaultCoverControl(context);
    }

    /**
     * 设置覆盖视图组
     *
     * @param coverGroup {@link ICoverGroup}
     */
    @Override
    public void setCoverGroup(ICoverGroup coverGroup) {
        if (coverGroup == null) return;
        if (this.coverGroup != null && this.coverGroup.equals(coverGroup)) {
            return;
        }
        //移除之前的covers
        removeAllCovers();
        //解除之前到添加移除监听
        if (this.coverGroup != null) {
            this.coverGroup.unBindCoverGroupChangeListener();
        }

        this.coverGroup = coverGroup;
        //对视图组件进行排序 级别最低的 在视图底部 最高的在视图的顶部 从低到高进行排序
        this.coverGroup.coverSort();
        //找到所有视图组件，并将组件添加到控制器中 按从低到高的顺序加入到控制器中
        this.coverGroup.loopCovers(new ICoverGroup.OnLoopCoverListener() {
            @Override
            public void getCover(ICover cover) {
                attachCover(cover);
            }
        });
        this.coverGroup.bindCoverGroupChangeListener(onCoverGroupChangeListener);
    }

    @Override
    public ICoverGroup getCoverGroup() {
        return this.coverGroup;
    }

    @Override
    public void destroy() {
        if (this.coverGroup != null) {
            this.coverGroup.unBindCoverGroupChangeListener();
        }
        removeAllCovers();
        removeRenderView();
    }

    /**
     * 连接覆盖视图组件控制器，添加视图组件
     */
    private void attachCover(ICover cover) {
        if (cover instanceof BaseCover) {
            cover.bindCoverGroup(coverGroup);
            cover.bindCoverEventListener(onCoverEventListener);
            BaseCover baseCover = (BaseCover) cover;
            coverControl.addCover(baseCover);
        }
    }

    /**
     * 连接覆盖视图组件控制器，移除视图组件
     */
    private void detachCover(ICover cover) {
        if (cover instanceof BaseCover) {
            cover.bindCoverGroup(null);
            cover.unBindCoverEventListener();
            BaseCover baseCover = (BaseCover) cover;
            coverControl.removeCover(baseCover);
        }
    }

    private void removeRenderView() {

    }

    /**
     * 移除之前的所有覆盖视图组件
     */
    private void removeAllCovers() {
        coverControl.removeAllCovers();
        if (coverGroup != null) {
            coverGroup.clearCovers();
        }
    }

    /**
     * 覆盖视图组件事件监听
     */
    OnCoverEventListener onCoverEventListener = new OnCoverEventListener() {
        @Override
        public void onEvent(int eventCode, Bundle bundle) {

        }
    };

    /**
     * 覆盖视图组件热插拔监听
     */
    OnCoverGroupChangeListener onCoverGroupChangeListener = new OnCoverGroupChangeListener() {
        @Override
        public void onAddCover(String key, ICover cover) {
            attachCover(cover);
        }

        @Override
        public void onRemoveCover(String key, ICover cover) {
            detachCover(cover);
        }
    };


}
