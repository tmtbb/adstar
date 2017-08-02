package com.cloudTop.starshare.ui.main.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.been.MoneyDetailListBean;
import com.cloudTop.starshare.greendao.GreenDaoManager;
import com.cloudTop.starshare.greendao.StarInfo;
import com.cloudTop.starshare.ui.view.RoundImageView;
import com.cloudTop.starshare.utils.BuyHandleStatuUtils;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;

import java.util.List;

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
        MoneyDetailListBean bean = bundle.getParcelable("dealDetail");
        if (bean != null) {
            LogUtils.logd("获取到信息---");
            String plus_minus;
            if (bean.getRecharge_type() == 0) {
                plus_minus = "+";
                petname.setText(BuyHandleStatuUtils.getRechargeType(bean.getDepositType()));
                headImage.setImageResource(BuyHandleStatuUtils.getRechargeImg(bean.getDepositType()));
                status.setText(BuyHandleStatuUtils.getRechargeStatus(bean.getStatus()));
                billingBankInfo.setText(getString(R.string.recharge_info));
                tvDetailBankInfo.setText(BuyHandleStatuUtils.getRechargeType(bean.getDepositType()));
            } else {
                plus_minus = "-";
                billingBankInfo.setText(getString(R.string.money_issuer_info));
                List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(bean.getTransaction_id());
                if (starInfos.size() != 0) {
                    StarInfo starInfo = starInfos.get(0);
                    petname.setText(starInfo.getName());
                    ImageLoaderUtils.display(this, headImage, starInfo.getPic_url());
                    tvDetailBankInfo.setText(String.format(mContext.getString(R.string.name_code), starInfo.getName(), bean.getTransaction_id()));
                }
                if (bean.getRecharge_type() == 1) {  //约见记录
                    status.setText("约见");
                } else if (bean.getRecharge_type() == 2) {  //聊天记录
                    status.setText("星聊");
                }
            }
            tvBillingDetailMoney.setText(plus_minus + bean.getAmount() + "");
            tvServiceRecharge.setText("");   //临时设置
            tvDetailSetupTime.setText(bean.getDepositTime());
        }
    }
}
