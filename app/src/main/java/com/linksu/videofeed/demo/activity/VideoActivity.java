package com.linksu.videofeed.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.adapter.VideoListAdapter;
import com.linksu.videofeed.demo.cover.AdvertPromatCover;
import com.linksu.videofeed.demo.test.DataFactory;
import com.linksu.videofeed.demo.test.VideoDataBean;
import com.linksu.videofeed.demo.test.VideoDataProvider;
import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.defualt.DefaultControlCover;
import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.decoder_cc.IDecoder;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.view.OnCoverNativePlayerListener;
import com.prim_player_cc.view.PrimPlayerCCView;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static com.prim_player_cc.cover_cc.event.CoverEventKey.DEFAULT_CONTROL_COVER;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_AUTO_PLAY_NEXT;

/**
 * @author prim
 * @version 1.0.0
 * @desc PrimPlayerCCView 的基本适用
 * @time 2018/7/27 - 上午10:18
 */
public class VideoActivity extends BaseActivity implements OnPlayerEventListener,
        OnCoverNativePlayerListener
        , View.OnClickListener {

    private PrimPlayerCCView playerCCView;

    private static final String TAG = "VideoActivity";

    private SwitchCompat switchSurfaceView, switchTextureView, switchOpenGesture, switcScrollGesture;

    private boolean isTextureView;

    private boolean isSurfaceView;

    private RadioGroup rg_video_aspect;

    private RadioGroup rg_video_speed;

    private RecyclerView rl_video_list;

    private VideoListAdapter adapter;

    private TextView tv_expand;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//不锁屏

        setContentView(R.layout.activity_video);

        playerCCView = (PrimPlayerCCView) findViewById(R.id.player_cc);

        switchSurfaceView = (SwitchCompat) findViewById(R.id.switchSurfaceView);

        switchTextureView = (SwitchCompat) findViewById(R.id.switchTextureView);

        rg_video_aspect = (RadioGroup) findViewById(R.id.rg_video_aspect);

        rg_video_speed = (RadioGroup) findViewById(R.id.rg_video_speed);

        rl_video_list = (RecyclerView) findViewById(R.id.rl_video_list);

        tv_expand = (TextView) findViewById(R.id.tv_expand);

        switcScrollGesture = (SwitchCompat) findViewById(R.id.switcScrollGesture);

        switchOpenGesture = (SwitchCompat) findViewById(R.id.switchOpenGesture);

        initListener();

        initRecycler();

        startVideo();
    }

    private void initRecycler() {
        adapter = new VideoListAdapter(this);
        rl_video_list.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL, false));
        rl_video_list.setAdapter(adapter);
        adapter.setDataBeanList(DataFactory.getLoadData());
    }

    private void initListener() {
        tv_expand.setOnClickListener(this);

        switchOpenGesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                playerCCView.setGesture(isChecked);
            }
        });

        switcScrollGesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                playerCCView.setScrollGesture(isChecked);
            }
        });

        switchSurfaceView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isSurfaceView = true;
                    playerCCView.setRenderView(IRenderView.SURFACE_VIEW);
                    switchTextureView.setChecked(false);
                } else {
                    isSurfaceView = false;
                    if (!isTextureView) {
                        playerCCView.setRenderView(IRenderView.RENDER_NONE);
                    }
                }
            }
        });

        switchTextureView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isTextureView = true;
                    switchSurfaceView.setChecked(false);
                    playerCCView.setRenderView(IRenderView.TEXTURE_VIEW);
                } else {
                    isTextureView = false;
                    if (!isSurfaceView) {
                        playerCCView.setRenderView(IRenderView.RENDER_NONE);
                    }
                }
            }
        });

        rg_video_aspect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_video_aspect_fit:
                        playerCCView.setDiaplayAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
                        break;
                    case R.id.rb_video_aspect_fill:
                        playerCCView.setDiaplayAspectRatio(IRenderView.AR_ASPECT_FILL_PARENT);
                        break;
                    case R.id.rb_video_aspect_match:
                        playerCCView.setDiaplayAspectRatio(IRenderView.AR_MATCH_PARENT);
                        break;
                    case R.id.rb_video_aspect_origin:
                        playerCCView.setDiaplayAspectRatio(IRenderView.AR_ASPECT_WRAP_CONTENT);
                        break;
                    case R.id.rb_video_aspect_16_9:
                        playerCCView.setDiaplayAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
                        break;
                    case R.id.rb_video_aspect_4_3:
                        playerCCView.setDiaplayAspectRatio(IRenderView.AR_4_3_FIT_PARENT);
                        break;
                }
            }
        });

        rg_video_speed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_video_speed:
                        playerCCView.setSpeed(0.5f);
                        break;
                    case R.id.rb_video_1_speed:
                        playerCCView.setSpeed(1);
                        break;
                    case R.id.rb_video_2_speed:
                        playerCCView.setSpeed(2);
                        break;
                }
            }
        });
    }

    private VideoDataProvider dataProvider;

    private void startVideo() {
        playerCCView.usedDefaultCoverGroup();//使用默认的视图组

        //或者使用自定义的视图组
//      PrimPlayerCC.getCoverCCManager()
//                .addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this))
//                .addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(this))
//                .addCover(DefaultCoverKey.DEFAULT_ERROR_COVER, new DefaultErrorCover(this))
//                .addCover(DefaultCoverKey.DEFAULT_COMPLETE_COVER, new DefaultCompleteCover(this))
//                .insertCoverGroup(playerCCView);

        playerCCView.dynamicChangedDecoder(11);//切换解码器

        //设置呈现视频的RenderView
        //使用框架默认的surfaceView
        playerCCView.setRenderView(IRenderView.TEXTURE_VIEW);
        /**
         * 使用自定的view,从解码器中返回 {@link IDecoder#getRenderView()}
         */
//      playerCCView.updateRenderView(IRenderView.CUSTOM_VIEW);

        /**
         * 使用框架默认的textureView
         */
//      playerCCView.updateRenderView(IRenderView.TEXTURE_VIEW);

        //允许自动播放下一条
        playerCCView.autoNextPlayWaitTime(3000);
        playerCCView.autoPlayNext(true);

        //只添加视图等级 > 0 的视图
        playerCCView.setCoverGroupFilter(new ICoverGroup.OnCoverFilter() {
            @Override
            public boolean filter(ICover cover) {
                return cover.getCoverLevel() > 0;
            }
        });

        //http:/api.m.mtime.cn/PageSubArea/TrailerList.api

        List<VideoDataBean> loadData = DataFactory.getLoadData();
        dataProvider = new VideoDataProvider(DataFactory.getLoadData());

        dataProvider.traversPool();

        playerCCView.setOnPlayerEventListener(this);

        playerCCView.setOnCoverNativePlayerListener(this);

        playerCCView.setDataProvider(dataProvider);

        adapter.setOnItemClickListener(new VideoListAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int position, Object data, View view) {
                playerCCView.selectDataPlay(position);
                adapter.updatePlaying(position);
                rl_video_list.scrollToPosition(position);
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });

        adapter.updatePlaying(0);

        //设置单个数据资源
