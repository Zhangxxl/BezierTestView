package com.hzblzx.beziertest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by zhangxx on 2017/3/2.
 * BezierTestView --> com.hzblzx.beziertest.view
 */

public class SecondBezierView extends View {

    private float startX, startY, endX, endY;
    private float currentX, currentY;
    private Paint blackPaint, redPaint;

    private int width, height;

    //    private int touchState = MotionEvent.ACTION_CANCEL;
    private Path bezierPath;

    public SecondBezierView(Context context) {
        this(context, null);
    }

    public SecondBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecondBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        blackPaint.setTextSize(45);
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(6);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        startX = getX() + 80;
        startY = height / 2;
        endX = w + getX() - 80;
        endY = height / 2;
        bezierPath = new Path();
        currentX = width / 2;
        currentY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(startX, startY, 10, blackPaint);
        canvas.drawCircle(endX, endY, 10, blackPaint);
        canvas.drawText("开始", startX, startY, blackPaint);
        canvas.drawText("结束", endX, endY, blackPaint);

//        if (touchState == MotionEvent.ACTION_DOWN || touchState == MotionEvent.ACTION_MOVE || touchState == MotionEvent.ACTION_UP) {
        bezierPath.reset();
        bezierPath.moveTo(startX, startY);
        bezierPath.quadTo(currentX, currentY, endX, endY);
        canvas.drawPath(bezierPath, redPaint);
        canvas.drawLine(startX, startY, currentX, currentY, blackPaint);
        canvas.drawLine(currentX, currentY, endX, endY, blackPaint);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        touchState = event.getAction();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                currentY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                over();
                break;
        }
        invalidate();
        return true;
    }

    private void over() {
        ValueAnimator overX = ValueAnimator.ofFloat(currentX, width / 2);
        overX.setDuration(500);
        overX.setInterpolator(new OvershootInterpolator());
        overX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        overX.start();
        ValueAnimator overY = ValueAnimator.ofFloat(currentY, height / 2);
        overY.setDuration(500);
        overY.setInterpolator(new OvershootInterpolator());
        overY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        overY.start();
    }
}
