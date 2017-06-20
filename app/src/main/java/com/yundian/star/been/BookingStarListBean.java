package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/5/24.
 */

public class BookingStarListBean implements Parcelable {

    private long uid;
    private long ownseconds;
    private int appoint;   //1-已约见,2,已拒绝,3-已完成
    private String starcode;
    private String starname;
    private String faccid;
    private int status;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getOwnseconds() {
        return ownseconds;
    }

    public void setOwnseconds(long ownseconds) {
        this.ownseconds = ownseconds;
    }

    public int getAppoint() {
        return appoint;
    }

    public void setAppoint(int appoint) {
        this.appoint = appoint;
    }

    public String getStarcode() {
        return starcode;
    }

    public void setStarcode(String starcode) {
        this.starcode = starcode;
    }

    public String getStarname() {
        return starname;
    }

    public void setStarname(String starname) {
        this.starname = starname;
    }

    public String getFaccid() {
        return faccid;
    }

    public void setFaccid(String faccid) {
        this.faccid = faccid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookingStarListBean{" +
                "uid=" + uid +
                ", ownseconds=" + ownseconds +
                ", appoint=" + appoint +
                ", starcode='" + starcode + '\'' +
                ", starname='" + starname + '\'' +
                ", faccid='" + faccid + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeLong(this.ownseconds);
        dest.writeInt(this.appoint);
        dest.writeString(this.starcode);
        dest.writeString(this.starname);
        dest.writeString(this.faccid);
        dest.writeInt(this.status);
    }

    public BookingStarListBean() {
    }

    protected BookingStarListBean(Parcel in) {
        this.uid = in.readLong();
        this.ownseconds = in.readLong();
        this.appoint = in.readInt();
        this.starcode = in.readString();
        this.starname = in.readString();
        this.faccid = in.readString();
        this.status = in.readInt();
    }

    public static final Creator<BookingStarListBean> CREATOR = new Creator<BookingStarListBean>() {
        @Override
        public BookingStarListBean createFromParcel(Parcel source) {
            return new BookingStarListBean(source);
        }

        @Override
        public BookingStarListBean[] newArray(int size) {
            return new BookingStarListBean[size];
        }
    };
}
