package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/6/16.
 */

public class OrderSucReturnBeen {

    /**
     * orderId : 5614661470616061867
     * result : -1
     */

    private long orderId;
    private int result;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OrderSucReturnBeen{" +
                "orderId=" + orderId +
                ", result=" + result +
                '}';
    }
}
