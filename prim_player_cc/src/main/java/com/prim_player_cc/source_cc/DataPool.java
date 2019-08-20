package com.prim_player_cc.source_cc;

import com.prim_player_cc.log.PrimLog;

import java.util.Random;

/**
 * @author prim
 * @version 1.0.0
 * @desc 资源数据池 存储多个数据资源。
 * 双链表实现同时便于多个资源的切换
 * @time 2018/10/19 - 下午6:42
 */
public class DataPool implements IDataPool, IPoolOperate {

    private transient int size = 0;

    /**
     * 头部节点
     */
    private transient NodeData mHeadNode = null;

    /**
     * 尾部节点
     */
    private transient NodeData mLastNode = null;

    /**
     * 数据池的偏移指针
     */
    private transient NodeData mOffsetNodePointer = mHeadNode;

    private @LoopMode
    int loopMode = LOOP_MODE_QUEUE;//默认为顺序播放

    private transient int mOffsetPointerIndex = 0;

    private transient PlayerSource mOffsetPointerSource = null;

    public DataPool() {
        size = 0;
        mOffsetPointerIndex = 0;
    }

    /**
     * 播放下一条资源数据
     *
     * @return {@link PlayerSource}
     */
    @Override
    public NodeData playNext() {
        if (isNext()) {
            NodeData nextData = mOffsetNodePointer.nextData;
            mOffsetNodePointer = nextData;
            return nextData;
        }
        return null;
    }

    /**
     * 播放上一条资源数据
     *
     * @return {@link PlayerSource]}
     */
    @Override
    public NodeData playForward() {
        if (isForward()) {
            NodeData perData = mOffsetNodePointer.perData;
            mOffsetNodePointer = perData;
            return perData;
        }
        return null;
    }

    /**
     * 是否存在下一个数据资源
     *
     * @return true 表示存在；false 不存在
     */
    @Override
    public boolean isNext() {
        return mOffsetNodePointer != null && mOffsetNodePointer.nextData != null;
    }

    /**
     * 是否存在上一个数据资源
     *
     * @return true 存在；false 不存在
     */
    @Override
    public boolean isForward() {
        return mOffsetNodePointer != null && mOffsetNodePointer.perData != null;
    }

