package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/6/14.
 */

public class TradingStatusBeen {

    /**
     * remainingTime : 1593
     * status : true
     * symbol : 1001
     */

    private int remainingTime;
    private boolean status;
    private String symbol;

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "TradingStatusBeen{" +
                "remainingTime=" + remainingTime +
                ", status=" + status +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
