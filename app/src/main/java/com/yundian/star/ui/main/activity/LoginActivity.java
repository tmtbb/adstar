package com.yundian.star.ui.main.activity;

import android.graphics.Point;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.base.baseapp.AppManager;
import com.yundian.star.been.LoginReturnInfo;
import com.yundian.star.been.RegisterReturnWangYiBeen;
import com.yundian.star.helper.CheckHelper;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.wangyi.DemoCache;
import com.yundian.star.ui.wangyi.config.preference.Preferences;
import com.yundian.star.ui.wangyi.config.preference.UserPreferences;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.CheckException;
import com.yundian.star.widget.WPEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Null on 2017/5/7.
 */

public class LoginActivity extends BaseActivity{
    @Bind(R.id.userNameEditText)
    WPEditText userNameEditText ;
    @Bind(R.id.passwordEditText)
    WPEditText passwordEditText ;
    @Bind(R.id.loginButton)
    Button loginButton ;
    @Bind(R.id.registerText)
    TextView registerText;
    @Bind(R.id.tv_retrieve_password)
    TextView tv_retrieve_password;
    private CheckHelper checkHelper = new CheckHelper();
    private AbortableFuture<LoginInfo> loginRequest;
    private long exitNow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        WindowManager.LayoutParams p = getWindow().getAttributes();// 获取对话框当前的参值
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        p.width = (int)(size.x*0.9);
        getWindow().setAttributes(p); // 设置生效
        userNameEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        checkHelper.checkButtonState(loginButton, userNameEditText, passwordEditText);
    }
    @OnClick(R.id.loginButton)
    public void loging() {
        CheckException exception = new CheckException();
        if (checkHelper.checkMobile(userNameEditText.getEditTextString(), exception)
                && checkHelper.checkPassword(passwordEditText.getEditTextString(), exception)) {
            NetworkAPIFactoryImpl.getUserAPI().login(userNameEditText.getEditTextString(), passwordEditText.getEditTextString(), new OnAPIListener<LoginReturnInfo>() {
                @Override
                public void onError(Throwable ex) {
                    LogUtils.logd("登录失败"+ex.toString());
                }

                @Override
                public void onSuccess(final LoginReturnInfo loginReturnInfo) {
                    if(loginReturnInfo==null||loginReturnInfo.getUserinfo()==null){
                        return;
                    }else {
                        LogUtils.logd("登录成功"+loginReturnInfo);
                        //网易云注册
                        NetworkAPIFactoryImpl.getUserAPI().registerWangYi(userNameEditText.getEditTextString(), userNameEditText.getEditTextString(), new OnAPIListener<RegisterReturnWangYiBeen>() {
                            @Override
                            public void onError(Throwable ex) {
                                LogUtils.logd("网易云注册失败"+ex.toString());
                                ToastUtils.showShort("网易云注册失败");
                            }

                            @Override
                            public void onSuccess(RegisterReturnWangYiBeen registerReturnWangYiBeen) {
                                LogUtils.logd("网易云注册成功"+registerReturnWangYiBeen.getResult_value()+"网易云token"+registerReturnWangYiBeen.getToken_value());
                                loginWangYi(loginReturnInfo,registerReturnWangYiBeen);
                            }
                        });


                    }

                }
            });
        } else {
            showLongToast(exception.getErrorMsg());
        }


    }

    private void loginWangYi(final LoginReturnInfo loginReturnInfos,RegisterReturnWangYiBeen registerReturnWangYiBeen) {
        LogUtils.logd(loginReturnInfos.getUserinfo().getPhone()+"..."+registerReturnWangYiBeen.getToken_value());
        // 登录
        loginRequest = NimUIKit.doLogin(new LoginInfo(loginReturnInfos.getUserinfo().getPhone(), registerReturnWangYiBeen.getToken_value()), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtils.logd("网易云登录成功");
                DemoCache.setAccount(param.getAccount());
                saveLoginInfo(param.getAccount(), param.getToken());
                // 初始化消息提醒配置
                initNotificationConfig();
                // 构建缓存
                DataCacheManager.buildDataCacheAsync();
                SharePrefUtil.getInstance().saveLoginUserInfo(loginReturnInfos.getUserinfo());
                LoginActivity.this.finish();
                LoginActivity.this.overridePendingTransition(0,R.anim.activity_off_top_out);
                finish();
            }

            @Override
            public void onFailed(int code) {
                if (code == 302 || code == 404) {
                    LogUtils.logd("网易云登录失败"+R.string.login_failed);
                } else {
                    LogUtils.logd("网易云登录失败"+code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                LogUtils.logd("网易云登录失败"+R.string.login_exception);
            }
        });
    }

    @OnClick(R.id.registerText)
    public void doingReregister() {
        startActivity(RegisterUserActivity.class);
        finish();
        overridePendingTransition(R.anim.activity_open_down_in,R.anim.activity_off_top_out);
    }
    @OnClick(R.id.tv_retrieve_password)
    public void retrievePassword(){
        finish();
        startActivity(ResetUserPwdActivity.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)) {

            if ((System.currentTimeMillis() - exitNow) > 2000) {
                Toast.makeText(this, String.format(getString(R.string.confirm_exit_app), getString(R.string.app_name)), Toast.LENGTH_SHORT).show();
                exitNow = System.currentTimeMillis();
            } else if ((System.currentTimeMillis() - exitNow) > 0) {
                AppManager.getAppManager().AppExit(this,false);
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }
}
