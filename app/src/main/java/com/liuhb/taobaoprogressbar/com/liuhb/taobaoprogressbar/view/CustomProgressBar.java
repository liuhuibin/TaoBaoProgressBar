package com.liuhb.taobaoprogressbar.com.liuhb.taobaoprogressbar.view;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.liuhb.taobaoprogressbar.R;

/**
 * Created by huibin on 15/5/20.
 */
public class CustomProgressBar extends View {

    private Paint mPaint;
    private TextPaint mTextPaint;

    private int mRadius;
    private int mProgressColor;
    private int mProgressDescColor;
    private int mMax;
    private int mProgress;
    private int mBorderWidth;
    private boolean mIsShowDesc ;

    private int DEFAULT_MAX = 10;
    private int DEFAULT_PROGRESS = 0;
    private int DEFAULT_RADIUS = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    private int DEFAULT_BORDER_COLOR = Color.parseColor("#FE78A6");
    private int DEFAULT_PROGRESS_COLOR = Color.parseColor("#FE78A6");
    private int DEFAULT_PROGRESS_DESC_COLOR = Color.parseColor("#B4B4B4");
    private int DEFAULT_BORDER_WIDTH = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
    private boolean DEFAULT_ISSHOWDESC = true ;

    private int mWidth;
    private int mHeight;
    private Rect mTextBounds;
    private String mProgressDesc = "";

    private OnFinishedListener mOnFinishedListener;
    private OnAnimationEndListener mOnAnimationEndListener;