//        PlayerSource playerSource = new
//                PlayerSource("http://rmrbtest-image.peopleapp.com/upload/video/201809/153723951440062dcc54a0912f.mp4");
//        playerSource.setTitle("带你去旅行-飞呀飞");
//        playerSource.setId("12");
//        playerSource.setTag("TEST");
//        playerSource.setThumbnailUrl("");
//
//        //设置播放资源
//        playerCCView.setDataSource(playerSource);
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerCCView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerCCView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerCCView.stop();
        playerCCView.destroy();
    }

    public void load(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover(DEFAULT_CONTROL_COVER,
                new DefaultControlCover(this));
    }

    public void removeLoad(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicDeleteCover(DEFAULT_CONTROL_COVER);
    }

    public void addAdvert(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover("Advert", new AdvertPromatCover(this));
    }

    public void removeAdvert(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicDeleteCover("Advert");
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PRIM_PLAYER_EVENT_AUTO_PLAY_NEXT:
                Log.e(TAG, "onPlayerEvent: auto play next --> " + playerCCView.currentPlayIndex());
                adapter.updatePlaying(playerCCView.currentPlayIndex());
                rl_video_list.scrollToPosition(playerCCView.currentPlayIndex());

                break;
        }
    }

    BottomSheetDialog bottomSheetDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_expand:
                bottomSheetDialog = new BottomSheetDialog(this, R.style.CoffeeDialog);
                View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null, false);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bottom_sheet);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                bottomSheetDialog.setContentView(view);
                BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) view.getParent());
                View contentView = getWindow().getDecorView().findViewById(android.R.id.content);
                mBehavior.setPeekHeight(contentView.getHeight() - playerCCView.getHeight());
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetDialog.show();
                break;
        }
    }

    //视图传递的事件
    @Override
    public void onEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case CoverEventCode.COVER_EVENT_VIDEO_MORE_INFO:
//                PrimPlayerCC.getCoverCCManager().dynamicInsertCover("more_info", new MoreInfoCover(this));
                break;
        }
    }
}
