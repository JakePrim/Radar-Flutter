package com.linksu.videofeed.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linksu.videofeed.R;

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
public class VideoAdapter extends RecyclerView.Adapter<VideoFeedHolder> {
    private List mlist;
    private Context context;
    private RecyclerView recyclerView;

    public VideoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public VideoFeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new VideoFeedHolder(inflater, context);
    }

    public void setList(List mlist) {
        this.mlist = mlist;
        this.mNotify();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void mNotify() {
        if (recyclerView != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(VideoFeedHolder holder, int position) {
        holder.update(position, mlist);
    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
