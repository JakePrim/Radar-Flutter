package com.linksu.videofeed.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.cover.AdvertPromatCover;
import com.linksu.videofeed.demo.test.TestDataProvider;
import com.linksu.videofeed.demo.test.TestVideoBean;
import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.cover_cc.defualt.DefaultCoverKey;
import com.prim_player_cc.cover_cc.defualt.DefaultLoadCover;
import com.prim_player_cc.decoder_cc.IDecoder;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.IDataProvider;
import com.prim_player_cc.source_cc.IPoolOperate;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.view.PrimPlayerCCView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/27 - 上午10:18
 */
public class VideoActivity extends AppCompatActivity {

    private PrimPlayerCCView playerCCView;

    private static final String TAG = "VideoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        playerCCView = (PrimPlayerCCView) findViewById(R.id.player_cc);

        playerCCView.usedDefaultCoverGroup();//使用默认的视图组

        playerCCView.switchDecoder(12);//切换解码器

        //使用自定义的视图组
//        PrimPlayerCC.getCoverCCManager()
//                .addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this))
//                .addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(this))
//                .addCover(DefaultCoverKey.DEFAULT_ERROR_COVER, new DefaultErrorCover(this))
//                .addCover(DefaultCoverKey.DEFAULT_COMPLETE_COVER, new DefaultCompleteCover(this))
//                .insertCoverGroup(playerCCView);

        //设置呈现视频的RenderView
        //使用框架默认的surfaceView
        playerCCView.setRenderView(IRenderView.TEXTURE_VIEW);
        /**
         * 使用自定的view,从解码器中返回 {@link IDecoder#getRenderView()}
         */
//      playerCCView.setRenderView(IRenderView.CUSTOM_VIEW);

        /**
         * 使用框架默认的textureView
         */
//      playerCCView.setRenderView(IRenderView.TEXTURE_VIEW);

        //设置数据提供者


        PlayerSource playerSource = new
                PlayerSource("http://rmrbtest-image.peopleapp.com/upload/video/201809/153723951440062dcc54a0912f.mp4");
        playerSource.setTitle("带你去旅行");
        //设置播放资源
        playerCCView.setDataSource(playerSource);
        //开始播放 MediaPlayer 在准备完毕后自动播放
//        playerCCView.start(); error ： -38

        testDataPool();
    }

    /**
     * 测试数据池
     */
    private void testDataPool() {
        List<TestVideoBean> testVideoBeanList = new ArrayList<>(100);

        for (int i = 0; i < 200; i++) {
            TestVideoBean testVideoBean = new TestVideoBean();
            testVideoBean.setId(10 + i);
            testVideoBean.setCount(i * 100);
            testVideoBean.setImgUrl("http:www.baidu");
            testVideoBean.setTitle("测试标题:" + i);
            testVideoBean.setUrl("123");
            testVideoBeanList.add(testVideoBean);
        }

        final TestDataProvider testDataProvider = new TestDataProvider(testVideoBeanList);

        testDataProvider.setOnDateProviderListener(new IDataProvider.OnDataProviderListener() {
            @Override
            public void onBindDataStart() {
                Log.e(TAG, "绑定数据开始");
            }

            @Override
            public void onBindDataCancle() {
                Log.e(TAG, "绑定数据取消");
            }

            @Override
            public void onBindDataSuccess(PlayerSource source) {
//                Log.e(TAG,"绑定数据成功" + source.getId());
            }

            @Override
            public void onBindDataError(int code) {
                Log.e(TAG, "绑定数据错误:" + code);
            }

            @Override
            public void onBindDataFinish() {
                Log.e(TAG, "绑定数据完成");
//                testDataProvider.traversPool();
                PlayerSource currentSourceData = testDataProvider.getCurrentSourceData();
                Log.e(TAG, "获取当前的数据池中的偏移量指针指向的资源数据:" + currentSourceData.getId());
                //自动播放下一条资源
                testDataProvider.autoGetNextPlaySource();
                Log.e(TAG, "onBindDataFinish: 自动播放下一个：" + testDataProvider.getCurrentSourceData().getId());

                testDataProvider.playForward();
                Log.e(TAG, "onBindDataFinish:播放前一个 " + testDataProvider.getCurrentSourceData().getId());

                testDataProvider.setLoopMode(IPoolOperate.LOOP_MODE_SINGLE);//设置单曲循环
                testDataProvider.autoGetNextPlaySource();
                Log.e(TAG, "onBindDataFinish: 单曲循环下的自动播放下一个：" + testDataProvider.getCurrentSourceData().getId());

                testDataProvider.setOffsetPointer(10);
                Log.e(TAG, "onBindDataFinish: 移动指针到第10条数据：" + testDataProvider.getCurrentSourceData().getId());

                testDataProvider.removeNode(10);
                Log.e(TAG, "onBindDataFinish: 移除第10到节点，这时指针正好指向第10个节点，移除后到指针为:" + testDataProvider.getCurrentSourceData().getId());

                testDataProvider.setOffsetPointer(0);
                testDataProvider.removeNode();
                Log.e(TAG, "onBindDataFinish: 将指针移到第0个节点，这时移除第0个节点，移除后到指针为:" + testDataProvider.getCurrentSourceData().getId());
            }
        });

        //开始绑定资源数据
        testDataProvider.startBindSourceData();


    }

    public void load(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(this));
    }

    public void removeLoad(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicDeleteCover(DefaultCoverKey.DEFAULT_LOAD_COVER);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerCCView.destroy();
    }

    public void addControl(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicInsertCover("advert", new AdvertPromatCover(this));
    }

    public void removeControl(View view) {
        PrimPlayerCC.getCoverCCManager().dynamicDeleteCover("advert");
    }

    public void addSound(View view) {

    }

    public void removeSound(View view) {

    }
}
