package com.yundian.star.ui.main.activity;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.RequestResultBean;
import com.yundian.star.helper.CheckHelper;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.utils.ViewConcurrencyUtils;
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
    @Bind(R.id.cb_agree)
    CheckBox cb_agree;
    @Bind(R.id.tv_disclaimer)
    TextView disclaimer;
    private CheckHelper checkHelper = new CheckHelper();
    private boolean isCheck = true;

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
        cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
            }
        });
    }

    @OnClick({R.id.btn_verification, R.id.tv_disclaimer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_verification:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                nextBtn();
                break;
            case R.id.tv_disclaimer:
                startActivity(DisclaimerActivity.class);
                break;
        }
    }

    private void nextBtn() {
        if (!isCheck) {
            ToastUtils.showShort("您尚未勾选《免责声明》，请选择。");
            return;
        }
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
//                            showIdentityDialog();  //提示开通支付
                            finish();
                            SharePrefUtil.getInstance().setRealName(etInputName.getText().toString().trim());
                            SharePrefUtil.getInstance().setIdnum(etInputCard.getText().toString().trim());
//
                        } else {
                            ToastUtils.showShort("实名认证失败");
                        }
                    }
                });
    }

    private void showIdentityDialog() {
        final Dialog mDetailDialog = new Dialog(this, R.style.custom_dialog);
        mDetailDialog.setContentView((R.layout.dialog_open_pay));
        final Button startIdentity = (Button) mDetailDialog.findViewById(R.id.btn_start_identity2);
        ImageView closeImg = (ImageView) mDetailDialog.findViewById(R.id.iv_dialog_close2);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.setCancelable(false);
        startIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logd("进入输入密码-----");
                startActivity(SettingDealPwdActivity.class);
                mDetailDialog.dismiss();
            }
        });

        mDetailDialog.show();
    }
}
