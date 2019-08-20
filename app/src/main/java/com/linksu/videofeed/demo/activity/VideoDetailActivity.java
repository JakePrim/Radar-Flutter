package com.linksu.videofeed.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.adapter.VideoDetailAdapter;
import com.linksu.videofeed.demo.test.VideoDataBean;
import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.view.AssistPlayerView;
import com.prim_player_cc.source_cc.PlayerSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/5 - 4:01 PM
 */
public class VideoDetailActivity extends BaseActivity {

    private AssistPlayerView player_cc;

    private static PlayerSource playerSource;

    private RecyclerView rv_video_list;

    private static VideoDataBean dataBean;

    private List<VideoDataBean> videoDataBeanList;

    private VideoDetailAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void startVideoDetail(Activity context, View view, PlayerSource source, VideoDataBean bean) {
        playerSource = source;
        dataBean = bean;
        Intent intent = new Intent(context, VideoDetailActivity.class);
        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, "image");
        ActivityCompat.startActivityForResult(context, intent, 1, optionsCompat.toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_video_detail_layout);
        videoDataBeanList = new ArrayList<>();
        rv_video_list = findViewById(R.id.rv_video_list);
        rv_video_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoDetailAdapter(this);
        rv_video_list.setAdapter(adapter);
        videoDataBeanList.add(dataBean);
        adapter.setDataList(videoDataBeanList);

        rv_video_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                int size = videoDataBeanList.size();
                for (int i = 0; i < 5; i++) {
                    videoDataBeanList.add(dataBean);
                }
                adapter.mNotify(true, size);
            }
        }, 2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
        setResult(RESULT_OK);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
