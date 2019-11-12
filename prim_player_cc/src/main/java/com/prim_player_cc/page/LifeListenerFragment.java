package com.prim_player_cc.page;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author prim
 * @version 1.0.0
 * @desc 建议个空白的fragment监听Activity的生命周期
 * @time 2019-11-12 - 15:34
 * @contact https://jakeprim.cn
 * @name PrimPlayerCC
 */
public class LifeListenerFragment extends Fragment {

    private LifeListener mLifeListener;

    public void addLifeListener(LifeListener lifeListener) {
        this.mLifeListener = lifeListener;
    }

    public void removeLifeListener() {
        this.mLifeListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mLifeListener != null) {
            mLifeListener.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLifeListener != null) {
            mLifeListener.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLifeListener != null) {
            mLifeListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLifeListener != null) {
            mLifeListener.onStop();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mLifeListener != null) {
            onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mLifeListener != null) {
            mLifeListener.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLifeListener != null) {
            mLifeListener.onDestroy();
        }
    }
}
