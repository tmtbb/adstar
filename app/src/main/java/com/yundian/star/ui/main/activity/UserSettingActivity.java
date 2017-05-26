package com.yundian.star.ui.main.activity;

import android.widget.LinearLayout;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;

import butterknife.OnClick;


/**
 * Created by Administrator on 2017/5/23.
 */

public class UserSettingActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.headImage)
    RoundImageView headImage;
    @Bind(R.id.ll_user_phone)
    LinearLayout userPhone;
    @Bind(R.id.ll_user_pet_name)
    LinearLayout userPetName;
    @Bind(R.id.ll_user_name)
    LinearLayout userName;
    @Bind(R.id.ll_user_card_number)
    LinearLayout userCardNumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ntTitle.setTitleText("个人信息");

    }

    @OnClick(R.id.headImage)
    public void onViewClicked() {
        ToastUtils.showShort("上传头像----");
    }
}
