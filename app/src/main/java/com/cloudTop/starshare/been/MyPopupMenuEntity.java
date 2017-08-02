package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Benjamin on 16/6/5.
 */
public class MyPopupMenuEntity implements Parcelable {
    private String text;
    private int iconId;

    public MyPopupMenuEntity() {
    }

    protected MyPopupMenuEntity(Parcel in) {
        text = in.readString();
        iconId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(iconId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyPopupMenuEntity> CREATOR = new Creator<MyPopupMenuEntity>() {
        @Override
        public MyPopupMenuEntity createFromParcel(Parcel in) {
            return new MyPopupMenuEntity(in);
        }

        @Override
        public MyPopupMenuEntity[] newArray(int size) {
            return new MyPopupMenuEntity[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
