package com.yundian.star.wxapi;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.yundian.star.R;

/**
 * Created by Administrator on 2017/3/20.
 */
public class LineMarkerView extends MarkerView {
    private Context context;
    private TextView currentPrice;
    private TextView time;
    private LinearLayout markerView;

    public LineMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        currentPrice = (TextView) findViewById(R.id.tv_currentPrice);
        time = (TextView) findViewById(R.id.time);
        markerView = (LinearLayout) findViewById(R.id.ll_marker_line);
        this.context = context;
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        if (entry.getData() == null) {
            markerView.setVisibility(INVISIBLE);
        } else {
            markerView.setVisibility(VISIBLE);
            currentPrice.setText(String.format("%.2f", entry.getVal()));
            time.setText(entry.getData() + "");
        }
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 5);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }

//    @Override
//    public void draw(Canvas canvas, float posx, float posy) {
//        if (posx > DisplayUtil.getScreenWidth(context) / 2) {
//            canvas.translate(0, 0);
//        } else {
//            canvas.translate(DisplayUtil.getScreenWidth(context) - getWidth(), 0);
//        }
//        draw(canvas);
//    }
}
