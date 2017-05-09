package com.yundian.star.ui.main.activity;

import android.graphics.Point;
import android.view.WindowManager;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.RegisterReturnBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.LogUtils;
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
    }
    @OnClick(R.id.loginButton)
    public void regist() {
        NetworkAPIFactoryImpl.getUserAPI().register(userNameEditText.getEditTextString(), passwordEditText.getEditTextString(), -1, "-1", "-1", new OnAPIListener<RegisterReturnBeen>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.logd("注册请求网络失败"+ex.toString());
            }

            @Override
            public void onSuccess(RegisterReturnBeen registerReturnBeen) {
                LogUtils.logd("注册请求网络成功" + registerReturnBeen.toString());
            }
        });
    }


}
