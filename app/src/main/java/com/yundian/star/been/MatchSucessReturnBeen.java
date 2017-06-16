package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/10.
 */

public class MatchSucessReturnBeen implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.amount);
        dest.writeInt(this.buySell);
        dest.writeInt(this.id);
        dest.writeDouble(this.openPrice);
        dest.writeLong(this.positionId);
        dest.writeInt(this.positionTime);
        dest.writeString(this.symbol);
    }

    public MatchSucessReturnBeen() {
    }

    protected MatchSucessReturnBeen(Parcel in) {
        this.amount = in.readInt();
        this.buySell = in.readInt();
        this.id = in.readInt();
        this.openPrice = in.readDouble();
        this.positionId = in.readLong();
        this.positionTime = in.readInt();
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
