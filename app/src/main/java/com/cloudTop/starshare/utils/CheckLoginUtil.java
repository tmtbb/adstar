package com.cloudTop.starshare.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.cloudTop.starshare.ui.main.activity.LoginActivity;
import com.cloudTop.starshare.R;

/**
 * Created by Administrator on 2017/6/6.
 * 检查登录工具类
 */

public class CheckLoginUtil {
    public static boolean checkLogin(Activity activity){
            String phoneNum = SharePrefUtil.getInstance().getPhoneNum();
            String token = SharePrefUtil.getInstance().getToken();
            if (TextUtils.isEmpty(phoneNum)||TextUtils.isEmpty(token)) { // 第一次登录, 需要走登录流程
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.overridePendingTransition(R.anim.activity_open_down_in,0);
                return false ;
            }
            return true;
    }
}
