package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/5/24.
 */

public class BookingStarListBean implements Parcelable {

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getOwnseconds() {
        return ownseconds;
    }

    public void setOwnseconds(long ownseconds) {
        this.ownseconds = ownseconds;
    }

    public int getAppoint() {
        return appoint;
    }

    public void setAppoint(int appoint) {
        this.appoint = appoint;
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

    public String getFaccid() {
        return faccid;
    }

    public void setFaccid(String faccid) {
        this.faccid = faccid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * uid : 10000002
     * ownseconds : 10001
     * appoint : 0
     * starcode : 1483422506
     * starname : 1
     * faccid : 0sd223kl

     * status : 1
     */

    private long uid;
    private long ownseconds;
    private int appoint;
    private String starcode;
    private String starname;
    private String faccid;
    private int status;

    public static class ListBean implements Parcelable {
        /**
         * faccid : 4
         * ownseconds : 6
         * starcode : 5
         * starname : 3
         */

        private String faccid;

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

        private int ownseconds;
        private String starcode;
        private String starname;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.faccid);
            dest.writeInt(this.ownseconds);
            dest.writeString(this.starcode);
            dest.writeString(this.starname);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.faccid = in.readString();
            this.ownseconds = in.readInt();
            this.starcode = in.readString();
            this.starname = in.readString();
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeLong(this.ownseconds);
        dest.writeInt(this.appoint);
        dest.writeString(this.starcode);
        dest.writeString(this.starname);
        dest.writeString(this.faccid);
        dest.writeInt(this.status);
    }

    public BookingStarListBean() {
    }

    protected BookingStarListBean(Parcel in) {
        this.uid = in.readLong();
        this.ownseconds = in.readLong();
        this.appoint = in.readInt();
        this.starcode = in.readString();
        this.starname = in.readString();
        this.faccid = in.readString();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<BookingStarListBean> CREATOR = new Parcelable.Creator<BookingStarListBean>() {
        @Override
        public BookingStarListBean createFromParcel(Parcel source) {
            return new BookingStarListBean(source);
        }

        @Override
        public BookingStarListBean[] newArray(int size) {
            return new BookingStarListBean[size];
        }
    };
}
