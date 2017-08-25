package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/5/9.
 */

public class LoginReturnInfo {
    /**
     * result : 1
     * token : d1fed7a841f167d03a31cd510aedcbea
     * token_time : 1498732793
     * userinfo : {"agentName":"","avatar_Large":"","balance":0,"id":146,"phone":"13072714518","type":0}
     */

    private int result;
    private String token;
    private long token_time;
    private UserinfoBean userinfo;

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

    public long getToken_time() {
        return token_time;
    }

    public void setToken_time(long token_time) {
        this.token_time = token_time;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    public String toString() {
        return "LoginReturnInfo{" +
                "result=" + result +
                ", token='" + token + '\'' +
                ", token_time=" + token_time +
                ", userinfo=" + userinfo +
                '}';
    }
    //    private String token;
//    private UserinfoBean userinfo;
//
//    public int getResult() {
//        return result;
//    }
//
//    public void setResult(int result) {
//        this.result = result;
//    }
//
//    private int result ;
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//    public UserinfoBean getUserinfo() {
//        return userinfo;
//    }
//
//    public void setUserinfo(UserinfoBean userinfo) {
//        this.userinfo = userinfo;
//    }
//
//    @Override
//    public String toString() {
//        return "LoginReturnInfo{" +
//                "token='" + token + '\'' +
//                ", userinfo=" + userinfo +
//                ", result='" + result + '\'' +
//                '}';
//    }
}
