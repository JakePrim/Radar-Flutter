package com.prim_player_cc.source_cc;

/**
 * @author prim
 * @version 1.0.0
 * @desc 数据池 用来存储多个数据,播放下一个和前一个
 * 用双链表来实现
 * @time 2018/10/19 - 下午6:20
 */
public interface IDataPool {
    NodeData playNext();

    NodeData playForward();

    boolean isNext();

    boolean isForward();

    boolean addNode(PlayerSource source);

    boolean addNode(PlayerSource source,int index);

    boolean removeNode(PlayerSource source);

    PlayerSource removeNode();

    PlayerSource removeNode(int index);

    void clear();

    PlayerSource getCurrentSourceData();

    PlayerSource playIndexData(int index);

    int size();

    int indexOf(Object o);

}
