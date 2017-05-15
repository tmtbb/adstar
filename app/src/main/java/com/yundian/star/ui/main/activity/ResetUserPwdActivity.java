package com.yundian.star.ui.main.activity;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.RegisterVerifyCodeBeen;
import com.yundian.star.helper.CheckHelper;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIException;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.yundian.star.utils.CountUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.MD5Util;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.CheckException;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.widget.WPEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10.
 */

public class ResetUserPwdActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar nt_title ;
    @Bind(R.id.phoneEditText)
    WPEditText phoneEditText;
    @Bind(R.id.msgEditText)
    WPEditText msgEditText;
    @Bind(R.id.pwdEditText1)
    WPEditText pwdEditText1;
    @Bind(R.id.pwdEditText2)
    WPEditText pwdEditText2;
    @Bind(R.id.okButton)
    Button okButton;

    private RegisterVerifyCodeBeen verifyCodeBeen ;

    private CheckHelper checkHelper = new CheckHelper();

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_user_pwd;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setTvLeftVisiable(true);
        nt_title.setTitleText(getString(R.string.butten_reset_psd));
        phoneEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        //当验证码接口有了后才用下面的方法
        checkHelper.checkButtonState(okButton, phoneEditText, msgEditText, pwdEditText1, pwdEditText2);
        checkHelper.checkVerificationCode(msgEditText.getRightText(), phoneEditText);
        /*msgEditText.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logd("此时网络的连接状态是:" + SocketAPINettyBootstrap.getInstance().isOpen());
                int verifyType = 1;// 0-注册 1-登录 2-更新服务
                VerifyCodeUtils.getCode(msgEditText, verifyType, context, v, phoneEditText);
            }
        });*/
        msgEditText.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SocketAPINettyBootstrap.getInstance().isOpen()) {
                    ToastUtils.showShort("网络连接失败,请检查网络");
                    return;
                }
                getCode(msgEditText,view, phoneEditText);
            }
        });


    }

    @OnClick(R.id.okButton)
    public void okButton(){
                LogUtils.logd("此时网络的连接状态是:" + SocketAPINettyBootstrap.getInstance().isOpen());
                CheckException exception = new CheckException();
                if (checkHelper.checkMobile(phoneEditText.getEditTextString(), exception)
                        && checkHelper.checkMobile(phoneEditText.getEditTextString(), exception)
                        && checkHelper.checkPassword(pwdEditText1.getEditTextString(), exception)
                        && checkHelper.checkPassword2(pwdEditText1.getEditTextString(), pwdEditText2.getEditTextString(), exception)) {
                    resetUserPwd();

                } else {
                    showShortToast(exception.getErrorMsg());
                }

    }

    private void resetUserPwd() {
        //本地校验验证码   MD5(yd1742653sd + code_time + rand_code + phone)
        if (!verifyCodeBeen.getVToken().equals(MD5Util.MD5("yd1742653sd" + verifyCodeBeen.getTimeStamp() + msgEditText.getEditTextString()+phoneEditText.getEditTextString()))) {
            ToastUtils.showShort("验证码错误,请重新输入");
            return;
        }
        //int type = 0;//0：登录密码 1：交易密码，提现密码
        NetworkAPIFactoryImpl.getUserAPI().resetPasswd(phoneEditText.getEditTextString(), pwdEditText2.getEditTextString()
                ,new OnAPIListener<Object>() {
                    @Override
                    public void onError(Throwable ex) {
                        ToastUtils.showShort("修改登录密码失败");
                        ex.printStackTrace();
                    }

                    @Override
                    public void onSuccess(Object o) {
                        ToastUtils.showShort("修改登录密码成功");
                        finish();
                        startActivity(LoginActivity.class);
                        overridePendingTransition(R.anim.activity_open_down_in,R.anim.activity_off_top_out);
                    }
                });
    }

    private void getCode(final WPEditText msgEditText,View view, WPEditText phoneEditText) {
        LogUtils.logd("请求网络获取短信验证码------------------------------");
        CheckException exception = new CheckException();
        String phoneEdit = phoneEditText.getEditTextString();
        if (new CheckHelper().checkMobile(phoneEdit, exception)) {
            //Utils.closeSoftKeyboard(view);
            NetworkAPIFactoryImpl.getUserAPI().verifyCode(phoneEdit, new OnAPIListener<RegisterVerifyCodeBeen>()  {
                @Override
                public void onError(Throwable ex) {
                    ex.printStackTrace();
                    LogUtils.logd("验证码请求网络错误------------------"+((NetworkAPIException) ex).getErrorCode());
                }

                @Override
                public void onSuccess(RegisterVerifyCodeBeen o) {
                    verifyCodeBeen = o ;
                    new CountUtil((TextView) msgEditText.getRightText()).start();   //收到回调才开启计时
                    LogUtils.logd("获取到--注册短信验证码,时间戳是:" + o.toString());
                }
            });
        } else {
            ToastUtils.showShort(exception.getErrorMsg());
        }
    }

}