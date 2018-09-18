package com.linksu.videofeed.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.linksu.videofeed.R;
import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.cover_cc.CoverCCManager;
import com.prim_player_cc.cover_cc.defualt.DefaultControlCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCoverKey;
import com.prim_player_cc.cover_cc.defualt.DefaultErrorCover;
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

        //初始化视图组
        PrimPlayerCC.getCoverCCManager()
                .addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this))
                .addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(this))
                .insertCoverGroup(playerCCView);

        //设置呈现视频的RenderView
        playerCCView.setRenderView(IRender.SURFACE_VIEW);
        //设置播放资源
        playerCCView.setDataSource(new PlayerSource("http://rmrbtest-image.peopleapp.com/upload/video/201707/1499914158feea8c512f348b4a.mp4"));
        //开始播放
        playerCCView.start();
    }

    public void load(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this));
    }

    public void removeLoad(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicDeleteCover(DefaultCoverKey.DEFAULT_LOAD_COVER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerCCView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerCCView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerCCView.destroy();
    }

    public void addControl(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(this));
    }

    public void removeControl(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicDeleteCover(DefaultCoverKey.DEFAULT_CONTROL_COVER);
    }

    public void addError(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover(DefaultCoverKey.DEFAULT_ERROR_COVER, new DefaultErrorCover(this));
    }

    public void removeError(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicDeleteCover(DefaultCoverKey.DEFAULT_ERROR_COVER);
    }
}
