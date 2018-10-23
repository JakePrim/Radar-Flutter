package com.prim_player_cc.source_cc;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.prim_player_cc.log.PrimLog;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 数据提供者的抽象类，需要继承此抽象类
 * 主要多用于：多个数据资源列表
 * @time 2018/10/22 - 4:04 PM
 */
public abstract class AbsDataProvider<T> extends DataPool implements IDataProvider<T> {

    private static final String TAG = "AbsDataProvider";

    protected T originalData;

    protected OnDataProviderListener mOnDataProviderListener;

    private static final int WHAT_BIND_SOURCE_DATA = 0x908;

    private static final int WHAT_BIND_SOURCE_FINISH = 0x909;

    protected Handler H = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_BIND_SOURCE_DATA:
                    PlayerSource source = (PlayerSource) msg.obj;
                    addNode(source);
                    if (mOnDataProviderListener != null) {
                        mOnDataProviderListener.onBindDataSuccess(source);
                    }
                    break;
                case WHAT_BIND_SOURCE_FINISH:
                    //设置偏移指针
                    if (getOffsetPointerIndex() != 0) {
                        setOffsetPointer(getOffsetPointerIndex());
                    } else {
                        setOffsetPointer(0);
                    }
                    if (mOnDataProviderListener != null) {
                        mOnDataProviderListener.onBindDataFinish();
                    }

                    break;
            }
        }
    };

    public AbsDataProvider(T t) {
        this.originalData = t;
    }

    public AbsDataProvider(T originalData, OnDataProviderListener mOnDataProviderListener) {
        this.originalData = originalData;
        this.mOnDataProviderListener = mOnDataProviderListener;
    }

    @Override
    public T getOriginalData() {
        return originalData;
    }


    /**
     * 开始绑定数据资源
     * 主要监听{@link #mOnDataProviderListener} 要设置在该方法之前
     * 否则会监听不到数据的绑定
     */
    @Override
    public void startBindSourceData() {
        if (originalData == null) return;
        //清空数据池
        cancleBind();
        clear();
        if (mOnDataProviderListener != null) {
            mOnDataProviderListener.onBindDataStart();
        }
        if (originalData instanceof List) {//多个列表数据
            try {
                List<Object> datas = (List<Object>) originalData;
                for (int i = 0; i < datas.size(); i++) {
                    PlayerSource source = getCustomizeSource(datas.get(i));
                    Message message = new Message();
                    message.what = WHAT_BIND_SOURCE_DATA;
                    message.obj = source;
                    H.sendMessage(message);
                }
                H.sendEmptyMessage(WHAT_BIND_SOURCE_FINISH);
            } catch (Exception e) {
                if (PrimLog.LOG_OPEN) {
                    e.printStackTrace();
                }
                if (mOnDataProviderListener != null) {
                    mOnDataProviderListener.onBindDataError(1);
                }
            }
        } else {//单个数据
            try {
                PlayerSource customizeSource = getCustomizeSource(originalData);
                Message message = new Message();
                message.what = WHAT_BIND_SOURCE_DATA;
                message.obj = customizeSource;
                H.sendMessage(message);
                H.sendEmptyMessage(WHAT_BIND_SOURCE_FINISH);
            } catch (Exception e) {
                if (PrimLog.LOG_OPEN) {
                    e.printStackTrace();
                }
                if (mOnDataProviderListener != null) {
                    mOnDataProviderListener.onBindDataError(1);
                }
            }
        }
    }

    /**
     * 自定义资源数据bean逻辑
     *
     * @param data 当前项目的bean
     * @return {@link PlayerSource} 返回自定义的播放资源数据
     */
    protected abstract PlayerSource getCustomizeSource(Object data);

    @Override
    public boolean cancleBind() {
        if (this.mOnDataProviderListener != null) {
            this.mOnDataProviderListener.onBindDataCancle();
        }
        H.removeCallbacksAndMessages(null);
        return true;
    }

    @Override
    public void destory() {
        clear();
        removeOnDataProviderListener();
        H.removeCallbacksAndMessages(null);
    }

    @Override
    public void setOnDateProviderListener(OnDataProviderListener onDateProviderListener) {
        this.mOnDataProviderListener = onDateProviderListener;
    }

    @Override
    public void removeOnDataProviderListener() {
        this.mOnDataProviderListener = null;
    }
}
