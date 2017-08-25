package com.cloudTop.starshare.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cloudTop.starshare.ui.main.activity.IdentityAuthenticationActivity;
import com.cloudTop.starshare.R;


/**
 * 检验是否实名认证
 * Created by sll on 2017/6/5.
 */

public class JudgeIdentityUtils {

    public static boolean isIdentityed(Context context) {
        String realName = SharePrefUtil.getInstance().getRealName();
        String idnum = SharePrefUtil.getInstance().getIdnum();

        if (TextUtils.isEmpty(realName) || TextUtils.isEmpty(idnum)) {
            showIdentityDialog(context);   //没有实名认证
            return false;
        } else {
            return true;
        }
    }

    //进行实名认证
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
}
