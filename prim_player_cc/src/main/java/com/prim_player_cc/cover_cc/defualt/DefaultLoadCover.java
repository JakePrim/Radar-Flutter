package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.state.PlayerState;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/26 - 上午11:21
 */
public class DefaultLoadCover extends BaseCover {

    public DefaultLoadCover(Context context) {
        super(context);
        setCoverLevelHeight(10);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_load_layout, null);
    }

    @Override
    public void onPlayEvent(@PlayerState int state, Bundle bundle) {
        super.onPlayEvent(state, bundle);
    }
}
