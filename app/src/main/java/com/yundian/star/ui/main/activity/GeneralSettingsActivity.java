package com.yundian.star.ui.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.app.Constant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.EventBusMessage;
import com.yundian.star.ui.wangyi.config.preference.Preferences;
import com.yundian.star.ui.wangyi.login.LogoutHelper;
import com.yundian.star.utils.DataCleanManagerTtil;
import com.yundian.star.utils.JudgeIsSetPayPwd;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.widget.NormalTitleBar;

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
            , R.id.ll_setting_reset_login_pwd, R.id.ll_setting_reset_pay_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_deal_rule:
                Bundle bundle1 = new Bundle();
                bundle1.putString("bundle", "https://www.baidu.com/");
                bundle1.putInt("tag", 1);
                startActivity(DealRulesActivity.class, bundle1);
                break;
            case R.id.ll_setting_about_us:
                Bundle bundle2 = new Bundle();
                bundle2.putString("bundle", "http://www.sina.com.cn/");
                bundle2.putInt("tag", 2);
                startActivity(DealRulesActivity.class, bundle2);
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
        finish();
//        startActivity(LoginActivity.class);
    }

    private void clearAppCache() {
        DataCleanManagerTtil.clearAllCache(mContext);
        cashSize.setText("0k");
    }

}
