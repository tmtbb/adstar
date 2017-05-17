package com.yundian.star.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yundian.star.app.AppApplication;
import com.yundian.star.app.Constant;
import com.yundian.star.been.WXAccessTokenEntity;
import com.yundian.star.utils.HttpUrlConnectionUtil;
import com.yundian.star.utils.LogUtils;



/**
 * Created by Administrator on 2017/3/13.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

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
                        try {
                            sendRequest();     //拿到了微信返回的code,立马再去请求access_token
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    /*String str = (String) msg.obj;
                    final WXAccessTokenEntity entity = JSONEntityUtil.JSONToEntity(WXAccessTokenEntity.class, str);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestUserInfo(entity);
                        }
                    }).start();*/
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
        String response = HttpUrlConnectionUtil.httpGet(url2);
        if (response!=null){

        }else {
            LogUtils.logd("获取用户信息失败");
        }
        /*Request request = new Request.Builder()
                .url(url2)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.d("获取用户信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body2 = response.body();
                    String str2 = body2.string();
                    LogUtil.d("获取用户信息成功:" + str2);
                    WXUserInfoEntity entity2 = JSONEntityUtil.JSONToEntity(WXUserInfoEntity.class, str2);
                    bindPhoneNumber(entity2);   //根据用户信息,绑定手机号码
                }
            }
        });*/
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
        String response = HttpUrlConnectionUtil.httpGet(url);
        if (response!=null){
            LogUtils.logd(response);
        }else {
            LogUtils.logd("获取用户信息失败");
        }
        /*Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();   //请求失败    break  弹出吐司
                LogUtil.d("okhttp请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    String str = body.string();
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = str;
                    handler.sendMessage(msg);
                }
            }
        });*/
    }

    /*private void bindPhoneNumber(final WXUserInfoEntity entity2) {
        //请求微信登录
        NetworkAPIFactoryImpl.getUserAPI().wxLogin(entity2.getOpenid(), new OnAPIListener<LoginReturnEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();

                LogUtil.d("微信登录失败,进入绑定手机号界面");  //进入绑定手机号码页面
                ToastUtils.show(WXEntryActivity.this, "请绑定手机号码");
                Intent intent = new Intent(WXEntryActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wxBind", entity2);
                intent.putExtra("wx", bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSuccess(LoginReturnEntity loginReturnEntity) {
                LogUtil.d("微信登录成功,直接调用登录:" + loginReturnEntity.toString());   //成功登录,进入主页

                UserEntity en = new UserEntity();
                en.setBalance(loginReturnEntity.getUserinfo().getBalance());
                en.setId(loginReturnEntity.getUserinfo().getId());
                en.setToken(loginReturnEntity.getToken());
                en.setUserType(loginReturnEntity.getUserinfo().getType());
                en.setMobile(loginReturnEntity.getUserinfo().getPhone());
                UserManager.getInstance().saveUserEntity(en);
                UserManager.getInstance().setLogin(true);
                MyApplication.getApplication().onUserUpdate(true);

                EventBus.getDefault().postSticky(new EventBusMessage(-6));  //传递消息
                finish();
            }
        });
    }*/
}
