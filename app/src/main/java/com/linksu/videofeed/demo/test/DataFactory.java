package com.linksu.videofeed.demo.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 测试数据
 * @time 2018/10/30 - 6:52 PM
 */
public class DataFactory {

    public static List<VideoDataBean> getLoadData() {
        List<VideoDataBean> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray trailers = jsonObject.getJSONArray("trailers");
            for (int i = 0; i < trailers.length(); i++) {
                JSONObject item = trailers.getJSONObject(i);
                VideoDataBean videoDataBean = new VideoDataBean();
                videoDataBean.coverImg = item.optString("coverImg");
                videoDataBean.hightUrl = item.optString("hightUrl");
                videoDataBean.id = item.optString("id");
                videoDataBean.url = item.optString("url");
                videoDataBean.movieId = item.optString("movieId");
                videoDataBean.movieName = item.optString("movieName");
                videoDataBean.videoTitle = item.optString("videoTitle");
                videoDataBean.type = item.optString("type");
                videoDataBean.rating = item.optString("rating");
                videoDataBean.summary = item.optString("summary");
                videoDataBean.videoLength = item.optString("videoLength");
                videoDataBean.advertUrl = "https://media.w3.org/2010/05/sintel/trailer.mp4";
                list.add(videoDataBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static final String jsonData = "{\n" +
            "    \"trailers\": [\n" +
            "        {\n" +
            "            \"id\": 72443,\n" +
            "            \"movieName\": \"渣女随便坐男人大腿，网上各种老公老婆乱叫，涂磊说的太有道理了\",\n" +
            "            \"coverImg\": \"http://rmrbtest-image.peopleapp.com/upload/zw/bjh_image/1537854934_d14a904efec480f98c90b040204b7108.jpeg?x-oss-process=style/w10\",\n" +
            "            \"movieId\": 190574,\n" +
            "            \"url\": \"http://rmrbtest-image.peopleapp.com/upload/zw/bjh_video/15378675494e70c2dbf84272dd4c4a09cf3b667144.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/26/mp4/181026140242572417.mp4\",\n" +
            "            \"videoTitle\": \"渣女随便坐男人大腿，网上各种老公老婆乱叫，涂磊说的太有道理了\",\n" +
            "            \"videoLength\": 144,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动画\",\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"喜剧\",\n" +
            "                \"家庭\",\n" +
            "                \"奇幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"完结篇没牙仔把妹下功夫\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72441,\n" +
            "            \"movieName\": \"上司男友出轨闺蜜，女孩直接崩溃一把扯掉假发，震惊全场！\",\n" +
            "            \"coverImg\": \"http://rmrbtest-image.peopleapp.com/upload/zw/bjh_image/1537854938_14656230366faa3f79d1dbc54a93cfb8.jpeg?x-oss-process=style/w10\",\n" +
            "            \"movieId\": 218707,\n" +
            "            \"url\": \"http://rmrbtest-image.peopleapp.com/upload/zw/bjh_video/15378675519443d92513c46a1340a9fbee859f4b04.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/25/mp4/181025210019382009.mp4\",\n" +
            "            \"videoTitle\": \"上司男友出轨闺蜜，女孩直接崩溃一把扯掉假发，震惊全场！\",\n" +
            "            \"videoLength\": 70,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"科幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"吴京首演宇航员助力中国科幻\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72420,\n" +
            "            \"movieName\": \"花儿与少年：花少团魔性全体陷入横店拍摄模式，无法自拔！\",\n" +
            "            \"coverImg\": \"http://rmrbtest-image.peopleapp.com/upload/zw/bjh_image/1537855010_5495cb5d0c94df7966c59f0117c47321.jpeg?x-oss-process=style/w10\",\n" +
            "            \"movieId\": 254606,\n" +
            "            \"url\": \"http://rmrbtest-image.peopleapp.com/upload/zw/bjh_video/1537867616787e579bf706d3d9d09e884b6c543c52.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/24/mp4/181024135044050740.mp4\",\n" +
            "            \"videoTitle\": \"花儿与少年：花少团魔性全体陷入横店拍摄模式，无法自拔！\",\n" +
            "            \"videoLength\": 120,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"催泪的父母之爱\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72416,\n" +
            "            \"movieName\": \"《叶问外传：张天志》定档预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/24/111134.47096131_120X90X4.jpg\",\n" +
            "            \"movieId\": 234316,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/24/mp4/181024112258582568.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/24/mp4/181024112258582568.mp4\",\n" +
            "            \"videoTitle\": \"张天志 定档预告\",\n" +
            "            \"videoLength\": 83,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动作\"\n" +
            "            ],\n" +
            "            \"summary\": \"张晋杨紫琼托尼贾各显真功夫\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72408,\n" +
            "            \"movieName\": \"王千源包贝尔《大人物》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/24/090821.25684408_120X90X4.jpg\",\n" +
            "            \"movieId\": 255481,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/24/mp4/181024090825363206.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/24/mp4/181024090825363206.mp4\",\n" +
            "            \"videoTitle\": \"大人物 “跳楼案”版定档预告\",\n" +
            "            \"videoLength\": 83,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"犯罪\",\n" +
            "                \"动作\"\n" +
            "            ],\n" +
            "            \"summary\": \"富二代挑衅警察强拆逼民跳楼\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72367,\n" +
            "            \"movieName\": \"《铁血战士》中文终极预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/22/083650.60046421_120X90X4.jpg\",\n" +
            "            \"movieId\": 227422,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/22/mp4/181022083653874222.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/22/mp4/181022083653874222.mp4\",\n" +
            "            \"videoTitle\": \"铁血战士 终极预告\",\n" +
            "            \"videoLength\": 90,\n" +
            "            \"rating\": 6,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"科幻\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"“铁血”不灭，热血依旧\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72373,\n" +
            "            \"movieName\": \"《无敌破坏王2》终极预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/22/104643.54636409_120X90X4.jpg\",\n" +
            "            \"movieId\": 226450,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/22/mp4/181022104811265262.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/22/mp4/181022104811265262.mp4\",\n" +
            "            \"videoTitle\": \"无敌破坏王  中国终极预告片\",\n" +
            "            \"videoLength\": 75,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动画\",\n" +
            "                \"冒险\",\n" +
            "                \"喜剧\",\n" +
            "                \"家庭\",\n" +
            "                \"奇幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"破坏王拉尔夫大闹网络\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72336,\n" +
            "            \"movieName\": \"《滴答屋》定档预告片\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/18/105522.82570420_120X90X4.jpg\",\n" +
            "            \"movieId\": 251180,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/18/mp4/181018105544048673.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/18/mp4/181018105544048673.mp4\",\n" +
            "            \"videoTitle\": \"滴答屋 中国内地定档预告\",\n" +
            "            \"videoLength\": 117,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"奇幻\",\n" +
            "                \"喜剧\",\n" +
            "                \"冒险\",\n" +
            "                \"家庭\",\n" +
            "                \"惊悚\",\n" +
            "                \"科幻\",\n" +
            "                \"恐怖\",\n" +
            "                \"悬疑\"\n" +
            "            ],\n" +
            "            \"summary\": \"大魔王大战神秘力量\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72303,\n" +
            "            \"movieName\": \"汤姆哈迪《毒液》定档预告 \",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/16/151251.92231589_120X90X4.jpg\",\n" +
            "            \"movieId\": 103937,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/16/mp4/181016153349501790.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/16/mp4/181016153349501790.mp4\",\n" +
            "            \"videoTitle\": \"毒液：致命守护者  “与毒共舞”版定档预告\",\n" +
            "            \"videoLength\": 111,\n" +
            "            \"rating\": 7.2,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"科幻\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"汤老湿恶灵附体血口舔屏\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72302,\n" +
            "            \"movieName\": \"《海王》定档预告片\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/16/153127.35819866_120X90X4.jpg\",\n" +
            "            \"movieId\": 132277,\n" +
            "            \"url\": \"http://rmrbtest-image.peopleapp.com/upload/zw/bjh_video/15378675519443d92513c46a1340a9fbee859f4b04.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/16/mp4/181016142837621932.mp4\",\n" +
            "            \"videoTitle\": \"海王  定档预告片\",\n" +
            "            \"videoLength\": 77,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"奇幻\",\n" +
            "                \"科幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"海王率领群鲨争夺王位\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72299,\n" +
            "            \"movieName\": \"《神奇动物2》定档预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/16/152605.11778405_120X90X4.jpg\",\n" +
            "            \"movieId\": 232243,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/16/mp4/181016115514715240.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/16/mp4/181016115514715240.mp4\",\n" +
            "            \"videoTitle\": \"神奇动物：格林沃德之罪  定档预告\",\n" +
            "            \"videoLength\": 75,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"冒险\",\n" +
            "                \"家庭\",\n" +
            "                \"奇幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"魔法暴击!纽特邓布利多同框\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72301,\n" +
            "            \"movieName\": \"《摘金奇缘》定档预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/16/142259.36860633_120X90X4.jpg\",\n" +
            "            \"movieId\": 233844,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/16/mp4/181016142314527523.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/16/mp4/181016142314527523.mp4\",\n" +
            "            \"videoTitle\": \"摘金奇缘  定档预告片\",\n" +
            "            \"videoLength\": 84,\n" +
            "            \"rating\": 6.9,\n" +
            "            \"type\": [\n" +
            "                \"喜剧\",\n" +
            "                \"爱情\"\n" +
            "            ],\n" +
            "            \"summary\": \"\\\"壕婆婆\\\"杨紫琼对决美国媳妇\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72281,\n" +
            "            \"movieName\": \"刘亚仁《国家破产之日》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/15/120601.28400237_120X90X4.jpg\",\n" +
            "            \"movieId\": 249691,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/15/mp4/181015120659161573.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/15/mp4/181015120659161573.mp4\",\n" +
            "            \"videoTitle\": \"国家破产之日 预告片\",\n" +
            "            \"videoLength\": 55,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72250,\n" +
            "            \"movieName\": \"迪士尼真人版《阿拉丁》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/12/101330.69941269_120X90X4.jpg\",\n" +
            "            \"movieId\": 241773,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/12/mp4/181012101111142697.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/12/mp4/181012101111142697.mp4\",\n" +
            "            \"videoTitle\": \"阿拉丁 先导预告\",\n" +
            "            \"videoLength\": 87,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"冒险\",\n" +
            "                \"家庭\",\n" +
            "                \"奇幻\",\n" +
            "                \"歌舞\",\n" +
            "                \"爱情\"\n" +
            "            ],\n" +
            "            \"summary\": \"取材于阿拉伯民间流传的神话故事\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72252,\n" +
            "            \"movieName\": \"《玻璃先生》正式预告片\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/12/102641.78850209_120X90X4.jpg\",\n" +
            "            \"movieId\": 250505,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/12/mp4/181012102652533562.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/12/mp4/181012102652533562.mp4\",\n" +
            "            \"videoTitle\": \"玻璃先生  正式预告片\",\n" +
            "            \"videoLength\": 163,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"剧情\",\n" +
            "                \"悬疑\",\n" +
            "                \"科幻\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"一美、威利斯大打出手\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72235,\n" +
            "            \"movieName\": \"新版《宠物坟场》中文预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/10/221817.90889396_120X90X4.jpg\",\n" +
            "            \"movieId\": 257670,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/10/mp4/181010222833316763.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/10/mp4/181010222833316763.mp4\",\n" +
            "            \"videoTitle\": \"宠物坟场 台版中文预告\",\n" +
            "            \"videoLength\": 124,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"恐怖\"\n" +
            "            ],\n" +
            "            \"summary\": \"斯蒂芬金经典小说改编\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72208,\n" +
            "            \"movieName\": \"人气漫画真人版《王者天下》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/09/153545.75958683_120X90X4.jpg\",\n" +
            "            \"movieId\": 255935,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/09/mp4/181009153307111224.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/09/mp4/181009153307111224.mp4\",\n" +
            "            \"videoTitle\": \"王者天下 先导预告\",\n" +
            "            \"videoLength\": 110,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"历史\",\n" +
            "                \"战争\"\n" +
            "            ],\n" +
            "            \"summary\": \"人气漫画《王者天下》真人版\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72171,\n" +
            "            \"movieName\": \"彼得杰克逊监制《掠食城市》终极预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/06/091129.62020381_120X90X4.jpg\",\n" +
            "            \"movieId\": 237246,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/06/mp4/181006091533361281.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/06/mp4/181006091533361281.mp4\",\n" +
            "            \"videoTitle\": \"掠食城市：致命引擎 中字终极预告\",\n" +
            "            \"videoLength\": 155,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"冒险\",\n" +
            "                \"奇幻\",\n" +
            "                \"科幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"反乌托邦背景下的移动城市\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72168,\n" +
            "            \"movieName\": \"金马提名片《谁先爱上他的》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/05/153003.50329419_120X90X4.jpg\",\n" +
            "            \"movieId\": 259910,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/05/mp4/181005153116740242.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/05/mp4/181005153116740242.mp4\",\n" +
            "            \"videoTitle\": \"谁先爱上他的 预告片\",\n" +
            "            \"videoLength\": 110,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"爱情\",\n" +
            "                \"喜剧\",\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"中年妇女大战男小三\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72166,\n" +
            "            \"movieName\": \"《非凡公主希瑞》正式预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/05/114702.82723128_120X90X4.jpg\",\n" +
            "            \"movieId\": 257653,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/05/mp4/181005114459198922.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/05/mp4/181005114459198922.mp4\",\n" +
            "            \"videoTitle\": \"非凡公主希瑞 正式预告片\",\n" +
            "            \"videoLength\": 164,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动画\",\n" +
            "                \"科幻\",\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"家庭\"\n" +
            "            ],\n" +
            "            \"summary\": \"童年回忆希瑞公主归来\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72163,\n" +
            "            \"movieName\": \"伊斯特伍德《骡子》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/05/095706.36164023_120X90X4.jpg\",\n" +
            "            \"movieId\": 260838,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/05/mp4/181005095042620189.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/05/mp4/181005095042620189.mp4\",\n" +
            "            \"videoTitle\": \"骡子 预告片\",\n" +
            "            \"videoLength\": 144,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"惊悚\",\n" +
            "                \"犯罪\",\n" +
            "                \"剧情\",\n" +
            "                \"悬疑\"\n" +
            "            ],\n" +
            "            \"summary\": \"二战老兵成毒品贩子\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72167,\n" +
            "            \"movieName\": \"《玛丽女王》中文预告 \",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/05/144454.25271974_120X90X4.jpg\",\n" +
            "            \"movieId\": 197804,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/05/mp4/181005151912131026.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/05/mp4/181005151912131026.mp4\",\n" +
            "            \"videoTitle\": \"苏格兰玛丽女王 中文国际版预告\",\n" +
            "            \"videoLength\": 149,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"传记\",\n" +
            "                \"剧情\",\n" +
            "                \"历史\"\n" +
            "            ],\n" +
            "            \"summary\": \"聚焦英国皇室传奇恩怨\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72162,\n" +
            "            \"movieName\": \"韩寒沈腾《飞驰人生》先导预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/04/151618.63089112_120X90X4.jpg\",\n" +
            "            \"movieId\": 254868,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/04/mp4/181004152252487123.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/04/mp4/181004152252487123.mp4\",\n" +
            "            \"videoTitle\": \"飞驰人生 先导预告\",\n" +
            "            \"videoLength\": 30,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"喜剧\"\n" +
            "            ],\n" +
            "            \"summary\": \"\\\"寒式幽默\\\"遇上\\\"腾式喜感\\\"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72156,\n" +
            "            \"movieName\": \"动画版《蜘蛛侠》中文预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/03/095712.54650366_120X90X4.jpg\",\n" +
            "            \"movieId\": 228745,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/03/mp4/181003095735959762.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/03/mp4/181003095735959762.mp4\",\n" +
            "            \"videoTitle\": \"蜘蛛侠：平行宇宙 预告片2\",\n" +
            "            \"videoLength\": 147,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动画\",\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"科幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"蜘蛛侠平行宇宙拓展新世界\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72151,\n" +
            "            \"movieName\": \"《火箭人》预告神还原埃尔顿约翰\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/10/02/073850.49798994_120X90X4.jpg\",\n" +
            "            \"movieId\": 255073,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/10/02/mp4/181002111732042844.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/10/02/mp4/181002111732042844.mp4\",\n" +
            "            \"videoTitle\": \"火箭人 中文先导预告片\",\n" +
            "            \"videoLength\": 65,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"传记\",\n" +
            "                \"剧情\",\n" +
            "                \"奇幻\",\n" +
            "                \"音乐\",\n" +
            "                \"歌舞\"\n" +
            "            ],\n" +
            "            \"summary\": \"讲述英国摇滚巨星埃尔顿·约翰的故事\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72091,\n" +
            "            \"movieName\": \"《X战警：黑凤凰》中文先导预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/27/132403.58916439_120X90X4.jpg\",\n" +
            "            \"movieId\": 241485,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/27/mp4/180927130707381557.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/27/mp4/180927130707381557.mp4\",\n" +
            "            \"videoTitle\": \"X战警：黑凤凰 中文先导预告\",\n" +
            "            \"videoLength\": 122,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"科幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"黑凤凰觉醒危机重重\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72048,\n" +
            "            \"movieName\": \"周润发郭富城《无双》终极预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/25/093043.85448073_120X90X4.jpg\",\n" +
            "            \"movieId\": 225752,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/25/mp4/180925092501717531.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/25/mp4/180925092501717531.mp4\",\n" +
            "            \"videoTitle\": \"无双 “钞级赢家”版终极预告\",\n" +
            "            \"videoLength\": 77,\n" +
            "            \"rating\": 7.8,\n" +
            "            \"type\": [\n" +
            "                \"犯罪\",\n" +
            "                \"动作\"\n" +
            "            ],\n" +
            "            \"summary\": \"犯罪天才与造假天才双剑合璧\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72046,\n" +
            "            \"movieName\": \"《大黄蜂》中文正式预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/24/172717.29211879_120X90X4.jpg\",\n" +
            "            \"movieId\": 246986,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/24/mp4/180924172925058661.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/24/mp4/180924172925058661.mp4\",\n" +
            "            \"videoTitle\": \"大黄蜂 中文版“蜂狂开战”预告\",\n" +
            "            \"videoLength\": 142,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"科幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"大黄蜂又帅又萌惹人爱\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71991,\n" +
            "            \"movieName\": \"漫威“惊奇队长”中文预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/18/204536.32204314_120X90X4.jpg\",\n" +
            "            \"movieId\": 218087,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/18/mp4/180918204610442500.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/18/mp4/180918204610442500.mp4\",\n" +
            "            \"videoTitle\": \"惊奇队长 中文先导预告片\",\n" +
            "            \"videoLength\": 119,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"科幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"万众期待！干掉灭霸就靠她了\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71997,\n" +
            "            \"movieName\": \"开心麻花《李茶的姑妈》终极预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/19/121104.49185261_120X90X4.jpg\",\n" +
            "            \"movieId\": 254620,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/19/mp4/180919121108161906.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/19/mp4/180919121108161906.mp4\",\n" +
            "            \"videoTitle\": \"李茶的姑妈 终极预告\",\n" +
            "            \"videoLength\": 72,\n" +
            "            \"rating\": 6.2,\n" +
            "            \"type\": [\n" +
            "                \"喜剧\"\n" +
            "            ],\n" +
            "            \"summary\": \"“三傻”合伙扮假姑妈\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 72017,\n" +
            "            \"movieName\": \"《无敌破坏王2》新预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/20/215658.75700969_120X90X4.jpg\",\n" +
            "            \"movieId\": 226450,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/21/mp4/180921104509661116.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/21/mp4/180921104509661116.mp4\",\n" +
            "            \"videoTitle\": \"无敌破坏王2 中文版预告片2\",\n" +
            "            \"videoLength\": 150,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动画\",\n" +
            "                \"冒险\",\n" +
            "                \"喜剧\",\n" +
            "                \"家庭\",\n" +
            "                \"奇幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"迪士尼诸多公主大聚会\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71994,\n" +
            "            \"movieName\": \"动画《绿毛怪格林奇》中文预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/19/093912.15370791_120X90X4.jpg\",\n" +
            "            \"movieId\": 233407,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/19/mp4/180919095245958262.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/19/mp4/180919095245958262.mp4\",\n" +
            "            \"videoTitle\": \"绿毛怪格林奇 “花式神偷”版预告\",\n" +
            "            \"videoLength\": 155,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动画\",\n" +
            "                \"喜剧\",\n" +
            "                \"家庭\",\n" +
            "                \"奇幻\"\n" +
            "            ],\n" +
            "            \"summary\": \"“小黄人”团队新作\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71996,\n" +
            "            \"movieName\": \"张艺谋《影》终极预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/19/103915.30334424_120X90X4.jpg\",\n" +
            "            \"movieId\": 242119,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/19/mp4/180919103609005609.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/19/mp4/180919103609005609.mp4\",\n" +
            "            \"videoTitle\": \"影 终极版预告片\",\n" +
            "            \"videoLength\": 119,\n" +
            "            \"rating\": 7.4,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"新预告动作场面气韵足\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71975,\n" +
            "            \"movieName\": \"《新欢乐满人间》中文预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/17/215157.45380259_120X90X4.jpg\",\n" +
            "            \"movieId\": 228392,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/17/mp4/180917215213735941.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/17/mp4/180917215213735941.mp4\",\n" +
            "            \"videoTitle\": \"新欢乐满人间 正式中字预告\",\n" +
            "            \"videoLength\": 147,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"家庭\",\n" +
            "                \"奇幻\",\n" +
            "                \"歌舞\"\n" +
            "            ],\n" +
            "            \"summary\": \"孩子们的仙女保姆又回来了\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71980,\n" +
            "            \"movieName\": \"\\\"龙纹身女孩\\\"续集预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/18/100143.25460044_120X90X4.jpg\",\n" +
            "            \"movieId\": 229567,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/18/mp4/180918100244501800.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/18/mp4/180918100244501800.mp4\",\n" +
            "            \"videoTitle\": \"蜘蛛网中的女孩 正式中字预告\",\n" +
            "            \"videoLength\": 60,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"犯罪\",\n" +
            "                \"剧情\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"克莱尔福伊凶悍亮相\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71948,\n" +
            "            \"movieName\": \"杰拉德巴特勒\\\"冰海陷落\\\"预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/14/072856.57965041_120X90X4.jpg\",\n" +
            "            \"movieId\": 157201,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/14/mp4/180914072848108088.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/14/mp4/180914072848108088.mp4\",\n" +
            "            \"videoTitle\": \"冰海陷落 终极预告\",\n" +
            "            \"videoLength\": 65,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"第三次世界大战一触即发\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71943,\n" +
            "            \"movieName\": \"科恩兄弟《歌谣》预告片\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/13/104310.17480337_120X90X4.jpg\",\n" +
            "            \"movieId\": 259954,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/13/mp4/180913113053453374.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/13/mp4/180913113053453374.mp4\",\n" +
            "            \"videoTitle\": \"巴斯特·斯克鲁格斯的歌谣 预告片\",\n" +
            "            \"videoLength\": 123,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"喜剧\",\n" +
            "                \"剧情\",\n" +
            "                \"西部\"\n" +
            "            ],\n" +
            "            \"summary\": \"付兰兰&连姆尼森参演致敬西部片\\n\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71873,\n" +
            "            \"movieName\": \"凯瑞穆里根《狂野生活》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/07/135800.44859454_120X90X4.jpg\",\n" +
            "            \"movieId\": 236797,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/07/mp4/180907135906609714.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/07/mp4/180907135906609714.mp4\",\n" +
            "            \"videoTitle\": \"狂野生活  中字预告片\",\n" +
            "            \"videoLength\": 155,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"保罗达诺导演处女作口碑爆棚\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71850,\n" +
            "            \"movieName\": \"杨幂《宝贝儿》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/06/083012.86422925_120X90X4.jpg\",\n" +
            "            \"movieId\": 254854,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/06/mp4/180906083142712290.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/06/mp4/180906083142712290.mp4\",\n" +
            "            \"videoTitle\": \"宝贝儿 先导预告\",\n" +
            "            \"videoLength\": 15,\n" +
            "            \"rating\": 6.8,\n" +
            "            \"type\": [\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"医院护工\\\"偷小孩\\\"折射弃婴遭遇\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71841,\n" +
            "            \"movieName\": \"贾樟柯《江湖儿女》终极预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/05/134901.74029650_120X90X4.jpg\",\n" +
            "            \"movieId\": 250339,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/05/mp4/180905134907179704.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/05/mp4/180905134907179704.mp4\",\n" +
            "            \"videoTitle\": \"江湖儿女 终极预告片\",\n" +
            "            \"videoLength\": 72,\n" +
            "            \"rating\": 7.8,\n" +
            "            \"type\": [\n" +
            "                \"犯罪\",\n" +
            "                \"爱情\"\n" +
            "            ],\n" +
            "            \"summary\": \"廖凡赵涛共谱传奇爱情\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71811,\n" +
            "            \"movieName\": \"高司令《登月第一人》新预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/03/202803.80336695_120X90X4.jpg\",\n" +
            "            \"movieId\": 229976,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/03/mp4/180903202704533375.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/03/mp4/180903202704533375.mp4\",\n" +
            "            \"videoTitle\": \"登月第一人 台版中字预告\",\n" +
            "            \"videoLength\": 90,\n" +
            "            \"rating\": 7.5,\n" +
            "            \"type\": [\n" +
            "                \"传记\",\n" +
            "                \"剧情\",\n" +
            "                \"历史\"\n" +
            "            ],\n" +
            "            \"summary\": \"震撼再现登月传奇\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71819,\n" +
            "            \"movieName\": \"《我的天才女友》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/09/04/155929.41301926_120X90X4.jpg\",\n" +
            "            \"movieId\": 259155,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/09/04/mp4/180904155412689104.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/09/04/mp4/180904155412689104.mp4\",\n" +
            "            \"videoTitle\": \"我的天才女友 预告片\",\n" +
            "            \"videoLength\": 121,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"畅销小说改编 两位闺蜜情深意长\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71788,\n" +
            "            \"movieName\": \"休·杰克曼《领先者》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/08/30/230722.32223160_120X90X4.jpg\",\n" +
            "            \"movieId\": 249339,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/08/30/mp4/180830230725882639.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/08/30/mp4/180830230725882639.mp4\",\n" +
            "            \"videoTitle\": \"领先者 中字预告\",\n" +
            "            \"videoLength\": 122,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"传记\",\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"风光一时却因丑闻戏剧性落败\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71757,\n" +
            "            \"movieName\": \"锤哥《皇家酒店谋杀案》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/08/29/082709.43112382_120X90X4.jpg\",\n" +
            "            \"movieId\": 257745,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/08/29/mp4/180829082744092695.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/08/29/mp4/180829082744092695.mp4\",\n" +
            "            \"videoTitle\": \"皇家酒店谋杀案 剧场版中文预告片\",\n" +
            "            \"videoLength\": 123,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"悬疑\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"密室悬疑戏充满诡异\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71744,\n" +
            "            \"movieName\": \"玄彬张东健僵尸片《猖獗》预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/08/28/152259.94317561_120X90X4.jpg\",\n" +
            "            \"movieId\": 256396,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/08/28/mp4/180828152314850264.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/08/28/mp4/180828152314850264.mp4\",\n" +
            "            \"videoTitle\": \"猖獗 预告片\",\n" +
            "            \"videoLength\": 60,\n" +
            "            \"rating\": 0,\n" +
            "            \"type\": [\n" +
            "                \"动作\"\n" +
            "            ],\n" +
            "            \"summary\": \"玄彬血战僵尸群\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71707,\n" +
            "            \"movieName\": \"新版《阴风阵阵》正式预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/08/23/222035.36075552_120X90X4.jpg\",\n" +
            "            \"movieId\": 168207,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/08/23/mp4/180823222144907956.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/08/23/mp4/180823222144907956.mp4\",\n" +
            "            \"videoTitle\": \"阴风阵阵 正式预告\",\n" +
            "            \"videoLength\": 157,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"奇幻\",\n" +
            "                \"恐怖\",\n" +
            "                \"悬疑\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"参赛威尼斯的文艺范儿恐怖片\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71712,\n" +
            "            \"movieName\": \"《悲伤逆流成河》“校园欺凌”预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/08/24/105030.21062701_120X90X4.jpg\",\n" +
            "            \"movieId\": 250539,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/08/24/mp4/180824105213943957.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/08/24/mp4/180824105213943957.mp4\",\n" +
            "            \"videoTitle\": \"悲伤逆流成河 “如果”版预告片\",\n" +
            "            \"videoLength\": 67,\n" +
            "            \"rating\": 6.1,\n" +
            "            \"type\": [\n" +
            "                \"剧情\"\n" +
            "            ],\n" +
            "            \"summary\": \"反清新套路直面残酷现实\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71652,\n" +
            "            \"movieName\": \"《碟中谍6》终极预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/08/20/110454.49211648_120X90X4.jpg\",\n" +
            "            \"movieId\": 226992,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/08/20/mp4/180820110500179769.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/08/20/mp4/180820110500179769.mp4\",\n" +
            "            \"videoTitle\": \"碟中谍6 终极预告\",\n" +
            "            \"videoLength\": 78,\n" +
            "            \"rating\": 7.9,\n" +
            "            \"type\": [\n" +
            "                \"动作\",\n" +
            "                \"冒险\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"阿汤哥再次徒手扒飞机\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71622,\n" +
            "            \"movieName\": \"《寡妇特工》中文预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/08/16/080004.76663845_120X90X4.jpg\",\n" +
            "            \"movieId\": 236877,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/08/16/mp4/180816075924846198.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/08/16/mp4/180816075924846198.mp4\",\n" +
            "            \"videoTitle\": \"寡妇特工 中字剧场版预告\",\n" +
            "            \"videoLength\": 143,\n" +
            "            \"rating\": -1,\n" +
            "            \"type\": [\n" +
            "                \"犯罪\",\n" +
            "                \"剧情\",\n" +
            "                \"惊悚\"\n" +
            "            ],\n" +
            "            \"summary\": \"结合黑人和女权主题\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 71574,\n" +
            "            \"movieName\": \"《阿尔法：狼伴归途》中文预告\",\n" +
            "            \"coverImg\": \"http://img5.mtime.cn/mg/2018/08/13/083952.78968548_120X90X4.jpg\",\n" +
            "            \"movieId\": 229758,\n" +
            "            \"url\": \"http://vfx.mtime.cn/Video/2018/08/13/mp4/180813084109743369.mp4\",\n" +
            "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2018/08/13/mp4/180813084109743369.mp4\",\n" +
            "            \"videoTitle\": \"阿尔法：狼伴归途 定档预告\",\n" +
            "            \"videoLength\": 60,\n" +
            "            \"rating\": 6.8,\n" +
            "            \"type\": [\n" +
            "                \"冒险\",\n" +
            "                \"剧情\",\n" +
            "                \"家庭\"\n" +
            "            ],\n" +
            "            \"summary\": \"科达和“狼主角”阿尔法的不凡冒险\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
