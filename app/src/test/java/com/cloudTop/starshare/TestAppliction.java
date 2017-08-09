package com.cloudTop.starshare;

import android.app.Application;

import com.cloudTop.starshare.networkapi.Host;
import com.cloudTop.starshare.networkapi.NetworkAPIConfig;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;

/**
 * Created by Administrator on 2017/7/11.
 */

public class TestAppliction extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        initNetworkAPIConfig();
    }
    private void initNetworkAPIConfig() {
        NetworkAPIConfig networkAPIConfig = new NetworkAPIConfig();
        networkAPIConfig.setContext(getApplicationContext());
        networkAPIConfig.setSocketServerIp(Host.getSocketServerIp());
        networkAPIConfig.setSocketServerPort(Host.getSocketServerPort());
        NetworkAPIFactoryImpl.initConfig(networkAPIConfig);
    }
}
