package com.yundian.star.ui.main.activity;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yundian.star.R;
import com.yundian.star.app.AppApplication;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.EventBusMessage;
import com.yundian.star.been.WXPayReturnEntity;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by sll on 2017/5/23.
 */

public class RechargeActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.iv_bank_sign)
    RoundImageView ivBankSign;
    @Bind(R.id.tv_bank_name)
    TextView tvBankName;
    @Bind(R.id.choose_recharge_type)
    RelativeLayout chooseRechargeType;
    @Bind(R.id.iv_recharge_sign)
    RoundImageView ivRechargeSign;
    @Bind(R.id.iv_recharge_wx)
    RoundImageView ivRechargeWx;
    @Bind(R.id.rb_recharge_money1)
    RadioButton rbRechargeMoney1;
    @Bind(R.id.rb_recharge_money2)
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
    private WXPayReturnEntity wxPayEntity;
    private boolean flag = true;

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

        if (flag) {
            EventBus.getDefault().register(this);
            flag = false;
        }
    }

    @OnClick({R.id.btn_recharge_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge_sure:
                ToastUtils.showShort("充值");
                String title = "微盘-余额充值";
                int price = 10;
                requestWXPay(title, price);
                break;
        }
    }

    /**
     * 请求微信支付
     *
     * @param title title
     * @param price 金额
     */
    private void requestWXPay(String title, double price) {
        NetworkAPIFactoryImpl.getDealAPI().weixinPay(title, price, new OnAPIListener<WXPayReturnEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(WXPayReturnEntity wxPayReturnEntity) {
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
                ToastUtils.showShort("支付成功");       //1-成功 2-取消支付
                LogUtils.logd("支付成功,更新余额,进入充值列表");
                break;
            case -2:  //取消支付
                ToastUtils.showShort("用户取消支付");
                break;

            case -10:  //取消支付
                ToastUtils.showShort("用户取消支付-10");
                break;
        }
    }

}
