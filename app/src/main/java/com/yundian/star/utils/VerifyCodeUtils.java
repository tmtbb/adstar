package com.yundian.star.utils;

import android.content.Context;
import android.view.View;

import com.yundian.star.been.RegisterVerifyCodeBeen;
import com.yundian.star.widget.WPEditText;


/**
 * Created by Administrator on 2017/2/22.
 */
public class VerifyCodeUtils {
    private  RegisterVerifyCodeBeen verifyCodeBeen ;
    public void getCode() {

//        else {
//            if (Utils.isMobile(text))
//                new CountUtil((TextView) msgEditText.getRightText()).start();
//            else
//                ToastUtils.show(context, "请输入正确的手机号码");
//        }


    }

    private void obtainAuthCode(final WPEditText msgEditText,String phoneEdit) {
        /*NetworkAPIFactoryImpl.getUserAPI().verifyCode(phoneEdit, verifyType, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("验证码请求网络错误------------------"+((NetworkAPIException) ex).getErrorCode());
                ErrorCodeUtil.showEeorMsg(context,ex);
            }

            @Override
            public void onSuccess(VerifyCodeReturnEntry verifyCodeReturnEntry) {
                new CountUtil((TextView) msgEditText.getRightText()).start();   //收到回调才开启计时
                if (verifyType == 0) {  //注册,保存注册返回的验证码时间戳
                    RegisterVerifyCodeEntry.timeStamp = verifyCodeReturnEntry.timestamp;
                    RegisterVerifyCodeEntry.vToken = verifyCodeReturnEntry.vToken;
//                    LogUtil.d("获取到--注册短信验证码,时间戳是:" + RegisterVerifyCodeEntry.timeStamp);
                } else if (verifyType == 1) {  //登录
                    LoginVerifyCodeEntry.timestamp = verifyCodeReturnEntry.timestamp;
                    LoginVerifyCodeEntry.vToken = verifyCodeReturnEntry.vToken;
//                    LogUtil.d("获取到--登录短信验证码,时间戳是:" + LoginVerifyCodeEntry.timestamp);
                } else if (verifyType == 2) {
                    //更新服务
                }
            }
        });*/



    }
}
