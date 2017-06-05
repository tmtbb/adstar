package com.yundian.star.ui.main.fragment;

import android.os.Handler;
import android.os.Message;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.StartTimeShareBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.TimeUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/6/1.
 * 分时图
 */

public class KChartFragment extends BaseFragment {
    @Bind(R.id.chart)
    CombinedChart mChart;
    private int colorHomeBg;
    private int colorDivide;
    private int colorText;
    private int colorLine;
    private MyHandler mHandler;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragmentl_kchart;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initChart();
        mHandler = new MyHandler(this);
        getData();
    }

    private void getData() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarStatist("1001", new OnAPIListener<StartTimeShareBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StartTimeShareBeen startData) {
                LogUtils.loge("分时图" + startData.toString());
                if (startData.getResult() == 1) {
                    loadChartData(startData.getStastic());
                }
            }
        });
    }


    public void initChart() {
        colorHomeBg = getResources().getColor(R.color.white); //背景色
        colorDivide = getResources().getColor(R.color.white);//分割线
        colorText = getResources().getColor(R.color.color_666666);
        colorLine = getResources().getColor(R.color.red);//条目

        mChart.setDescription("");//描述信息
        mChart.setDrawGridBackground(false); //是否显示表格颜色
        mChart.setBackgroundColor(colorHomeBg);
        mChart.setGridBackgroundColor(colorHomeBg);
        mChart.setScaleYEnabled(true);  //Y轴激活
        mChart.setPinchZoom(false); //如果禁用,扩展可以在x轴和y轴分别完成
        mChart.setNoDataText("加载中...");
//        mChart.setAutoScaleMinMaxEnabled(true);
//        mChart.setDragEnabled(false); //可以拖拽
        mChart.setScaleEnabled(false);  //放大缩小
        mChart.getLegend().setEnabled(false);//图例
        mChart.setTouchEnabled(true);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});  //设置绘制顺序

//        mChart.requestDisallowInterceptTouchEvent(true);
        mChart.setExtraLeftOffset(10);
        mChart.setExtraRightOffset(10);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(colorLine);
        xAxis.setTextColor(colorText);
        xAxis.setSpaceBetweenLabels(4);// 轴刻度间的宽度，默认值是4
        xAxis.setEnabled(false);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setLabelCount(6, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setGridColor(colorLine);
        rightAxis.setTextColor(colorText);
        rightAxis.setDrawGridLines(false);
        rightAxis.setStartAtZero(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
        //refreshMarkerView(chartType);
    }

    public void loadChartData(ArrayList<StartTimeShareBeen.StasticBean> currentTimeLineEntities) {
        mChart.resetTracking();

        Collections.reverse(currentTimeLineEntities);
        if (currentTimeLineEntities.size() == 0) {
            return;
        }


        int itemcount = currentTimeLineEntities.size();
        ArrayList<String> xVals = new ArrayList<>();  //X集合
        for (int i = 0; i < itemcount; i++) {
            String formatData = TimeUtil.formatData(TimeUtil.dateFormat, currentTimeLineEntities.get(i).getTimestamp());
            xVals.add(formatData);  //日期集合
        }
        CombinedData combinedData = new CombinedData(xVals);
        //分时图

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int index = 0; index < itemcount; index++) {
            float currentPrice = (float) currentTimeLineEntities.get(index).getValue();
            yVals.add(new Entry((currentPrice), index));
        }
        LineDataSet lineDataSet = generateLineDataSet(yVals, colorLine, "");
        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData lineData = new LineData(xVals, dataSets);
        combinedData.setData(lineData); //加入MA5\MA10\MA20三种折线图数据

        mChart.setData(combinedData);//当前屏幕会显示所有的数据
        //mChart.setVisibleXRange(30, 100);
        mChart.invalidate();
    }


    private static class MyHandler extends Handler {
        final private static int GRT_DATA = 111;
        private final WeakReference<KChartFragment> mFragment;

        public MyHandler(KChartFragment mfragment) {
            mFragment = new WeakReference<KChartFragment>(mfragment);
        }

        @Override
        public void handleMessage(Message msg) {
            KChartFragment fragment = mFragment.get();
            if (fragment != null) {
                switch (msg.what) {
                    case GRT_DATA:
                        fragment.getData();
                        fragment.mHandler.sendEmptyMessageDelayed(GRT_DATA, 5 * 1000);
                        break;
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mHandler != null) {
            mHandler.removeMessages(mHandler.GRT_DATA);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHandler != null) {
            mHandler.removeMessages(mHandler.GRT_DATA);
            mHandler.sendEmptyMessage(mHandler.GRT_DATA);
        }
    }

    private LineDataSet generateLineDataSet(List<Entry> entries, int color, String label) {
        LineDataSet set = new LineDataSet(entries, label);
        set.setColor(color);
        set.setLineWidth(1f);
        set.setDrawCubic(true);//圆滑曲线
        set.setDrawCircles(false);//设置有圆点
        set.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set.setFillColor(R.color.color_cccccc);
        set.setCubicIntensity(0.2f);
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        set.setHighlightEnabled(true);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setHighLightColor(R.color.color_666666);
        return set;
    }
}
