package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/7/6.
 */

public class MeetStarInfoBean implements Parcelable {

    private String star_code;
    private String star_name;
    private String star_pic;
    private int star_type;
    private int meet_id;
    private String meet_name;
    private String meet_city;
    private String meet_time;
    private int meet_type;
    private String comment;

    public String getStar_code() {
        return star_code;
    }

    public void setStar_code(String star_code) {
        this.star_code = star_code;
    }

    public String getStar_name() {
        return star_name;
    }

    public void setStar_name(String star_name) {
        this.star_name = star_name;
    }

    public String getStar_pic() {
        return star_pic;
    }

    public void setStar_pic(String star_pic) {
        this.star_pic = star_pic;
    }

    public int getStar_type() {
        return star_type;
    }

    public void setStar_type(int star_type) {
        this.star_type = star_type;
    }

    public int getMeet_id() {
        return meet_id;
    }

    public void setMeet_id(int meet_id) {
        this.meet_id = meet_id;
    }

    public String getMeet_name() {
        return meet_name;
    }

    public void setMeet_name(String meet_name) {
        this.meet_name = meet_name;
    }

    public String getMeet_city() {
        return meet_city;
    }

    public void setMeet_city(String meet_city) {
        this.meet_city = meet_city;
    }

    public String getMeet_time() {
        return meet_time;
    }

    public void setMeet_time(String meet_time) {
        this.meet_time = meet_time;
    }

    public int getMeet_type() {
        return meet_type;
    }

    public void setMeet_type(int meet_type) {
        this.meet_type = meet_type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static Creator<MeetStarInfoBean> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.star_code);
        dest.writeString(this.star_name);
        dest.writeString(this.star_pic);
        dest.writeInt(this.star_type);
        dest.writeInt(this.meet_id);
        dest.writeString(this.meet_name);
        dest.writeString(this.meet_city);
        dest.writeString(this.meet_time);
        dest.writeInt(this.meet_type);
        dest.writeString(this.comment);
    }

    public MeetStarInfoBean() {
    }

    protected MeetStarInfoBean(Parcel in) {
        this.star_code = in.readString();
        this.star_name = in.readString();
        this.star_pic = in.readString();
        this.star_type = in.readInt();
        this.meet_id = in.readInt();
        this.meet_name = in.readString();
        this.meet_city = in.readString();
        this.meet_time = in.readString();
        this.meet_type = in.readInt();
        this.comment = in.readString();
    }

    public static final Creator<MeetStarInfoBean> CREATOR = new Creator<MeetStarInfoBean>() {
        @Override
        public MeetStarInfoBean createFromParcel(Parcel source) {
            return new MeetStarInfoBean(source);
        }

        @Override
        public MeetStarInfoBean[] newArray(int size) {
            return new MeetStarInfoBean[size];
        }
    };
}
