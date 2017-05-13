package com.yundian.star.networkapi.socketapi;


import com.yundian.star.networkapi.DealAPI;
import com.yundian.star.networkapi.InformationAPI;
import com.yundian.star.networkapi.NetworkAPIConfig;
import com.yundian.star.networkapi.NetworkAPIFactory;
import com.yundian.star.networkapi.UserAPI;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketAPIRequestManage;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketAPIFactoryImpl implements NetworkAPIFactory {

    private NetworkAPIConfig config;

    private static SocketAPIFactoryImpl socketAPIFactory = null;

    public static synchronized NetworkAPIFactory getInstance() {
        if (socketAPIFactory == null) {
            socketAPIFactory = new SocketAPIFactoryImpl();
        }
        return socketAPIFactory;
    }

    @Override
    public void initConfig(NetworkAPIConfig config) {
        this.config = config;
        SocketAPIRequestManage.getInstance().start();
    }

    @Override
    public NetworkAPIConfig getConfig() {
        return config;
    }

    @Override
    public UserAPI getUserAPI() {
        return new SocketUserAPI();
    }

    @Override
    public DealAPI getDealAPI() {
        return new SocketDealAPI();
    }

    @Override
    public InformationAPI getInformationAPI() {
        return new SocketInformationAPI();
    }

}
