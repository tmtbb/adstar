package com.yundian.star.ui.main.fragment;

import android.view.View;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NumberButton;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/24.
 */

public class AskToBuyMarketFragment extends BaseFragment {
    @Bind(R.id.but_buy_price)
    NumberButton but_buy_price;
    @Bind(R.id.but_buy_num)
    NumberButton but_buy_num;
    @Bind(R.id.tv_sure_buy)
    TextView tv_sure_buy ;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_ask_to_buy_mar;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        but_buy_price.setBuyMax(100)
                .setInventory(200)
                .setCurrentNumber(5)
                .setBuyMin(1)
                .setDoubleType(true)
                .setOnWarnListener(new NumberButton.OnWarnListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {

                    }

                    @Override
                    public void onWarningForBuyMax(int buyMax) {

                    }

                    @Override
                    public void onWarningForBuyMin(int min) {

                    }
                });
        tv_sure_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.loge("获取数值"+but_buy_price.getFloatNumber());
            }
        });
    }
}
