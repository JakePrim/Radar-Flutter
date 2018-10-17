package com.prim_player_cc.render_cc;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/30 - 下午2:55
 */
public interface IRenderControl {
    ViewGroup getRenderRootView();

    void addRenderView(IRenderView view);

    void removeRenderView();
}
