package com.yundian.star.ui.main.activity;

import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.RegisterReturnBeen;
import com.yundian.star.been.RegisterVerifyCodeBeen;
import com.yundian.star.been.WXUserInfoEntity;
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
import com.yundian.star.widget.WPEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RegisterUserActivity extends BaseActivity {
    @Bind(R.id.userNameEditText)
    WPEditText userNameEditText ;
    @Bind(R.id.msgEditText)
    WPEditText msgEditText ;
    @Bind(R.id.passwordEditText)
    WPEditText passwordEditText ;
    @Bind(R.id.registerButton)
    Button registerButton;
    @Bind(R.id.tv_retrieve_password)
    TextView tv_retrieve_password;
    @Bind(R.id.registerText)
    TextView registerText;
    private CheckHelper checkHelper = new CheckHelper();
    private String phone;
    private String pwd;
    private String vCode;
    private RegisterVerifyCodeBeen verifyCodeBeen ;
    private boolean isWXBind = false;
    private WXUserInfoEntity wxUserInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        wxUserInfo = (WXUserInfoEntity)intent.getParcelableExtra("wxBind");
        if (wxUserInfo !=null){
            registerButton.setText("微信绑定");
            isWXBind = true;
        }
        WindowManager.LayoutParams p = getWindow().getAttributes();// 获取对话框当前的参值
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        p.width = (int)(size.x*0.85);
        getWindow().setAttributes(p); // 设置生效
        userNameEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        checkHelper.checkButtonState(registerButton, userNameEditText, msgEditText, passwordEditText);
        //checkHelper.checkVerificationCode(msgEditText.getRightText(), passwordEditText);
        msgEditText.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SocketAPINettyBootstrap.getInstance().isOpen()) {
                    ToastUtils.showShort("网络连接失败,请检查网络");
                    return;
                }
                getCode(msgEditText,view, userNameEditText);
            }
        });
    }

    @OnClick(R.id.registerButton)
    public void registerButton() {
                /*String loader = "正在注册...";
                if (isBind) {
                    loader = "正在绑定...";
                }
                showLoader(loader);*/
                CheckException exception = new CheckException();
                phone = userNameEditText.getEditTextString();
                pwd = passwordEditText.getEditTextString();
                vCode = msgEditText.getEditTextString();

                if (checkHelper.checkMobile(phone, exception) && checkHelper.checkPassword(pwd, exception)
                        && checkHelper.checkVerifyCode(vCode, exception)) {
                    if (isWXBind){
                        wxBindInfo();
                    }else {
                        register();
                    }
                } else {
                    ToastUtils.showShort(exception.getErrorMsg());
                }
    }

    private void wxBindInfo() {
        if (!verifyCode()){
            return;
        }
        NetworkAPIFactoryImpl.getUserAPI().bindNumber(userNameEditText.getEditTextString(), wxUserInfo.getOpenid()
                , passwordEditText.getEditTextString(), verifyCodeBeen.getTimeStamp(), verifyCodeBeen.getVToken(), vCode,
                -1, "-1", "-1", wxUserInfo.getNickname(), wxUserInfo.getHeadimgurl(), new OnAPIListener<RegisterReturnBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        LogUtils.logd("微信绑定失败!");
                    }

                    @Override
                    public void onSuccess(RegisterReturnBeen registerReturnBeen) {
                        LogUtils.logd("微信绑定成功" + registerReturnBeen.toString());
                        if (registerReturnBeen.getResult() == -301) {
                            ToastUtils.showShort("用户已经注册,请直接登录");
                            startActivity(LoginActivity.class);
                            finish();
                            overridePendingTransition(R.anim.activity_open_down_in,R.anim.activity_off_top_out);
                        } else if (registerReturnBeen.getResult() == 1) {
                            ToastUtils.showShort("注册成功");
                            /*//loginGetUserInfo(newPwd);  //登录请求数据
                            finish();
                            overridePendingTransition(0,R.anim.activity_off_top_out);*/

                            startActivity(LoginActivity.class);
                            finish();
                            overridePendingTransition(R.anim.activity_open_down_in,R.anim.activity_off_top_out);
                        }
                    }
                });
    }

    @OnClick(R.id.tv_retrieve_password)
    public void retrievePassword(){
        finish();
        startActivity(ResetUserPwdActivity.class);
    }

    @OnClick(R.id.registerText)
    public void doingLoging() {
        startActivity(LoginActivity.class);
        finish();
        overridePendingTransition(R.anim.activity_open_down_in,R.anim.activity_off_top_out);
    }

    private boolean verifyCode() {
        //本地校验验证码   MD5(yd1742653sd + code_time + rand_code + phone)
        if (!verifyCodeBeen.getVToken().equals(MD5Util.MD5("yd1742653sd" + verifyCodeBeen.getTimeStamp() + vCode+userNameEditText.getEditTextString()))) {
            ToastUtils.showShort("验证码错误,请重新输入");
            return false;
        }else {
            return true ;
        }
    }


    private void register() {
        if (!verifyCode()){
            return;
        }
        NetworkAPIFactoryImpl.getUserAPI().register(userNameEditText.getEditTextString(), passwordEditText.getEditTextString(), -1, "-1", "-1", new OnAPIListener<RegisterReturnBeen>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.logd("注册请求网络失败"+ex.toString());
            }

            @Override
            public void onSuccess(RegisterReturnBeen registerReturnBeen) {
                /*//网易云注册
                NetworkAPIFactoryImpl.getUserAPI().registerWangYi(userNameEditText.getEditTextString(), passwordEditText.getEditTextString(), new OnAPIListener<RegisterReturnWangYiBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        LogUtils.logd("网易云注册失败"+ex.toString());
                    }

                    @Override
                    public void onSuccess(RegisterReturnWangYiBeen registerReturnWangYiBeen) {
                        LogUtils.logd("网易云注册成功"+registerReturnWangYiBeen.getResult_value()+"网易云token"+registerReturnWangYiBeen.getToken_value());
                    }
                });*/
                LogUtils.logd("注册请求网络成功" + registerReturnBeen.toString());
                if (registerReturnBeen.getResult() == -301) {
                    ToastUtils.showShort("用户已经注册,请直接登录");
                    startActivity(LoginActivity.class);
                    finish();
                    overridePendingTransition(R.anim.activity_open_down_in,R.anim.activity_off_top_out);
                } else if (registerReturnBeen.getResult() == 1) {
                    ToastUtils.showShort("注册成功");
//                            loginGetUserInfo(newPwd);  //登录请求数据
                    finish();
                    overridePendingTransition(0,R.anim.activity_off_top_out);
                }
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.activity_off_top_out);
    }
}
