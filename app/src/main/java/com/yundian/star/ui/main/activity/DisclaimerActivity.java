package com.yundian.star.ui.main.activity;

import android.widget.Button;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 免责声明
 * Created by sll on 2017/5/28.
 */

public class DisclaimerActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.btn_disclaimer_agree)
    Button btnDisclaimerAgree;

    @Override
    public int getLayoutId() {
        return R.layout.activity_disclaimer;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getString(R.string.disclaimer_title));
    }


    @OnClick(R.id.btn_disclaimer_agree)
    public void onViewClicked() {
        startActivity(SettingDealPwdActivity.class);
    }
}
