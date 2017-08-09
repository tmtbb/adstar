package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/6/15.
 */

public class TodayDealReturnBean implements Parcelable {


    /**
     * amount : 600
     * buyHandle : 0
     * buyUid : 142
     * closeTime : 0
     * grossProfit : 0.0
     * handle : 0
     * openCharge : 0.0
     * openPrice : 8.88
     * openTime : 1497534583
     * orderId : 470734038129997840
     * positionId : 8846179457208765800
     * sellHandler : 0
     * sellUid : 142
     * symbol : 1001
     */

    private long amount;
    private int buyHandle;
    private long buyUid;
    private long closeTime;
    private double grossProfit;
    private int handle;
    private double openCharge;
    private double openPrice;
    private long openTime;
    private long orderId;
    private long positionId;
    private int sellHandler;
    private long sellUid;
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getBuyHandle() {
        return buyHandle;
    }

    public void setBuyHandle(int buyHandle) {
        this.buyHandle = buyHandle;
    }

    public long getBuyUid() {
        return buyUid;
    }

    public void setBuyUid(long buyUid) {
        this.buyUid = buyUid;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
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

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public int getSellHandler() {
        return sellHandler;
    }

    public void setSellHandler(int sellHandler) {
        this.sellHandler = sellHandler;
    }

    public long getSellUid() {
        return sellUid;
    }

    public void setSellUid(long sellUid) {
        this.sellUid = sellUid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.amount);
        dest.writeInt(this.buyHandle);
        dest.writeLong(this.buyUid);
        dest.writeLong(this.closeTime);
        dest.writeDouble(this.grossProfit);
        dest.writeInt(this.handle);
        dest.writeDouble(this.openCharge);
        dest.writeDouble(this.openPrice);
        dest.writeLong(this.openTime);
        dest.writeLong(this.orderId);
        dest.writeLong(this.positionId);
        dest.writeInt(this.sellHandler);
        dest.writeLong(this.sellUid);
    }

    public TodayDealReturnBean() {
    }

    protected TodayDealReturnBean(Parcel in) {
        this.amount = in.readLong();
        this.buyHandle = in.readInt();
        this.buyUid = in.readLong();
        this.closeTime = in.readLong();
        this.grossProfit = in.readDouble();
        this.handle = in.readInt();
        this.openCharge = in.readDouble();
        this.openPrice = in.readDouble();
        this.openTime = in.readLong();
        this.orderId = in.readLong();
        this.positionId = in.readLong();
        this.sellHandler = in.readInt();
        this.sellUid = in.readLong();
        this.symbol = in.readString();
    }

    public static final Creator<TodayDealReturnBean> CREATOR = new Creator<TodayDealReturnBean>() {
        @Override
        public TodayDealReturnBean createFromParcel(Parcel source) {
            return new TodayDealReturnBean(source);
        }

        @Override
        public TodayDealReturnBean[] newArray(int size) {
            return new TodayDealReturnBean[size];
        }
    };
}
