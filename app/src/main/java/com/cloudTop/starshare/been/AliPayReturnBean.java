package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/6/17.
 */

public class AliPayReturnBean implements Parcelable{
    private String orderinfo;
    private String rid;

    public String getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(String orderinfo) {
        this.orderinfo = orderinfo;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderinfo);
        dest.writeString(this.rid);
    }

    public AliPayReturnBean() {
    }

    protected AliPayReturnBean(Parcel in) {
        this.orderinfo = in.readString();
        this.rid = in.readString();
    }

    public static final Creator<AliPayReturnBean> CREATOR = new Creator<AliPayReturnBean>() {
        @Override
        public AliPayReturnBean createFromParcel(Parcel source) {
            return new AliPayReturnBean(source);
        }

        @Override
        public AliPayReturnBean[] newArray(int size) {
            return new AliPayReturnBean[size];
        }
    };
}
