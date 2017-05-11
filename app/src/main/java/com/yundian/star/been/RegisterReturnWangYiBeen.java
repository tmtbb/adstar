package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RegisterReturnWangYiBeen implements Parcelable {

    /**
     * result_value : success
     * token_value : c3ea820d1e204350053ebe9d620ae320
     */

    private String result_value;
    private String token_value;

    public String getResult_value() {
        return result_value;
    }

    public void setResult_value(String result_value) {
        this.result_value = result_value;
    }

    public String getToken_value() {
        return token_value;
    }

    public void setToken_value(String token_value) {
        this.token_value = token_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.result_value);
        dest.writeString(this.token_value);
    }

    public RegisterReturnWangYiBeen() {
    }

    protected RegisterReturnWangYiBeen(Parcel in) {
        this.result_value = in.readString();
        this.token_value = in.readString();
    }

    public static final Parcelable.Creator<RegisterReturnWangYiBeen> CREATOR = new Parcelable.Creator<RegisterReturnWangYiBeen>() {
        @Override
        public RegisterReturnWangYiBeen createFromParcel(Parcel source) {
            return new RegisterReturnWangYiBeen(source);
        }

        @Override
        public RegisterReturnWangYiBeen[] newArray(int size) {
            return new RegisterReturnWangYiBeen[size];
        }
    };
}
