package com.prim_player_cc.expand;

/**
 * @author prim
 * @version 1.0.0
 * @desc 事件生产者的抽象类，自定义的事件生产者必须继承此类
 * @time 2018/11/27 - 11:53 AM
 */
public abstract class AbsEventProducer implements IEventProducer {
    private CoverEventSender mCoverEventSender;

    public void attachSender(CoverEventSender coverEventSender) {
        this.mCoverEventSender = coverEventSender;
    }

    @Override
    public CoverEventSender getSender() {
        return mCoverEventSender;
    }
}
