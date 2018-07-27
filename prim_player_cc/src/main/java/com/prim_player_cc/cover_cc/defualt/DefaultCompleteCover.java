package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.view.View;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.BaseCover;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 下午3:08
 */
public class DefaultCompleteCover extends BaseCover {
    public DefaultCompleteCover(Context context) {
        super(context);
        setCoverLevelLow(1);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_complete_layout, null);
    }
}
