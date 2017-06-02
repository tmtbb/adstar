package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/5/27.
 */

public class MoneyDetailListBean implements Parcelable {
    /**
     * rid : 10000002
     * id : 10001
     * amount : 100.1
     * depositTime : 1483422506
     * depositType : 1
     * depositName : 微信
     * status : 1
     */

    private long rid;
    private int id;
    private double amount;
    private String depositTime;
    private int depositType;
    private String depositName;
    private int status;

    @Override
    public String toString() {
        return "MoneyDetailListBean{" +
                "rid=" + rid +
                ", id=" + id +
                ", amount=" + amount +
                ", depositTime='" + depositTime + '\'' +
                ", depositType=" + depositType +
                ", depositName='" + depositName + '\'' +
                ", status=" + status +
                '}';
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(String depositTime) {
        this.depositTime = depositTime;
    }

    public int getDepositType() {
        return depositType;
    }

    public void setDepositType(int depositType) {
        this.depositType = depositType;
    }

    public String getDepositName() {
        return depositName;
    }

    public void setDepositName(String depositName) {
        this.depositName = depositName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.rid);
        dest.writeInt(this.id);
        dest.writeDouble(this.amount);
        dest.writeString(this.depositTime);
        dest.writeInt(this.depositType);
        dest.writeString(this.depositName);
        dest.writeInt(this.status);
    }

    public MoneyDetailListBean() {
    }

    protected MoneyDetailListBean(Parcel in) {
        this.rid = in.readLong();
        this.id = in.readInt();
        this.amount = in.readDouble();
        this.depositTime = in.readString();
        this.depositType = in.readInt();
        this.depositName = in.readString();
        this.status = in.readInt();
    }

    public static final Creator<MoneyDetailListBean> CREATOR = new Creator<MoneyDetailListBean>() {
        @Override
        public MoneyDetailListBean createFromParcel(Parcel source) {
            return new MoneyDetailListBean(source);
        }

        @Override
        public MoneyDetailListBean[] newArray(int size) {
            return new MoneyDetailListBean[size];
        }
    };
}
