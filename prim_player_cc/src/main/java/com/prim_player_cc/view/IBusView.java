package com.prim_player_cc.view;

import android.view.View;

import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.render_cc.IRenderView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/26 - 上午11:23
 */
public interface IBusView {
    void setCoverGroup(ICoverGroup coverGroup);

    void setGesture(boolean gesture);

    ICoverGroup getCoverGroup();

    void destroy();

    void setRenderView(IRenderView render);

    void removeRenderView();

    void setScrollGesture(boolean scrollGesture);

    void setCoverFilter(ICoverGroup.OnCoverFilter coverFilter);
}
