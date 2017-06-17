package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/9.
 */

public class UserinfoBean implements Parcelable {
    /**
     * balance : 0
     * id : 0
     * phone :
     * type : 0
     */

    private double balance;
    private int id;
    private String phone;
    private int type;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.balance);
        dest.writeInt(this.id);
        dest.writeString(this.phone);
        dest.writeInt(this.type);
    }

    public UserinfoBean() {
    }

    protected UserinfoBean(Parcel in) {
        this.balance = in.readInt();
        this.id = in.readInt();
        this.phone = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<UserinfoBean> CREATOR = new Parcelable.Creator<UserinfoBean>() {
        @Override
        public UserinfoBean createFromParcel(Parcel source) {
            return new UserinfoBean(source);
        }

        @Override
        public UserinfoBean[] newArray(int size) {
            return new UserinfoBean[size];
        }
    };

    @Override
    public String toString() {
        return "UserinfoBean{" +
                "balance=" + balance +
                ", id=" + id +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                '}';
    }
}
