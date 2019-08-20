package com.linksu.videofeed.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.linksu.videofeed.R;
import com.prim_player_cc.utils.Tools;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/10/25 - 5:24 PM
 */
public class PrimActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prim_layout);
    }

    public void onBasic(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

    public void onListPlay(View view) {
        showToast();
    }

    public void onCarryPlay(View view) {
        showToast();
    }

    public void onMulitAnimCarryPlay(View view) {
        showToast();
    }

    public void shortPlay(View view) {
        showToast();
    }

    public void windowPlay(View view) {
        showToast();
    }

    private void showToast() {
        Toast.makeText(this, "客官不要着急，功能正在，加紧实现中...", Toast.LENGTH_SHORT).show();
    }
}
