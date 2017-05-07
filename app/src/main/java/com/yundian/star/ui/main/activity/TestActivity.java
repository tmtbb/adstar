package com.yundian.star.ui.main.activity;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.ui.main.contract.TestContract;
import com.yundian.star.ui.main.model.TestModel;
import com.yundian.star.ui.main.presenter.TestPresenter;

/**
 * Created by Null on 2017/5/6.
 */

public class TestActivity extends BaseActivity<TestPresenter,TestModel> implements TestContract.View{
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void showUserInfo(TestModel userInfoModel) {

    }
}
