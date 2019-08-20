package com.linksu.videofeed.demo.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.activity.VideoDetailActivity;
import com.linksu.videofeed.demo.adapter.HomeAdapter;
import com.linksu.videofeed.demo.test.DataFactory;
import com.linksu.videofeed.demo.test.VideoDataBean;
import com.linksu.videofeed.demo.view.DetailsLayout;
import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.transitions.TransitionUtils;
import com.prim_player_cc.view.PrimPlayerCCView;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/2 - 3:06 PM
 */
public class HomePager extends Fragment implements HomeAdapter.OnItemClickListener {

    private RecyclerView recyclerView;

    private HomeAdapter adapter;

    private View view;

    private static final String TAG = "HomePager";

    private int position;

    private PlayerSource source;

    private PrimPlayerCCView video_view;

    private SwipeRefreshLayout swipeRefresh;

    private FrameLayout container;

    private Scene detailScene;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setRetainInstance(true);
        Log.e(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        }
        Log.e(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: ");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated: ");
        adapter = new HomeAdapter(getContext());
        adapter.setOnItemClickListener(this);
        recyclerView = view.findViewById(R.id.rv_home);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        container = view.findViewById(R.id.container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        List<VideoDataBean> loadData = DataFactory.getLoadData();
        adapter.setList(loadData);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {//模拟耗时操作
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);//取消刷新
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(int position, final View view, PlayerSource source, VideoDataBean dataBean) {
        this.position = position;
        this.source = source;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            VideoDetailActivity.startVideoDetail(getActivity(), view, source, dataBean);
        }
    }

    public static int LOCAL_HEIGHT_STATUS_BAR = 0;

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        if (LOCAL_HEIGHT_STATUS_BAR == 0) {

            int result = 0;

            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }

            LOCAL_HEIGHT_STATUS_BAR = result;

            return result;

        } else {

            return LOCAL_HEIGHT_STATUS_BAR;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder instanceof HomeAdapter.VideoHolder) {
            HomeAdapter.VideoHolder videoHolder = (HomeAdapter.VideoHolder) viewHolder;
            AssistPlayer.defaultPlayer().resumePlay(videoHolder.item_video_view, source, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AssistPlayer.defaultPlayer().destroy();
    }
}


