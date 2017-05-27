package com.yundian.star.ui.main.activity;

import android.widget.Button;
import android.widget.EditText;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.helper.CheckHelper;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 身份认证
 * Created by sll on 2017/5/26.
 */

public class IdentityAuthenticationActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.et_input_name)
    EditText etInputName;
    @Bind(R.id.et_input_card)
    EditText etInputCard;
    @Bind(R.id.btn_verification)
    Button btnVerification;
    private CheckHelper checkHelper = new CheckHelper();

    @Override
    public int getLayoutId() {
        return R.layout.activity_identity_cuthentication;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getString(R.string.identity_verification));
        checkHelper.checkButtonState1(btnVerification, etInputName, etInputCard);
//        checkHelper.checkIdentityCard(etInputCard,mContext);

    }


    @OnClick(R.id.btn_verification)
    public void onViewClicked() {
        //请求接口验证身份证,成功后  免责声明,设置支付密码
        startActivity(SettingDealPwdActivity.class);
    }
}
