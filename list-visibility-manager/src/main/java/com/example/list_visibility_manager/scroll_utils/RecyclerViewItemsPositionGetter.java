package com.example.list_visibility_manager.scroll_utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by suful on 2017/12/16.
 */

public class RecyclerViewItemsPositionGetter implements ItemsPositionGetter {
    private static final String TAG = "RecyclerViewItemsPositionGetter";

    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    public RecyclerViewItemsPositionGetter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        this.recyclerView = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public View getChildAt(int position) {
        View childAt = recyclerView.getChildAt(position);
        return childAt;
    }

    @Override
    public int indexOfChild(View view) {
        int indexOfChild = recyclerView.indexOfChild(view);
        return indexOfChild;
    }

    @Override
    public int getChildCount() {
        return linearLayoutManager.getChildCount();
    }

    @Override
    public int getFirstVisiblePosition() {
        return linearLayoutManager.findFirstVisibleItemPosition();
    }

    @Override
    public int getLastVisiblePosition() {
        return linearLayoutManager.findLastVisibleItemPosition();
    }
}
