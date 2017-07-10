package com.yundian.star.ui.main.activity;

import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/8.
 * 发售页面
 */

public class StarSellActivity extends BaseActivity {
    @Bind(R.id.tv_sure_buy)
    TextView tv_sure_buy;
    @Bind(R.id.nl_title)
    NormalTitleBar nl_title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_shell;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nl_title.setTitleText(getString(R.string.shells));
        nl_title.setBackVisibility(true);
        nl_title.setRightImagVisibility(true);
    }
}
