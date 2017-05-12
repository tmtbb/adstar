package com.yundian.star.networkapi.http;


import com.yundian.star.networkapi.DealAPI;
import com.yundian.star.networkapi.NetworkAPIConfig;
import com.yundian.star.networkapi.NetworkAPIFactory;
import com.yundian.star.networkapi.UserAPI;

public class NetworkHttpAPIFactoryImpl implements NetworkAPIFactory {
    private NetworkAPIConfig config;
    private static NetworkHttpAPIFactoryImpl networkHttpAPIFactory = null;

    public static synchronized NetworkAPIFactory getInstance() {
        if (networkHttpAPIFactory == null) {
            networkHttpAPIFactory = new NetworkHttpAPIFactoryImpl();
        }
        return networkHttpAPIFactory;
    }

    private NetworkHttpAPIFactoryImpl() {

    }

    @Override
    public UserAPI getUserAPI() {
        return null;
    }

    @Override
    public DealAPI getDealAPI() {
        return null;
    }

    @Override
    public void initConfig(NetworkAPIConfig config) {
        this.config = config;
    }

    @Override
    public NetworkAPIConfig getConfig() {
        return config;
    }
//
//    @Override
//    public SocializeAPI getSocializeAPI() {
//        return socializeAPI;
//    }

}
