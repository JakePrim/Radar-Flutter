package com.linksu.videofeed.demo.cover;

import android.content.Context;
import android.view.View;

import com.prim_player_cc.cover_cc.BaseCover;

/**
 * @author prim
 * @version 1.0.0
 * @desc 自动播放下一个的提示视图
 * @time 2018/10/19 - 下午5:20
 */
public class NextPromatCover extends BaseCover {
    public NextPromatCover(Context context) {
        super(context);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context,1,null);
    }
}
