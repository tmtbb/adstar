package com.yundian.star.ui.main.activity;

import android.graphics.Point;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.helper.CheckHelper;
import com.yundian.star.widget.WPEditText;

import butterknife.Bind;

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
    Button registerButton ;
    private CheckHelper checkHelper = new CheckHelper();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
        checkHelper.checkButtonState(registerButton, userNameEditText, msgEditText, passwordEditText);
        checkHelper.checkVerificationCode(msgEditText.getRightText(), passwordEditText);
        //initListener();
    }

    /*private void initListener() {
        msgEditText.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SocketAPINettyBootstrap.getInstance().isOpen()) {
                    ToastUtils.show(context, "网络连接失败,请检查网络");
                    return;
                }
                int verifyType = 0;// 0-注册 1-登录 2-更新服务
                VerifyCodeUtils.getCode(msgEditText, verifyType, context, view, phoneEditText);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loader = "正在注册...";
                if (isBind) {
                    loader = "正在绑定...";
                }
                showLoader(loader);
                CheckException exception = new CheckException();
                phone = phoneEditText.getEditTextString();
                pwd = pwdEditText.getEditTextString();
                vCode = msgEditText.getEditTextString();

                if (checkHelper.checkMobile(phone, exception) && checkHelper.checkPassword(pwd, exception)
                        && checkHelper.checkVerifyCode(vCode, exception)) {
                    Utils.closeSoftKeyboard(v);

                    newPwd = SHA256Util.shaEncrypt(SHA256Util.shaEncrypt(pwd + "t1@s#df!") + phone);
                    memberUnitText = 0;
                    if (!TextUtils.isEmpty(memberUnit.getEditTextString())) {
                        memberUnitText = Long.parseLong(memberUnit.getEditTextString());
                    }
                    agentIdText = agentId.getEditTextString();
                    refereeIdText = refereeId.getEditTextString();
                    if (isBind) {
                        bindUserInfo();
                    } else {
                        register();
                    }
                } else {
                    closeLoader();
                    showToast(exception.getErrorMsg());
                }
            }
        });
    }*/
}
