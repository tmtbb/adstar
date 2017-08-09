package com.cloudTop.starshare.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.cloudTop.starshare.R;


public class MyProgressView extends AppCompatImageView {
    private float rotateDegrees;
    private int frameTime;
    private boolean needToUpdateView;
    private Runnable updateViewRunnable;

    public MyProgressView(Context context) {
        this(context, null);
    }

    public MyProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setImageResource(R.drawable.ios_progress);
        frameTime = 1000 / 12;
        updateViewRunnable = new Runnable() {
            @Override
            public void run() {
                rotateDegrees += 30;
                rotateDegrees = rotateDegrees < 360 ? rotateDegrees : rotateDegrees - 360;
                invalidate();
                if (needToUpdateView) {
                    postDelayed(this, frameTime);
                }
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(rotateDegrees, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        needToUpdateView = true;
        post(updateViewRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        needToUpdateView = false;
        super.onDetachedFromWindow();
    }
}
