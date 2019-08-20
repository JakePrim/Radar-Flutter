package com.prim_player_cc.status;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.prim_player_cc.status.Status.STATE_COMPLETE;
import static com.prim_player_cc.status.Status.STATE_END;
import static com.prim_player_cc.status.Status.STATE_ERROR;
import static com.prim_player_cc.status.Status.STATE_IDEL;
import static com.prim_player_cc.status.Status.STATE_INIT;
import static com.prim_player_cc.status.Status.STATE_PAUSE;
import static com.prim_player_cc.status.Status.STATE_PREPARED;
import static com.prim_player_cc.status.Status.STATE_PREPARING;
import static com.prim_player_cc.status.Status.STATE_START;
import static com.prim_player_cc.status.Status.STATE_STOP;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/24 - 下午2:39
 */
@IntDef({STATE_INIT, STATE_PREPARED, STATE_PREPARING, STATE_PAUSE, STATE_COMPLETE, STATE_ERROR, STATE_IDEL, STATE_END,STATE_START
,STATE_STOP})
@Retention(RetentionPolicy.SOURCE)
public @interface PlayerStatus {
}
