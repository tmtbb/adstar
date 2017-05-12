package com.yundian.star.been;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RegisterReturnBeen {

    /**
     * result : 1
     * uid : 1
     */

    private int result;
    private int uid;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "RegisterReturnBeen{" +
                "result=" + result +
                ", uid=" + uid +
                '}';
    }
}
