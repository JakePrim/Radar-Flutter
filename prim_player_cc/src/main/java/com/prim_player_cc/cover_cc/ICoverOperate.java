package com.prim_player_cc.cover_cc;

import android.os.Bundle;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图组件的操作接口
 * @time 2018/7/26 - 下午3:30
 */
public interface ICoverOperate {
    void coverRequestPause();

    void coverRequestStart();

    void coverRequestStop();

    void coverRequestSeek(Bundle bundle);

    void coverRequestFullScreen();

    void coverRequestVerticalScreen();

    void coverRequestVerticalFullScreen();
}
