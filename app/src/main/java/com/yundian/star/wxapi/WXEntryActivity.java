package com.yundian.star.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.umeng.weixin.callback.WXCallbackActivity;
import com.yundian.star.R;
import com.yundian.star.app.AppApplication;
import com.yundian.star.app.Constant;
import com.yundian.star.base.baseapp.AppManager;
import com.yundian.star.been.EventBusMessage;
import com.yundian.star.been.LoginReturnInfo;
import com.yundian.star.been.RegisterReturnWangYiBeen;
import com.yundian.star.been.WXAccessTokenEntity;
import com.yundian.star.been.WXUserInfoEntity;
import com.yundian.star.been.WXinLoginReturnBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.activity.LoginActivity;
import com.yundian.star.ui.main.activity.RegisterUserActivity;
import com.yundian.star.ui.wangyi.DemoCache;
import com.yundian.star.ui.wangyi.config.preference.Preferences;
import com.yundian.star.ui.wangyi.config.preference.UserPreferences;
import com.yundian.star.utils.HttpUrlConnectionUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2017/3/13.
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    private String code;
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppApplication.api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        AppApplication.api.handleIntent(getIntent(), this);
    }

    //微信直接发送给app的消息处理回调
    @Override
    public void onReq(BaseReq baseReq) {
    }

    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        finish();
        LogUtils.loge(resp.errCode + "" + "resp.getType()" + resp.getType());
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {
                    LogUtils.logd("分享失败--------------");
                } else {
                    LogUtils.logd("登录失败-----------------");
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        code = ((SendAuth.Resp) resp).code;
                        LogUtils.loge("微信登录返回code"+((SendAuth.Resp) resp).code);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    sendRequest();     //拿到了微信返回的code,立马再去请求access_token
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        LogUtils.logd("微信分享成功-----");
                        finish();
                        break;
                }
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;

                case 1:   //请求AccessToken成功
                    String str = (String) msg.obj;
                    final WXAccessTokenEntity entity = JSON.parseObject(str, WXAccessTokenEntity.class);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestUserInfo(entity);
                        }
                    }).start();
                    break;
                case 2:
                    String info = String.valueOf(msg.obj);
                    LogUtils.loge("获取用户信息成功:" + info);
                    WXUserInfoEntity tokenEntity = JSON.parseObject(info, WXUserInfoEntity.class);
                    bindPhoneNumber(tokenEntity);   //根据用户信息,绑定手机号码
                    break;
                case 3:
                    ToastUtils.showLong(getString(R.string.often_error));
                    WXEntryActivity.this.finish();
                    break;
                default:
                    break;
            }

        }
    };

    private void requestUserInfo(WXAccessTokenEntity entity) {
        //1,获取用户信息,头像,昵称等等    get请求
        StringBuffer sb2 = new StringBuffer();
        sb2.append("https://api.weixin.qq.com/sns/userinfo?")
                .append("access_token=")
                .append(entity.getAccess_token())
                .append("&openid=")
                .append(entity.getOpenid());
        String url2 = sb2.toString();
        String info = HttpUrlConnectionUtil.httpGet(url2);
        boolean iserrcode = info.contains("errcode");
        Message message = Message.obtain();
        if (iserrcode){
            message.what = 3;
            handler.sendMessage(message);
        }else if (info != null){
            message.what = 2;
            message.obj = info;
            handler.sendMessage(message);
        }
    }

    /**
     * 根据code,获取access_token
     *
     * @throws Exception
     */
    public void sendRequest() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("https://api.weixin.qq.com/sns/oauth2/access_token?")
                .append("appid=")
                .append(Constant.APP_ID)
                .append("&secret=")
                .append(Constant.SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=")
                .append("authorization_code");
        String url = sb.toString();
        LogUtils.loge("拼接的获取access_token地址"+url);
        String response = HttpUrlConnectionUtil.httpGet(url);
        boolean iserrcode = response.contains("errcode");
        LogUtils.loge(response);
        Message msg = new Message();
        if (iserrcode){
            msg.what = 3;
            handler.sendMessage(msg);
        }else if (response != null){
            LogUtils.logd(response);
            msg.what = 1;
            msg.obj = response;
            handler.sendMessage(msg);
        }
    }

    private void bindPhoneNumber(final WXUserInfoEntity entity2) {
        NetworkAPIFactoryImpl.getUserAPI().wxLogin(entity2.getOpenid(), new OnAPIListener<WXinLoginReturnBeen>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("微信登录失败,进入绑定手机号界面" + Thread.currentThread().getName());  //进入绑定手机号码页面
                ToastUtils.showLong("微信授权成功,请绑定手机号码");
                //EventBus.getDefault().postSticky(new EventBusMessage(-6));  //传递消息
                AppManager.getAppManager().finishActivity(LoginActivity.class);
                Intent intent = new Intent(WXEntryActivity.this, RegisterUserActivity.class);
                intent.putExtra("wxBind", entity2);
                startActivity(intent);
                WXEntryActivity.this.finish();
            }

            @Override
            public void onSuccess(final WXinLoginReturnBeen info) {
                LogUtils.loge("微信登录接口返回数据" + info.toString());
                int result = info.getResult();
                if (result == 1) {
                    if (info.getUserinfo() == null || info.getUserinfo().getPhone() == null) {
                        return;
                    } else {
                        if (TextUtils.isEmpty(SharePrefUtil.getInstance().getUserPhotoUrl())) { //如果没有设置过昵称和头像,保存在下来
                            SharePrefUtil.getInstance().putUserPhotoUrl(entity2.getHeadimgurl());
                        }
                        if (TextUtils.isEmpty(SharePrefUtil.getInstance().getUserNickName())) {
                            SharePrefUtil.getInstance().putUserNickName(entity2.getNickname());
                        }
                        LogUtils.logd("登录成功" + info.getUserinfo().getPhone());
                        String name = "星说"+info.getUserinfo().getPhone().substring(8,info.getUserinfo().getPhone().length());
                        //网易云注册    usertype  : 0普通用户 1,明星
                        NetworkAPIFactoryImpl.getUserAPI().registerWangYi(0,info.getUserinfo().getPhone(), name, info.getUserinfo().getId(), new OnAPIListener<RegisterReturnWangYiBeen>() {
                            @Override
                            public void onError(Throwable ex) {
                                LogUtils.logd("网易云注册失败" + ex.toString());
                                ToastUtils.showShort("网易云注册失败");
                            }

                            @Override
                            public void onSuccess(RegisterReturnWangYiBeen registerReturnWangYiBeen) {
                                LogUtils.logd("网易云注册成功" + registerReturnWangYiBeen.getResult_value() + "网易云token" + registerReturnWangYiBeen.getToken_value());
                                loginWangYi(info, registerReturnWangYiBeen);
                            }
                        });


                    }
                } else {
                    LogUtils.loge("微信登录失败,进入绑定手机号界面");  //进入绑定手机号码页面
                    ToastUtils.showLong("微信授权成功,请绑定手机号码");
                    Intent intent = new Intent(WXEntryActivity.this, RegisterUserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    AppManager.getAppManager().finishActivity(LoginActivity.class);
                    //EventBus.getDefault().postSticky(new EventBusMessage(-6));  //传递消息
                    intent.putExtra("wxBind", entity2);
                    startActivity(intent);
                    WXEntryActivity.this.finish();
                }
            }
        });

    }

    private AbortableFuture<LoginInfo> loginRequest;

    private void loginWangYi(final WXinLoginReturnBeen loginReturnInfos, RegisterReturnWangYiBeen registerReturnWangYiBeen) {
        LogUtils.logd(loginReturnInfos.getUserinfo().getPhone() + "..." + registerReturnWangYiBeen.getToken_value());
        // 登录
        loginRequest = NimUIKit.doLogin(new LoginInfo(loginReturnInfos.getUserinfo().getPhone(), registerReturnWangYiBeen.getToken_value()), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtils.logd("网易云登录成功");
                NetworkAPIFactoryImpl.getUserAPI().saveDevice(loginReturnInfos.getUserinfo().getId(), new OnAPIListener<Object>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(Object o) {
                        LogUtils.logd("上传设备id和类型成功:" + o.toString());
                    }
                });
                DemoCache.setAccount(param.getAccount());
                saveLoginInfo(param.getAccount(), param.getToken());
                // 初始化消息提醒配置
                initNotificationConfig();
                // 构建缓存
                DataCacheManager.buildDataCacheAsync();
                LoginReturnInfo info = new LoginReturnInfo();
                info.setToken(loginReturnInfos.getToken());
                info.setUserinfo(loginReturnInfos.getUserinfo());
                info.setToken_time(loginReturnInfos.getToken_time());
                SharePrefUtil.getInstance().saveLoginUserInfo(info);
                SharePrefUtil.getInstance().putTokenTime(info.getToken_time());
                SharePrefUtil.getInstance().putLoginPhone(info.getUserinfo().getPhone());
                //EventBus.getDefault().postSticky(new EventBusMessage(-6));  //传递消息
                AppManager.getAppManager().finishActivity(LoginActivity.class);
                EventBus.getDefault().postSticky(new EventBusMessage(1));  //登录成功消息
                WXEntryActivity.this.finish();
            }

            @Override
            public void onFailed(int code) {
                if (code == 302 || code == 404) {
                    LogUtils.logd("网易云登录失败" + R.string.login_failed);
                } else {
                    LogUtils.logd("网易云登录失败" + code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                LogUtils.logd("网易云登录失败" + R.string.login_exception);
            }
        });
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
