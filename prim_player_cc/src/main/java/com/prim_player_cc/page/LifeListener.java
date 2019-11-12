package com.prim_player_cc.page;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-11-12 - 15:34
 * @contact https://jakeprim.cn
 * @name PrimPlayerCC
 */
public interface LifeListener {
    void onCreate(Bundle bundle);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
