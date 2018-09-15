package com.prim_player_cc.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.prim_player_cc.state.State.STATE_COMPLETE;
import static com.prim_player_cc.state.State.STATE_END;
import static com.prim_player_cc.state.State.STATE_ERROR;
import static com.prim_player_cc.state.State.STATE_IDEL;
import static com.prim_player_cc.state.State.STATE_INIT;
import static com.prim_player_cc.state.State.STATE_PAUSE;
import static com.prim_player_cc.state.State.STATE_PLAYING;
import static com.prim_player_cc.state.State.STATE_PREPARED;
import static com.prim_player_cc.state.State.STATE_STOP;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/24 - 下午2:39
 */
@IntDef({STATE_INIT,STATE_PREPARED,STATE_PLAYING,STATE_PAUSE,STATE_STOP,STATE_COMPLETE,STATE_ERROR,STATE_IDEL,STATE_END})
@Retention(RetentionPolicy.SOURCE)
public @interface PlayerState {
}
