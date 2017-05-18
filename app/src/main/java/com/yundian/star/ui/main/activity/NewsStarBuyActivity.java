package com.yundian.star.ui.main.activity;

import android.content.Intent;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;

/**
 * Created by Administrator on 2017/5/18.
 */

public class NewsStarBuyActivity extends BaseActivity {

    private String code;

    @Override
    public int getLayoutId() {
        return R.layout.activity_newstar_buy;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        initData();
    }

    private void initData() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarBrief(code, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(Object o) {
                o.toString();
            }
        });
    }
}
