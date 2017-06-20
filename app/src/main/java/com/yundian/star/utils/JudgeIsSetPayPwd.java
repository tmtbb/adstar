package com.yundian.star.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yundian.star.R;
import com.yundian.star.ui.main.activity.SettingDealPwdActivity;

/**
 * 校验是否设置支付密码
 * Created by sll on 2017/6/20.
 */

public class JudgeIsSetPayPwd {

    public static boolean isSetPwd(Context context) {
        int isSetpwd = SharePrefUtil.getInstance().getIsSetpwd();

        if (isSetpwd == 1) {    //没有设置交易密码
            LogUtils.loge("没有设置交易密码");
            showOpenPayDialog(context);
            return false;
        } else {
            return true;
        }
    }

//    private static void requestBalance(final Context context) {
//        NetworkAPIFactoryImpl.getDealAPI().balance(new OnAPIListener<AssetDetailsBean>() {
//            @Override
//            public void onSuccess(AssetDetailsBean bean) {
//                LogUtils.loge("余额请求成功:" + bean.toString());
//                SharePrefUtil.getInstance().saveAssetInfo(bean);
//                if (bean.getIs_setpwd() == 1) {   //没有设置交易密码
//                    showOpenPayDialog(context);
//                } else {
//                    LogUtils.loge("当前是已经设置过了密码----------------------");
//                    Intent intent = new Intent(context, RechargeActivity.class);
//                    context.startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex) {
//                LogUtils.loge("余额请求失败:" + ex.getMessage());
//            }
//        });
//    }

    //设置交易密码
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
