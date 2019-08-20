package com.linksu.videofeed.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.cover.SoundCover;
import com.linksu.videofeed.demo.test.VideoDataBean;
import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.cover_cc.CoverGroup;
import com.prim_player_cc.utils.ArrayTools;
import com.prim_player_cc.view.AssistPlayerView;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.PlayerSource;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/2 - 3:22 PM
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<VideoDataBean> list;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<VideoDataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_home_layout, null, false);
        return new VideoHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoHolder videoHolder = (VideoHolder) holder;
        videoHolder.update(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private static final String TAG = "HomeAdapter";

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        VideoHolder videoHolder = (VideoHolder) holder;
        if (videoHolder.fl_item.getVisibility() == View.GONE) {
            Log.e(TAG, "onViewDetachedFromWindow: 当前的view销毁");
            AssistPlayer.defaultPlayer().stop();
            videoHolder.fl_item.setVisibility(View.VISIBLE);
            videoHolder.fl_item.setEnabled(true);
        }
    }

    private VideoHolder mVideoHolder;

    public class VideoHolder extends RecyclerView.ViewHolder {

        public AssistPlayerView item_video_view;

        public TextView item_tv_home_video_title, item_tv_home_video_secondary_title;

        public ImageView item_video_img, iv_thumiled;

        public FrameLayout fl_item;

        public RelativeLayout rl_item_video_title;

        public VideoHolder(View itemView) {
            super(itemView);
            item_video_view = itemView.findViewById(R.id.item_video_view);
            item_tv_home_video_title = itemView.findViewById(R.id.item_tv_home_video_title);
            item_tv_home_video_secondary_title = itemView.findViewById(R.id.item_tv_home_video_secondary_title);
            item_video_img = itemView.findViewById(R.id.item_video_img);
            fl_item = itemView.findViewById(R.id.fl_item);
            iv_thumiled = itemView.findViewById(R.id.iv_thumiled);
            rl_item_video_title = itemView.findViewById(R.id.rl_item_video_title);
        }

        public void update(final int position) {
            if (ArrayTools.isEmpty(list)) return;
            final VideoDataBean dataBean = list.get(position);
            if (dataBean == null) return;
            item_tv_home_video_title.setText(dataBean.movieName);
            item_tv_home_video_secondary_title.setText(dataBean.summary);
            Glide.with(context).asBitmap().load(dataBean.coverImg).into(iv_thumiled);
            //surfaceView
//            item_video_view.setRenderView(IRenderView.SURFACE_VIEW);
            //关闭手势 否则会影响 view的点击事件
            item_video_view.setGesture(false);
            //设置播放资源
            final PlayerSource source = new PlayerSource();
            source.setTitle(dataBean.videoTitle);
            source.setUrl(dataBean.url);
            source.setAdvertUrl(dataBean.advertUrl);
            source.setHeightUrl(dataBean.hightUrl);
            source.setId(dataBean.movieId);
            source.setData(dataBean);

            fl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopCurVideoView();
                    mVideoHolder = VideoHolder.this;
                    startCurVideoView(source);
                }
            });

            item_video_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, item_video_view, source, dataBean);
                    }
                }
            });

            rl_item_video_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!AssistPlayer.defaultPlayer().isPlaying() || !AssistPlayer.defaultPlayer().getCurrentSource().equals(source)) {
                        stopCurVideoView();
                        mVideoHolder = VideoHolder.this;
                        startCurVideoView(source);
                    }
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, item_video_view, source, dataBean);
                    }
                }
            });

        }
    }

    public void stopCurVideoView() {
        if (mVideoHolder != null) {
            AssistPlayer.defaultPlayer().stop();
            mVideoHolder.fl_item.setVisibility(View.VISIBLE);
            mVideoHolder.fl_item.setEnabled(true);
        }
    }

    public void startCurVideoView(PlayerSource source) {
        if (mVideoHolder != null) {
            CoverGroup coverGroup = new CoverGroup();
            coverGroup.addCover("Volume", new SoundCover(context));
            mVideoHolder.item_video_view.setCoverGroup(coverGroup);
            //由播放器助手播放
            AssistPlayer.defaultPlayer().resumePlay(mVideoHolder.item_video_view, source, true);
            mVideoHolder.item_video_view.setVolume(0, 0);
            mVideoHolder.fl_item.setVisibility(View.GONE);
            mVideoHolder.fl_item.setEnabled(false);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view, PlayerSource source, VideoDataBean dataBean);
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
