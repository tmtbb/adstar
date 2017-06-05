package com.yundian.star.ui.main.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yundian.star.R;
import com.yundian.star.alipay.PayResult;
import com.yundian.star.app.AppApplication;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.EventBusMessage;
import com.yundian.star.been.WXPayReturnEntity;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.view.CustomerRadioGroup;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


import static com.yundian.star.R.id.back;
import static com.yundian.star.R.id.iv_recharge_type;
import static com.yundian.star.R.id.rb_recharge_money1;
import static com.yundian.star.R.id.rb_recharge_money2;

/**
 * Created by sll on 2017/5/23.
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.iv_bank_sign)
    RoundImageView ivBankSign;
    @Bind(R.id.tv_bank_name)
    TextView tvBankName;
    @Bind(R.id.choose_recharge_type)
    RelativeLayout chooseRechargeType;
    @Bind(rb_recharge_money1)
    RadioButton rbRechargeMoney1;
    @Bind(rb_recharge_money2)
    RadioButton rbRechargeMoney2;
    @Bind(R.id.rb_recharge_money3)
    RadioButton rbRechargeMoney3;
    @Bind(R.id.rb_recharge_money4)
    RadioButton rbRechargeMoney4;
    @Bind(R.id.rb_recharge_money5)
    RadioButton rbRechargeMoney5;
    @Bind(R.id.rb_recharge_money6)
    RadioButton rbRechargeMoney6;
    @Bind(R.id.btn_recharge_sure)
    Button btnRechargeSure;
    @Bind(R.id.ll_user_recharge_type)
    LinearLayout rechargeType;
    @Bind(R.id.tv_recharge_name)
    TextView rechargeName;
    @Bind(iv_recharge_type)
    ImageView rechargeIcon;
    @Bind(R.id.edit_recharge_money)
    EditText rechargeMoney;
    @Bind(R.id.rg_recharge_type)
    CustomerRadioGroup rgRechargeType;
    private WXPayReturnEntity wxPayEntity;
    private boolean flag = true;
    private PopupWindow popupWindow;
    private boolean isAliPay = false;
    private double price = 1;

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.user_recharge));
        ntTitle.setBackVisibility(true);
//        rechargeMoney.setInputType(InputType.TYPE_CLASS_NUMBER);  //输入整数
        rechargeMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);  //输入小数
        if (flag) {
            EventBus.getDefault().register(this);
            flag = false;
        }
        rbRechargeMoney1.setChecked(true);

        initPopupWindow();
        initListener();
    }

    private void initListener() {
        rgRechargeType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                rechargeMoney.setText("");
                switch (checkedId) {
                    case R.id.rb_recharge_money1:
                        price = 1;
                        rbRechargeMoney1.setChecked(true);
                        break;
                    case R.id.rb_recharge_money2:
                        rbRechargeMoney2.setChecked(true);
                        price = 10;
                        break;
                    case R.id.rb_recharge_money3:
                        rbRechargeMoney3.setChecked(true);
                        price = 100;
                        break;
                    case R.id.rb_recharge_money4:
                        rbRechargeMoney4.setChecked(true);
                        price = 1000;
                        break;
                    case R.id.rb_recharge_money5:
                        rbRechargeMoney5.setChecked(true);
                        price = 10000;
                        break;
                    case R.id.rb_recharge_money6:
                        rbRechargeMoney6.setChecked(true);
                        price = 50000;
                        break;
                }
                rechargeMoney.setText(price+"");
            }
        });
        rechargeMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    if (getCheckedRadioButton() != null) {
                        getCheckedRadioButton().setChecked(false);
                    }
                } else {
                    rbRechargeMoney1.setChecked(true);
                }
            }
        });
    }

    private RadioButton getCheckedRadioButton() {
        if (rbRechargeMoney1.isChecked()) {
            return rbRechargeMoney1;
        } else if (rbRechargeMoney2.isChecked()) {
            return rbRechargeMoney2;
        } else if (rbRechargeMoney3.isChecked()) {
            return rbRechargeMoney3;
        } else if (rbRechargeMoney4.isChecked()) {
            return rbRechargeMoney4;
        } else if (rbRechargeMoney5.isChecked()) {
            return rbRechargeMoney5;
        } else if (rbRechargeMoney6.isChecked()) {
            return rbRechargeMoney6;
        } else {
            return null;
        }
    }

    @OnClick({R.id.btn_recharge_sure, R.id.ll_user_recharge_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge_sure:
                applyRecharge();
                break;
            case R.id.ll_user_recharge_type:  //选择充值方式
                popupWindow.showAtLocation(findViewById(R.id.ll_recharge_ui), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    private void applyRecharge() {
        ToastUtils.showShort("充值");
        String title = "星享-充值";
        if (!TextUtils.isEmpty(rechargeMoney.getEditableText().toString().trim())) {
            price = Double.parseDouble(rechargeMoney.getEditableText().toString().trim());
        }

        if (isAliPay) {
            requestAliPay();
        } else {
            requestWXPay(title, price);
        }
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.layout_dialog_recharge_type, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        contentview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        contentview.findViewById(R.id.ll_recharge_alipay).setOnClickListener(this);
        contentview.findViewById(R.id.ll_recharge_weixin).setOnClickListener(this);
        contentview.findViewById(R.id.iv_pay_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_recharge_alipay:
                ToastUtils.showShort("支付宝");
                rechargeIcon.setImageResource(R.drawable.icon_alipay);
                rechargeName.setText(getString(R.string.recharge_ali_pay));
                isAliPay = true;
                //设置图标
                popupWindow.dismiss();
                break;
            case R.id.ll_recharge_weixin:
                isAliPay = false;
                rechargeIcon.setImageResource(R.drawable.icon_weixin);
                rechargeName.setText(getString(R.string.recharge_wxpay));
                popupWindow.dismiss();
                break;
            case R.id.iv_pay_close:
                popupWindow.dismiss();
                break;
        }
    }

    private void requestAliPay() {
        ToastUtils.showShort("请求支付宝支付");
        final String orderInfo = "";   // 订单信息   请求服务端返回payinfo
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                String result = alipay.pay(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);    // 必须异步调用
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    /**
     * 请求微信支付
     *
     * @param title title
     * @param price 金额
     */
    private void requestWXPay(String title, double price) {
        if (!AppApplication.api.isWXAppInstalled()) {
            ToastUtils.showShort("您还未安装微信客户端");
            return;
        }
        NetworkAPIFactoryImpl.getDealAPI().weixinPay(title, price, new OnAPIListener<WXPayReturnEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtils.logd("微信支付调用失败");
            }

            @Override
            public void onSuccess(WXPayReturnEntity wxPayReturnEntity) {
                LogUtils.logd("微信支付调用成功 :" + wxPayReturnEntity.toString());
                wxPayEntity = wxPayReturnEntity;
                PayReq request = new PayReq();
                request.appId = wxPayReturnEntity.getAppid();
                request.partnerId = wxPayReturnEntity.getPartnerid();
                request.prepayId = wxPayReturnEntity.getPrepayid();
//                    request.packageValue = wxPayReturnEntity.getPackage();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = wxPayReturnEntity.getNoncestr();
                request.timeStamp = wxPayReturnEntity.getTimestamp();
                request.sign = wxPayReturnEntity.getSign();
                AppApplication.api.sendReq(request);
            }
        });
    }

    /**
     * EventBus接收消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessage(EventBusMessage eventBusMessage) {
        switch (eventBusMessage.Message) {
            case 0:  //成功
                ToastUtils.showShort("支付成功:" + eventBusMessage.Message);       //1-成功 2-取消支付
                LogUtils.logd("支付成功,更新余额,进入充值列表");
                break;
            case -2:  //取消支付
                showDialogTip();
                ToastUtils.showShort("用户取消支付:" + eventBusMessage.Message);
                break;
//            default:
//                ToastUtils.showShort("接收到的信息:" + eventBusMessage.Message);
//                break;
        }
    }

    private void showDialogTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认退出登录吗？")
                .setTitle("提示")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(MoneyBagDetailActivity.class);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
