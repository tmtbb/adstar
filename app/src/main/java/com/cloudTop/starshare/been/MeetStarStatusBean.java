package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sll on 2017/7/6.
 */

public class MeetStarStatusBean implements Parcelable {

    private int result;
    private List<MeetStarInfoBean> list;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<MeetStarInfoBean> getList() {
        return list;
    }

    public void setList(List<MeetStarInfoBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeTypedList(this.list);
    }

    public MeetStarStatusBean() {
    }

    protected MeetStarStatusBean(Parcel in) {
        this.result = in.readInt();
        this.list = in.createTypedArrayList(MeetStarInfoBean.CREATOR);
    }

    public static final Creator<MeetStarStatusBean> CREATOR = new Creator<MeetStarStatusBean>() {
        @Override
        public MeetStarStatusBean createFromParcel(Parcel source) {
            return new MeetStarStatusBean(source);
        }

        @Override
        public MeetStarStatusBean[] newArray(int size) {
            return new MeetStarStatusBean[size];
        }
    };
}
