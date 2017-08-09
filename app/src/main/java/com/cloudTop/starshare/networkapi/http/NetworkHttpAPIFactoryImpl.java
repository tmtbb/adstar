package com.cloudTop.starshare.networkapi.http;


import com.cloudTop.starshare.networkapi.DealAPI;
import com.cloudTop.starshare.networkapi.InformationAPI;
import com.cloudTop.starshare.networkapi.NetworkAPIConfig;
import com.cloudTop.starshare.networkapi.NetworkAPIFactory;
import com.cloudTop.starshare.networkapi.UserAPI;

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
    public InformationAPI getInformationAPI() {
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
