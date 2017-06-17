package com.yundian.star.been;

/**
 * Created by Administrator on 2017/6/17.
 */

public class BuyShellReutrnBeen {

    /**
     * buyCount : 0
     * buyTime : 0
     * sellCount : 0
     * sellTime : 0
     * symbol : 1009
     */

    private int buyCount;
    private int buyTime;
    private int sellCount;
    private int sellTime;
    private String symbol;

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public int getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(int buyTime) {
        this.buyTime = buyTime;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getSellTime() {
        return sellTime;
    }

    public void setSellTime(int sellTime) {
        this.sellTime = sellTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "BuyShellReutrnBeen{" +
                "buyCount=" + buyCount +
                ", buyTime=" + buyTime +
                ", sellCount=" + sellCount +
                ", sellTime=" + sellTime +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
