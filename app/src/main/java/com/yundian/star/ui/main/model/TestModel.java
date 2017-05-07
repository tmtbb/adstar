package com.yundian.star.ui.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.yundian.star.ui.main.contract.TestContract;

/**
 * Created by Null on 2017/5/6.
 */

public class TestModel implements TestContract.Model, Parcelable {
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

    public TestModel() {
    }

    protected TestModel(Parcel in) {
        this.id = in.readInt();
        this.phoneNum = in.readString();
        this.username = in.readString();
        this.token = in.readString();
    }

    public static final Parcelable.Creator<TestModel> CREATOR = new Parcelable.Creator<TestModel>() {
        @Override
        public TestModel createFromParcel(Parcel source) {
            return new TestModel(source);
        }

        @Override
        public TestModel[] newArray(int size) {
            return new TestModel[size];
        }
    };
}
