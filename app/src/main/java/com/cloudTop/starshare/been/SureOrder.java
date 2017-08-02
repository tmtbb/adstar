package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/15.
 */

public class SureOrder implements Parcelable {

    /**
     * orderId : 2302570406174920400
     * status : 0
     * uid : 142
     */

    private long orderId;
    private int status;

    @Override
    public String toString() {
        return "SureOrder{" +
                "orderId=" + orderId +
                ", status=" + status +
                ", uid=" + uid +
                '}';
    }

    private int uid;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.orderId);
        dest.writeInt(this.status);
        dest.writeInt(this.uid);
    }

    public SureOrder() {
    }

    protected SureOrder(Parcel in) {
        this.orderId = in.readLong();
        this.status = in.readInt();
        this.uid = in.readInt();
    }

    public static final Parcelable.Creator<SureOrder> CREATOR = new Parcelable.Creator<SureOrder>() {
        @Override
        public SureOrder createFromParcel(Parcel source) {
            return new SureOrder(source);
        }

        @Override
        public SureOrder[] newArray(int size) {
            return new SureOrder[size];
        }
    };
}
