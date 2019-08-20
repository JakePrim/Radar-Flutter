package com.linksu.videofeed.demo.cover;

import android.content.Context;
import android.view.View;

import com.linksu.videofeed.R;
import com.prim_player_cc.cover_cc.BaseCover;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/1 - 6:58 PM
 */
public class MoreInfoCover extends BaseCover {
    //定时关闭  倍速播放  循环播放  后台音频播放 切换视频比例  下载
    public MoreInfoCover(Context context) {
        super(context);
        coverVisibility(View.VISIBLE);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.cover_more_info_layout, null);
    }

    @Override
    protected int[] setCoverLevel() {
        return new int[]{LEVEL_HEIGHT, 20};
    }
}
