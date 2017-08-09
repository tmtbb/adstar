package com.cloudTop.starshare.been;

/**
 * Created by sll on 2017/5/28.
 */

public class RequestResultBean {
    private int status = -1;
    private int result = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RequestResultBean{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
