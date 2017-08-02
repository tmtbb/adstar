package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/7.
 */

public class SrealSendBeen implements Parcelable {
    @Override
    public String toString() {
        return "SrealSendBeen{" +
                "symbol='" + symbol + '\'' +
                ", aType=" + aType +
                '}';
    }

    /**
     * symbol : 1011650450024
     * aType : 5
     */

    private String symbol;
    private int aType;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getAType() {
        return aType;
    }

    public void setAType(int aType) {
        this.aType = aType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.symbol);
        dest.writeInt(this.aType);
    }

    public SrealSendBeen() {
    }

    protected SrealSendBeen(Parcel in) {
        this.symbol = in.readString();
        this.aType = in.readInt();
    }

    public static final Parcelable.Creator<SrealSendBeen> CREATOR = new Parcelable.Creator<SrealSendBeen>() {
        @Override
        public SrealSendBeen createFromParcel(Parcel source) {
            return new SrealSendBeen(source);
        }

        @Override
        public SrealSendBeen[] newArray(int size) {
            return new SrealSendBeen[size];
        }
    };
}
