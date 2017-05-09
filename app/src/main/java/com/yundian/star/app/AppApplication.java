package com.yundian.star.app;

import com.yundian.star.BuildConfig;
import com.yundian.star.base.baseapp.BaseApplication;
import com.yundian.star.networkapi.Host;
import com.yundian.star.networkapi.NetworkAPIConfig;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.MD5Util;
import com.yundian.star.utils.Utils;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
        initNetworkAPIConfig();
        //checkToken();
    }

    private void checkToken() {
        SocketAPINettyBootstrap.getInstance().setOnConnectListener(new SocketAPINettyBootstrap.OnConnectListener() {
            @Override
            public void onExist() {
            }

            @Override
            public void onSuccess() {
                LogUtils.logd("检测到连接成功-------------------");
                //judgeIsLogin();
            }

            @Override
            public void onFailure(boolean tag) {
                LogUtils.logd("检测到连接失败--------------");
                if (tag){
                   // connectionError();
                }

            }
        });
    }

    private void initNetworkAPIConfig() {
        NetworkAPIConfig networkAPIConfig = new NetworkAPIConfig();
        networkAPIConfig.setContext(getApplicationContext());
        networkAPIConfig.setSocketServerIp(Host.getSocketServerIp());
        networkAPIConfig.setSocketServerPort(Host.getSocketServerPort());
        NetworkAPIFactoryImpl.initConfig(networkAPIConfig);
    }
    public static String getAndroidId() {
        return MD5Util.MD5(Utils.getUniquePsuedoID());
    }
}
