package com.prim_player_cc.cover_cc.event;

import android.os.Bundle;

import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.log.PrimLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图组件事件分发者
 * @time 2018/9/15 - 下午5:09
 */
public class CoverEventDispatcher implements IEventDispatcher {

    private static final String TAG = "CoverEventDispatcher";

    private ICoverGroup coverGroup;

    public CoverEventDispatcher(ICoverGroup coverGroup) {
        this.coverGroup = coverGroup;
    }


    @Override
    public boolean dispatchPlayEvent(final int eventCode, final Bundle bundle) {
        PrimLog.d(TAG, "dispatchPlayEvent -->> eventCode:" + eventCode);
        switch (eventCode) {
            default:
                if (coverGroup != null){
                    //给所有的视图分发播放事件 需要的处理即可 不需要的不用处理
                    coverGroup.loopCovers(new ICoverGroup.OnLoopCoverListener() {
                        @Override
                        public void getCover(ICover cover) {
                            cover.onPlayEvent(eventCode,bundle);
                        }
                    });
                }
                break;
        }
        bundleClear(bundle);
        return false;
    }

    private void bundleClear(Bundle bundle) {
        if (bundle != null){
            bundle.clear();
        }
    }

    @Override
    public boolean dispatchErrorEvent(final int eventCode, final Bundle bundle) {
        PrimLog.d(TAG, "dispatchErrorEvent -->> eventCode:" + eventCode);
        switch (eventCode) {
            default:
                if (coverGroup != null){
                    //给所有的视图分发播放事件 需要的处理即可 不需要的不用处理
                    coverGroup.loopCovers(new ICoverGroup.OnLoopCoverListener() {
                        @Override
                        public void getCover(ICover cover) {
                            cover.onPlayEvent(eventCode,bundle);
                        }
                    });
                }
                break;
        }
        bundleClear(bundle);
        return false;
    }
}
