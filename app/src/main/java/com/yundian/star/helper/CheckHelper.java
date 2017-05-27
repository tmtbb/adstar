package com.yundian.star.helper;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.yundian.star.utils.StringUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.utils.Utils;
import com.yundian.star.widget.CheckException;
import com.yundian.star.widget.SimpleTextWatcher;
import com.yundian.star.widget.WPEditText;
import java.util.regex.Pattern;


/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-11-23 10:02
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class CheckHelper {

    private static final String EMPEY_USERNAME = "用户名不能为空";
    private static final String LENGTH_MAX_USERNAME = "用户名不能超过50个字符";
    private static final String LENGTH_MIN_PASSWORD = "密码不能小于6个字符";
    private static final String LENGTH_MAX_PASSWORD = "密码不能大于16个字符";
    private static final String EMPEY_PASSWORD = "密码不能为空";
    private static final String ERROR_MOBILE = "手机号码不正确";
    private static final String ERROR_MOBILE2 = "两次手机号码不一致";
    private static final String ERROR_PWD2 = "两次密码输入不一致";
    private static final String ERROR_NUM2 = "两次号码不一致";
    private static final String EMPEY_VERIFY_CODE = "短信验证码不能为空";
    private static final String LENGTH_MAX_VERIFY = "验证码不能大于10个字符";

    public boolean checkUsername(String username, CheckException exception) {
        if (exception == null) {
            exception = new CheckException();
        }
        if (username.length() > 50) {
            exception.setErrorMsg(LENGTH_MAX_USERNAME);
            return false;
        }
        return true;
    }

    public boolean checkPassword(String password, CheckException exception) {
        if (exception == null) {
            exception = new CheckException();
        }

        if (password.length() < 6) {  //临时修改
            exception.setErrorMsg(LENGTH_MIN_PASSWORD);
            return false;
        } else if (password.length() > 16) {
            exception.setErrorMsg(LENGTH_MAX_PASSWORD);
            return false;
        }
        return true;
    }

    public boolean checkPassword2(String password, String password2, CheckException exception) {
        if (exception == null) {
            exception = new CheckException();
        }
        if (!password.equals(password2)) {
            exception.setErrorMsg(ERROR_PWD2);
            return false;
        }
        return true;
    }

    public boolean checkNumber2(String number, String number1, CheckException exception) {
        if (exception == null) {
            exception = new CheckException();
        }
        if (!number.equals(number1)) {
            exception.setErrorMsg(ERROR_NUM2);
            return false;
        }
        return true;
    }

    public boolean checkMobile(String mobile, CheckException exception) {
        if (exception == null) {
            exception = new CheckException();
        }
        if (!Utils.isMobile(mobile)) {
            exception.setErrorMsg(ERROR_MOBILE);
            return false;
        }
        return true;
    }


    public boolean checkMobile2(String mobile, String mobile2, CheckException exception) {
        if (exception == null) {
            exception = new CheckException();
        }
        if (!mobile2.equals(mobile)) {
            exception.setErrorMsg(ERROR_MOBILE2);
            return false;
        }
        return true;
    }

    public void checkVerificationCode(final View button, final WPEditText editText) {

        //button.setEnabled(!StringUtil.isEmpty(editText.getEditTextString()));
        editText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                //button.setEnabled(!StringUtil.isEmpty(editable.toString()));
                button.setTag(editable.toString());
            }
        });

    }

    public void checkButtonState(final View button, final WPEditText... editText) {
        button.setEnabled(false);
        for (int i = 0; i < editText.length; i++) {
            final int positon = i;
            editText[i].addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable editable) {
                    super.afterTextChanged(editable);
                    if (!StringUtil.isEmpty(editable.toString())) {
                        boolean enable = true;
                        for (int j = 0; j < editText.length; j++) {
                            if (StringUtil.isEmpty(editText[j].getEditTextString())) {
                                enable = false;
                                break;
                            }
                        }
                        button.setEnabled(enable);
                    } else {
                        button.setEnabled(false);
                    }

                }
            });
        }
    }

    public void checkButtonState1(final View button, final EditText... editText) {
        button.setEnabled(false);
        for (int i = 0; i < editText.length; i++) {
            final int positon = i;
            editText[i].addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable editable) {
                    super.afterTextChanged(editable);
                    if (!StringUtil.isEmpty(editable.toString())) {

                        boolean enable = true;
                        for (int j = 0; j < editText.length; j++) {
                            if (StringUtil.isEmpty(editText[j].getText().toString().trim())) {
                                enable = false;
                                break;
                            }
                        }
                        button.setEnabled(enable);
                    } else {
                        button.setEnabled(false);
                    }

                }
            });
        }
    }

    public boolean checkVerifyCode(String verifycode, CheckException exception) {
        if (exception == null) {
            exception = new CheckException();
        }

        if (TextUtils.isEmpty(verifycode)) {
            exception.setErrorMsg(EMPEY_VERIFY_CODE);
            return false;
        } else if (verifycode.length() > 8) {
            exception.setErrorMsg(LENGTH_MAX_VERIFY);
            return false;
        }
        return true;
    }

    public void checkIdentityCard(EditText editText, Context context) {
        editText.addTextChangedListener(new NoChineseTextWatcher(editText, context));
    }

    public class NoChineseTextWatcher implements TextWatcher {
        final String reg = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";//正则表达式，非中文
        private boolean isNotMatch = false;
        private EditText editText;
        private Context context;

        public NoChineseTextWatcher(EditText editText, Context context) {
            this.editText = editText;
            this.context = context;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (!TextUtils.isEmpty(str)) {
                char[] chars = str.toCharArray();
                for (int i = 0; i < str.length(); i++) {
                    String aChar = String.valueOf(chars[i]);
                    if ( !Pattern.matches(reg, aChar)){
                        isNotMatch = true;
                    }else{
                        isNotMatch = false;
                    }

                }
                if (!isNotMatch) {
                    ToastUtils.showShort("请输入正确的身份证号码");
                    editText.setText("");
                    isNotMatch = true;
                }
            } else {
                isNotMatch = false;
            }
        }
    }
}
