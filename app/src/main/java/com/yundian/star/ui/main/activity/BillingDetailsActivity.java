package com.yundian.star.ui.main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.MoneyDetailListBean;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
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
    @Bind(R.id.tv_petname)
    TextView petname;
    @Bind(R.id.tv_status)
    TextView status;
//    private boolean isDeal = false;

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
        String userNickName = SharePrefUtil.getInstance().getUserNickName();
        if (TextUtils.isEmpty(userNickName)) {
            petname.setText(SharePrefUtil.getInstance().getPhoneNum());
        } else {
            petname.setText(userNickName);
        }

        String userPhotoUrl = SharePrefUtil.getInstance().getUserPhotoUrl();
        if (TextUtils.isEmpty(userPhotoUrl)) {
            headImage.setImageResource(R.drawable.user_default_head);
        } else {
            ImageLoaderUtils.display(mContext, headImage, userPhotoUrl);
        }

        Bundle bundle = getIntent().getExtras();
        MoneyDetailListBean bean = bundle.getParcelable("dealDetail");
        if (bean != null) {
            LogUtils.logd("获取到信息---");
            tvBillingDetailMoney.setText(bean.getAmount() + "");
            tvServiceRecharge.setText("");   //临时设置
            tvDetailBankInfo.setText(bean.getDepositName());
            if (bean.getDepositType() == 2) {
                billingBankInfo.setText(getString(R.string.money_bank_info));
            } else {
                billingBankInfo.setText(getResources().getString(R.string.money_issuer_info));
            }
            tvDetailSetupTime.setText(bean.getDepositTime());
            status.setText(getRechargeStatus(bean.getStatus()));
        }
    }

    private String getRechargeStatus(int flag) {
        String status = "";
        switch (flag) {   //1-处理中，2-成功，3-失败
            case 1:
                status = "处理中";
                break;
            case 2:
                status = "充值成功";
                break;
            case 3:
                status = "充值失败";
                break;
        }
        return status;
    }
}
