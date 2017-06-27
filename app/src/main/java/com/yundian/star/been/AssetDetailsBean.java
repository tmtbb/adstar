package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/5/31.
 */

public class AssetDetailsBean implements Parcelable {
    private double balance;
    private double market_cap;  //市值
    private double total_amt;   //总资产
    private int is_setpwd = -100;  //是否需要设置支付密码 ：0-否,1-是
    private String head_url;  //头像
    private String nick_name;  //昵称

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(double market_cap) {
        this.market_cap = market_cap;
    }

    public double getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(double total_amt) {
        this.total_amt = total_amt;
    }

    public int getIs_setpwd() {
        return is_setpwd;
    }

    public void setIs_setpwd(int is_setpwd) {
        this.is_setpwd = is_setpwd;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.balance);
        dest.writeDouble(this.market_cap);
        dest.writeDouble(this.total_amt);
        dest.writeInt(this.is_setpwd);
        dest.writeString(this.head_url);
        dest.writeString(this.nick_name);
    }

    public AssetDetailsBean() {
    }

    protected AssetDetailsBean(Parcel in) {
        this.balance = in.readDouble();
        this.market_cap = in.readDouble();
        this.total_amt = in.readDouble();
        this.is_setpwd = in.readInt();
        this.head_url = in.readString();
        this.nick_name = in.readString();
    }

    public static final Creator<AssetDetailsBean> CREATOR = new Creator<AssetDetailsBean>() {
        @Override
        public AssetDetailsBean createFromParcel(Parcel source) {
            return new AssetDetailsBean(source);
        }

        @Override
        public AssetDetailsBean[] newArray(int size) {
            return new AssetDetailsBean[size];
        }
    };

    @Override
    public String toString() {
        return "AssetDetailsBean{" +
                "balance=" + balance +
                ", market_cap=" + market_cap +
                ", total_amt=" + total_amt +
                ", is_setpwd=" + is_setpwd +
                ", head_url='" + head_url + '\'' +
                ", nick_name='" + nick_name + '\'' +
                '}';
    }
}
