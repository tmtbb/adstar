package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Null on 2017/5/6.
 */

public class AdstarUser implements Parcelable {
    private int id ;
    private String phoneNum;
    private String username;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.phoneNum);
        dest.writeString(this.username);
        dest.writeString(this.token);
    }

    public AdstarUser() {
    }

    protected AdstarUser(Parcel in) {
        this.id = in.readInt();
        this.phoneNum = in.readString();
        this.username = in.readString();
        this.token = in.readString();
    }

    public static final Creator<AdstarUser> CREATOR = new Creator<AdstarUser>() {
        @Override
        public AdstarUser createFromParcel(Parcel source) {
            return new AdstarUser(source);
        }

        @Override
        public AdstarUser[] newArray(int size) {
            return new AdstarUser[size];
        }
    };
}
