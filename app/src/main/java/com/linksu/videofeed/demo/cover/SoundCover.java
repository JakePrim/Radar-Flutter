package com.linksu.videofeed.demo.cover;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.linksu.videofeed.R;
import com.prim_player_cc.cover_cc.BaseCover;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/9 - 6:18 PM
 */
public class SoundCover extends BaseCover implements View.OnClickListener {
    ImageView cover_iv_volume;

    boolean isVolume;

    public SoundCover(Context context) {
        super(context);
        cover_iv_volume = findViewById(R.id.cover_iv_volume);
        cover_iv_volume.setOnClickListener(this);
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.cover_sound_layout, null);
    }

    @Override
    protected int[] setCoverLevel() {
        return new int[]{LEVEL_MIDDLE, 10};
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cover_iv_volume:
                if (!isVolume) {
                    isVolume = true;
                    if (mediaPlayerControl != null) {
                        mediaPlayerControl.setVolume(1, 1);
//                        cover_iv_volume.setImageResource(R.drawable.ic_volume_on);
                    }
                } else {
                    isVolume = false;
                    if (mediaPlayerControl != null) {
                        mediaPlayerControl.setVolume(0, 0);
//                        cover_iv_volume.setImageResource(R.mipmap.ic_volume_off);
                    }
                }

                break;
        }
    }
}
