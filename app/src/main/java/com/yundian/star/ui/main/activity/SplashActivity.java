package com.yundian.star.ui.main.activity;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/5.
 */

public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        SetTranslanteBar();
    }
}
