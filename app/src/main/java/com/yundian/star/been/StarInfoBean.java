package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/6/16.
 */

public class StarInfoBean implements Parcelable {

    /**
     * accid : accidvalue
     * brief : brief
     * code : 123
     * gender : 1
     * name : 44
     * phone : 158158
     * pic_url : www
     * price : 1.5
     */

    private String accid;
    private String brief;
    private String code;
    private int gender;
    private String name;
    private String phone;
    private String pic_url;
    private double price;

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accid);
        dest.writeString(this.brief);
        dest.writeString(this.code);
        dest.writeInt(this.gender);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.pic_url);
        dest.writeDouble(this.price);
    }

    public StarInfoBean() {
    }

    protected StarInfoBean(Parcel in) {
        this.accid = in.readString();
        this.brief = in.readString();
        this.code = in.readString();
        this.gender = in.readInt();
        this.name = in.readString();
        this.phone = in.readString();
        this.pic_url = in.readString();
        this.price = in.readDouble();
    }

    public static final Parcelable.Creator<StarInfoBean> CREATOR = new Parcelable.Creator<StarInfoBean>() {
        @Override
        public StarInfoBean createFromParcel(Parcel source) {
            return new StarInfoBean(source);
        }

        @Override
        public StarInfoBean[] newArray(int size) {
            return new StarInfoBean[size];
        }
    };
}
