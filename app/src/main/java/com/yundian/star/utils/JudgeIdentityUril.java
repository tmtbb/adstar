package com.yundian.star.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yundian.star.R;
import com.yundian.star.ui.main.activity.IdentityAuthenticationActivity;
import com.yundian.star.ui.main.activity.SettingDealPwdActivity;


/**
 * Created by sll on 2017/6/5.
 */

public class JudgeIdentityUril {

    public static boolean isIdentityed(Context context) {
        String realName = SharePrefUtil.getInstance().getRealName();
        String idnum = SharePrefUtil.getInstance().getIdnum();
        int isSetpwd = SharePrefUtil.getInstance().getIsSetpwd();

        if (TextUtils.isEmpty(realName) || TextUtils.isEmpty(idnum)) {
            showIdentityDialog(context);   //没有实名认证
            return false;
        } else if (isSetpwd == 0) {    //已经实名认证,但是没有设置交易密码
            showOpenPayDialog(context);
            return false;
        } else {
            return true;
        }
    }

    private static void showIdentityDialog(final Context context) {
        final Dialog mDetailDialog = new Dialog(context, R.style.custom_dialog);
        mDetailDialog.setContentView(R.layout.dialog_identity_authentivation);
        final Button startIdentity = (Button) mDetailDialog.findViewById(R.id.btn_start_identity);
        ImageView closeImg = (ImageView) mDetailDialog.findViewById(R.id.iv_dialog_close);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.setCancelable(false);
        startIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logd("进入身份认证页面-----");
                Intent intent = new Intent(context, IdentityAuthenticationActivity.class);
                context.startActivity(intent);
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.show();
    }

    private static void showOpenPayDialog(final Context context) {
        final Dialog mDetailDialog = new Dialog(context, R.style.custom_dialog);
        mDetailDialog.setContentView(R.layout.dialog_open_pay);
        final Button startIdentity = (Button) mDetailDialog.findViewById(R.id.btn_start_identity2);
        ImageView closeImg = (ImageView) mDetailDialog.findViewById(R.id.iv_dialog_close2);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.setCancelable(false);
        startIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logd("进入输入密码-----");
                Intent intent = new Intent(context, SettingDealPwdActivity.class);
                context.startActivity(intent);
                mDetailDialog.dismiss();
            }
        });

        mDetailDialog.show();
    }
}
