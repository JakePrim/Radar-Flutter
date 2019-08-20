package com.prim_player_cc.transitions;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/15 - 5:37 PM
 */
public class TransitionUtils {
    private static final String DEFAULT_TRANSITION_NAME = "transition";

    public static int getItemPositionFromTransition(final String transitionName) {
        return Integer.parseInt(transitionName.substring(transitionName.length() - 1));
    }

    public static String getRecyclerViewTransitionName(final int position) {
        return DEFAULT_TRANSITION_NAME + position;
    }
}
