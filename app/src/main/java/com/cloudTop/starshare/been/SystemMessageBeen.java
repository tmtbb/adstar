package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/6/15.
 */

public class SystemMessageBeen {
    /**
     * amount : 80
     * buySell : 2
     * id : 142
     * openPrice : 22.1
     * positionId : 4596320529293132300
     * positionTime : 1497340525
     * symbol : 1001
     */

    private int amount;
    private int buySell;
    private int id;
    private double openPrice;
    private long positionId;
    private int positionTime;
    private String symbol;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBuySell() {
        return buySell;
    }

    public void setBuySell(int buySell) {
        this.buySell = buySell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public int getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(int positionTime) {
        this.positionTime = positionTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
