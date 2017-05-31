package com.yundian.star.been;

/**
 * Created by Administrator on 2017/5/9.
 */

public class LoginReturnInfo {
    private String token;
    private UserinfoBean userinfo;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    private int result ;

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
        return "LoginReturnInfo{" +
                "token='" + token + '\'' +
                ", userinfo=" + userinfo +
                ", result='" + result + '\'' +
                '}';
    }
}
