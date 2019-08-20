package com.linksu.videofeed.demo.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.linksu.videofeed.R;
import com.linksu.videofeed.demo.test.VideoDataBean;
import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.view.AssistPlayerView;

/**
 * @author prim
 * @version 1.0.0
 * @desc 详情页场景转换layout
 * @time 2018/11/15 - 6:09 PM
 */
public class DetailsLayout extends FrameLayout {

    public AssistPlayerView assistPlayerView;

    public RecyclerView detail_rv;

    public DetailsLayout(@NonNull Context context) {
        this(context, null);
    }

    public DetailsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DetailsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DetailsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        assistPlayerView = findViewById(R.id.temp_player_view);
        detail_rv = findViewById(R.id.detail_rv);
    }

    private void setData(VideoDataBean data) {

    }

    /**
     * 转换场景 显示视频详情页场景
     *
     * @param activity
     * @param container
     * @param sharedView
     * @param transitionName
     * @param dataBean
     * @return Scene
     */
    public static Scene showDetailScene(Activity activity, ViewGroup container, View sharedView, String transitionName, VideoDataBean dataBean) {
        //添加到容器中
        DetailsLayout detailsLayout = (DetailsLayout) activity.getLayoutInflater().inflate(R.layout.details_layout, container, false);
//        detailsLayout.assistPlayerView = detailsLayout.findViewById(R.id.temp_player_view);
//        detailsLayout.detail_rv = detailsLayout.findViewById(R.id.detail_rv);
        //将此view绑定
        AssistPlayer.defaultPlayer().resumePlay(detailsLayout.assistPlayerView, null, false);
        detailsLayout.setData(dataBean);
        //一组转场动画
        TransitionSet transitionSet = null;
        Scene scene = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            transitionSet = new ShowDetailsTransitionSet(activity, sharedView, detailsLayout, transitionName);
            scene = new Scene(container, (View) detailsLayout);
            //场景转换
            TransitionManager.go(scene, transitionSet);
        }

        return scene;
    }

    /**
     * 转换场景 隐藏视频详情页场景
     *
     * @param activity
     * @param container
     * @param sharedView
     * @param transitionName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Scene hideDetailScene(Activity activity, ViewGroup container, View sharedView, String transitionName) {
        DetailsLayout detailsLayout = container.findViewById(R.id.video_details_container);
        TransitionSet transitionSet = new HideDetailsTransitionSet(activity, sharedView, detailsLayout, transitionName);
        Scene scene = new Scene(container, detailsLayout);
        TransitionManager.go(scene, transitionSet);
        return scene;
    }


}
