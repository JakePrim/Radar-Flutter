package com.prim_player_cc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.decoder_cc.ProxyDecoderCC;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.view.BasePlayerCCView;

/**
 * @author prim
 * @version 1.0.0
 * @desc 无缝续播助手View，用于视频播放的跳转无缝切换
 * @time 2018/11/8 - 11:11 AM
 */
public class AssistPlayerView extends BasePlayerCCView {

    private static final String TAG = "SharePlayerView";

    public AssistPlayerView(@NonNull Context context) {
        super(context);
    }

    public AssistPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AssistPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AssistPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void _init(Context context, AttributeSet attrs, int defStyleAttr) {
        //创建视图组
        createCoverGroup();
        //创建总线view
        createBusView(context);
        //获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestLayout();
        //add BusPlayerView
        addBusPlayerView();
        //初始化view
        initView();
        //初始化render view
        setRenderView(IRenderView.TEXTURE_VIEW);
    }

    public void setDecoderCC(ProxyDecoderCC proxyDecoderCC) {
        _resetListener();
        this.proxyDecoderCC = proxyDecoderCC;
        _initListener();
    }

    /**
     * ProxyDecoderCC 重置media player，重新绑定render view
     */
    public void restSurfaceHolder(String key) {
        _resetListener();
        proxyDecoderCC = AssistPlayer.get(key).getProxyDecoderCC();
        _initListener();
        if (mRenderView != null) {
            bindSurfaceHolder(proxyDecoderCC, mSurfaceHolder);
        } else {
            setRenderView(mRenderType);
        }
    }

    public void postRenderView() {
        if (mRenderView != null) {
            mRenderView.postRenderView();
        }
    }

    /**
     * 销毁render view
     */
    public void releaseRenderView() {
        PrimLog.e("PRIM!!", "releaseRenderView");
        releaseRender();
        //必须将解码器置空 否则会被其他view引用
        //✔ 由于item view 使用的是一个Media Player，
        // 不使用后要将当前view的Media Player置null 否则上下滑动重新创建view的时候，就会使Media Player 导致绑定混乱。
        proxyDecoderCC = null;
    }


    @Override
    protected void openFullScreen() {
        PrimLog.e("PRIM!!", "由于该view的Context是Application，所以不能用内部的去实现 openFullScreen 交给外部处理");
    }

    @Override
    protected void openVerticalScreen() {
        PrimLog.e("PRIM!!", "由于该view的Context是Application，所以不能用内部的去实现 openVerticalScreen 交给外部处理");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PrimLog.e("PRIM!!", "onKeyDown 交给外部处理");
        return false;
    }

    @Override
    protected void initView() {

    }
}
