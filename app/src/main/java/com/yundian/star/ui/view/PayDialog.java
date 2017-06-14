package com.yundian.star.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.yundian.star.R;


/**
 * 支付密码弹窗
 */

public class
PayDialog extends BaseDialog {

    private PayPwdEditText payPwdEditText;

    public PayDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_dialog_lyaout);
        payPwdEditText = (PayPwdEditText) findViewById(R.id.ppet);

        payPwdEditText.initStyle(R.drawable.edit_num_bg_red, 6, 0.33f, R.color.colorAccent, R.color.colorAccent, 20);
        payPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChange(String str) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                payPwdEditText.setFocus();
            }
        }, 100);

    }
}