    /**
     * 添加到数据池尾部。
     *
     * @param source {@link PlayerSource}
     * @return true 添加成功，false 添加失败
     */
    @Override
    public boolean addNode(PlayerSource source) {
        try {
            addLinkLast(source);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将数据资源添加到 index的位置
     *
     * @param source {@link PlayerSource} 数据资源
     * @param index  添加的位置 index > 0 && index < size
     * @return true 添加成功；false 添加失败
     */
    @Override
    public boolean addNode(PlayerSource source, int index) {
        try {
            if (index < 0 || index > size) return false;
            if (index == size) {
                addLinkLast(source);
            } else {
                addLinkBefore(source, node(index));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void addLinkBefore(PlayerSource source, NodeData nodeData) {
        NodeData perData = nodeData.perData;
        NodeData newNode = new NodeData(perData, nodeData, source, source.getId());
        nodeData.perData = newNode;
        if (perData == null) {
            mHeadNode = newNode;
        } else {
            perData.nextData = newNode;
        }
        size++;
    }

    private void addLinkLast(PlayerSource source) {
        NodeData node = this.mLastNode;
        NodeData newNode = new NodeData(node, null, source, source.getId());
        this.mLastNode = newNode;
        if (node == null) {
            this.mHeadNode = newNode;
        } else {
            node.nextData = newNode;
        }
        size++;
    }

    /**
     * 根据数据资源,删除某个节点
     *
     * @param source {@link PlayerSource}
     * @return true 删除成功；false 删除失败
     */
    @Override
    public boolean removeNode(PlayerSource source) {
        if (source == null) {
            for (NodeData x = mHeadNode; x != null; x = x.nextData) {
                if (x.source == null) {
                    unLink(x);
                    return true;
                }
            }
        } else {
            for (NodeData x = mHeadNode; x != null; x = x.nextData) {
                if (source.equals(x.source)) {
                    unLink(x);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 从头部节点依次移除
     *
     * @return {@link PlayerSource}
     */
    @Override
    public PlayerSource removeNode() {
        NodeData headNode = this.mHeadNode;
        PlayerSource source = headNode.source;
        NodeData nextData = headNode.nextData;
        headNode.source = null;
        headNode.nextData = null;
        if (mOffsetNodePointer.equals(mHeadNode)) {//如果指针正好等于头部节点 这时如果要删除头部节点
            mOffsetNodePointer = nextData;
        }
        this.mHeadNode = nextData;
        if (nextData == null) {
            this.mLastNode = null;
        } else {
            nextData.perData = null;
        }
        size--;
        return source;
    }


    /**
     * 移除某个位置的节点资源数据
     *
     * @param index index > 0 && index < size
     * @return
     */
    @Override
    public PlayerSource removeNode(int index) {
        if (index < 0 || index > size) {
            return null;
        }
        return unLink(node(index));
    }


    /**
     * 遍历数据池 查看数据池的数据是否正确
     */
    public void traversPool() {
        PrimLog.e(TAG, "遍历数据的大小:" + size);
        NodeData node = this.mHeadNode;
        while (node != null) {
            PrimLog.e(TAG, "" + node.source.getId());
            node = node.nextData;
        }
    }


    /**
     * 移除所有节点资源数据
     */
    @Override
    public void clear() {
        for (NodeData x = mHeadNode; x != null; ) {
            NodeData nextData = x.nextData;
            x.source = null;
            x.nextData = null;
            x.perData = null;
            x = nextData;
        }
        mHeadNode = mLastNode = mOffsetNodePointer = null;
        size = 0;
    }

    @Override
    public PlayerSource getCurrentSourceData() {
        if (mOffsetNodePointer != null) {
            return mOffsetNodePointer.source;
        }
        return null;
    }

    @Override
    public PlayerSource playIndexData(int index) {
        if (index < 0 || index > size) {
            return null;
        }
        NodeData node = node(index);
        mOffsetNodePointer = node;
        mOffsetPointerIndex = index;
        mOffsetPointerSource = node.source;
        return mOffsetPointerSource;
    }

    private PlayerSource unLink(NodeData nodeData) {
        if (mOffsetNodePointer.equals(nodeData)) {
            //如果要删除到节点 指针正好指向这个节点 将指针移到前个一节点
            mOffsetNodePointer = nodeData.perData;
        }
        PlayerSource source = nodeData.source;
        NodeData perData = nodeData.perData;
        NodeData nextData = nodeData.nextData;
        if (perData == null) {
            mHeadNode = nextData;
        } else {
            perData.nextData = nextData;
            nodeData.perData = null;
        }

        if (nextData == null) {
            mLastNode = perData;
        } else {
            nextData.perData = perData;
            nodeData.nextData = null;
        }
        nodeData.source = null;
        size--;
        return source;
    }

    NodeData node(int index) {
        if (index < (size >> 1)) {
            NodeData node = this.mHeadNode;
            for (int i = 0; i < index; i++) {
                node = node.nextData;
            }
            return node;
        } else {
            NodeData node = this.mLastNode;
            for (int i = size - 1; i > index; i--) {
                node = node.perData;
            }
            return node;
        }
    }

    /**
     * 返回数据池的大小
     *
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 查找某个资源数据在数据池的位置
     *
     * @param o {@link PlayerSource}
     * @return index 位置
     */
    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (NodeData x = mHeadNode; x != null; x = x.nextData) {
                if (x.source == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (NodeData x = mHeadNode; x != null; x = x.nextData) {
                if (o.equals(x.source)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    /**
     * 当前播放的位置
     *
     * @return 当前播放在数据池中的位置
     */
    public int currentPlayIndex() {
        return indexOf(mOffsetNodePointer.source);
    }


    /**
     * 设置播放模式 {@link IPoolOperate#LOOP_MODE_LIST_LOOP}
     *
     * @param mode 数据池拿取数据的模式
     */
    @Override
    public void setLoopMode(@LoopMode int mode) {
        this.loopMode = mode;
    }

    /**
     * 设置指针偏移
     *
     * @param source 资源数据
     */
    @Override
    public void setOffsetPointer(PlayerSource source) {
        if (source == null || mHeadNode == null) return;
        this.mOffsetPointerSource = source;
        if (source.equals(mHeadNode.source)) {//播放的为第一个节点数据资源
            mOffsetNodePointer = mHeadNode;
            return;
        }
        for (NodeData x = mHeadNode; x != null; x = x.nextData) {
            if (source.equals(x.source)) {
                mOffsetNodePointer = x;
                break;
            }
        }
    }

    private static final String TAG = "DataPool";

    @Override
    public void setOffsetPointer(int index) {
        if (index < 0 || index > size) {
            return;
        }
        this.mOffsetPointerIndex = index;
        this.mOffsetNodePointer = node(index);
    }

    public int getOffsetPointerIndex() {
        return mOffsetPointerIndex;
    }

    public PlayerSource getOffsetPointerSource() {
        return mOffsetPointerSource;
    }

    @Override
    public @LoopMode
    int getLoopMode() {
        return loopMode;
    }

    /**
     * 自动获取下一个播放资源
     * 当视频播放完毕后，根据数据池拿取数据的模式，自动获取下一个播放资源数据
     *
     * @return {@link NodeData} 当前节点
     */
    @Override
    public NodeData autoGetNextPlaySource() {
        NodeData nodeData = null;
        switch (loopMode) {
            case LOOP_MODE_SINGLE:
                if (mOffsetNodePointer != null) {
                    nodeData = mOffsetNodePointer;
                } else {
                    nodeData = mOffsetNodePointer = mHeadNode;
                }
                break;
            case LOOP_MODE_QUEUE:
                nodeData = queueNext();
                break;
            case LOOP_MODE_LIST_LOOP:
                nodeData = listLoopNext();
                break;
            case LOOP_MODE_RANDOM:
                nodeData = randomNext();
                break;
            default:
                break;
        }
        return nodeData;
    }

    private NodeData randomNext() {
        NodeData nodeData;
        Random random = new Random();
        int randomNumber = random.nextInt(size - 1) % ((size + 1));
        nodeData = mOffsetNodePointer = node(randomNumber);
        return nodeData;
    }

    /**
     * 手动播放下一个
     *
     * @return {@link NodeData}
     */
    @Override
    public NodeData manualGetNextPlaySource() {
        NodeData nodeData = null;
        if (isNext()) {
            nodeData = playNext();
        } else {
            nodeData = mOffsetNodePointer = mHeadNode;
        }
        return nodeData;
    }

    private NodeData queueNext() {
        NodeData nodeData = null;
        if (isNext()) {
            nodeData = playNext();
        }
        return nodeData;
    }

    private NodeData listLoopNext() {
        NodeData nodeData;
        if (isNext()) {
            nodeData = playNext();
        } else {
            nodeData = mOffsetNodePointer = mHeadNode;
        }
        return nodeData;
    }

    /**
     * 手动播放前一个
     *
     * @return {@link NodeData}
     */
    @Override
    public NodeData manualGetForwardPlaySource() {
        NodeData nodeData = null;
        if (isForward()) {
            nodeData = playForward();
        } else {
            nodeData = mOffsetNodePointer = mLastNode;
        }
        return nodeData;
    }

    /**
     * 当数据池当偏移指针指向{@link #mLastNode} 最后节点当前一个节点时
     * 自动请求服务器，是否还存在更多当资源数据，如果存在，请求服务器
     * 将数据放到数据池中
     */
    @Override
    public void autoLoadMorePlaySource() {
        //这里需要更具具体的逻辑自己去实现
    }
}
