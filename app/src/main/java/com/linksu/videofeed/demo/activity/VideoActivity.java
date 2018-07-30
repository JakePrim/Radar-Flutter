package com.linksu.videofeed.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.linksu.videofeed.R;
import com.prim_player_cc.cover_cc.CoverGroup;
import com.prim_player_cc.cover_cc.defualt.DefaultCoverKey;
import com.prim_player_cc.cover_cc.defualt.DefaultLoadCover;
import com.prim_player_cc.view.PlayerCCView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 上午10:18
 */
public class VideoActivity extends AppCompatActivity {

    private PlayerCCView playerCCView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        playerCCView = (PlayerCCView) findViewById(R.id.player_cc);
//        playerCCView.usedDefaultCoverGroup();//使用默认的视图组
    }

    public void load(View view) {
        CoverGroup coverGroup = new CoverGroup();
        coverGroup.addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this));
        playerCCView.setCoverGroup(coverGroup);
    }

    public void removeLoad(View view) {
        playerCCView.getCoverGroup().removeCover(DefaultCoverKey.DEFAULT_LOAD_COVER);
    }
}
