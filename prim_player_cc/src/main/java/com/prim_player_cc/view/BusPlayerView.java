package com.prim_player_cc.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.listener.OnCoverEventListener;
import com.prim_player_cc.cover_cc.listener.OnCoverGestureListener;
import com.prim_player_cc.cover_cc.listener.OnCoverGroupChangeListener;
import com.prim_player_cc.cover_cc.control.DefaultCoverControl;
import com.prim_player_cc.cover_cc.control.ICoverControl;
import com.prim_player_cc.cover_cc.event.CoverEventDispatcher;
import com.prim_player_cc.cover_cc.event.IEventDispatcher;
import com.prim_player_cc.decoder_cc.IMediaController;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.expand.AbsEventProducer;
import com.prim_player_cc.expand.DefaultCoverEventSender;
import com.prim_player_cc.expand.DelegateEventSender;
import com.prim_player_cc.expand.ExpandGroup;
import com.prim_player_cc.expand.IExpandGroup;
import com.prim_player_cc.loader.ImageEngine;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.render_cc.IRenderControl;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.render_cc.RenderControl;
import com.prim_player_cc.touch.TouchGestureHelper;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图组件的总线view
 * @time 2018/7/25 - 下午2:47
 */
public class BusPlayerView extends FrameLayout implements IBusView, OnCoverGestureListener {

    private ICoverGroup coverGroup;

    private ICoverControl coverControl;

    private IRenderControl renderControl;

    private IEventDispatcher eventDispatcher;

    private static final String TAG = "BusPlayerView";

    private TouchGestureHelper touchGestureHelper;

    private OnCoverNativePlayerListener onCoverNativePlayerListener;

    private IMediaController.MediaPlayerControl mediaPlayerControl;

    private FrameLayout imgView;

    private IExpandGroup expandGroup;

    public BusPlayerView(@NonNull Context context) {
        super(context);
        _init(context);
    }

    private void _init(Context context) {
        PrimLog.d(TAG, "build Bus View");
        //初始化背景
        setBackgroundColor(Color.BLACK);
        //创建扩展事件组
        expandGroup = new ExpandGroup(new DefaultCoverEventSender(mDelegateEventSender));
        //初始化手势事件
        initTouchGesture(context);
        //初始化显示视频画面的view
        initRenderView(context);
        //初始化视图控制器
        initCoverControl(context);
    }

    /**
     * 添加扩展事件生产者
     *
     * @param producer {@link AbsEventProducer}
     */
    public void addEventProducer(AbsEventProducer producer) {
        if (null != expandGroup) {
            expandGroup.addProducer(producer);
        }
    }

    /**
     * 移除扩展事件生产者
     *
     * @param producer
     */
    public void removeEventProducer(AbsEventProducer producer) {
        if (null != expandGroup) {
            expandGroup.removeProducer(producer);
        }
    }

    /**
     * 扩展事件生产者，分发事件
     */
    private DelegateEventSender mDelegateEventSender = new DelegateEventSender() {
        @Override
        public void sendEvent(int eventCode, Bundle bundle, ICoverGroup.OnCoverFilter filter) {
            dispatchExpandEvent(eventCode, bundle, filter);
        }
    };

    /**
     * 初始化触摸手势 并将手势事件分发给各个视图{@link ICover}
     */
    private void initTouchGesture(Context context) {
        touchGestureHelper = new TouchGestureHelper(context, this, this);
        setScrollGesture(true);
    }

