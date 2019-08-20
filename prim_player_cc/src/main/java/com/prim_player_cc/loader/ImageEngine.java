package com.prim_player_cc.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

/**
 * @author prim
 * @version 1.0.0
 * @desc 缩率图加载器 需要自己去实现
 * @time 2018/11/2 - 4:30 PM
 */
public interface ImageEngine {
    void loadThumbnailImage(Context context, String url, ImageView view);

    void loadThumbnailImage(Context context, Uri uri, ImageView view);
}
