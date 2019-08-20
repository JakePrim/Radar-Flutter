package com.linksu.videofeed.demo.test;

import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.PlayerSource;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视频数据提供者
 * @time 2018/10/30 - 7:09 PM
 */
public class VideoDataProvider extends AbsDataProvider<List<VideoDataBean>> {
    public VideoDataProvider(List<VideoDataBean> videoDataBeans) {
        super(videoDataBeans);
    }

    @Override
    protected PlayerSource getCustomizeSource(Object data) {
        PlayerSource source = new PlayerSource();
        if (data instanceof VideoDataBean) {
            VideoDataBean dataBean = (VideoDataBean) data;
            source.setUrl(dataBean.url);
            source.setThumbnailUrl(dataBean.coverImg);
            source.setAdvertUrl(dataBean.advertUrl);
            source.setHeightUrl(dataBean.getHightUrl());
            source.setTitle(dataBean.videoTitle);
            source.setId(dataBean.movieId);
            source.setVideoLength(dataBean.videoLength);
            source.setData(dataBean);
        }
        return source;
    }
}
