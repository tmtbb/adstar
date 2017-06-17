package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/6/15.
 */

public class TodayEntrustReturnBean implements Parcelable {

    /**
     * amount : 180
     * buySell : -1
     * handle : 0
     * id : 142
     * openCharge : 0
     * openPrice : 188.1
     * positionId : 3674123779994929000
     * positionTime : 1497448629
     * symbol : 1001
     */

    private long amount;
    private int buySell;
    private int handle;
    private int id;
    private double openCharge;
    private double openPrice;
    private long positionId;
    private long positionTime;
    private String symbol;
    private long rtAmount;

    public long getRtAmount() {
        return rtAmount;
    }

    public void setRtAmount(long rtAmount) {
        this.rtAmount = rtAmount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getBuySell() {
        return buySell;
    }

    public void setBuySell(int buySell) {
        this.buySell = buySell;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOpenCharge() {
        return openCharge;
    }

    public void setOpenCharge(double openCharge) {
        this.openCharge = openCharge;
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

    public long getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(long positionTime) {
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
        dest.writeLong(this.amount);
        dest.writeInt(this.buySell);
        dest.writeInt(this.handle);
        dest.writeInt(this.id);
        dest.writeDouble(this.openCharge);
        dest.writeDouble(this.openPrice);
        dest.writeLong(this.positionId);
        dest.writeLong(this.positionTime);
        dest.writeString(this.symbol);
    }

    public TodayEntrustReturnBean() {
    }

    protected TodayEntrustReturnBean(Parcel in) {
        this.amount = in.readLong();
        this.buySell = in.readInt();
        this.handle = in.readInt();
        this.id = in.readInt();
        this.openCharge = in.readDouble();
        this.openPrice = in.readDouble();
        this.positionId = in.readLong();
        this.positionTime = in.readLong();
        this.symbol = in.readString();
        this.rtAmount = in.readLong();
    }

    public static final Creator<TodayEntrustReturnBean> CREATOR = new Creator<TodayEntrustReturnBean>() {
        @Override
        public TodayEntrustReturnBean createFromParcel(Parcel source) {
            return new TodayEntrustReturnBean(source);
        }

        @Override
        public TodayEntrustReturnBean[] newArray(int size) {
            return new TodayEntrustReturnBean[size];
        }
    };
}
