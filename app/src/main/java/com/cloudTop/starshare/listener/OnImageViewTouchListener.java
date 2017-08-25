package com.cloudTop.starshare.listener;

import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class OnImageViewTouchListener implements View.OnTouchListener {
    public final float[] BT_SELECTED = new float[]{1, 0, 0, 0, -50, 0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0};
    public final float[] BT_NOT_SELECTED = new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
    private static OnImageViewTouchListener INSTANCE;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v instanceof ImageView) {
                ImageView iv = (ImageView) v;
                iv.setDrawingCacheEnabled(true);

                iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
            } else {
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                v.setBackgroundDrawable(v.getBackground());
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            if (v instanceof ImageView) {
                ImageView iv = (ImageView) v;
                iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
            } else {
                v.getBackground().setColorFilter(
                        new ColorMatrixColorFilter(BT_NOT_SELECTED));
                v.setBackgroundDrawable(v.getBackground());
            }
        }
        return false;
    }

    public synchronized static OnImageViewTouchListener getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OnImageViewTouchListener();
        }
        return INSTANCE;
    }

    public synchronized void setSelected(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
    }
}