    public void setMediaPlayerControl(IMediaController.MediaPlayerControl mediaPlayerControl) {
        this.mediaPlayerControl = mediaPlayerControl;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchGestureHelper != null) {
            return touchGestureHelper.onTouch(event);
        } else {
            return false;
        }
    }

    /**
     * 初始化video view
     * 添加呈现视图的view{@link android.view.SurfaceView} 或 {@link android.view.TextureView}
     *
     * @param context Context
     */
    private void initRenderView(Context context) {
        renderControl = new RenderControl(context);
        if (renderControl.getRenderRootView() != null) {
            addView(renderControl.getRenderRootView(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        }
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
     * 视图桥接播放器的监听事件
     *
     * @param onCoverNativePlayerListener {@link OnCoverNativePlayerListener}
     */
    public void setOnCoverNativePlayerListener(OnCoverNativePlayerListener onCoverNativePlayerListener) {
        this.onCoverNativePlayerListener = onCoverNativePlayerListener;
    }

    /**
     * 设置覆盖视图组
     * 移除之前的所有视图和解除监听，并根据视图的优先级进行排序，重新调整list{@link ICoverGroup#coverSort()}
     * 然后遍历list添加到视图控制器{@link DefaultCoverControl}
     * {@link ICoverGroup} 视图组绑定视图更改监听
     *
     * @param coverGroup {@link ICoverGroup}
     */
    @Override
    public void setCoverGroup(ICoverGroup coverGroup) {
        if (coverGroup == null) return;
        if (this.coverGroup != null && this.coverGroup.equals(coverGroup)) {
            return;
        }
        PrimLog.e("PRIM!!", "BusPlayerView -> 添加视图组");
        //移除之前的covers
        removeAllCovers();
        //解除之前到添加移除监听
        if (this.coverGroup != null) {
            this.coverGroup.unBindCoverGroupChangeListener();
        }
        //new 触发视图事件
        this.eventDispatcher = new CoverEventDispatcher(coverGroup);
        //视图组
        this.coverGroup = coverGroup;
        //对视图组件进行排序 级别最低的 在视图底部 最高的在视图的顶部 从低到高进行排序
        this.coverGroup.coverSort();
        //将视图重新排序后，找到所有视图组件，并将组件添加到控制器中 按从低到高的优先级加入到控制器中
        this.coverGroup.loopCovers(coverFilter(), new ICoverGroup.OnLoopCoverListener() {
            @Override
            public void getCover(ICover cover) {
                attachCover(cover);
            }
        });
        //绑定视图组中的视图 动态改变监听 实现了视图的热插拔
        this.coverGroup.bindCoverGroupChangeListener(onCoverGroupChangeListener);
    }

    /**
     * 获取当前的覆盖视图组
     *
     * @return {@link ICoverGroup}
     */
    @Override
    public ICoverGroup getCoverGroup() {
        return this.coverGroup;
    }

    /**
     * 给视图组件{@link ICover} 分发播放事件, 具体分发类请看{@link CoverEventDispatcher}
     *
     * @param eventCode 事件码
     * @param bundle    传递的数据
     */
    public void dispatchPlayEvent(final int eventCode, final Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchPlayEvent(eventCode, bundle);
        }
    }

    /**
     * 给视图组件分发事件
     *
     * @param eventCode
     * @param bundle
     */
    public void dispatchCoverNativeEvent(int eventCode, Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchCoverNativeEvent(eventCode, bundle);
        }
    }

    /**
     * 给视图组件{@link ICover} 分发播放错误事件, 具体分发类请看{@link CoverEventDispatcher}
     *
     * @param eventCode 事件码
     * @param bundle    传递的数据
     */
    public void dispatchErrorEvent(final int eventCode, final Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchErrorEvent(eventCode, bundle);
        }
    }

    /**
     * 给视图组件{@link ICover} 分发扩展事件, 具体分发类请看{@link CoverEventDispatcher}
     *
     * @param eventCode 事件码
     * @param bundle    传递数据
     * @param filter    拦截器
     */
    public void dispatchExpandEvent(int eventCode, Bundle bundle, ICoverGroup.OnCoverFilter filter) {
        if (null != eventDispatcher) {
            eventDispatcher.dispatchExpandEvent(eventCode, bundle, filter);
        }
    }


    /**
     * 设置呈现视频的view
     *
     * @param render view
     */
    @Override
    public void setRenderView(IRenderView render) {
        removeRenderView();
        if (renderControl != null) {
            renderControl.addRenderView(render);
        }
    }


    /**
     * 连接覆盖视图组件控制器，添加视图组件
     */
    private void attachCover(ICover cover) {
        //所有的视图必须继承BaseCover
        if (cover instanceof BaseCover) {
            cover.bindCoverGroup(coverGroup);
            cover.bindCoverEventListener(onCoverEventListener);
            BaseCover baseCover = (BaseCover) cover;
            //设置mediaControl
            if (mediaPlayerControl != null) {
                baseCover.setMediaPlayer(mediaPlayerControl);
            }
            baseCover.setAnchorView(coverControl.getCoverRootView());
            coverControl.addCover(baseCover);
        } else {
            PrimLog.e(TAG, "无效的Cover ：" + cover);
        }
    }

    /**
     * 动态插入视图组件，首先对视图组件按优先级重排序，然后在将组件添加到视图中
     * 按优先级显示
     *
     * @param key   标志
     * @param cover 实现的cover
     */
    private void dynamicAttachCover(String key, ICover cover) {
        if (cover instanceof BaseCover) {
            if (coverGroup != null) {
                //视图控制器移除所有视图
                coverControl.removeAllCovers();
                //对视图组件重新进行排序 级别最低的 在视图底部 最高的在视图的顶部 从低到高进行排序
                this.coverGroup.coverSort();
                //找到所有视图组件，并将组件添加到控制器中 按从低到高的优先级加入到控制器中
                this.coverGroup.loopCovers(coverFilter(), new ICoverGroup.OnLoopCoverListener() {
                    @Override
                    public void getCover(ICover cover) {
                        attachCover(cover);
                    }
                });
            }
        }
    }

    /**
     * 连接覆盖视图组件控制器，移除视图组件
     */
    private void dynamicDetachCover(String key, ICover cover) {
        if (cover instanceof BaseCover) {
            cover.bindCoverGroup(null);
            cover.unBindCoverEventListener();
            BaseCover baseCover = (BaseCover) cover;
            coverControl.removeCover(baseCover);
        }
    }

    private ICoverGroup.OnCoverFilter coverFilter() {
        return new ICoverGroup.OnCoverFilter() {
            @Override
            public boolean filter(ICover cover) {
                if (coverFilter != null) {
                    return (cover instanceof BaseCover) && coverFilter.filter(cover);
                } else {
                    return cover instanceof BaseCover;
                }
            }
        };
    }

    private ICoverGroup.OnCoverFilter coverFilter = null;

    /**
     * 设置视图组拦截器
     *
     * @param coverFilter {@link ICoverGroup.OnCoverFilter}
     */
    @Override
    public void setCoverFilter(ICoverGroup.OnCoverFilter coverFilter) {
        this.coverFilter = coverFilter;
    }


    /**
     * 移除renderView
     */
    @Override
    public void removeRenderView() {
        if (renderControl != null) {
            renderControl.removeRenderView();
        }
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
            if (onCoverNativePlayerListener != null) {
                onCoverNativePlayerListener.onEvent(eventCode, bundle);
            }
        }
    };

    /**
     * 覆盖视图组件热插拔监听
     */
    OnCoverGroupChangeListener onCoverGroupChangeListener = new OnCoverGroupChangeListener() {
        @Override
        public void onAddCover(String key, ICover cover) {
            dynamicAttachCover(key, cover);
        }

        @Override
        public void onRemoveCover(String key, ICover cover) {
            dynamicDetachCover(key, cover);
        }
    };

    @Override
    public void destroy() {
        if (this.coverGroup != null) {
            this.coverGroup.unBindCoverGroupChangeListener();
        }
        if (this.onCoverNativePlayerListener != null) {
            this.onCoverNativePlayerListener = null;
        }
        if (expandGroup != null) {
            expandGroup.onDestory();
        }
        removeAllCovers();
        removeRenderView();
    }

    //------------------- 手势设置 -----------------------//

    /**
     * 设置是否开启手势
     *
     * @param gesture
     */
    @Override
    public void setGesture(boolean gesture) {
        if (touchGestureHelper != null) {
            touchGestureHelper.setGesture(gesture);
        }
    }

    /**
     * 设置是否开启滑动手势
     *
     * @param scrollGesture
     */
    @Override
    public void setScrollGesture(boolean scrollGesture) {
        if (touchGestureHelper != null) {
            touchGestureHelper.setScrollGesture(scrollGesture);
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        if (eventDispatcher != null) {
            return eventDispatcher.dispatchOnSingleTapUp(event);
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        if (eventDispatcher != null) {
            return eventDispatcher.dispatchOnDoubleTap(event);
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        if (eventDispatcher != null) {
            return eventDispatcher.dispatchOnDown(event);
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dX, float dY) {
        if (eventDispatcher != null) {
            return eventDispatcher.dispatchOnScroll(e1, e2, dX, dY);
        }
        return false;
    }

    @Override
    public boolean onTouchCancle() {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchOnTouchCancle();
        }
        return false;
    }
}
