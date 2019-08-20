package com.prim_player_cc.render_cc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.decoder_cc.IDecoder;
import com.prim_player_cc.log.PrimLog;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc 框架默认实现的TextureView 参考了Ijk的实现
 * @time 2018/7/30 - 上午10:34
 */
public class AssistTextureView extends TextureView implements IRenderView {

    private static final String TAG = "RenderTextureView";

    private MeasureHelper measureHelper;

    private SurfaceCallback surfaceCallback;

    public AssistTextureView(Context context) {
        super(context);
        initView(context);
    }

    public AssistTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AssistTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AssistTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        measureHelper = new MeasureHelper(this);
        surfaceCallback = new SurfaceCallback(this);
        setSurfaceTextureListener(surfaceCallback);
    }

    @Override
    public void release() {
        if (mSurface != null) {
            PrimLog.e("PRIM!!", "销毁surface");
            mSurface.release();
            mSurface = null;
        }
        if (mSurfaceTexture != null) {
            PrimLog.e("PRIM!!", "销毁SurfaceTexture");
            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }
    }

    @Override
    public void postRenderView() {
        postInvalidate();
    }

    @Override
    public View getRenderView() {
        return this;
    }

    @Override
    public void updateRenderSize(int width, int height) {
        if (width > 0 && height > 0) {
            measureHelper.setVideoSize(width, height);
            requestLayout();
        }
    }

    @Override
    public void setAspectRatio(int aspectRatio) {
        measureHelper.setAspectRatio(aspectRatio);
        requestLayout();
    }

    @Override
    public void setVideoRotation(int degree) {
        measureHelper.setVideoRotation(degree);
        setRotation(degree);
    }

    @Override
    public void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen) {
        if (videoSarNum > 0 && videoSarDen > 0) {
            measureHelper.setVideoSampleAspectRatio(videoSarNum, videoSarDen);
            requestLayout();
        }
    }

    @Override
    public boolean shouldWaitForResize() {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureHelper.getMeasuredWidth(), measureHelper.getMeasuredHeight());
    }

    @Override
    public void setRenderCallback(IRenderCallback renderCallback) {
        surfaceCallback.addRenderCallback(renderCallback);
    }

    @Override
    public void removeRenderCallback(@NonNull IRenderCallback renderCallback) {
        surfaceCallback.removeRenderCallback(renderCallback);
    }

    @Override
    public Bitmap getShortcut() {
        return null;
    }

    public ISurfaceHolder getSurfaceHolder() {
        return new InternalSurfaceHolder(this, surfaceCallback.mSurfaceTexture, surfaceCallback);
    }

    private static final class SurfaceCallback implements SurfaceTextureListener, ISurfaceTextureHost {

        private WeakReference<AssistTextureView> mWeakRenderView;

        private Map<IRenderCallback, Object> mRenderCallback = new ConcurrentHashMap<>();

        private SurfaceTexture mSurfaceTexture;
        private int mWidth;
        private int mHeight;
        private boolean mIsFormatChanged;

        private boolean mOwnSurfaceTexture = true;
        private boolean mWillDetachFromWindow = false;
        private boolean mDidDetachFromWindow = false;

        public SurfaceCallback(AssistTextureView renderTextureView) {
            mWeakRenderView = new WeakReference<>(renderTextureView);
        }

        public void setOwnSurfaceTexture(boolean ownSurfaceTexture) {
            mOwnSurfaceTexture = ownSurfaceTexture;
        }

        public void addRenderCallback(@NonNull IRenderCallback renderCallback) {
            mRenderCallback.put(renderCallback, renderCallback);

            ISurfaceHolder surfaceHolder = null;
            if (mSurfaceTexture != null) {
                if (surfaceHolder == null) {
                    surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), mSurfaceTexture, this);
                }
                renderCallback.surfaceCreated(surfaceHolder, mWidth, mHeight);
            }

            if (mIsFormatChanged) {
                if (surfaceHolder == null) {
                    surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), mSurfaceTexture, this);
                }
                renderCallback.surfaceChanged(surfaceHolder, 0, mWidth, mHeight);
            }
        }

        public void removeRenderCallback(@NonNull IRenderCallback renderCallback) {
            mRenderCallback.remove(renderCallback);
        }

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            PrimLog.e(TAG, "onSurfaceTextureAvailable");
            mSurfaceTexture = surface;
            mIsFormatChanged = false;
            mWidth = 0;
            mHeight = 0;

            ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), mSurfaceTexture, this);
            for (IRenderCallback callback : mRenderCallback.keySet()) {
                callback.surfaceCreated(surfaceHolder, 0, 0);
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            PrimLog.e(TAG, "onSurfaceTextureSizeChanged");
            if (mSurfaceTexture == null) {
                mSurfaceTexture = surface;
            }
            mIsFormatChanged = true;
            mWidth = width;
            mHeight = height;

            ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), mSurfaceTexture, this);
            for (IRenderCallback callback : mRenderCallback.keySet()) {
                callback.surfaceChanged(surfaceHolder, 0, width, height);
            }
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            PrimLog.e(TAG, "onSurfaceTextureDestroyed：" + mOwnSurfaceTexture);
            mSurfaceTexture = surface;
            mIsFormatChanged = false;
            mWidth = 0;
            mHeight = 0;

            ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), surface, this);
            for (IRenderCallback callback : mRenderCallback.keySet()) {
                callback.surfaceDestroyed(surfaceHolder);
            }
            //mOwnSurfaceTexture
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }

        @Override
        public void releaseSurfaceTexture(SurfaceTexture surfaceTexture) {
            if (surfaceTexture == null) {
                PrimLog.d(TAG, "releaseSurfaceTexture: null");
            } else if (mDidDetachFromWindow) {
                if (surfaceTexture != mSurfaceTexture) {
                    PrimLog.d(TAG, "releaseSurfaceTexture: didDetachFromWindow(): release different SurfaceTexture");
                    surfaceTexture.release();
                } else if (!mOwnSurfaceTexture) {
                    PrimLog.d(TAG, "releaseSurfaceTexture: didDetachFromWindow(): release detached SurfaceTexture");
                    surfaceTexture.release();
                } else {
                    PrimLog.d(TAG, "releaseSurfaceTexture: didDetachFromWindow(): already released by TextureView");
                }
            } else if (mWillDetachFromWindow) {
                if (surfaceTexture != mSurfaceTexture) {
                    PrimLog.d(TAG, "releaseSurfaceTexture: willDetachFromWindow(): release different SurfaceTexture");
                    surfaceTexture.release();
                } else if (!mOwnSurfaceTexture) {
                    PrimLog.d(TAG, "releaseSurfaceTexture: willDetachFromWindow(): re-attach SurfaceTexture to TextureView");
                    setOwnSurfaceTexture(true);
                } else {
                    PrimLog.d(TAG, "releaseSurfaceTexture: willDetachFromWindow(): will released by TextureView");
                }
            } else {
                if (surfaceTexture != mSurfaceTexture) {
                    PrimLog.d(TAG, "releaseSurfaceTexture: alive: release different SurfaceTexture");
                    surfaceTexture.release();
                } else if (!mOwnSurfaceTexture) {
                    PrimLog.d(TAG, "releaseSurfaceTexture: alive: re-attach SurfaceTexture to TextureView");
                    setOwnSurfaceTexture(true);
                } else {
                    PrimLog.d(TAG, "releaseSurfaceTexture: alive: will released by TextureView");
                }
            }
        }

        public void willDetachFromWindow() {
            mWillDetachFromWindow = true;
        }

        public void didDetachFromWindow() {
            mDidDetachFromWindow = true;
        }
    }

    //画框
    private Surface mSurface;

    //画面
    private SurfaceTexture mSurfaceTexture;

    private void setMySurfaceTexture(SurfaceTexture surfaceTexture) {
        this.mSurfaceTexture = surfaceTexture;
    }

    public SurfaceTexture getMySurfaceTexture() {
        return mSurfaceTexture;
    }

    public void setSurface(Surface surface) {
        this.mSurface = surface;
    }


    Surface getSurface() {
        return mSurface;
    }


    private static final class InternalSurfaceHolder implements ISurfaceHolder {

        private AssistTextureView renderTextureView;
        private SurfaceTexture surfaceTexture;
        private ISurfaceTextureHost surfaceTextureHost;

        public InternalSurfaceHolder(AssistTextureView renderTextureView, SurfaceTexture surfaceTexture, ISurfaceTextureHost surfaceTextureHost) {
            this.renderTextureView = renderTextureView;
            this.surfaceTexture = surfaceTexture;
            this.surfaceTextureHost = surfaceTextureHost;
        }

        @Override
        public void bindToMediaPlayer(IDecoder decoder) {
            if (decoder == null) {
                return;
            }
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) &&
                    (decoder instanceof ISurfaceTextureHolder)) {
                ISurfaceTextureHolder textureHolder = (ISurfaceTextureHolder) decoder;
                renderTextureView.surfaceCallback.setOwnSurfaceTexture(false);
                SurfaceTexture surfaceTexture = textureHolder.getSurfaceTexture();
                if (surfaceTexture != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        renderTextureView.setSurfaceTexture(surfaceTexture);
                    } else {
                        textureHolder.setSurfaceTexture(surfaceTexture);
                        textureHolder.setSurfaceTextureHost(surfaceTextureHost);
                    }
                } else {
                    textureHolder.setSurfaceTexture(surfaceTexture);
                    textureHolder.setSurfaceTextureHost(surfaceTextureHost);
                }
            } else {

                Surface surface = openSurface();
                PrimLog.e("PRIM!!", "bindToMediaPlayer" + surface);
//                renderTextureView.setSurfaceTexture(surfaceTexture);
                decoder.setSurface(surface);
                renderTextureView.setSurface(surface);
            }

        }

        @NonNull
        @Override
        public IRenderView getRenderView() {
            return renderTextureView;
        }

        @NonNull
        @Override
        public SurfaceHolder getSurfaceHolder() {
            return null;
        }

        @NonNull
        @Override
        public Surface openSurface() {
            if (surfaceTexture == null) {
                return null;
            }
            return new Surface(surfaceTexture);
        }

        @NonNull
        @Override
        public SurfaceTexture getSurfaceTexture() {
            return surfaceTexture;
        }
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(AssistTextureView.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AssistTextureView.class.getName());
    }

    @Override
    protected void onDetachedFromWindow() {
        surfaceCallback.willDetachFromWindow();
        super.onDetachedFromWindow();
        surfaceCallback.didDetachFromWindow();
    }
}
