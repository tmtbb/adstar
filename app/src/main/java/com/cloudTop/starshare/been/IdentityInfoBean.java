package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/6/3.
 */

public class IdentityInfoBean  implements Parcelable {
    private String realname;
    private String id_card;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.realname);
        dest.writeString(this.id_card);
    }

    public IdentityInfoBean() {
    }

    protected IdentityInfoBean(Parcel in) {
        this.realname = in.readString();
        this.id_card = in.readString();
    }

    public static final Creator<IdentityInfoBean> CREATOR = new Creator<IdentityInfoBean>() {
        @Override
        public IdentityInfoBean createFromParcel(Parcel source) {
            return new IdentityInfoBean(source);
        }

        @Override
        public IdentityInfoBean[] newArray(int size) {
            return new IdentityInfoBean[size];
        }
    };
}
