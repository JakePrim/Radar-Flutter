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
 * @desc 默认实现的播放器组件View
 * @time 2018/7/26 - 下午7:14
 */
public class PrimPlayerCCView extends BasePlayerCCView {
    public PrimPlayerCCView(@NonNull Context context) {
        super(context);
    }

    public PrimPlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PrimPlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PrimPlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initView() {
//        releaseRender();
//        setRenderView(mRenderType);
    }
}
