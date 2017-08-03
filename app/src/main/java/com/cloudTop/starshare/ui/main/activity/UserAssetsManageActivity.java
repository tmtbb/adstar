package com.cloudTop.starshare.ui.main.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.AssetDetailsBean;
import com.cloudTop.starshare.been.BankCardBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.JudgeIsSetPayPwd;
import com.cloudTop.starshare.utils.SharePrefUtil;

import butterknife.Bind;
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
    @Bind(R.id.parent_view)
    LinearLayout parentView;
    @Bind(R.id.star_money)
    TextView starMoney;
    //    @Bind(R.id.hold_money)
//    TextView holdMoney;
//    @Bind(R.id.user_money)
//    TextView userMoney;
    private PopupWindow popupWindow;
//    private PayDialog payDialog;

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
//        payDialog = new PayDialog(this);
        showPopupWindow();
        ntTitle.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(ntTitle.getRightImage(), 0, 0);
//                popupWindow.showAtLocation(ntTitle.getRightImage(),  Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        requestBankInfo();
    }

    private void requestBankInfo() {
        startProgressDialog("加载中...");
        NetworkAPIFactoryImpl.getDealAPI().bankCardList(new OnAPIListener<BankCardBean>() {
            @Override
            public void onSuccess(BankCardBean bankCardBeen) {
                stopProgressDialog();
                if (TextUtils.isEmpty(bankCardBeen.getCardNo()) || TextUtils.isEmpty(bankCardBeen.getBankUsername())) {
                    LogUtils.loge("银行卡列表失败----------------------------------------------");
                    SharePrefUtil.getInstance().saveCardNo("");
                } else {
                    LogUtils.loge("银行卡列表----------------成功");
                    SharePrefUtil.getInstance().saveCardNo(bankCardBeen.getCardNo());
                }

            }

            @Override
            public void onError(Throwable ex) {
                stopProgressDialog();
                SharePrefUtil.getInstance().saveCardNo("");
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


    @OnClick({R.id.ll_recharge, R.id.ll_user_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_recharge:
//                ToastUtils.showShort("充值");
//                if (JudgeIdentityUtils.isIdentityed(this)) {
//                    startActivity(RechargeActivity.class);
//                }

                if (JudgeIsSetPayPwd.isSetPwd(this)) {  //检验是否设置交易密码
                    startActivity(RechargeActivity.class);
                }
                break;
            case R.id.ll_user_cash:
                String cardNo = SharePrefUtil.getInstance().getCardNo();
                if (TextUtils.isEmpty(cardNo)) {
                    showBindBankDialog();
                } else {
                    startActivity(CashActivity.class);
                }
                break;
        }
    }

    private void showBindBankDialog() {
        final Dialog mDetailDialog = new Dialog(this, R.style.custom_dialog);
        mDetailDialog.setContentView(R.layout.dialog_bind_bank_card);
        final Button startIdentity = (Button) mDetailDialog.findViewById(R.id.btn_start_identity);
        ImageView closeImg = (ImageView) mDetailDialog.findViewById(R.id.iv_dialog_close);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.setCancelable(false);
        startIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddBankCardActvivity.class);
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_money_detail:
                startActivity(MoneyBagDetailActivity.class);
                popupWindow.dismiss();
                break;
            case R.id.tv_bank_info:
                String cardNo = SharePrefUtil.getInstance().getCardNo();
                if (TextUtils.isEmpty(cardNo)) {
                    showBindBankDialog();
                } else {
                    startActivity(BankCardInfoActivity.class);
                }
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
                starMoney.setText(String.format("%.3f",bean.getBalance()));
                //holdMoney.setText(bean.getBalance() + "");
                //userMoney.setText(bean.getBalance() + "");
                if (bean.getIs_setpwd() != -100) {
                    SharePrefUtil.getInstance().saveAssetInfo(bean);
                }
                if (!TextUtils.isEmpty(bean.getHead_url()) && !TextUtils.isEmpty(bean.getNick_name())) {
                    SharePrefUtil.getInstance().putUserNickName(bean.getNick_name());
                    SharePrefUtil.getInstance().putUserPhotoUrl(bean.getHead_url());
                }
            }

            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("余额请求失败:" + ex.getMessage());
            }
        });
    }
}
