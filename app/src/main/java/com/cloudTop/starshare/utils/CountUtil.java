package com.cloudTop.starshare.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


public class CountUtil {

    private CountDownTimer countDownTimer;
    private TextView button;

    public CountUtil(final TextView button) {
        this(button, "");
    }

    public CountUtil(TextView button, String desc) {
        this(button, desc, false);
    }

    public CountUtil(final TextView button, final String desc, final boolean customColor) {
        this.button = button;
        countDownTimer = new CountDownTimer(60 * 1000, 1 * 1000 - 10) {

            @Override
            public void onTick(long time) {
                button.setVisibility(View.VISIBLE);
                String tempDesc = desc;
                if (TextUtils.isEmpty(tempDesc)) {
                    tempDesc = "S后重发";
                }
                button.setText(((time + 15) / 1000) + tempDesc);
                //button.setBackgroundResource(R.drawable.login_shape_unenable);
                if (!customColor) {
                    button.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText("发送验证码");
                // button.setBackgroundResource(R.drawable.login_selector);
                if (!customColor) {
                    button.setTextColor(Color.WHITE);
                }
            }
        };
    }


    public void start() {
        button.setEnabled(false);
        countDownTimer.start();
    }

}
