/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.harry.rv.utils.AppUtils;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/7/8.
 */
public class RingView extends View {

    private float mRingWidth;
    private float mCircleWidth;

    private float mRadiusX;
    private float mRadiusY;
    private int mCenterX;
    private int mCenterY;
    private RectF mRingRect;
    private RectF mCircleRect;
    private RectF mOutCircleRect;

    private int mBgColor = 0xfff98d90;
    private int mProgressColor = 0xfff98d90;
    private int mLineColor = 0xffc7c6cc;
    private int mMaskColor = 0x40eeeeee;
    protected int mTextColor = 0xffffffff;

    private int mFontSize;
    private float mProgress;
    private float mSecProgress = 0f;

    private Paint mPaint = new Paint();
    private Path mLine;
    private int mLineHeaight;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mRingWidth = AppUtils.dp2px(context, 11);
        mCircleWidth = AppUtils.dp2px(context, 6);
        mFontSize = AppUtils.sp2px(context, 16);
        mLineHeaight = AppUtils.dp2px(context, 20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制底部实心圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mBgColor);
        canvas.drawOval(mCircleRect, mPaint);

        //绘制底部圆环
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);
        mPaint.setColor(mBgColor);
        float arc = -360 * mProgress / 100;
        canvas.drawArc(mRingRect, -90, arc, false, mPaint);
        if (mProgress > 200) {
            mSecProgress = 0;
        }
        else if(mSecProgress <200 && mSecProgress>100){
            mSecProgress = 100 - mSecProgress;
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);
        mPaint.setColor(mProgressColor);
        arc = -360 * mSecProgress / 100;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mMaskColor);
        if (mProgress <100) {
            canvas.drawArc(mOutCircleRect, -90, 360, true, mPaint);
        }
        if (mSecProgress > 0) {
            canvas.drawArc(mOutCircleRect, -90, arc, true, mPaint);
        }
        drawText(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(mLineColor);
        canvas.drawPath(mLine, mPaint);
    }
    
    private void drawText(Canvas canvas) {
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mFontSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        String text = mProgress + "%";
        float textWidth = mPaint.measureText(text, 0, text.length());
        int txtHeight = (int) (mPaint.getFontMetrics().descent + mPaint.getFontMetrics().ascent);
        canvas.drawText(text, mCenterX - textWidth / 2, mCenterY - txtHeight / 2, mPaint);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w >> 1;
        mCenterY = h >> 1;

        mCenterX = Math.min(mCenterX, mCenterY);
        mCenterY = mCenterX;

        mRadiusX = mCenterX - mRingWidth / 2f;
        mRadiusY = mCenterY - mRingWidth / 2f;

        mRingRect = new RectF(mCenterX - mRadiusX, mCenterY - mRadiusY, mCenterX + mRadiusX, mCenterY + mRadiusY);
        mOutCircleRect = new RectF(mRingRect);

        mRingRect.inset(mRingWidth / 2, mRingWidth / 2);

        mCircleRect = new RectF(mRingRect);

        mCircleRect.inset(mCircleWidth + mRingWidth, mCircleWidth + mRingWidth);

        float y2 = mCenterY * 5 / 3;
        float x2 = mCenterX + mRadiusX + mRingWidth;

        float y = (mRadiusY - mRingWidth / 2) / 2;

        float x1 = mCenterX + (float) Math.sqrt(3) * y;
        float y1 = mCenterY + y;

        mLine = new Path();
        mLine.moveTo(x1, y1);
        mLine.lineTo(x2, y2);
        mLine.lineTo(w, y2);
    }

    public synchronized void setProgress(float progress) {
        this.mProgress = progress;
        if (mProgress > 100 && mProgress < 200) {
            mSecProgress = mProgress - 100;
        }
        invalidate();
    }
}
