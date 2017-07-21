package com.yundian.star.ui.wangyi.config.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.yundian.star.ui.wangyi.DemoCache;


/**
 * Created by hzxuwen on 2015/4/13.
 */
public class Preferences {
    private static final String KEY_USER_ACCOUNT = "account";
    private static final String KEY_USER_TOKEN = "token";

    public static void saveUserAccount(String account) {
        saveString(KEY_USER_ACCOUNT, account);
    }

    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }

    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    static SharedPreferences getSharedPreferences() {
        if (DemoCache.getContext()!=null){
            return DemoCache.getContext().getSharedPreferences("Demo", Context.MODE_PRIVATE);
        }else {
            return null;
        }
    }
}
