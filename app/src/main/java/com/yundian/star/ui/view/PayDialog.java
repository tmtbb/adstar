package com.yundian.star.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yundian.star.R;
import com.yundian.star.app.Constant;
import com.yundian.star.ui.main.activity.ResetPayPwdActivity;
import com.yundian.star.utils.LogUtils;


/**
 * 支付密码弹窗
 */

public class PayDialog extends BaseDialog {

    private PayPwdEditText payPwdEditText;
    private Context context;
    private TextView resetPwd;

    public PayDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_dialog_lyaout);
        payPwdEditText = (PayPwdEditText) findViewById(R.id.ppet);
        resetPwd = (TextView) findViewById(R.id.tv_reset_pwd);

        payPwdEditText.initStyle(R.drawable.edit_num_bg_red, 6, 0.33f, R.color.colorAccent, R.color.colorAccent, 20);
        payPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
//                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                //校验支付密码
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

        resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.loge("重新设置交易密码---------------------");
                Bundle bundle4 = new Bundle();
                bundle4.putString("resetPwd", Constant.PAY_PWD);

                Intent intent = new Intent(context, ResetPayPwdActivity.class);
                intent.putExtras(bundle4);
                context.startActivity(intent);
                payPwdEditText.clearText();
            }
        });
    }
}
