package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 下午3:08
 */
public class DefaultCompleteCover extends BaseCover implements View.OnClickListener {
    private Button cover_complete_btn;

    public DefaultCompleteCover(Context context) {
        super(context);
        setCoverLevelLow(1);
        cover_complete_btn = findViewById(R.id.cover_complete_btn);
        cover_complete_btn.setOnClickListener(this);
        coverVisibility(View.GONE);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_complete_layout, null);
    }

    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
                coverVisibility(View.VISIBLE);
                break;
            default:
                coverVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cover_complete_btn) {
            coverRequestStart();
        }
    }
}
