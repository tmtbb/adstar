package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/6/17.
 */

public class AliPayReturnBean implements Parcelable{
    private String orderinfo;

    public String getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(String orderinfo) {
        this.orderinfo = orderinfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderinfo);
    }

    public AliPayReturnBean() {
    }

    protected AliPayReturnBean(Parcel in) {
        this.orderinfo = in.readString();
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
