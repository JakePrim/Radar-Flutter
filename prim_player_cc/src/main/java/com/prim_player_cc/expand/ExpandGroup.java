package com.prim_player_cc.expand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 扩展事件组, 将扩展事件都添加到此组中
 * @time 2018/11/27 - 11:36 AM
 */
public class ExpandGroup implements IExpandGroup {

    //视图事件发送者
    private CoverEventSender mCoverEventSender;

    //事件生产者队列
    private List<AbsEventProducer> mProducers;

    public ExpandGroup(CoverEventSender mCoverEventSender) {
        this.mCoverEventSender = mCoverEventSender;
        mProducers = new ArrayList<>();
    }

    @Override
    public void addProducer(AbsEventProducer producer) {
        if (!mProducers.contains(producer)) {
            producer.attachSender(mCoverEventSender);
            mProducers.add(producer);
            producer.onAdded();
        }
    }

    @Override
    public boolean removeProducer(AbsEventProducer producer) {
        boolean remove = mProducers.remove(producer);
        if (producer != null) {
            producer.onRemoved();
            producer.attachSender(null);
        }
        return remove;
    }

    @Override
    public void onDestory() {
        for (AbsEventProducer producer : mProducers) {
            producer.onRemoved();
            producer.onDestroy();
            producer.attachSender(null);
        }
        mProducers.clear();
    }
}
