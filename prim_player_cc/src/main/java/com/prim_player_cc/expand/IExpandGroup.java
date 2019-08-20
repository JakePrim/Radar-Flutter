package com.prim_player_cc.expand;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/27 - 11:55 AM
 */
public interface IExpandGroup {
    void addProducer(AbsEventProducer producer);

    boolean removeProducer(AbsEventProducer producer);

    void onDestory();
}
