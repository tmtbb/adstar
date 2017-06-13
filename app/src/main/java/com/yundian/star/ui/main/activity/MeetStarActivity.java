package com.yundian.star.ui.main.activity;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/6/13.
 * 约见名人
 */

public class MeetStarActivity extends BaseActivity {
    @Bind(R.id.nl_title)
    NormalTitleBar nl_title;
    @Override
    public int getLayoutId() {
        return R.layout.activity_meet_start;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nl_title.setBackVisibility(true);
        nl_title.setRightImagVisibility(true);
        nl_title.setRightImagSrc(R.drawable.share);
    }
}
