package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/8/4.
 */

public class ReturnAmountBean {

    /**
     * result : 1
     * total_amount : 0
     * total_num : 0
     */

    private int result;
    private float total_amount;
    private int total_num;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    @Override
    public String toString() {
        return "ReturnAmountBean{" +
                "result=" + result +
                ", total_amount=" + total_amount +
                ", total_num=" + total_num +
                '}';
    }
}
