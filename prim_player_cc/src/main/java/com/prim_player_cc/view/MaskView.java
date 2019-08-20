package com.prim_player_cc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.prim_player_cc.R;

public class MaskView extends View {
    private static final String TAG = "MaskView";
    private int BACKGROUND_COLOR = 0xB2000000;
    private int startY;
    private int endY;
    private Paint mPaint;
    private int mBackgroundColor;
    private int mWidth;
    private int mHeight;


    public MaskView(Context context) {
        this(context, null);
    }

    public MaskView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MaskView);
            mBackgroundColor = arr.getColor(R.styleable.MaskView_backgroundColor, BACKGROUND_COLOR);
            arr.recycle();
        }
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, mWidth, startY, mPaint);
        mPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0, startY, mWidth, endY, mPaint);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(0, endY, mWidth, mHeight, mPaint);
    }

    public void changeMaskLocation(int startY, int endY) {
        this.startY = startY;
        this.endY = endY;
        invalidate();
    }
}
