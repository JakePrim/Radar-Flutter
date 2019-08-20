package com.linksu.videofeed.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.adapter.VideoListAdapter;
import com.linksu.videofeed.demo.cover.AdvertPromatCover;
import com.linksu.videofeed.demo.test.DataFactory;
import com.linksu.videofeed.demo.test.VideoDataProvider;
import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.defualt.DefaultControlCover;
import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.decoder_cc.IDecoder;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.IPoolOperate;
import com.prim_player_cc.view.OnCoverNativePlayerListener;
import com.prim_player_cc.view.PrimPlayerCCView;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static com.prim_player_cc.cover_cc.event.CoverEventKey.DEFAULT_CONTROL_COVER;
import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_AUTO_PLAY_NEXT;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/2 - 6:47 PM
 */
public class VideoFragment extends Fragment implements OnPlayerEventListener,
        OnCoverNativePlayerListener
        , View.OnClickListener {


    private PrimPlayerCCView playerCCView;

    private static final String TAG = "VideoActivity";

    private SwitchCompat switchSurfaceView, switchTextureView, switchOpenGesture, switcScrollGesture;

    private SwitchCompat switchMediaPlayer, switchIjkPlayer, switchExoPlayer;

    private boolean isTextureView;

    private boolean isSurfaceView;

    private RadioGroup rg_video_aspect;

    private RadioGroup rg_video_speed;

    private RecyclerView rl_video_list;

    private VideoListAdapter adapter;

    private TextView tv_expand;

    private RadioGroup rg_play_type;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        switchMediaPlayer = view.findViewById(R.id.switchMediaPlayer);
        switchIjkPlayer = view.findViewById(R.id.switchIjkPlayer);
        switchExoPlayer = view.findViewById(R.id.switchExoPlayer);
        playerCCView = (PrimPlayerCCView) view.findViewById(R.id.player_cc);

        switchSurfaceView = (SwitchCompat) view.findViewById(R.id.switchSurfaceView);

        switchTextureView = (SwitchCompat) view.findViewById(R.id.switchTextureView);

        rg_video_aspect = (RadioGroup) view.findViewById(R.id.rg_video_aspect);

        rg_video_speed = (RadioGroup) view.findViewById(R.id.rg_video_speed);

        rl_video_list = (RecyclerView) view.findViewById(R.id.rl_video_list);

        tv_expand = (TextView) view.findViewById(R.id.tv_expand);

        rg_play_type = (RadioGroup) view.findViewById(R.id.rg_play_type);

        switcScrollGesture = (SwitchCompat) view.findViewById(R.id.switcScrollGesture);

        switchOpenGesture = (SwitchCompat) view.findViewById(R.id.switchOpenGesture);

        view.findViewById(R.id.btn_addControl).setOnClickListener(this);
        view.findViewById(R.id.btn_removeControl).setOnClickListener(this);
        view.findViewById(R.id.btn_addAdvert).setOnClickListener(this);
        view.findViewById(R.id.btn_removeAdvert).setOnClickListener(this);

        initListener();

        initRecycler();

        startVideo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//不锁屏


    }

    private void initRecycler() {
        adapter = new VideoListAdapter(getContext());
        rl_video_list.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
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

        switchMediaPlayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    playerCCView.dynamicChangedDecoder(11);
                    switchIjkPlayer.setChecked(false);
                    switchExoPlayer.setChecked(false);
                }
            }
        });

        switchIjkPlayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    playerCCView.dynamicChangedDecoder(12);
                    switchMediaPlayer.setChecked(false);
                    switchExoPlayer.setChecked(false);
                }
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

        rg_play_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_queue:
                        playerCCView.setLoopMode(IPoolOperate.LOOP_MODE_QUEUE);
                        break;
                    case R.id.rb_queue_list:
                        playerCCView.setLoopMode(IPoolOperate.LOOP_MODE_LIST_LOOP);
                        break;
                    case R.id.rb_loop:
                        playerCCView.setLoopMode(IPoolOperate.LOOP_MODE_SINGLE);
                        break;
                    case R.id.rb_finish_pause:
                        playerCCView.setLoopMode(IPoolOperate.LOOP_MODE_FINISH_PAUSE);
                        break;
                    case R.id.rb_remode:
                        playerCCView.setLoopMode(IPoolOperate.LOOP_MODE_RANDOM);
                        break;

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

        playerCCView.dynamicChangedDecoder(12);//切换解码器

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
    public void onPause() {
        super.onPause();
        playerCCView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        playerCCView.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerCCView.stop();
        playerCCView.destroy();
    }

    public void load() {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover(DEFAULT_CONTROL_COVER,
                new DefaultControlCover(getContext()));
    }

    public void removeLoad() {
        PrimPlayerCC.getCoverCCManager().dynamicDeleteCover(DEFAULT_CONTROL_COVER);
    }

    public void addAdvert() {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover("Advert", new AdvertPromatCover(getContext()));
    }

    public void removeAdvert() {
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
                bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.CoffeeDialog);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet, null, false);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bottom_sheet);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                bottomSheetDialog.setContentView(view);
                BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) view.getParent());
                View contentView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
                mBehavior.setPeekHeight(contentView.getHeight() - playerCCView.getHeight());
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetDialog.show();
                break;
            case R.id.btn_addControl:
                load();
                break;
            case R.id.btn_removeControl:
                removeLoad();
                break;
            case R.id.btn_addAdvert:
                addAdvert();
                break;
            case R.id.btn_removeAdvert:
                removeAdvert();
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
