package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

public class WXPayReturnEntity implements Parcelable {

    private String appid;
    private String partnerid;
    private String prepayid;
    private String Package;
    private String noncestr;
    private String timestamp;
    private String sign;
    private String rid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public static Creator<WXPayReturnEntity> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appid);
        dest.writeString(this.partnerid);
        dest.writeString(this.prepayid);
        dest.writeString(this.Package);
        dest.writeString(this.noncestr);
        dest.writeString(this.timestamp);
        dest.writeString(this.sign);
        dest.writeString(this.rid);
    }

    public WXPayReturnEntity() {
    }

    protected WXPayReturnEntity(Parcel in) {
        this.appid = in.readString();
        this.partnerid = in.readString();
        this.prepayid = in.readString();
        this.Package = in.readString();
        this.noncestr = in.readString();
        this.timestamp = in.readString();
        this.sign = in.readString();
        this.rid = in.readString();
    }

    public static final Creator<WXPayReturnEntity> CREATOR = new Creator<WXPayReturnEntity>() {
        @Override
        public WXPayReturnEntity createFromParcel(Parcel source) {
            return new WXPayReturnEntity(source);
        }

        @Override
        public WXPayReturnEntity[] newArray(int size) {
            return new WXPayReturnEntity[size];
        }
    };
}
