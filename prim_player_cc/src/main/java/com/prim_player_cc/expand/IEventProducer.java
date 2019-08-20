package com.prim_player_cc.expand;

/**
 * @author prim
 * @version 1.0.0
 * @desc 事件生产者接口，用来扩展事件的生产者，用于系统的一些监听或者网络监听等事件
 * @time 2018/11/27 - 11:37 AM
 */
public interface IEventProducer {
    void onAdded();

    void onRemoved();

    void onDestroy();

    CoverEventSender getSender();
}
