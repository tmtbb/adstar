package com.yundian.star.ui.main.fragment;

import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/24.
 * 明细主界面
 */

public class DetailFragment extends BaseFragment {

    private int currentType = 0;
    private TodayBuyFragment todayBuyFragment;
    private TodayEntrustFragment entrustFragment;
    private HistoryEntrustFragment historyEntrustFragment;
    private HistoryBuyFragment historyBuyFragment;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        initFragment();
        switchTo(1);
    }

    private void initData() {
        tv_1 = (TextView) rootView.findViewById(R.id.tv_1);
        tv_2 = (TextView) rootView.findViewById(R.id.tv_2);
        tv_3 = (TextView) rootView.findViewById(R.id.tv_3);
        tv_4 = (TextView) rootView.findViewById(R.id.tv_4);
    }

    private void initFragment() {
        todayBuyFragment = new TodayBuyFragment();
        entrustFragment = new TodayEntrustFragment();
        historyEntrustFragment = new HistoryEntrustFragment();
        historyBuyFragment = new HistoryBuyFragment();
    }


    @OnClick({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4})
    public void onTextClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                switchTo(1);
                break;
            case R.id.tv_2:
                switchTo(2);
                break;
            case R.id.tv_3:
                switchTo(3);
                break;
            case R.id.tv_4:
                switchTo(4);
                break;
        }
    }


    private void switchTo(int type) {
        if (currentType == type) {
            return;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (type) {
            case 1:
                currentType = type;
                tv_1.setTextColor(getResources().getColor(R.color.color_black_333333));
                tv_2.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_3.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_4.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv_4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                transaction.replace(R.id.fl_main, todayBuyFragment);
                transaction.commit();
                break;
            case 2:
                currentType = type;
                tv_2.setTextColor(getResources().getColor(R.color.color_black_333333));
                tv_1.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_3.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_4.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv_4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                transaction.replace(R.id.fl_main, entrustFragment);
                transaction.commit();
                break;
            case 3:
                currentType = type;
                tv_3.setTextColor(getResources().getColor(R.color.color_black_333333));
                tv_2.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_1.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_4.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv_4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                transaction.replace(R.id.fl_main, historyEntrustFragment);
                transaction.commit();
                break;
            case 4:
                currentType = type;
                tv_4.setTextColor(getResources().getColor(R.color.color_black_333333));
                tv_2.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_3.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_1.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv_4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                transaction.replace(R.id.fl_main, historyBuyFragment);
                transaction.commit();
                break;

        }
    }
}
