package com.linksu.videofeed.demo.test;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prim_player_cc.loader.ImageEngine;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/5 - 10:28 AM
 */
public class ImageLoader implements ImageEngine {
    @Override
    public void loadThumbnailImage(Context context, String url, ImageView view) {
        Glide.with(context).asBitmap().load(url).into(view);
    }

    @Override
    public void loadThumbnailImage(Context context, Uri uri, ImageView view) {
        Glide.with(context).asBitmap().load(uri).into(view);
    }
}
