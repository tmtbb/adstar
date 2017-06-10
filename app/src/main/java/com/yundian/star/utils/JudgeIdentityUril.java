package com.yundian.star.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yundian.star.R;
import com.yundian.star.been.AssetDetailsBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.activity.IdentityAuthenticationActivity;
import com.yundian.star.ui.main.activity.RechargeActivity;
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
        } else if (isSetpwd == 1) {    //已经实名认证,但是没有设置交易密码   请求余额
        LogUtils.loge("已经实名认证,但是没有设置交易密码");
            requestBalance(context);
            return false;
        } else {
            return true;
        }
    }

    private static void requestBalance(final Context context) {
        NetworkAPIFactoryImpl.getDealAPI().balance(new OnAPIListener<AssetDetailsBean>() {
            @Override
            public void onSuccess(AssetDetailsBean bean) {
                LogUtils.loge("余额请求成功:" + bean.toString());
                SharePrefUtil.getInstance().saveAssetInfo(bean);
                if (bean.getIs_setpwd() == 1){   //没有设置交易密码
                    showOpenPayDialog(context);
                }else{
                    LogUtils.loge("当前是已经设置过了密码----------------------");
                    Intent intent = new Intent(context, RechargeActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("余额请求失败:" + ex.getMessage());
            }
        });
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
