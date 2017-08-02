package com.cloudTop.starshare.networkapi;

public interface NetworkAPIFactory {
    void initConfig(NetworkAPIConfig config);

    NetworkAPIConfig getConfig();

//    SocializeAPI getSocializeAPI();

    /**
     * 用户相关接口
     *
     * @return
     */
    UserAPI getUserAPI();

    /**
     * 交易 行情相关接口
     *
     * @return
     */
    DealAPI getDealAPI();

    InformationAPI getInformationAPI();

}
