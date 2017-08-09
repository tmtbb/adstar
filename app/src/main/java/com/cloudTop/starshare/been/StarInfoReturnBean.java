package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

import com.cloudTop.starshare.greendao.StarInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sll on 2017/6/16.
 */

public class StarInfoReturnBean implements Parcelable {
    private int result;
    private List<StarInfo> list;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<StarInfo> getList() {
        return list;
    }

    public void setList(List<StarInfo> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeList(this.list);
    }

    public StarInfoReturnBean() {
    }

    protected StarInfoReturnBean(Parcel in) {
        this.result = in.readInt();
        this.list = new ArrayList<StarInfo>();
        in.readList(this.list, StarInfo.class.getClassLoader());
    }

    public static final Creator<StarInfoReturnBean> CREATOR = new Creator<StarInfoReturnBean>() {
        @Override
        public StarInfoReturnBean createFromParcel(Parcel source) {
            return new StarInfoReturnBean(source);
        }

        @Override
        public StarInfoReturnBean[] newArray(int size) {
            return new StarInfoReturnBean[size];
        }
    };
}
