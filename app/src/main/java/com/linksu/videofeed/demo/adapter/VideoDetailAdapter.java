package com.linksu.videofeed.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
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
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.view.AssistPlayerView;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/15 - 4:24 PM
 */
public class VideoDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoDataBean> list;

    private Context context;

    public VideoDetailAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<VideoDataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setMoreDataList(List<VideoDataBean> list, boolean loadMore, int oldLength) {
        this.list = list;
        mNotify(loadMore, oldLength);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_video_detail_layout, null, false);
        return new VideoDetailHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        VideoDetailHolder detailHolder = (VideoDetailHolder) viewHolder;
        detailHolder.update(i);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void mNotify(boolean loadMore, int oldLength) {
        if (loadMore) {
            try {
                notifyItemRangeInserted(oldLength, list.size() - oldLength);
            } catch (Exception e) {
                notifyDataSetChanged();
            }
        } else {
            notifyDataSetChanged();
        }
    }

    private VideoDetailHolder mVideoHolder;

    private class VideoDetailHolder extends RecyclerView.ViewHolder {

        public AssistPlayerView item_video_view;

        public TextView item_tv_home_video_title, item_tv_home_video_secondary_title;

        public ImageView item_video_img, iv_thumiled;

        public FrameLayout fl_item;

        public RelativeLayout rl_item_video_title;

        public VideoDetailHolder(@NonNull View itemView) {
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
            if (list == null) return;
            VideoDataBean dataBean = list.get(position);
            if (dataBean == null) return;
            //设置播放资源
            final PlayerSource source = new PlayerSource();
            source.setTitle(dataBean.videoTitle);
            source.setUrl(dataBean.url);
            source.setId(dataBean.movieId);

            item_video_view.setRenderView(IRenderView.TEXTURE_VIEW);

            if (position == 0) {
                fl_item.setVisibility(View.GONE);
//                AssistPlayer.get().resumePlay(item_video_view, source, false);
                ViewCompat.setTransitionName(item_video_view, "image");
                item_video_view.usedDefaultCoverGroup();
            }

            item_tv_home_video_title.setText(dataBean.movieName);
            item_tv_home_video_secondary_title.setText(dataBean.summary);
            Glide.with(context).asBitmap().load(dataBean.coverImg).into(iv_thumiled);

            fl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopCurVideoView();
                    mVideoHolder = VideoDetailHolder.this;
                    startCurVideoView(source);
                    fl_item.setVisibility(View.GONE);
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
//            AssistPlayer.defaultPlayer().resumePlay(mVideoHolder.item_video_view, source, true);
            mVideoHolder.item_video_view.setVolume(0, 0);
            mVideoHolder.fl_item.setVisibility(View.GONE);
            mVideoHolder.fl_item.setEnabled(false);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view, PlayerSource source);
    }

    public HomeAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(HomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
