package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.TodayEntrustReturnBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.HistoryEntrustAdapter;
import com.yundian.star.utils.DisplayUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.timeselectutils.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/25.
 * 历史委托
 */

public class HistoryEntrustFragment extends BaseFragment {


    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private List<TodayEntrustReturnBean> list = new ArrayList<>();
    private List<TodayEntrustReturnBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private HistoryEntrustAdapter historyEntrustAdapter;
    private int end_year;
    private int start_year = 2017;
    private int end_month;
    private int start_month = 1;
    private int end_day;
    private int start_day = 1;
    private int current_end_year;
    private int current_end_month;
    private int current_end_day;
    private LRecyclerView lrv;
    private LinearLayout ll_start_time;
    private LinearLayout ll_end_time;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private FrameLayout parentView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_history_entrust;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        initAdapter();
        getDateTime();
        getData(false, 1);
    }

    private void initData() {
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
        ll_start_time = (LinearLayout) rootView.findViewById(R.id.ll_start_time);
        ll_end_time = (LinearLayout) rootView.findViewById(R.id.ll_end_time);
        tv_start_time = (TextView) rootView.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) rootView.findViewById(R.id.tv_end_time);
        parentView = (FrameLayout) rootView.findViewById(R.id.parent_view);

    }

    private void getDateTime() {
        Calendar c = Calendar.getInstance();
        current_end_year = c.get(Calendar.YEAR);
        current_end_month = c.get(Calendar.MONTH) + 1;
        current_end_day = c.get(Calendar.DAY_OF_MONTH);
        end_year = current_end_year;
        end_month = current_end_month;
        end_day = current_end_day;
        tv_end_time.setText(end_year + "-" + end_month + "-" + end_day);
    }

    private void initAdapter() {
        historyEntrustAdapter = new HistoryEntrustAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(historyEntrustAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setNoMore(false);
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_0_5)
                .setPadding(R.dimen.dp_25)
                .setColorResource(R.color.color_dcdcdc)
                .build();
        //mRecyclerView.setHasFixedSize(true);
        lrv.addItemDecoration(divider);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter + 1);
            }
        });
    }

    private void getData(final boolean isLoadMore, int start) {
        NetworkAPIFactoryImpl.getInformationAPI().todayEntrust(start, REQUEST_COUNT, AppConstant.HISTORY_ENTRUST_OPCODE, new OnAPIListener<List<TodayEntrustReturnBean>>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        historyEntrustAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
            }

            @Override
            public void onSuccess(List<TodayEntrustReturnBean> todayEntrustReturnBeen) {
                if (todayEntrustReturnBeen == null) {
                    lrv.setNoMore(true);
                    return;
                }
                if (isLoadMore) {
                    closeErrorView();
                    loadList.clear();
                    loadList = todayEntrustReturnBeen;
                    loadMoreData();
                } else {
                    list.clear();
                    list = todayEntrustReturnBeen;
                    showData();
                }
            }
        });
    }

    public void showData() {
        if (list.size() == 0) {
            showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
            return;
        } else {
            closeErrorView();
        }
        historyEntrustAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        historyEntrustAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            historyEntrustAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    @OnClick({R.id.ll_start_time, R.id.ll_end_time})
    public void OnTimeSelectClick(View v) {
        switch (v.getId()) {
            case R.id.ll_start_time:
                onYearMonthStartTime();
                break;
            case R.id.ll_end_time:
                onYearMonthEndTime();
                break;

        }
    }

    private void onYearMonthEndTime() {
        final DatePicker picker = new DatePicker(getActivity());
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(DisplayUtil.dip2px(20));
        picker.setRangeEnd(current_end_year, current_end_month, current_end_day);
        picker.setRangeStart(start_year, start_month, start_day);
        picker.setSelectedItem(end_year, end_month, end_day);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                end_year = Integer.valueOf(year).intValue();
                end_month = Integer.valueOf(month).intValue();
                end_day = Integer.valueOf(day).intValue();
                tv_end_time.setText(year + "-" + month + "-" + day);
                //调用获取数据
                //getData();
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    public void onYearMonthStartTime() {
        final DatePicker picker = new DatePicker(getActivity());
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(DisplayUtil.dip2px(20));
        picker.setRangeEnd(end_year, end_month, end_day);
        picker.setRangeStart(2017, 1, 1);
        picker.setSelectedItem(end_year, end_month, end_day);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                LogUtils.loge(year + "-" + month + "-" + day);
                start_year = Integer.valueOf(year).intValue();
                start_month = Integer.valueOf(month).intValue();
                start_day = Integer.valueOf(day).intValue();
                tv_start_time.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }


}
