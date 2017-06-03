package com.yundian.star.ui.main.activity;

import android.widget.Button;
import android.widget.EditText;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.RequestResultBean;
import com.yundian.star.helper.CheckHelper;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
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
        NetworkAPIFactoryImpl.getDealAPI().identityAuthentication(etInputName.getText().toString().trim(),
                etInputCard.getText().toString().trim(), new OnAPIListener<RequestResultBean>() {
                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                        LogUtils.logd("实名认证请求失败");
                    }

                    @Override
                    public void onSuccess(RequestResultBean resultBean) {
                        LogUtils.logd("实名认证请求成功:" + resultBean.toString());
                        if (resultBean.getResult() == 0) {
                            ToastUtils.showShort("实名认证成功");
                            SharePrefUtil.getInstance().setRealName(etInputName.getText().toString().trim());
                            SharePrefUtil.getInstance().setIdnum(etInputCard.getText().toString().trim());
                            startActivity(DisclaimerActivity.class);
                        } else {
                            ToastUtils.showShort("实名认证失败");
                        }

//                        if (resultBean.getResult() == 0) {
//                            //请求接口验证身份证,成功后  免责声明,设置支付密码
//                            ToastUtils.showShort("实名认证成功");
//                            startActivity(DisclaimerActivity.class);
//                        } else {
//                            ToastUtils.showShort("实名认证失败");
//                        }
                    }
                });
    }
}
