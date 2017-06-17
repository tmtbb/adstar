package com.yundian.star.ui.main.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.AssetDetailsBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.view.PayDialog;
import com.yundian.star.utils.JudgeIdentityUtils;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户资产管理
 * Created by Administrator on 2017/5/23.
 */

public class UserAssetsManageActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.ll_recharge)
    LinearLayout recharge;
    @Bind(R.id.ll_user_fudai)
    LinearLayout fudai;
    @Bind(R.id.parent_view)
    LinearLayout parentView;
    @Bind(R.id.star_money)
    TextView starMoney;
    @Bind(R.id.hold_money)
    TextView holdMoney;
    @Bind(R.id.user_money)
    TextView userMoney;
    private PopupWindow popupWindow;
    private PayDialog payDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_assets_manage;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.user_asset_manage));
        ntTitle.setBackVisibility(true);
        ntTitle.setTitleColor(Color.rgb(255, 255, 255));
        ntTitle.setBackGroundColor(Color.rgb(251, 153, 56));
        ntTitle.setRightImagSrc(R.drawable.money_bag_more);
        payDialog = new PayDialog(this);
        showPopupWindow();
        ntTitle.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(ntTitle.getRightImage(), 0, 0);
//                popupWindow.showAtLocation(ntTitle.getRightImage(),  Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    private void showPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.popup_money_bag, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        contentview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();

                    return true;
                }
                return false;
            }
        });
        contentview.findViewById(R.id.tv_money_detail).setOnClickListener(this);
        contentview.findViewById(R.id.tv_bank_info).setOnClickListener(this);

    }


    @OnClick({R.id.ll_recharge, R.id.ll_user_fudai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_recharge:
                ToastUtils.showShort("充值");
                if (JudgeIdentityUtils.isIdentityed(this)) {
                    startActivity(RechargeActivity.class);
                }
                break;
            case R.id.ll_user_fudai:
                LogUtils.loge("点击福袋；；");
//                showErrorView(parentView,"sssss");
//                payDialog.show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_money_detail:
//                ToastUtils.showShort("钱包明细");
                startActivity(MoneyBagDetailActivity.class);
                popupWindow.dismiss();
                break;
            case R.id.tv_bank_info:
//                ToastUtils.showShort("银行卡");
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestBalance();
    }

    private void requestBalance() {
        NetworkAPIFactoryImpl.getDealAPI().balance(new OnAPIListener<AssetDetailsBean>() {
            @Override
            public void onSuccess(AssetDetailsBean bean) {
                starMoney.setText(bean.getBalance() + "");
                holdMoney.setText(bean.getBalance() + "");
                userMoney.setText(bean.getBalance() + "");
                SharePrefUtil.getInstance().saveAssetInfo(bean);
            }

            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("余额请求失败:" + ex.getMessage());
            }
        });
    }

}
