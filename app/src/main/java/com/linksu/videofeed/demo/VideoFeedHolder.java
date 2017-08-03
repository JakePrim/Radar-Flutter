package com.linksu.videofeed.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linksu.video_manager_library.listener.OnVideoPlayerListener;
import com.linksu.video_manager_library.ui.LVideoView;
import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.bean.TabFragMainBeanItemBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/27 0027
 * 描    述：
 * 修订历史：
 * ================================================
 */
class VideoFeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View video_masked;
    private TextView item_tv, tv_video_time, tv_video_readCount;
    private List<TabFragMainBeanItemBean> mlist;
    private ImageView img;
    private LinearLayout ll_not_wifi;
    private ImageView iv_video_feed_start;
    private TextView tv_video_shear, tv_video_more, tv_video_comment;
    private Context context;
    private FrameLayout ll_video;
    private TabFragMainBeanItemBean itemBean;

    /**
     * 初始化view
     *
     * @param itemView
     * @param context
     */
    VideoFeedHolder(View itemView, Context context) {
        super(itemView);
        this.item_tv = (TextView) itemView.findViewById(R.id.item_tv);
        this.ll_video = (FrameLayout) itemView.findViewById(R.id.ll_video);
        this.img = (ImageView) itemView.findViewById(R.id.img);
        this.video_masked = itemView.findViewById(R.id.video_masked);
        this.tv_video_time = (TextView) itemView.findViewById(R.id.tv_video_time);
        this.tv_video_readCount = (TextView) itemView.findViewById(R.id.tv_video_readCount);
        this.iv_video_feed_start = (ImageView) itemView.findViewById(R.id.iv_video_feed_start);
        this.ll_not_wifi = (LinearLayout) itemView.findViewById(R.id.ll_not_wifi);
        this.tv_video_shear = (TextView) itemView.findViewById(R.id.tv_video_shear);
        this.tv_video_more = (TextView) itemView.findViewById(R.id.tv_video_more);
        this.tv_video_comment = (TextView) itemView.findViewById(R.id.tv_video_comment);
        this.context = context;
        bindListener();
    }

    /**
     * 绑定监听
     */
    private void bindListener() {
        tv_video_shear.setOnClickListener(this);
        tv_video_more.setOnClickListener(this);
        tv_video_comment.setOnClickListener(this);
        iv_video_feed_start.setOnClickListener(this);
    }

    /**
     * 初始化值
     *
     * @param position
     */
    public void update(int position, List<TabFragMainBeanItemBean> mlist) {
        this.mlist = mlist;
        itemBean = mlist.get(position);
        item_tv.setText(itemBean.title);
    }


    /**
     * 判断是不是WiFi的情况
     */
    public void playerWifi() {
//        if (Constants.VIDEO_FEED_WIFI) {
//            ll_not_wifi.setVisibility(View.GONE);
//            iv_video_feed_start.setEnabled(false);
//        } else {
//            int netType = NetChangeManager.getInstance().getNetType();
//            if (netType != 1) {// 不是WiFi下的情况
//                ll_not_wifi.setVisibility(View.VISIBLE);
//                iv_video_feed_start.setEnabled(true);
//            } else {
//                ll_not_wifi.setVisibility(View.GONE);
//                iv_video_feed_start.setEnabled(false);
//            }
//        }
    }

    /**
     * 显示当前播放的 item 的蒙层和 图片
     */
    public void visMasked() {
        img.setVisibility(View.VISIBLE);
        video_masked.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏当前播放的 item 的蒙层和 图片
     */
    public void goneMasked() {
        img.setVisibility(View.GONE);
        video_masked.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_video_feed_start:
//                Constants.VIDEO_FEED_WIFI = true;
                ll_not_wifi.setVisibility(View.GONE);
                iv_video_feed_start.setEnabled(false);
                if (listener != null) {
                    listener.videoWifiStart();
                }
                break;
            case R.id.tv_video_shear:
                if (listener != null) {

                }
                break;
            case R.id.tv_video_comment:
                break;
            case R.id.tv_video_more:
                break;
        }
    }


    public interface OnHolderVideoFeedListener {
        void videoWifiStart();

        void videoShare();

        void nightMode(boolean isNight);
    }

    private OnHolderVideoFeedListener listener;

    /**
     * 注册监听
     *
     * @param listener
     */
    public void registerVideoPlayerListener(OnHolderVideoFeedListener listener) {
        this.listener = listener;
    }

    /**
     * 解除监听
     */
    public void unRegisterVideoPlayerListener() {
        if (listener != null) {
            listener = null;
        }
    }
}
