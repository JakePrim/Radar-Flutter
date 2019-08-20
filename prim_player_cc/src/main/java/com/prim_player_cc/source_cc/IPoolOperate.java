package com.prim_player_cc.source_cc;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author prim
 * @version 1.0.0
 * @desc 数据池操作接口
 * @time 2018/10/22 - 3:08 PM
 */
public interface IPoolOperate {
    //单个循环
    int LOOP_MODE_SINGLE = 0x132;

    //顺序播放
    int LOOP_MODE_QUEUE = 0x133;

    //随机播放
    int LOOP_MODE_RANDOM = 0x134;

    //列表循环播放
    int LOOP_MODE_LIST_LOOP = 0x135;

    //播完暂停
    int LOOP_MODE_FINISH_PAUSE = 0x136;

    @IntDef({
            LOOP_MODE_SINGLE,
            LOOP_MODE_QUEUE,
            LOOP_MODE_RANDOM,
            LOOP_MODE_LIST_LOOP,
            LOOP_MODE_FINISH_PAUSE
    })
    @Retention(SOURCE)
    @interface LoopMode {

    }

    void setLoopMode(@LoopMode int mode);

    void setOffsetPointer(PlayerSource source);

    void setOffsetPointer(int index);

    @LoopMode
    int getLoopMode();

    NodeData autoGetNextPlaySource();

    NodeData manualGetNextPlaySource();

    NodeData manualGetForwardPlaySource();

    void autoLoadMorePlaySource();
}
