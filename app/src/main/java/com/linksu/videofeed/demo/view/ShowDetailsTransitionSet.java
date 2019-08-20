package com.linksu.videofeed.demo.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.View;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.transition.ShowDetailsTransition;
import com.prim_player_cc.transitions.TransitionBuilder;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/16 - 2:20 PM
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ShowDetailsTransitionSet extends TransitionSet {

    private final View from;
    private final DetailsLayout to;
    private final String transitionName;
    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShowDetailsTransitionSet(Context mContext, View from, DetailsLayout to, String transitionName) {
        this.mContext = mContext;
        this.from = from;
        this.to = to;
        this.transitionName = transitionName;
        addTransition(slide());
//        addTransition(new ShowDetailsTransition());
        addTransition(shared());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Transition slide() {
        ShowDetailsTransition showDetailsTransition = new ShowDetailsTransition();
        return new TransitionBuilder(showDetailsTransition)
                .excludeTarget(transitionName, true)
                .excludeTarget(to.assistPlayerView, true)
                .excludeTarget(to.detail_rv, true)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Transition shared(){
        return new TransitionBuilder(TransitionInflater.from(mContext).inflateTransition(android.R.transition.move))
                .link(from,to.assistPlayerView,transitionName)
                .build();
    }
}
