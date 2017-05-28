package com.yundian.star.ui.main.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

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

    }


    @OnClick({R.id.ll_setting_deal_rule, R.id.ll_setting_about_us, R.id.ll_setting_clear_cache, R.id.ll_setting_quit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_deal_rule:
                ToastUtils.showShort("模拟第一次交易");


                break;
            case R.id.ll_setting_about_us:
                break;
            case R.id.ll_setting_clear_cache:
                break;
            case R.id.ll_setting_quit_login:
                break;
        }
    }
}
