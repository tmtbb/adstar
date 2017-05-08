package com.yundian.star.ui.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.yundian.star.ui.main.contract.NewInfoContract;

/**
 * Created by Null on 2017/5/6.
 */

public class NewsInforModel implements NewInfoContract.Model, Parcelable {
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

    public NewsInforModel() {
    }

    protected NewsInforModel(Parcel in) {
        this.id = in.readInt();
        this.phoneNum = in.readString();
        this.username = in.readString();
        this.token = in.readString();
    }

    public static final Creator<NewsInforModel> CREATOR = new Creator<NewsInforModel>() {
        @Override
        public NewsInforModel createFromParcel(Parcel source) {
            return new NewsInforModel(source);
        }

        @Override
        public NewsInforModel[] newArray(int size) {
            return new NewsInforModel[size];
        }
    };
}
