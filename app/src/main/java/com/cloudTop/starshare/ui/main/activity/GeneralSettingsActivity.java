package com.cloudTop.starshare.ui.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.app.Constant;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.EventBusMessage;
import com.cloudTop.starshare.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.cloudTop.starshare.ui.wangyi.config.preference.Preferences;
import com.cloudTop.starshare.ui.wangyi.login.LogoutHelper;
import com.cloudTop.starshare.utils.DataCleanManagerTtil;
import com.cloudTop.starshare.utils.JudgeIsSetPayPwd;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by sll on 2017/5/24.
 */

public class GeneralSettingsActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.ll_setting_deal_rule)
    LinearLayout llSettingDealRule;
    @Bind(R.id.ll_setting_about_us)
    LinearLayout llSettingAboutUs;
    @Bind(R.id.ll_setting_clear_cache)
    LinearLayout llSettingClearCache;
    @Bind(R.id.ll_setting_quit_login)
    LinearLayout llSettingQuitLogin;
    @Bind(R.id.ll_setting_reset_login_pwd)
    LinearLayout resetLoginPwd;
    @Bind(R.id.ll_setting_reset_pay_pwd)
    LinearLayout resetPayPwd;
    @Bind(R.id.tv_cash_size)
    TextView cashSize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_genneral_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.my_setting));
        ntTitle.setBackVisibility(true);
        initData();
    }

    private void initData() {
        try {
            String totalCacheSize = DataCleanManagerTtil.getTotalCacheSize(mContext);
            cashSize.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.ll_setting_deal_rule, R.id.ll_setting_about_us, R.id.ll_setting_clear_cache, R.id.ll_setting_quit_login
            , R.id.ll_setting_reset_login_pwd, R.id.ll_setting_reset_pay_pwd,R.id.ll_law_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_deal_rule:
                CommonWebActivity.startAction(GeneralSettingsActivity.this,"http://122.144.169.219:3389/transaction","交易规则");
                break;
            case R.id.ll_setting_about_us:
                CommonWebActivity.startAction(GeneralSettingsActivity.this,"http://122.144.169.219:3389/aboutStar","关于我们");
                break;
            case R.id.ll_setting_clear_cache:
                clearAppCache();
                break;
            case R.id.ll_setting_quit_login:
                //退出登录
                showAlertDialog();
                break;
            case R.id.ll_setting_reset_login_pwd:
                judgePayPwd(Constant.USER_PWD);
                break;
            case R.id.ll_law_info:
                CommonWebActivity.startAction(GeneralSettingsActivity.this,"http://122.144.169.219:3389/law","法律说明");
                break;
            case R.id.ll_setting_reset_pay_pwd:
                if (JudgeIsSetPayPwd.isSetPwd(this)) {
                    judgePayPwd(Constant.PAY_PWD);
                }
                break;
        }
    }

    private void judgePayPwd(String type) {
            Bundle bundle = new Bundle();
            bundle.putString("resetPwd", type);
            startActivity(ResetPayPwdActivity.class, bundle);
    }


    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认退出登录吗？")
                .setTitle("提示")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void logout() {

        SharePrefUtil.getInstance().clearUserInfo();
        SharePrefUtil.getInstance().clearUserLoginInfo();
        Preferences.saveUserToken("");
        LogoutHelper.logout();
//        DataCacheManager.clearDataCache();  //清空缓存
        EventBus.getDefault().postSticky(new EventBusMessage(2));  //登录取消消息
        //关闭soket通道。让其重连，重新生成
        SocketAPINettyBootstrap.getInstance().closeChannel();
        finish();
//        startActivity(LoginActivity.class);
    }

    private void clearAppCache() {
        DataCleanManagerTtil.clearAllCache(mContext);
        cashSize.setText("0k");
    }

}
