package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/10.
 */

public class MatchSucessReturnBeen implements Parcelable {
    /**
     * amount : 600
     * buyUid : 142
     * openPositionTime : 1497505224
     * openPrice : 0.01
     * orderId : 6726153502960405765
     * sellUid : 142
     * symbol : 1001
     */

    private int amount;
    private int buyUid;
    private int openPositionTime;
    private double openPrice;
    private long orderId;
    private int sellUid;
    private String symbol;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBuyUid() {
        return buyUid;
    }

    public void setBuyUid(int buyUid) {
        this.buyUid = buyUid;
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getSellUid() {
        return sellUid;
    }

    public void setSellUid(int sellUid) {
        this.sellUid = sellUid;
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
        dest.writeInt(this.amount);
        dest.writeInt(this.buyUid);
        dest.writeInt(this.openPositionTime);
        dest.writeDouble(this.openPrice);
        dest.writeLong(this.orderId);
        dest.writeInt(this.sellUid);
        dest.writeString(this.symbol);
    }

    public MatchSucessReturnBeen() {
    }

    protected MatchSucessReturnBeen(Parcel in) {
        this.amount = in.readInt();
        this.buyUid = in.readInt();
        this.openPositionTime = in.readInt();
        this.openPrice = in.readDouble();
        this.orderId = in.readLong();
        this.sellUid = in.readInt();
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
