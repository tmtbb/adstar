package com.yundian.star.been;

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

    protected WXPayReturnEntity(Parcel in) {
        appid = in.readString();
        partnerid = in.readString();
        prepayid = in.readString();
        Package = in.readString();
        noncestr = in.readString();
        timestamp = in.readString();
        sign = in.readString();
        rid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appid);
        dest.writeString(partnerid);
        dest.writeString(prepayid);
        dest.writeString(Package);
        dest.writeString(noncestr);
        dest.writeString(timestamp);
        dest.writeString(sign);
        dest.writeString(rid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WXPayReturnEntity> CREATOR = new Creator<WXPayReturnEntity>() {
        @Override
        public WXPayReturnEntity createFromParcel(Parcel in) {
            return new WXPayReturnEntity(in);
        }

        @Override
        public WXPayReturnEntity[] newArray(int size) {
            return new WXPayReturnEntity[size];
        }
    };

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
}
