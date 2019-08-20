package com.linksu.videofeed.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.test.VideoDataBean;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/10/31 - 2:54 PM
 */
public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    private List<VideoDataBean> dataBeanList;

    public VideoListAdapter(Context context) {
        this.context = context;
    }

    public void setDataBeanList(List<VideoDataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_video_list_layout, parent, false);
        return new VideoHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoHolder videoHolder = (VideoHolder) holder;
        videoHolder.update(position);
    }

    @Override
    public int getItemCount() {
        return dataBeanList == null ? 0 : dataBeanList.size();
    }

    private class VideoHolder extends RecyclerView.ViewHolder {

        TextView item_tv_video_title;

        public VideoHolder(View itemView) {
            super(itemView);
            item_tv_video_title = (TextView) itemView.findViewById(R.id.item_tv_video_title);
        }

        void update(final int position) {
            if (dataBeanList == null) return;
            final VideoDataBean dataBean = dataBeanList.get(position);
            if (dataBean == null) return;
            item_tv_video_title.setText(dataBean.movieName);
            if (dataBean.isPlaying) {
                item_tv_video_title.setBackgroundResource(R.drawable.res_shape_radius_85r_aa);
            } else {
                item_tv_video_title.setBackgroundResource(R.drawable.res_shape_radius_85r_ff);
            }
            item_tv_video_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null && !dataBean.isPlaying) {
                        onItemClickListener.OnClick(position, dataBean, v);
                    }
                }
            });
        }
    }

    public void updatePlaying(int position) {
        for (int i = 0; i < dataBeanList.size(); i++) {
            if (position == i) {
                dataBeanList.get(i).isPlaying = true;
            } else {
                dataBeanList.get(i).isPlaying = false;
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void OnClick(int position, Object data, View view);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
