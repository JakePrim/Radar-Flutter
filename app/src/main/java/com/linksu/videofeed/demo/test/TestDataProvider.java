package com.linksu.videofeed.demo.test;

import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.PlayerSource;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 测试数据提供者
 * @time 2018/10/23 - 2:13 PM
 */
public class TestDataProvider extends AbsDataProvider<List<TestVideoBean>> {

    public TestDataProvider(List<TestVideoBean> testVideoBeans) {
        super(testVideoBeans);
    }

    @Override
    protected PlayerSource getCustomizeSource(Object data) {
        PlayerSource source = new PlayerSource();
        if (data instanceof TestVideoBean) {
            TestVideoBean bean = (TestVideoBean) data;
            source.setTitle(bean.getTitle());
            source.setId(bean.getId() + "");
            source.setThumbnailUrl(bean.getImgUrl());
            source.setTag("Test");
            source.setData(bean);
        }
        return source;
    }

    @Override
    public void autoLoadMorePlaySource() {
        super.autoLoadMorePlaySource();
    }
}
