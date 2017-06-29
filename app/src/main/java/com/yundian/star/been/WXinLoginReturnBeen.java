package com.yundian.star.been;

/**
 * Created by Administrator on 2017/5/18.
 */

public class WXinLoginReturnBeen {

    /**
     * result : 1
     * token : 54e22898436573dd5d12f71e502643eb
     * userinfo : {"id":94,"phone":"13072714518","type":0}
     */

    private int result;
    private String token;
    private UserinfoBean userinfo;
    private long token_time;

    public long getToken_time() {
        return token_time;
    }

    public void setToken_time(long token_time) {
        this.token_time = token_time;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    public String toString() {
        return "WXinLoginReturnBeen{" +
                "result=" + result +
                ", token='" + token + '\'' +
                ", userinfo=" + userinfo +
                ", token_time=" + token_time +
                '}';
    }
}
