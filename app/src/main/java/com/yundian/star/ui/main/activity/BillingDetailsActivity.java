package com.yundian.star.ui.main.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;

/**
 * Created by sll on 2017/5/25.
 */

public class BillingDetailsActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.headImage)
    RoundImageView headImage;
    @Bind(R.id.tv_billing_detail_money)
    TextView tvBillingDetailMoney;
    @Bind(R.id.tv_service_recharge)
    TextView tvServiceRecharge;
    @Bind(R.id.ll_service_recharge)
    LinearLayout llServiceRecharge;
    @Bind(R.id.tv_detail_bank_info)
    TextView tvDetailBankInfo;
    @Bind(R.id.ll_bank_info)
    LinearLayout llBankInfo;
    @Bind(R.id.tv_detail_setup_time)
    TextView tvDetailSetupTime;
    @Bind(R.id.ll_setup_time)
    LinearLayout llSetupTime;
    @Bind(R.id.tv_billing_bank_info)
    TextView billingBankInfo;
    private boolean isDeal = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_billing_details;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.money_billing_detail));
        initData();


    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        BookingStarListBean bean = bundle.getParcelable("dealDetail");
        if (bean != null) {
            isDeal = true;
            LogUtils.logd("获取到信息---");
        }

        billingBankInfo.setText(isDeal ? getResources().getString(R.string.money_bank_info) : getResources().getString(R.string.money_issuer_info));
    }

}
