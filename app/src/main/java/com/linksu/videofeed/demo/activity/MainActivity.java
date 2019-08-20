package com.linksu.videofeed.demo.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.fragment.HomePager;
import com.linksu.videofeed.demo.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.mipmap.logo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, new HomePager(), "HOME_PAGE");
        fragmentTransaction.commit();
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, fragment, tag);
        fragmentTransaction.commit();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.activity) {
            item.setChecked(true);
            return true;
        } else if (id == R.id.fragment) {
            return true;
        } else if (id == R.id.autoPlay) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {//基础功能
            addFragment(new VideoFragment(), "video");
        } else if (id == R.id.nav_gallery) {
            showToastDec();
        } else if (id == R.id.nav_slideshow) {
            showToastDec();
        } else if (id == R.id.nav_manage) {
            showToastDec();
        } else if (id == R.id.nav_share) {
            showToastDec();
        } else if (id == R.id.nav_send) {
            showToastDec();
        } else if (id == R.id.nav_home) {
            addFragment(new HomePager(), "HOME_PAGE");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showToastDec() {
        Toast.makeText(this, "客官你靠的太近，客官你不要着急...", Toast.LENGTH_SHORT).show();
    }

    private long time;

    private static final String TAG = "MainActivity";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e(TAG, "onKeyDown: ");
            if (System.currentTimeMillis() - time >= 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
                return true;
            } else {
                finish();
                return super.onKeyDown(keyCode, event);
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
