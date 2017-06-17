package com.yundian.star.been;

/**
 * Created by Administrator on 2017/6/17.
 */

public class BuyShellReutrnBeen {

    /**
     * buyCount : 9
     * sellCount : 8
     * symbol : 1001
     */

    private int buyCount;
    private int sellCount;
    private String symbol;

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
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
                ", sellCount=" + sellCount +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
