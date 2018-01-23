package com.primplayer.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PlayerState;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;
import com.primplayer.R;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：1/23 0023
 * 描    述：write a custom PLVideoTextureView instead of this class
 * A PLVideoTextureView widget must implement all the interface defined by {@link IPrimPlayerView}
 * 修订历史：2.0.0 version
 * ================================================
 */
public class PrimPlayerTextureView extends FrameLayout implements IPrimPlayerView,View.OnClickListener {

    public PrimPlayerTextureView(@NonNull Context context) {
        this(context, null);
    }

    public PrimPlayerTextureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrimPlayerTextureView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * core player
     * Please refer to the detailed information {https://developer.qiniu.com/pili/sdk/1210/the-android-client-sdk}
     */
    private PLVideoTextureView player;

    /**
     * player loading view
     */
    private View playerLoadingView;

    /**
     * set player cover view
     * The view will be shown when the player is preparing {player.setCoverView}
     */
    private ImageView ivPlayerCover;

    /**
     * player progress
     */
    private ProgressBar proPlayerProgress;

    /**
     * player container
     * will player add container
     */
    private FrameLayout fraPlayerContainer;

    /**
     * player error show {@link #llPlayerError} view
     */
    private LinearLayout llPlayerError;

    /**
     * set player replay view
     */
    private FrameLayout flReplay;

    /**
     * player replay click {@link #tvReplay} transfer replay method {@link #replayed}
     */
    private TextView tvReplay;

    /**
     * network status change : not wifi show view
     */
    private LinearLayout llPlayerNetworkStatus;

    /**
     * When not WiFi click {@link #ivPlayerStart} transfer start player method {@link #startPlayer}
     */
    private ImageView ivPlayerStart;

    private void initView(Context context) {
        View.inflate(context, R.layout.layout_player, this);
        playerLoadingView = findViewById(R.id.pro_player_loading);
        ivPlayerCover = (ImageView) findViewById(R.id.iv_player_thumbnail);
        proPlayerProgress = (ProgressBar) findViewById(R.id.pro_player_progress);
        fraPlayerContainer = (FrameLayout) findViewById(R.id.fra_player_container);
        llPlayerError = (LinearLayout) findViewById(R.id.ll_player_error);
        flReplay = (FrameLayout) findViewById(R.id.fl_replay);
        tvReplay = (TextView) findViewById(R.id.tv_replay);
        llPlayerNetworkStatus = (LinearLayout) findViewById(R.id.ll_player_network_status);
        ivPlayerStart = (ImageView) findViewById(R.id.iv_player_start);
        initPlayer(context);
    }

    @Override
    public void initPlayer(Context context) {
        player = new PLVideoTextureView(context);
        player.setBufferingIndicator(playerLoadingView);
        player.setCoverView(ivPlayerCover);
    }

    @Override
    public void setPlayerOptions(AVOptions options) {
        player.setAVOptions(options);
        player.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
    }

    @Override
    public void setSeekTo(long position) {

    }

    @Override
    public void recycleVideoView() {

    }

    @Override
    public void setWifiState(boolean isWifi) {

    }

    @Override
    public void addController() {

    }

    @Override
    public void removeController() {

    }

    @Override
    public void setDataSource(String path) {

    }

    @Override
    public void startPlayer() {

    }

    @Override
    public void currentPlayer() {

    }

    @Override
    public void stopPlayer() {

    }

    @Override
    public void completePlayer() {

    }

    @Override
    public void replayed() {

    }

    @Override
    public void pausePlayer() {

    }

    @Override
    public boolean isPlayerComplete() {
        return false;
    }

    @Override
    public boolean isPlayerPause() {
        return false;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public PlayerState getPlayerState() {
        return null;
    }

    @Override
    public void setPlayerVolume(float var1, float var2) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void setDisplayOrientation(int rotation) {

    }

    @Override
    public void onClick(View v) {

    }
}
