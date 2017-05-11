package com.yundian.star.networkapi.socketapi;

import com.yundian.star.app.AppApplication;
import com.yundian.star.app.SocketAPIConstant;
import com.yundian.star.been.LoginReturnInfo;
import com.yundian.star.been.RegisterReturnBeen;
import com.yundian.star.been.RegisterReturnWangYiBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.UserAPI;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.yundian.star.utils.LogUtils;

import java.util.HashMap;

import static android.R.attr.password;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketUserAPI extends SocketBaseAPI implements UserAPI {
    @Override
    public void login(String phone, String password,OnAPIListener<LoginReturnInfo> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", password);
        map.put("deviceId", AppApplication.getAndroidId());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Login,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket,LoginReturnInfo.class,listener);
    }

    @Override
    public void registerWangYi(String name_value, String accid_value, OnAPIListener<RegisterReturnWangYiBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name_value", name_value);
        map.put("accid_value", accid_value);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.WangYi,
                SocketAPIConstant.ReqeutType.Wangyi, map);
        requestEntity(socketDataPacket, RegisterReturnWangYiBeen.class, listener);
    }

    @Override
    public void register(String phone, String password, long memberId, String agentId, String recommend, OnAPIListener<RegisterReturnBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", password);
        map.put("memberId", memberId);
        map.put("agentId", agentId);
        map.put("recommend", recommend);
        map.put("timeStamp", System.currentTimeMillis());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Register,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, RegisterReturnBeen.class, listener);
    }

    @Override
    public void resetPasswd(String phone, String pwd, OnAPIListener<Object> listener) {
        LogUtils.logd("修改用户密码-----------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", pwd);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.ResetPasswd,
                SocketAPIConstant.ReqeutType.User, map);
        requestJsonObject(socketDataPacket,listener);
    }

//    @Override
//    public void login(String phone, String password, String deviceId, OnAPIListener<LoginReturnEntity> listener) {
//        LogUtil.d("开始登录");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("phone", phone);
//        map.put("pwd", password);
//        map.put("source", 2);
//        map.put("deviceId", MyApplication.getApplication().getAndroidId());
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Login,
//                SocketAPIConstant.ReqeutType.User, map);
//        requestEntity(socketDataPacket, LoginReturnEntity.class, listener);
//    }
//
//    @Override
//    public void wxLogin(String openId, OnAPIListener<LoginReturnEntity> listener) {
//        LogUtil.d("微信登录");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("openId", openId);
//        map.put("deviceId", MyApplication.getApplication().getAndroidId());
//        map.put("source", 2);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.WXLogin,
//                SocketAPIConstant.ReqeutType.User, map);
//        requestEntity(socketDataPacket, LoginReturnEntity.class, listener);
//    }
//
//    @Override
//    public void register(String phone, String password, String vCode, long memberId, String agentId, String recommend, OnAPIListener<RegisterReturnEntity> listener) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("phone", phone);
//        map.put("pwd", password);
//        map.put("vCode", vCode);
//        map.put("timeStamp", RegisterVerifyCodeEntry.timeStamp);
//        map.put("vToken", RegisterVerifyCodeEntry.vToken);
//        map.put("memberId", memberId);
//        map.put("agentId", agentId);
//        map.put("recommend", recommend);
//        map.put("deviceId", MyApplication.getApplication().getAndroidId());
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Register,
//                SocketAPIConstant.ReqeutType.User, map);
//        requestEntity(socketDataPacket, RegisterReturnEntity.class, listener);
//    }


//
//    @Override
//    public void verifyCode(String phone, int verifyType, OnAPIListener<VerifyCodeReturnEntry> listener) {
//        LogUtil.d("负责加入网络请求---获取短信验证码--------");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("verifyType", verifyType);  //0-注册 1-登录 2-更新服务（暂用 1）
//        map.put("phone", phone);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.VerifyCode,
//                SocketAPIConstant.ReqeutType.User, map);
//        requestEntity(socketDataPacket, VerifyCodeReturnEntry.class, listener);
//    }
//

//
//    @Override
//    public void test(int testID, OnAPIListener<Object> listener) {
//        LogUtil.d("心跳包-----------");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", testID);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Test,
//                SocketAPIConstant.ReqeutType.User, map);
//        requestEntity(socketDataPacket, VerifyCodeReturnEntry.class, listener);
//    }
//
//    @Override
//    public void loginWithToken(OnAPIListener<LoginReturnEntity> listener) {
//        LogUtil.d("用token登录");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Token,
//                SocketAPIConstant.ReqeutType.User, map);
//        requestEntity(socketDataPacket, LoginReturnEntity.class, listener);
//    }
//
//    @Override
//    public void balance(OnAPIListener<BalanceInfoEntity> listener) {
//        LogUtil.d("请求余额信息");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Balance,
//                SocketAPIConstant.ReqeutType.Verify, map);
//        requestEntity(socketDataPacket, BalanceInfoEntity.class, listener);
//    }
//
//    @Override
//    public void bindNumber(String phone, String openid, String password, String vCode, long memberId, String agentId, String recommend, String nickname, String headerUrl, OnAPIListener<RegisterReturnEntity> listener) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("phone", phone);
//        map.put("openid", openid);
//        map.put("pwd", password);
//        map.put("vCode", vCode);
//        map.put("nickname", nickname);
//        map.put("headerUrl", nickname);
//        map.put("timeStamp", RegisterVerifyCodeEntry.timeStamp);
//        map.put("vToken", RegisterVerifyCodeEntry.vToken);
//        map.put("memberId", memberId);
//        map.put("agentId", agentId);
//        map.put("recommend", recommend);
//        map.put("deviceId", MyApplication.getApplication().getAndroidId());
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.WXBind,
//                SocketAPIConstant.ReqeutType.User, map);
//        requestEntity(socketDataPacket, RegisterReturnEntity.class, listener);
//    }
}
