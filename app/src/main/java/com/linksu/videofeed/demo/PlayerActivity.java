package com.linksu.videofeed.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.activity.VideoActivity;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：8/18 0018
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup rg_video;
    private Button bt_start;
    private static int PLAYER_VIDEO_TYPE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        rg_video = (RadioGroup) findViewById(R.id.rg_video);
        bt_start = (Button) findViewById(R.id.bt_start);
        initListener();
    }

    private void initListener() {
        bt_start.setOnClickListener(this);
        rg_video.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.cb_plvideo:
                        PLAYER_VIDEO_TYPE = 0;
                        break;
                    case R.id.cb_video:
                        PLAYER_VIDEO_TYPE = 1;
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                Intent intent = new Intent(this, VideoActivity.class);
                intent.putExtra("type", PLAYER_VIDEO_TYPE);
                startActivity(intent);
                break;
        }
    }
}
