package com.yundian.star.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yundian.star.app.AppApplication;

import java.util.List;
import java.util.Map;

/**
 * Created by dawuhan-0001 on 2016/11/22.
 */

public class ThirdPartLoginControler {
    private static final String TAG = "ThirdPartLoginControler";

    private static ThirdLoginCallback sCallback;

    public interface ThirdLoginCallback {
        public void thirdLoginNext(String uid, String openid, String screenName, String profileImageUrl, String gender, String loginType, String accessToken);
    }

    private static ThirdPartLoginControler sIntance;

    private ThirdPartLoginControler() {
    }

    private static Activity sActivity;

    public static void init(Activity act, ThirdLoginCallback callback) {
        sActivity = act;
        sCallback = callback;
        sShareAPI = UMShareAPI.get(act);

        // 每次授权都想调起授权页
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        sShareAPI.setShareConfig(config);
    }

    public static ThirdPartLoginControler getIntance() {
        if (sIntance == null) {
            sIntance = new ThirdPartLoginControler();
        }
        return sIntance;
    }

    public static boolean isInstall(SHARE_MEDIA platform, boolean isShowTip) {
        String name = platform.name();
        ;
        String packageName = "";
        if (SHARE_MEDIA.WEIXIN == platform || SHARE_MEDIA.WEIXIN_CIRCLE == platform) {
            packageName = "com.tencent.mm";
            name = "微信";
        } else if (SHARE_MEDIA.SINA == platform) {
            packageName = "com.sina.weibo";
            name = "新浪";
        } else if (SHARE_MEDIA.QQ == platform || SHARE_MEDIA.QZONE == platform) {
            packageName = "com.tencent.mobileqq";
            name = "QQ";
        }

        PackageManager packageManager = AppApplication.getAppContext().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }

