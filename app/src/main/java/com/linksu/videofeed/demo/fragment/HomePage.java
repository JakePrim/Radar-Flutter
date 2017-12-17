package com.linksu.videofeed.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linksu.videofeed.R;

/**
 * Created by suful on 2017/12/17.
 */

public class HomePage extends Fragment {
    public static HomePage newInstance() {
        return new HomePage();
    }

    private View rootView;

    private Fragment fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
//        fragment = FragmentA.newInstance(((BaseActivity) getActivity()).lVideoView);
//        getFragmentManager().beginTransaction().add(R.id.fragmentLayout,
//                fragment, FragmentA.class.getName()).commit();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
