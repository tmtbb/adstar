package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/8/5.
 */

public class StarTimeReaturnBean {
    private int result;
    private long star_own_time;
    private long user_star_time;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getStar_own_time() {
        return star_own_time;
    }

    public void setStar_own_time(long star_own_time) {
        this.star_own_time = star_own_time;
    }

    public long getUser_star_time() {
        return user_star_time;
    }

    public void setUser_star_time(long user_star_time) {
        this.user_star_time = user_star_time;
    }

    @Override
    public String toString() {
        return "StarTimeReaturnBean{" +
                "result=" + result +
                ", star_own_time=" + star_own_time +
                ", user_star_time=" + user_star_time +
                '}';
    }
}
