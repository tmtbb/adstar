package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/9.
 */

public class AskToBuyReturnBeen implements Parcelable {

    /**
     * amount : 600
     * buySell : 1
     * id : 142
     * openPrice : 615.11
     * positionId : 6751082814001339799
     * positionTime : 1496994919
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

    public AskToBuyReturnBeen() {
    }

    protected AskToBuyReturnBeen(Parcel in) {
        this.amount = in.readInt();
        this.buySell = in.readInt();
        this.id = in.readInt();
        this.openPrice = in.readDouble();
        this.positionId = in.readLong();
        this.positionTime = in.readInt();
        this.symbol = in.readString();
    }

    public static final Parcelable.Creator<AskToBuyReturnBeen> CREATOR = new Parcelable.Creator<AskToBuyReturnBeen>() {
        @Override
        public AskToBuyReturnBeen createFromParcel(Parcel source) {
            return new AskToBuyReturnBeen(source);
        }

        @Override
        public AskToBuyReturnBeen[] newArray(int size) {
            return new AskToBuyReturnBeen[size];
        }
    };

    @Override
    public String toString() {
        return "AskToBuyReturnBeen{" +
                "amount=" + amount +
                ", buySell=" + buySell +
                ", id=" + id +
                ", openPrice=" + openPrice +
                ", positionId=" + positionId +
                ", positionTime=" + positionTime +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
