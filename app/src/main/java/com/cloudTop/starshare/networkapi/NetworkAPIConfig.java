package com.cloudTop.starshare.networkapi;

import android.content.Context;

import java.util.HashMap;

public class NetworkAPIConfig {
    public static String ContextKey = "ConfigContextKey";
    public static String UserIdKey = "ConfigUserIdKey";
    public static String UserTokenKey = "ConfigUserTokenKey";
    public static String SocketTimeoutKey = "ConfigSocketTimeoutKey";
    public static String SocketServerIpKey = "ConfigSocketServerIpKey";
    public static String SocketServerPortKey = "ConfigSocketServerPortKey";
    private HashMap<String,Object> config =  new HashMap<String,Object>();


    public NetworkAPIConfig() {
        config.put(UserIdKey,0);
        config.put(UserTokenKey,"");
        config.put(SocketTimeoutKey,5000);
    }


    public void putConfig(String key, Object value) {
        config.put(key,value);
    }

    public <T> T getConfig(String key) {
        return (T)config.get(key);
    }


    public void setSocketTimeout(int timeout) {
        config.put(SocketTimeoutKey,timeout);
    }

    public int getSocketTimeout() {
        return getConfig(SocketTimeoutKey);
    }


    public Context getContext() {
        return  getConfig(ContextKey);
    }

    public void setContext(Context context) {
        config.put(ContextKey,context);
    }

    public String getUserToken() {
       return getConfig(UserTokenKey);
    }

    public void setUserToken(String userToken) {
        config.put(UserTokenKey,userToken);

    }

    public int getUserId() {
        return getConfig(UserIdKey);
    }

    public void setUserId(int userId) {
        config.put(UserIdKey,userId);
    }

    public void setSocketServerIp(String serverIp) {
        config.put(SocketServerIpKey,serverIp);
    }

    public void setSocketServerPort(short port) {
        config.put(SocketServerPortKey,port);
    }

    public String getSocketServerIp() {
        return getConfig(SocketServerIpKey);
    }
    public short getSocketServerPort() {
        return getConfig(SocketServerPortKey);
    }
}
