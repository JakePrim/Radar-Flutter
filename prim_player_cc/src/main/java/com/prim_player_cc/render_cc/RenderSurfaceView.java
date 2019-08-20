package com.prim_player_cc.render_cc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.prim_player_cc.decoder_cc.IDecoder;
import com.prim_player_cc.log.PrimLog;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static android.graphics.PixelFormat.TRANSLUCENT;

/**
 * @author prim
 * @version 1.0.0
 * @desc 自定义SurfaceView 渲染器 参考了ijk的实现
 * @time 2018/7/30 - 上午10:33
 */
public class RenderSurfaceView extends SurfaceView implements IRenderView {

    private SurfaceCallback surfaceCallback;

    private MeasureHelper measureHelper;

    private static final String TAG = "RenderSurfaceView";

    public RenderSurfaceView(Context context) {
        super(context);
        initView(context);
    }

    public RenderSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RenderSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RenderSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        measureHelper = new MeasureHelper(this);
        surfaceCallback = new SurfaceCallback(this);
        getHolder().addCallback(surfaceCallback);
        //noinspection deprecation
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        getHolder().setFormat(TRANSLUCENT);
    }

    @Override
    public void release() {

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
            PrimLog.e(TAG, "updateRenderSize width:" + width + " height:" + height);
            measureHelper.setVideoSize(width, height);
            getHolder().setFixedSize(width, height);
            requestLayout();
        }
    }

    @Override
    public void setAspectRatio(int aspectRatio) {
        measureHelper.setAspectRatio(aspectRatio);
    }

    @Override
    public void setVideoRotation(int degree) {
        PrimLog.e(TAG, "SurfaceView doesn't support rotation (" + degree + ")!");
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
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureHelper.getMeasuredWidth(), measureHelper.getMeasuredHeight());
    }

    @Override
    public void setRenderCallback(@NonNull IRenderCallback renderCallback) {
        if (surfaceCallback != null) {
            surfaceCallback.addRenderCallback(renderCallback);
        }
    }

    @Override
    public void removeRenderCallback(@NonNull IRenderCallback renderCallback) {
        if (surfaceCallback != null) {
            surfaceCallback.removeRenderCallback(renderCallback);
        }
    }

    @Override
    public Bitmap getShortcut() {
        return null;
    }

    private static final class SurfaceCallback implements SurfaceHolder.Callback {

        private SurfaceHolder mSurfaceHolder;
        private boolean mIsFormatChanged;
        private int mFormat;
        private int mWidth;
        private int mHeight;

        private WeakReference<RenderSurfaceView> mWeakSurfaceView;

        //ConcurrentHashMap 线程安全的Map
        private Map<IRenderCallback, Object> mRenderCallbackMap = new ConcurrentHashMap<>();

        public SurfaceCallback(@NonNull RenderSurfaceView renderSurfaceView) {
            mWeakSurfaceView = new WeakReference<>(renderSurfaceView);
        }

        public void addRenderCallback(@NonNull IRenderCallback renderCallback) {
            mRenderCallbackMap.put(renderCallback, renderCallback);

            ISurfaceHolder surfaceHolder = null;
            if (mSurfaceHolder != null) {
                if (surfaceHolder == null) {
                    surfaceHolder = new InternalSurfacceHolder(mSurfaceHolder, mWeakSurfaceView.get());
                }
                renderCallback.surfaceCreated(surfaceHolder, mWidth, mHeight);
            }

            if (mIsFormatChanged) {
                if (surfaceHolder == null) {
                    surfaceHolder = new InternalSurfacceHolder(mSurfaceHolder, mWeakSurfaceView.get());
                }
                renderCallback.surfaceChanged(surfaceHolder, mFormat, mWidth, mHeight);
            }
        }

        public void removeRenderCallback(@NonNull IRenderCallback renderCallback) {
            mRenderCallbackMap.remove(renderCallback);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mSurfaceHolder = holder;
            mWidth = 0;
            mHeight = 0;
            mIsFormatChanged = false;
            mFormat = 0;

            ISurfaceHolder surfaceHolder = new InternalSurfacceHolder(mSurfaceHolder, mWeakSurfaceView.get());
            for (IRenderCallback renderCallback : mRenderCallbackMap.keySet()) {
                renderCallback.surfaceCreated(surfaceHolder, 0, 0);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mSurfaceHolder = holder;
            mWidth = width;
            mHeight = height;
            mIsFormatChanged = true;
            mFormat = format;

            ISurfaceHolder surfaceHolder = new InternalSurfacceHolder(mSurfaceHolder, mWeakSurfaceView.get());
            for (IRenderCallback renderCallback : mRenderCallbackMap.keySet()) {
                renderCallback.surfaceChanged(surfaceHolder, format, width, height);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mSurfaceHolder = null;
            mWidth = 0;
            mHeight = 0;
            mIsFormatChanged = false;
            mFormat = 0;

            ISurfaceHolder surfaceHolder = new InternalSurfacceHolder(mSurfaceHolder, mWeakSurfaceView.get());
            for (IRenderCallback renderCallback : mRenderCallbackMap.keySet()) {
                renderCallback.surfaceDestroyed(surfaceHolder);
            }
        }
    }

    private static final class InternalSurfacceHolder implements IRenderView.ISurfaceHolder {

        private SurfaceHolder mSurfaceHolder;
        private RenderSurfaceView mSurfaceView;

        public InternalSurfacceHolder(SurfaceHolder mSurfaceHolder, RenderSurfaceView mSurfaceView) {
            this.mSurfaceHolder = mSurfaceHolder;
            this.mSurfaceView = mSurfaceView;
        }

        @Override
        public void bindToMediaPlayer(IDecoder decoder) {
            if (decoder != null) {
                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        && (decoder instanceof ISurfaceTextureHolder)) {
                    ISurfaceTextureHolder surfaceTextureHolder = (ISurfaceTextureHolder) decoder;
                    surfaceTextureHolder.setSurfaceTexture(null);
                }
                decoder.setDisplay(mSurfaceHolder);
            }
        }

        @NonNull
        @Override
        public IRenderView getRenderView() {
            return mSurfaceView;
        }

        @Nullable
        @Override
        public SurfaceHolder getSurfaceHolder() {
            return mSurfaceHolder;
        }

        @Nullable
        @Override
        public Surface openSurface() {
            if (mSurfaceHolder == null) {
                return null;
            }
            return mSurfaceHolder.getSurface();
        }

        @Nullable
        @Override
        public SurfaceTexture getSurfaceTexture() {
            return null;
        }
    }

    /**
     * 系统调用此方法来获取超出文本内容的视图状态的附加信息。
     * 如果自定义视图提供简单TextView或Button以外的交互控制,开发者应该重写该方法，
     * 并且使用该方法设置该视图的额外信息到事件中,如密码区域类型,复选框类型或提供用户交互或反馈的状态。
     * 如果重写这个方法,开发者必须调用它父类的实现方法，然后只修改那些父类中尚未设置的属性。
     *
     * @param event
     */
    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(RenderSurfaceView.class.getName());
    }

    /**
     * (API级别14)该方法为无障碍服务提供视图的状态信息。
     * 默认View的实现包含一组标准的视图属性，
     * 但是如果自定义视图提供了超出TextView或Button的交互，
     * 开发者应该重写该方法，并在AccessibilityNodeInfo对象中设置视图的额外信息，该对象被该方法处理。
     *
     * @param info
     */
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            info.setClassName(RenderSurfaceView.class.getName());
        }
    }
}
