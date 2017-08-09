package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/12.
 */

public class RegisterVerifyCodeBeen implements Parcelable {

    @Override
    public String toString() {
        return "RegisterVerifyCodeBeen{" +
                "result=" + result +
                ", timeStamp=" + timeStamp +
                ", vToken='" + vToken + '\'' +
                '}';
    }

    /**
     * result : 1
     * timeStamp : 1494571020
     * vToken : 5686f72e65eae9574e99855aa5c8169f
     */

    private int result;
    private long timeStamp;
    private String vToken;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getVToken() {
        return vToken;
    }

    public void setVToken(String vToken) {
        this.vToken = vToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeLong(this.timeStamp);
        dest.writeString(this.vToken);
    }

    public RegisterVerifyCodeBeen() {
    }

    protected RegisterVerifyCodeBeen(Parcel in) {
        this.result = in.readInt();
        this.timeStamp = in.readInt();
        this.vToken = in.readString();
    }

    public static final Parcelable.Creator<RegisterVerifyCodeBeen> CREATOR = new Parcelable.Creator<RegisterVerifyCodeBeen>() {
        @Override
        public RegisterVerifyCodeBeen createFromParcel(Parcel source) {
            return new RegisterVerifyCodeBeen(source);
        }

        @Override
        public RegisterVerifyCodeBeen[] newArray(int size) {
            return new RegisterVerifyCodeBeen[size];
        }
    };
}
