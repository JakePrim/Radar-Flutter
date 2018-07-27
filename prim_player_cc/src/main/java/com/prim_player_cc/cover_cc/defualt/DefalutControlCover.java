package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.view.View;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.player_cc.BasePlayerCC;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 下午3:04
 */
public class DefalutControlCover extends BaseCover {
    public DefalutControlCover(Context context) {
        super(context);
        setCoverLevelHeight(15);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_control_layout, null);
    }
}
