package com.linksu.videofeed.demo.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.linksu.videofeed.demo.fragment.HomePage;

/**
 * Created by suful on 2017/12/17.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private String[] titles;

    public FragmentPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return HomePage.newInstance();
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
