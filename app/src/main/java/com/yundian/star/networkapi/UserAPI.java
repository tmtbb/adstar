package com.yundian.star.networkapi;


import com.yundian.star.been.LoginReturnInfo;
import com.yundian.star.been.RegisterReturnBeen;
import com.yundian.star.been.RegisterReturnWangYiBeen;
import com.yundian.star.been.RegisterVerifyCodeBeen;
import com.yundian.star.listener.OnAPIListener;

/**
 * Created by yaowang on 2017/2/20.
 * 用户相关API接口
 */

public interface UserAPI {
    void login(String phone, String password , OnAPIListener<LoginReturnInfo> listener);
    void registerWangYi(String phone,String name, String password , OnAPIListener<RegisterReturnWangYiBeen> listener);
//
//    void wxLogin(String openId, OnAPIListener<LoginReturnEntity> listener);
//
    void register(String phone, String password, long memberId, String agentId, String recommend, OnAPIListener<RegisterReturnBeen> listener);
//
    void verifyCode(String phone, OnAPIListener<RegisterVerifyCodeBeen> listener);
//
    void resetPasswd(String phone, String pwd,OnAPIListener<Object> listener); //修改交易/用户密码
//
//    void test(int testID, OnAPIListener<Object> listener);
//
//    void loginWithToken(OnAPIListener<LoginReturnEntity> listener);
//
//    void balance(OnAPIListener<BalanceInfoEntity> listener);
//
//    void bindNumber(String phone, String openid, String password, String vCode, long memberId, String agentId, String recommend, String nickname, String headerUrl, OnAPIListener<RegisterReturnEntity> listener);
}
