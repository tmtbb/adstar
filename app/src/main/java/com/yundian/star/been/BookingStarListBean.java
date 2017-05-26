package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sll on 2017/5/24.
 */

public class BookingStarListBean implements Parcelable {

    /**
     * list : [{"faccid":"4","ownseconds":6,"starcode":"5","starname":"3"}]
     * result : 1
     */

    private int result;
    private List<ListBean> list;

    public BookingStarListBean() {
    }

    protected BookingStarListBean(Parcel in) {
        result = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(result);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingStarListBean> CREATOR = new Creator<BookingStarListBean>() {
        @Override
        public BookingStarListBean createFromParcel(Parcel in) {
            return new BookingStarListBean(in);
        }

        @Override
        public BookingStarListBean[] newArray(int size) {
            return new BookingStarListBean[size];
        }
    };

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * faccid : 4
         * ownseconds : 6
         * starcode : 5
         * starname : 3
         */

        private String faccid;
        private int ownseconds;
        private String starcode;
        private String starname;

        protected ListBean() {
        }

        protected ListBean(Parcel in) {
            faccid = in.readString();
            ownseconds = in.readInt();
            starcode = in.readString();
            starname = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public String getFaccid() {
            return faccid;
        }

        public void setFaccid(String faccid) {
            this.faccid = faccid;
        }

        public int getOwnseconds() {
            return ownseconds;
        }

        public void setOwnseconds(int ownseconds) {
            this.ownseconds = ownseconds;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(faccid);
            dest.writeInt(ownseconds);
            dest.writeString(starcode);
            dest.writeString(starname);
        }
    }
}
