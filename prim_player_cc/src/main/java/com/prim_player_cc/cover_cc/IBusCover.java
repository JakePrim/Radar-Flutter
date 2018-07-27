package com.prim_player_cc.cover_cc;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/26 - 上午11:23
 */
public interface IBusCover {
    void setCoverGroup(ICoverGroup coverGroup);

    ICoverGroup getCoverGroup();

    void destroy();
}
