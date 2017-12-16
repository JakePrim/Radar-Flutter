package com.example.list_visibility_manager.scroll_utils;

import android.view.View;

/**
 * Created by suful on 2017/12/16.
 * This is class API ,using this class is access all this data for RecyclerView or ListView
 * <p>
 * get item position view
 */

public interface ItemsPositionGetter {
    View getChildAt(int position);

    int indexOfChild(View view);

    int getChildCount();

    int getFirstVisiblePosition();

    int getLastVisiblePosition();
}
