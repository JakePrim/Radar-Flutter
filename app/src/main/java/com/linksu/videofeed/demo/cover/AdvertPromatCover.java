package com.linksu.videofeed.demo.cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.linksu.videofeed.R;
import com.prim_player_cc.cover_cc.BaseCover;

/**
 * @author prim
 * @version 1.0.0
 * @desc 播放广告视频的提示视图
 * @time 2018/9/20 - 上午9:38
 */
public class AdvertPromatCover extends BaseCover {
    public AdvertPromatCover(Context context) {
        super(context);
        setCoverLevelHeight(14);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.advert_layout, null);
    }

    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
        super.onPlayEvent(eventCode, bundle);
    }
}
