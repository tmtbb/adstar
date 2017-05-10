package com.yundian.star.ui.main.activity;

import android.graphics.Point;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.base.baseapp.AppManager;
import com.yundian.star.been.LoginReturnInfo;
import com.yundian.star.helper.CheckHelper;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
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

                }

                @Override
                public void onSuccess(LoginReturnInfo loginReturnInfo) {
                    if(loginReturnInfo==null||loginReturnInfo.getUserinfo()==null){
                        return;
                    }else {
                        LogUtils.logd("登录成功"+loginReturnInfo);
                        SharePrefUtil.getInstance().saveLoginUserInfo(loginReturnInfo.getUserinfo());
                        LoginActivity.this.finish();
                        LoginActivity.this.overridePendingTransition(0,R.anim.activity_off_top_out);
                    }

                }
            });
        } else {
            showLongToast(exception.getErrorMsg());
        }


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
}
