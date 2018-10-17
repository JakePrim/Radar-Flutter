package com.prim_player_cc.render_cc;

import android.graphics.SurfaceTexture;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/10/17 - 下午4:04
 */
public interface ISurfaceTextureHolder {
    void setSurfaceTexture(SurfaceTexture surfaceTexture);

    SurfaceTexture getSurfaceTexture();

    void setSurfaceTextureHost(ISurfaceTextureHost surfaceTextureHost);
}
