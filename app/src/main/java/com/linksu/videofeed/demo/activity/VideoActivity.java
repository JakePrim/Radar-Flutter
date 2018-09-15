package com.linksu.videofeed.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.linksu.videofeed.R;
import com.prim_player_cc.cover_cc.CoverCCManager;
import com.prim_player_cc.cover_cc.defualt.DefalutControlCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCoverKey;
import com.prim_player_cc.cover_cc.defualt.DefaultLoadCover;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.render_cc.IRender;
import com.prim_player_cc.view.DefaultPlayerCCView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 上午10:18
 */
public class VideoActivity extends AppCompatActivity {

    private DefaultPlayerCCView playerCCView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        playerCCView = (DefaultPlayerCCView) findViewById(R.id.player_cc);
//        playerCCView.usedDefaultCoverGroup();//使用默认的视图组
        CoverCCManager.getInstance().setCoverGroup().addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this));
        playerCCView.setCoverGroup(CoverCCManager.getInstance().getCoverGroup());
        playerCCView.setRenderView(IRender.SURFACE_VIEW);
        playerCCView.setDataSource(new PlayerSource("http://rmrbtest-image.peopleapp.com/upload/video/201707/1499914158feea8c512f348b4a.mp4"));
        playerCCView.start();
    }

    public void load(View view) {
//        ICover cover = CoverCCManager.getInstance().getCoverGroup().getCover(DefaultCoverKey.DEFAULT_CONTROL_COVER);
//        cover.coverVisibility(View.VISIBLE);
        CoverCCManager.getInstance().addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefalutControlCover(this));
    }

    public void removeLoad(View view) {
//        ICover cover = CoverCCManager.getInstance().getCoverGroup().getCover(DefaultCoverKey.DEFAULT_CONTROL_COVER);
//        cover.coverVisibility(View.GONE);
        CoverCCManager.getInstance().removeCover(DefaultCoverKey.DEFAULT_CONTROL_COVER);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerCCView.destroy();
    }
}