    /**
     * set finish listener
     * @param onFinishedListener
     */
    public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
        mOnFinishedListener = onFinishedListener;
    }

    /**
     * set animation end listener
     * @param onAnimationEndListener
     */
    public void setOnAnimationEndListener(OnAnimationEndListener onAnimationEndListener){
        mOnAnimationEndListener = onAnimationEndListener;
    }

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomProgressBar);
        try {
            mMax = a.getInt(R.styleable.CustomProgressBar_max, DEFAULT_MAX);
            mProgress = a.getInt(R.styleable.CustomProgressBar_progress,
                    DEFAULT_PROGRESS);
            mRadius = (int) a.getDimension(
                    R.styleable.CustomProgressBar_progressRadius, DEFAULT_RADIUS);
            mProgressColor = a.getColor(
                    R.styleable.CustomProgressBar_progressColor,
                    DEFAULT_PROGRESS_COLOR);
            mProgressDescColor = a.getColor(
                    R.styleable.CustomProgressBar_progressDescColor,
                    DEFAULT_PROGRESS_DESC_COLOR);
            mBorderWidth = (int) a.getDimension(
                    R.styleable.CustomProgressBar_borderWidth,
                    DEFAULT_BORDER_WIDTH);
            mProgressDesc = a
                    .getString(R.styleable.CustomProgressBar_progressDesc);
            mIsShowDesc = a.getBoolean(R.styleable.CustomProgressBar_isShowDesc,DEFAULT_ISSHOWDESC) ;

        } finally {
            a.recycle();
        }

        init();

    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextBounds = new Rect();

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, getResources()
                        .getDisplayMetrics()));
        mTextPaint.setColor(mProgressDescColor);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawBorder(canvas);
        drawProgress(canvas);
        if (mIsShowDesc)  drawProgressDesc(canvas);
    }

    private void drawBorder(Canvas canvas) {

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mProgressColor);
        mPaint.setStrokeWidth(mBorderWidth);

        int left = mBorderWidth / 2;
        int top = mBorderWidth / 2;
        int right = mWidth - mBorderWidth / 2;
        int bottom = mHeight - mBorderWidth / 2;

        Path path = new Path();
        path.moveTo(left + mRadius, top);
        path.lineTo(right - mRadius, top);
        path.arcTo(
                new RectF(right - 2 * mRadius, top, right, top + 2 * mRadius),
                -90, 90);
        path.lineTo(right, bottom - mRadius);
        path.arcTo(new RectF(right - 2 * mRadius, bottom - 2 * mRadius, right,
                bottom), 0, 90);
        path.lineTo(left + mRadius, bottom);
        path.arcTo(new RectF(left, bottom - 2 * mRadius, left + 2 * mRadius,
                bottom), 90, 90);
        path.lineTo(left, top + mRadius);
        path.arcTo(new RectF(left, top, left + 2 * mRadius, top + 2 * mRadius),
                180, 90);
        path.close();

        canvas.drawPath(path, mPaint);
    }

    private void drawProgress(Canvas canvas) {

        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mProgressColor);
        mPaint.setStrokeWidth(mBorderWidth);

        float left = mBorderWidth * .5f;
        float top = mBorderWidth * .5f;
        float right = mWidth - mBorderWidth * .5f;
        float bottom = mHeight - mBorderWidth * .5f;

        Path path = new Path();
        path.moveTo(left, top + mRadius);
        float scale = (mProgress * 1.f / mMax)
                / (mRadius * 1.f / (right - left));
        float scale2 = (mProgress * 1.f / mMax)
                / ((right - mRadius) * 1.f / (right - left));
        if (scale <= 1) {

            float a = scale * mRadius;
            double angle = Math.acos((mRadius - a) / mRadius);
            path.arcTo(new RectF(left, top, left + 2 * mRadius, top + 2
                    * mRadius), 180, (float) (angle * 180 / Math.PI));
            float y = (float) (Math.pow(
                    Math.pow(mRadius, 2) - Math.pow((a - mRadius), 2), 0.5)
                    + bottom - mRadius);
            path.lineTo(left + a, y);
            path.arcTo(new RectF(left, bottom - 2 * mRadius,
                            left + 2 * mRadius, bottom),
                    180 - (float) (angle * 180 / Math.PI),
                    (float) (angle * 180 / Math.PI));
            path.close();
            canvas.drawPath(path, mPaint);
        } else if (scale2 <= 1) {

            path.arcTo(new RectF(left, top, left + 2 * mRadius, top + 2
                    * mRadius), 180, 90);
            path.lineTo(left + (mProgress * 1.f / mMax) * (right - left), top);
            path.lineTo(left + (mProgress * 1.f / mMax) * (right - left),
                    bottom);
            path.lineTo(left + mRadius, bottom);
            path.arcTo(new RectF(left, bottom - 2 * mRadius,
                    left + 2 * mRadius, bottom), 90, 90);
            path.close();

            canvas.drawPath(path, mPaint);

        } else {

            float a = (mProgress * 1.f / mMax) * (right - left)
                    - (right - mRadius);
            double angle = Math.asin(a / mRadius);
            path.arcTo(new RectF(left, top, left + 2 * mRadius, top + 2
                    * mRadius), 180, 90);
            path.lineTo(right - mRadius, top);
            path.arcTo(new RectF(right - 2 * mRadius, top, right, top + 2
                    * mRadius), -90, (float) (angle * 180 / Math.PI));
            double y = Math.pow((Math.pow(mRadius, 2) - Math.pow(a, 2)), .5)
                    + top + mRadius;
            path.lineTo(right - mRadius + a, (float) y);
            path.arcTo(new RectF(right - 2 * mRadius, bottom - 2 * mRadius,
                            right, bottom), (float) (90 - (angle * 180 / Math.PI)),
                    (float) (angle * 180 / Math.PI));
            path.lineTo(left + mRadius, bottom);
            path.arcTo(new RectF(left, bottom - 2 * mRadius,
                    left + 2 * mRadius, bottom), 90, 90);
            path.close();

            canvas.drawPath(path, mPaint);

        }

    }

    private void drawProgressDesc(Canvas canvas) {

        String finalProgressDesc = mProgressDesc + " " + mProgress + "/" + mMax;
        mTextPaint.getTextBounds(finalProgressDesc, 0,
                finalProgressDesc.length(), mTextBounds);

        canvas.drawText(finalProgressDesc, (int) (mWidth / 2.0 - mTextBounds.width() / 2.0), (int) (mHeight / 2.0 - (mTextPaint.ascent() + mTextPaint.descent()) / 2.0f), mTextPaint);

    }

    public void setMaxProgress(int max) {

        mMax = max < 0 ? 0 : max;
        invalidateView();

    }

    private void setProgress(int progress) {

        mProgress = progress > mMax ? mMax : progress;
        invalidateView();

        if (mProgress>= mMax && mOnFinishedListener!=null) {
            mOnFinishedListener.onFinish();
        }

    }

    /**
     * 得到ProgressBar的最大进度
     * @return
     */
    public int getMax() {
        return mMax ;

    }

    /**
     * 获取当前ProgressBar的进度
     * @return
     */
    public final int getProgress() {

        return  mProgress ;
    }

    public void setProgressDesc(String desc) {
        mProgressDesc = desc;
        invalidateView();
    }

    /**
     * 设置当前进度条的进度(默认动画时间1.5s)
     * @param progress
     */
    public void setCurProgress (final int progress) {

        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", progress).setDuration(1500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mOnAnimationEndListener!=null) {
                    mOnAnimationEndListener.onAnimationEnd();
                }
            }
        });
        animator.start();
    }

    /**
     * 设置当前进度条的进度
     * @param progress 目标进度
     * @param duration 动画时长
     */
    public void setCurProgress (final int progress,long duration) {

        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", progress).setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mOnAnimationEndListener!=null) {
                    mOnAnimationEndListener.onAnimationEnd();
                }
            }
        });
        animator.start();

    }

    /**
     * 设置ProgressBar的颜色
     * @param color
     */
    public void setProgressColor(int color){
        mProgressColor = color ;
        invalidateView();
    }

    /**
     * 设置是否显示当前进度
     * @param isShowDesc true:显示
     */
    public void setIsShowDesc(boolean isShowDesc) {

        mIsShowDesc = isShowDesc ;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public interface OnFinishedListener {
        void onFinish();
    }

    public interface OnAnimationEndListener{
        void onAnimationEnd();
    }
}

