package com.prim_player_cc.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/26 - 下午7:14
 */
public class PlayerCCView extends BasePlayerCCView {
    public PlayerCCView(@NonNull Context context) {
        super(context);
    }

    public PlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initView() {

    }
}
