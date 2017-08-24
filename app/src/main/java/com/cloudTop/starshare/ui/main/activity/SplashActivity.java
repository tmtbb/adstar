package com.cloudTop.starshare.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppApplication;
import com.cloudTop.starshare.app.AppConfig;
import com.cloudTop.starshare.been.MyAddressBean;
import com.cloudTop.starshare.been.QiNiuAdressBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.GetIPAddressUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.testin.agent.Bugout;
import com.testin.agent.BugoutConfig;

/**
 * Created by Administrator on 2017/5/5.
 */

public class SplashActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1 :
                    startNextAct();
                    break;
            }
        }


    };
    private void startNextAct() {
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.act_in_from_right, R.anim.act_out_from_left);
        finish();
    }



    public void initView() {
      Bugout.init(this, "1664ea921dcbe122834e440f7f584e2e", "yingyongbao");
      initBugOut();
        mHandler.sendEmptyMessageDelayed(1,2000);


        new Thread(new Runnable() {
            @Override
            public void run() {
                final MyAddressBean ipAddress = GetIPAddressUtils.getIpAddress();
                if (ipAddress.getData()==null){
                    return;
                }
                AppConfig.AREA_ID = Long.valueOf(ipAddress.getData().getArea_id());
                AppConfig.AREA = ipAddress.getData().getArea();
                AppConfig.ISP_ID = Long.valueOf(ipAddress.getData().getIsp_id());
                AppConfig.ISP = ipAddress.getData().getIsp();
                NetworkAPIFactoryImpl.getUserAPI().getQiNiuPicDress(new OnAPIListener<QiNiuAdressBean>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(QiNiuAdressBean o) {
                        LogUtils.loge("ysl_七牛"+o.toString());
                        String area = ipAddress.getData().getArea();
                        if ("华东".equals(area)&& !TextUtils.isEmpty(o.getQINIU_URL_HUADONG())){
                            AppConfig.QI_NIU_PIC_ADRESS = o.getQINIU_URL_HUADONG();
                            LogUtils.loge("ysl_七牛"+"华东");
                        }else if ("华北".equals(area)&& !TextUtils.isEmpty(o.getQINIU_URL_HUABEI())){
                            AppConfig.QI_NIU_PIC_ADRESS = o.getQINIU_URL_HUABEI();
                            LogUtils.loge("ysl_七牛"+"华北");
                        }else if ("华南".equals(area)&& !TextUtils.isEmpty(o.getQINIU_URL_HUANAN())){
                            AppConfig.QI_NIU_PIC_ADRESS = o.getQINIU_URL_HUANAN();
                            LogUtils.loge("ysl_七牛"+"华南");
                        }
                    }
                });
            }
        }).start();
    }

    private void initBugOut() {
        BugoutConfig config = new BugoutConfig.Builder(this)
                .withAppKey("1664ea921dcbe122834e440f7f584e2e")     // 您的应用的项目ID,如果已经在 Manifest 中配置则此处可略
                //  .withAppChannel(cnl)     // 发布应用的渠道,如果已经在 Manifest 中配置则此处可略
                .withUserInfo(AppApplication.getAndroidId())    // 用户信息-崩溃分析根据用户记录崩溃信息
                .withDebugModel(true)    // 输出更多SDK的debug信息
                .withErrorActivity(true)    // 发生崩溃时采集Activity信息
                .withCollectNDKCrash(true) //  收集NDK崩溃信息
                .withOpenCrash(true)    // 收集崩溃信息开关
                .withOpenEx(true)     // 是否收集异常信息
                .withReportOnlyWifi(true)    // 仅在 WiFi 下上报崩溃信息
                .withReportOnBack(true)    // 当APP在后台运行时,是否采集信息
                .withQAMaster(true)    // 是否收集摇一摇反馈
                .withCloseOption(false)   // 是否在摇一摇菜单展示‘关闭摇一摇选项’
                .withLogCat(true)  // 是否系统操作信息
                .build();
        Bugout.init(config);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //* 注：回调 1
       Bugout.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //* 注：回调 2
      Bugout.onPause(this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //* 注：回调 3
      Bugout.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

}