        if (isShowTip) ToastUtils.showShort("未能在设备上检测到应用程序");
        return false;
    }

    public void doOauthVerify(SHARE_MEDIA platform) {
        if (isInstall(platform, true)) {
            if (SHARE_MEDIA.WEIXIN == platform) {
                sShareAPI.doOauthVerify(sActivity, platform, mUMAuthListener);
            } else {
                if (sShareAPI.isSupport(sActivity, platform)) {
                    sShareAPI.doOauthVerify(sActivity, platform, mUMAuthListener);
                } else {
                    ToastUtils.showShort("对不起，我们暂时无法唤起应用");
                }
            }
        }
    }

    /**
     * qq授权防杀死
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void qqFaildonActivityResult(int requestCode, int resultCode, Intent data) {
//        UMShareAPI.get(sActivity).HandleQQError(sActivity, requestCode, mUMAuthListener);
        sShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    private static UMShareAPI sShareAPI;
    private UMAuthListener mUMGetUserInfoListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String loginType = "";
            if (SHARE_MEDIA.WEIXIN == platform) {
                loginType = "2";
                LogUtils.logd("WEIXIN Authorize succeed; action:" + action + "; msg:");
            } else if (SHARE_MEDIA.SINA == platform) {
                loginType = "3";
                LogUtils.logd("SINA Authorize succeed; action:" + action + "; msg:");
            } else if (SHARE_MEDIA.QQ == platform) {
                loginType = "1";
                LogUtils.loge(TAG, "QQ Authorize succeed; action:" + action + "; msg:");
            }

            String uid = "";
            String openid = "";
            String screenName = "";
            String profileImageUrl = "";
            String gender = "-1";
            String access_token = "";
            for (Map.Entry<String, String> entry : data.entrySet()) {
                LogUtils.logd(TAG, "key:" + entry.getKey() + ", value:" + entry.getValue());
                if (TextUtils.equals("uid", entry.getKey())) {
                    uid = entry.getValue();
                } else if (TextUtils.equals("id", entry.getKey()) || TextUtils.equals("openid", entry.getKey())) {
                    openid = entry.getValue();
                } else if (TextUtils.equals("name", entry.getKey())) {
                    screenName = entry.getValue();
                } else if (TextUtils.equals("iconurl", entry.getKey())) {
                    profileImageUrl = entry.getValue();
                } else if (TextUtils.equals("gender", entry.getKey())) {
                    String sex = entry.getValue();
                    if (TextUtils.equals("男", sex)) {
                        gender = "0";
                    } else if (TextUtils.equals("女", sex)) {
                        gender = "1";
                    } else {
                        gender = "-1";
                    }
                } else if (TextUtils.equals("accessToken", entry.getKey())) {
                    access_token = entry.getValue();
                }
            }
            // 获取到用户信息后，上传服务器注册
            // 注册成功后，返回登录流程，作进一步的处理
            if (sCallback != null)
                sCallback.thirdLoginNext(uid, openid, screenName, profileImageUrl, gender, loginType, access_token);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (SHARE_MEDIA.WEIXIN == platform) {
                LogUtils.logd(TAG, "WEIXIN Authorize fail; action:" + action + "; msg:" + t.getMessage());
                ToastUtils.showShort("微信登录失败");
            } else if (SHARE_MEDIA.SINA == platform) {
                LogUtils.logd(TAG, "SINA Authorize fail; action:" + action + "; msg:" + t.getMessage());
                ToastUtils.showShort("新浪微博登录失败");
            } else if (SHARE_MEDIA.QQ == platform) {
                LogUtils.logd(TAG, "QQ Authorize fail; action:" + action + "; msg:" + t.getMessage());
                ToastUtils.showShort("QQ登录失败");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (SHARE_MEDIA.WEIXIN == platform) {
                LogUtils.logd(TAG, "WEIXIN Authorize cancel");
                ToastUtils.showShort("微信登录取消, 请重新登录");
            } else if (SHARE_MEDIA.SINA == platform) {
                LogUtils.logd(TAG, "SINA Authorize cancel");
                ToastUtils.showShort("新浪微博登录取消, 请重新登录");
            } else if (SHARE_MEDIA.QQ == platform) {
                LogUtils.logd(TAG, "QQ Authorize cancel; action:" + action);
                ToastUtils.showShort("QQ登录取消, 请重新登录");
            }
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    private UMAuthListener mUMAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (SHARE_MEDIA.WEIXIN == platform) {
                LogUtils.logd(TAG, "WEIXIN Authorize succeed; action:" + action + "; msg:");
                //ToastUtil.showSingletonToast("微信授权成功");
            } else if (SHARE_MEDIA.SINA == platform) {
                LogUtils.logd(TAG, "SINA Authorize succeed; action:" + action + "; msg:");
                //ToastUtil.showSingletonToast("新浪微博授权成功");
            } else if (SHARE_MEDIA.QQ == platform) {
                LogUtils.logd(TAG, "QQ Authorize succeed; action:" + action + "; msg:");
                //ToastUtil.showSingletonToast("QQ授权成功");
            }
            sShareAPI.getPlatformInfo(sActivity, platform, mUMGetUserInfoListener);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (SHARE_MEDIA.WEIXIN == platform) {
                LogUtils.logd(TAG, "WEIXIN Authorize fail; action:" + action + "; msg:" + t.getMessage());
                ToastUtils.showShort("微信授权失败, 请重试");
            } else if (SHARE_MEDIA.SINA == platform) {
                LogUtils.logd(TAG, "SINA Authorize fail; action:" + action + "; msg:" + t.getMessage());
                ToastUtils.showShort("微博授权失败, 请重试");
            } else if (SHARE_MEDIA.QQ == platform) {
                LogUtils.logd(TAG, "QQ Authorize fail; action:" + action + "; msg:" + t.getMessage());
                ToastUtils.showShort("QQ授权失败, 请重试");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (SHARE_MEDIA.WEIXIN == platform) {
                LogUtils.logd(TAG, "WEIXIN Authorize cancel");
                ToastUtils.showShort("微信授权取消");
            } else if (SHARE_MEDIA.SINA == platform) {
                LogUtils.logd(TAG, "SINA Authorize cancel");
                ToastUtils.showShort("新浪微博授权取消");
            } else if (SHARE_MEDIA.QQ == platform) {
                LogUtils.logd(TAG, "QQ Authorize cancel; action:" + action);
                ToastUtils.showShort("QQ授权取消");
            }
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

}
