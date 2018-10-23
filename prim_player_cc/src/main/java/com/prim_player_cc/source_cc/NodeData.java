package com.prim_player_cc.source_cc;

import com.prim_player_cc.utils.ObjectEquals;

/**
 * @author prim
 * @version 1.0.0
 * @desc 数据池节点数据类
 * @time 2018/10/19 - 下午7:21
 */
public class NodeData {
    public NodeData perData;

    public NodeData nextData;

    public PlayerSource source;

    public String _id;

    public NodeData(NodeData perData, NodeData nextData, PlayerSource source, String _id) {
        this.perData = perData;
        this.nextData = nextData;
        this.source = source;
        this._id = _id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return ObjectEquals.equals(perData, nodeData.perData) &&
                ObjectEquals.equals(nextData, nodeData.nextData) &&
                ObjectEquals.equals(source, nodeData.source) &&
                ObjectEquals.equals(_id, nodeData._id);
    }

    @Override
    public int hashCode() {

        return ObjectEquals.hash(perData, nextData, source, _id);
    }
}
