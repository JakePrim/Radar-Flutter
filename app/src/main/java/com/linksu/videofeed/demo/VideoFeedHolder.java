package com.linksu.videofeed.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linksu.video_manager_library.listener.OnVideoPlayerListener;
import com.linksu.video_manager_library.ui.LVideoView;
import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.bean.TabFragMainBeanItemBean;
import com.linksu.videofeed.demo.manager.NetChangeManager;

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
public class VideoFeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public View video_masked;
    private TextView item_tv, tv_video_time, tv_video_readCount;
    private List<TabFragMainBeanItemBean> mlist;
    private ImageView img, btn_play;
    private LinearLayout ll_not_wifi;
    private ImageView iv_video_feed_start;
    private TextView tv_video_shear, tv_video_more;
    private Context context;
    public FrameLayout ll_video;
    private TabFragMainBeanItemBean itemBean;
    private FrameLayout fl_img;
    public RelativeLayout rl_bottom;
    public RelativeLayout rl;
    public TextView tv_video_comment;

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
        this.fl_img = (FrameLayout) itemView.findViewById(R.id.fl_img);
        this.btn_play = (ImageView) itemView.findViewById(R.id.btn_play);
        this.rl_bottom = (RelativeLayout) itemView.findViewById(R.id.rl_bottom);
        this.rl = (RelativeLayout) itemView.findViewById(R.id.rl);
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
        btn_play.setOnClickListener(this);
        btn_play.setClickable(false);
        btn_play.setEnabled(false);
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
        if (!NetChangeManager.getInstance().hasNet()) {
            fl_img.setVisibility(View.VISIBLE);
            ll_not_wifi.setVisibility(View.GONE);
            iv_video_feed_start.setEnabled(false);
        } else {
            if (Constants.VIDEO_FEED_WIFI) {
                ll_not_wifi.setVisibility(View.GONE);
                iv_video_feed_start.setEnabled(false);
            } else {
                int netType = NetChangeManager.getInstance().getNetType();
                if (netType != 1) {// 不是WiFi下的情况
                    video_masked.setVisibility(View.GONE);
                    fl_img.setVisibility(View.GONE);
                    ll_not_wifi.setVisibility(View.VISIBLE);
                    iv_video_feed_start.setEnabled(true);
                } else {
                    ll_not_wifi.setVisibility(View.GONE);
                    iv_video_feed_start.setEnabled(false);
                }
            }
        }
    }

    /**
     * 显示当前播放的 item 的蒙层和 图片
     */
    public void visMasked() {
//        fl_img.setVisibility(View.VISIBLE);
//        video_masked.setVisibility(View.VISIBLE);
        ll_not_wifi.setVisibility(View.GONE);
//        itemView.setEnabled(true);
        tv_video_shear.setEnabled(false);
        tv_video_more.setEnabled(false);
        tv_video_comment.setEnabled(false);
        btn_play.setClickable(false);
        btn_play.setEnabled(false);
        video_masked.setVisibility(View.VISIBLE);
        fl_img.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fl_img.getVisibility() == View.GONE)
                    fl_img.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    /**
     * 隐藏当前播放的 item 的蒙层和 图片
     */
    public void goneMasked() {
        video_masked.setVisibility(View.GONE);
//        itemView.setEnabled(false);
        ll_not_wifi.setVisibility(View.GONE);
        tv_video_shear.setEnabled(true);
        tv_video_more.setEnabled(true);
        tv_video_comment.setEnabled(true);
        btn_play.setClickable(true);
        btn_play.setEnabled(true);
    }

    public void missImg() {
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fl_img.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(500);
        fl_img.setAnimation(animation);
    }

    public void startPlay() {
        fl_img.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_video_feed_start:
                Constants.VIDEO_FEED_WIFI = true;
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
                if (listener != null) {
                    listener.intentComment();
                }
                break;
            case R.id.tv_video_more:
                break;
            case R.id.btn_play:
                if (listener != null) {
                    listener.thurmVideoPlayer();
                }
                break;
        }
    }


    public interface OnHolderVideoFeedListener {
        void videoWifiStart();

        void videoShare();

        void nightMode(boolean isNight);

        void thurmVideoPlayer();

        void intentComment();
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
