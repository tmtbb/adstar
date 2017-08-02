package com.cloudTop.starshare.ui.main.activity;

import android.animation.ObjectAnimator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.utils.MD5Util;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.been.RequestResultBean;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.view.PayPwdEditText;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by sll on 2017/5/26.
 */

public class SettingDealPwdActivity extends BaseActivity {
    @Bind(R.id.nt_title1)
    NormalTitleBar ntTitle1;
    @Bind(R.id.ppe_pwd1)
    PayPwdEditText ppePwd1;
    @Bind(R.id.btn_next1)
    Button btnNext1;
    @Bind(R.id.set_pwd_layout1)
    LinearLayout setPwdLayout1;
    @Bind(R.id.nt_title2)
    NormalTitleBar ntTitle2;
    @Bind(R.id.ppe_pwd2)
    PayPwdEditText ppePwd2;
    @Bind(R.id.btn_next2)
    Button btnNext2;
    @Bind(R.id.set_pwd_layout2)
    LinearLayout setPwdLayout2;
    private String newPwd;
    private int setPwdLayout1Width;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_deal_pwd;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void initView() {
        btnNext1.setEnabled(false);
        btnNext2.setEnabled(false);
        ppePwd1.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.font_999999, R.color.alpha_95_black, 20);
        ppePwd2.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.font_999999, R.color.alpha_95_black, 20);
        ntTitle1.setTitleText(getString(R.string.setting_pay_pwd));
        ntTitle2.setTitleText(getString(R.string.setting_pay_pwd2));
        initListener();

    }

    private String prePwd;

    private void initListener() {
        ppePwd1.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                prePwd = str;
                btnNext1.setEnabled(true);
            }

            @Override
            public void onChange(String str) {
                btnNext1.setEnabled(false);
            }
        });

        ppePwd2.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                newPwd = str;
                if (!prePwd.equals(str)) {
                    //两次输入密码相等,保存交易密码
                    ToastUtils.showShort("两次交易密码不一致,请重新输入");
                    ppePwd2.clearText();
                } else {
                    btnNext2.setEnabled(true);
                }
            }

            @Override
            public void onChange(String str) {
                btnNext2.setEnabled(false);
            }
        });
    }

    @OnClick({R.id.btn_next1, R.id.btn_next2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next1:
                ObjectAnimator animator = ObjectAnimator.ofFloat(setPwdLayout2, "translationX", setPwdLayout1Width, 0.0f);
                animator.setDuration(200).start();

                setPwdLayout2.setVisibility(View.VISIBLE);
                setPwdLayout1.setVisibility(View.GONE);
                ppePwd1.clearText();
                break;
            case R.id.btn_next2:
                if (prePwd.equals(newPwd)) {
//                    ToastUtils.showShort("进入下一个页面");
                    settingDealPwd(newPwd);
                }

                break;
        }
    }

    private void settingDealPwd(String newPwd) {
        String vCode = null;
        int type = 0; //0-设置密码1-重置密码
        String pwd = MD5Util.MD5(newPwd);  //加密之后
        String vToken = SharePrefUtil.getInstance().getToken();
        String phone = SharePrefUtil.getInstance().getPhoneNum();

        NetworkAPIFactoryImpl.getDealAPI().dealPwd(phone, vToken, vCode, 0, type, pwd, new OnAPIListener<RequestResultBean>() {
            @Override
            public void onSuccess(RequestResultBean resultBean) {
                LogUtils.loge("设置交易密码成功回调:" + resultBean.toString());
                if (resultBean.getStatus() == 0) {
                    ToastUtils.showShort("设置交易密码成功");
                    SharePrefUtil.getInstance().putIsSetpwd(0);   //设置过密码
                    finish();
                    //关闭前面几个页面
//                    AppManager.getAppManager().finishActivity(DisclaimerActivity.class);
//                    AppManager.getAppManager().finishActivity(IdentityAuthenticationActivity.class);
                } else {
                    ToastUtils.showShort("设置交易密码失败");
                }
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtils.logd("交易密码设置失败");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setPwdLayout1Width = setPwdLayout1.getWidth();
        LogUtils.logd("sss:" + setPwdLayout1Width);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)) {
            LogUtils.logd("点击了返回键");
            if (setPwdLayout2.getVisibility() == View.VISIBLE) {

                ObjectAnimator animator = ObjectAnimator.ofFloat(setPwdLayout2, "translationX", 0.0f, setPwdLayout1Width);
                animator.setDuration(200).start();
                setPwdLayout2.setVisibility(View.GONE);
                setPwdLayout1.setVisibility(View.VISIBLE);
                ppePwd2.clearText();
                return true;
            } else {
                LogUtils.logd("return:false-----------");
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
