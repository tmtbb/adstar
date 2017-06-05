package com.yundian.star.ui.main.activity;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;

/**
 * Created by Administrator on 2017/6/5.
 */

public class AddUserCommentActivity extends BaseActivity {

    private String code;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_comment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        code = getIntent().getStringExtra(AppConstant.STAR_CODE);

    }
}
