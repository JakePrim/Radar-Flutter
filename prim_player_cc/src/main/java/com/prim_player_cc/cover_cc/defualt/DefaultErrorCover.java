package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.decoder_cc.ErrorEventCode;

import static com.prim_player_cc.decoder_cc.ErrorEventCode.PLAYER_EVENT_ERROR_UNKNOWN;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 下午3:08
 */
public class DefaultErrorCover extends BaseCover {
    public DefaultErrorCover(Context context) {
        super(context);
        setCoverLevelLow(2);
        coverVisibility(View.GONE);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_error_layout, null);
    }

    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PLAYER_EVENT_ERROR_UNKNOWN:
                coverVisibility(View.VISIBLE);
                break;
            default:
                coverVisibility(View.GONE);
                break;
        }
    }
}
