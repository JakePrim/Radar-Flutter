package com.linksu.videofeed.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.linksu.videofeed.demo.test.TestDataProvider;
import com.linksu.videofeed.demo.test.TestVideoBean;
import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.IDataProvider;
import com.prim_player_cc.source_cc.IPoolOperate;
import com.prim_player_cc.source_cc.PlayerSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/10/30 - 6:15 PM
 */
public class BaseActivity extends AppCompatActivity {

    protected AbsDataProvider dataProvider;

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 测试数据提供者 及数据池
     */
    protected void testDataPool() {
        List<TestVideoBean> testVideoBeanList = new ArrayList<>(100);

        for (int i = 0; i < 200; i++) {
            TestVideoBean testVideoBean = new TestVideoBean();
            testVideoBean.setId(10 + i);
            testVideoBean.setCount(i * 100);
            testVideoBean.setImgUrl("http:www.baidu");
            testVideoBean.setTitle("测试标题:" + i);
            testVideoBean.setUrl("http://rmrbtest-image.peopleapp.com/upload/video/201809/153723951440062dcc54a0912f.mp4");
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
}
