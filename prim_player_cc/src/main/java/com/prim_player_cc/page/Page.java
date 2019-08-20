package com.prim_player_cc.page;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * @author prim
 * @version 1.0.0
 * @desc 作为一个页面
 * @time 2018/11/6 - 5:05 PM
 */
public abstract class Page extends ContextWrapper {

    public Page(Activity base) {
        super(base);
    }
}
