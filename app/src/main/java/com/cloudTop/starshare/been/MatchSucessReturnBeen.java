package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/10.
 */

public class MatchSucessReturnBeen implements Parcelable {
    /**
     * orderId : 4596320529293132300
     * buyUid : 142
     * sellUid : 152
     * amount : 22
     * openPositionTime : 1497340525
     * openPrice : 22.1
     * symbol : 1001
     */

    private long orderId;
    private int buyUid;
    private int sellUid;
    private int amount;
    private int openPositionTime;
    private double openPrice;
    private String symbol;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getBuyUid() {
        return buyUid;
    }

    public void setBuyUid(int buyUid) {
        this.buyUid = buyUid;
    }

    public int getSellUid() {
        return sellUid;
    }

    public void setSellUid(int sellUid) {
        this.sellUid = sellUid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOpenPositionTime() {
        return openPositionTime;
    }

    public void setOpenPositionTime(int openPositionTime) {
        this.openPositionTime = openPositionTime;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.orderId);
        dest.writeInt(this.buyUid);
        dest.writeInt(this.sellUid);
        dest.writeInt(this.amount);
        dest.writeInt(this.openPositionTime);
        dest.writeDouble(this.openPrice);
        dest.writeString(this.symbol);
    }

    public MatchSucessReturnBeen() {
    }

    protected MatchSucessReturnBeen(Parcel in) {
        this.orderId = in.readLong();
        this.buyUid = in.readInt();
        this.sellUid = in.readInt();
        this.amount = in.readInt();
        this.openPositionTime = in.readInt();
        this.openPrice = in.readDouble();
        this.symbol = in.readString();
    }

    public static final Parcelable.Creator<MatchSucessReturnBeen> CREATOR = new Parcelable.Creator<MatchSucessReturnBeen>() {
        @Override
        public MatchSucessReturnBeen createFromParcel(Parcel source) {
            return new MatchSucessReturnBeen(source);
        }

        @Override
        public MatchSucessReturnBeen[] newArray(int size) {
            return new MatchSucessReturnBeen[size];
        }
    };
}
