package com.yundian.star.ui.main.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.AssetDetailsBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.im.activity.SystemMessagesActivity;
import com.yundian.star.ui.view.PayDialog;
import com.yundian.star.utils.JudgeIsSetPayPwd;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用户资产管理
 * Created by Administrator on 2017/5/23.
 */

public class UserAssetsManageActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.ll_recharge)
    LinearLayout recharge;
    @Bind(R.id.ll_user_fudai)
    LinearLayout fudai;
    @Bind(R.id.parent_view)
    LinearLayout parentView;
    @Bind(R.id.star_money)
    TextView starMoney;
    @Bind(R.id.hold_money)
    TextView holdMoney;
    @Bind(R.id.user_money)
    TextView userMoney;
    private PopupWindow popupWindow;
    private PayDialog payDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_assets_manage;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.user_asset_manage));
        ntTitle.setBackVisibility(true);
        ntTitle.setTitleColor(Color.rgb(255, 255, 255));
        ntTitle.setBackGroundColor(Color.rgb(251, 153, 56));
        ntTitle.setRightImagSrc(R.drawable.money_bag_more);
        payDialog = new PayDialog(this);
        showPopupWindow();
        ntTitle.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(ntTitle.getRightImage(), 0, 0);
//                popupWindow.showAtLocation(ntTitle.getRightImage(),  Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    private void showPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.popup_money_bag, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
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
        contentview.findViewById(R.id.tv_money_detail).setOnClickListener(this);
        contentview.findViewById(R.id.tv_bank_info).setOnClickListener(this);

    }


    @OnClick({R.id.ll_recharge, R.id.ll_user_fudai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_recharge:
                ToastUtils.showShort("充值");
//                if (JudgeIdentityUtils.isIdentityed(this)) {
//                    startActivity(RechargeActivity.class);
//                }

                if (JudgeIsSetPayPwd.isSetPwd(this)){  //检验是否设置交易密码
                    startActivity(RechargeActivity.class);
                }
                break;
            case R.id.ll_user_fudai:
                LogUtils.loge("点击福袋；；");
//                testNotify();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_money_detail:
                startActivity(MoneyBagDetailActivity.class);
                popupWindow.dismiss();
                break;
            case R.id.tv_bank_info:
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestBalance();
    }

    private void requestBalance() {
        NetworkAPIFactoryImpl.getDealAPI().balance(new OnAPIListener<AssetDetailsBean>() {
            @Override
            public void onSuccess(AssetDetailsBean bean) {
                starMoney.setText(bean.getBalance() + "");
                holdMoney.setText(bean.getBalance() + "");
                userMoney.setText(bean.getBalance() + "");
                if ( bean.getIs_setpwd() != -100) {
                    SharePrefUtil.getInstance().saveAssetInfo(bean);
                }
                if (!TextUtils.isEmpty(bean.getHead_url()) && !TextUtils.isEmpty(bean.getNick_name())){
                    SharePrefUtil.getInstance().putUserNickName(bean.getNick_name());
                    SharePrefUtil.getInstance().putUserPhotoUrl(bean.getHead_url());
                }
            }

            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("余额请求失败:" + ex.getMessage());
            }
        });
    }

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private void testNotify(){
        mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        Notification notification = mBuilder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        mBuilder.build().defaults = Notification.DEFAULT_ALL;
        mBuilder.setContentTitle("交易")//设置通知栏标题
                .setContentText("测试内容")   // /<span style="font-family: Arial;">/设置通知栏显示内容</span>
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
                .setFullScreenIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL), false)
//  .setNumber(10) //设置通知集合的数量
                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_MAX) //设置该通知优先级
                //  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
        mNotificationManager.notify(100, mBuilder.build());
    }

    public PendingIntent getDefalutIntent(int flags) {
        Intent intent = new Intent(this, SystemMessagesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, flags);
        return pendingIntent;
    }
}
