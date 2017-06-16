package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sll on 2017/6/15.
 */

public class FansEntrustReturnBean implements Parcelable {


    private List<PositionsListBean> positionsList;

    public List<PositionsListBean> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(List<PositionsListBean> positionsList) {
        this.positionsList = positionsList;
    }

    public static class PositionsListBean {
        /**
         * trades : {"amount":600,"buySell":-1,"handle":0,"id":152,"openCharge":0,"openPrice":33.33,"positionId":650467027314661200,"positionTime":1497522004,"symbol":"1001"}
         * user : {"gender":0,"headUrl":"","nickname":"","uid":0}
         */

        private TradesBean trades;
        private UserBean user;

        public TradesBean getTrades() {
            return trades;
        }

        public void setTrades(TradesBean trades) {
            this.trades = trades;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class TradesBean {
            /**
             * amount : 600
             * buySell : -1
             * handle : 0
             * id : 152
             * openCharge : 0
             * openPrice : 33.33
             * positionId : 650467027314661200
             * positionTime : 1497522004
             * symbol : 1001
             */

            private long amount;
            private int buySell;
            private int handle;
            private int id;
            private double openCharge;
            private double openPrice;
            private long positionId;
            private long positionTime;
            private String symbol;

            public long getAmount() {
                return amount;
            }

            public void setAmount(long amount) {
                this.amount = amount;
            }

            public int getBuySell() {
                return buySell;
            }

            public void setBuySell(int buySell) {
                this.buySell = buySell;
            }

            public int getHandle() {
                return handle;
            }

            public void setHandle(int handle) {
                this.handle = handle;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getOpenCharge() {
                return openCharge;
            }

            public void setOpenCharge(double openCharge) {
                this.openCharge = openCharge;
            }

            public double getOpenPrice() {
                return openPrice;
            }

            public void setOpenPrice(double openPrice) {
                this.openPrice = openPrice;
            }

            public long getPositionId() {
                return positionId;
            }

            public void setPositionId(long positionId) {
                this.positionId = positionId;
            }

            public long getPositionTime() {
                return positionTime;
            }

            public void setPositionTime(long positionTime) {
                this.positionTime = positionTime;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }
        }

        public static class UserBean {
            /**
             * gender : 0
             * headUrl :
             * nickname :
             * uid : 0
             */

            private int gender;
            private String headUrl;
            private String nickname;
            private int uid;

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.positionsList);
    }

    public FansEntrustReturnBean() {
    }

    protected FansEntrustReturnBean(Parcel in) {
        this.positionsList = new ArrayList<PositionsListBean>();
        in.readList(this.positionsList, PositionsListBean.class.getClassLoader());
    }

    public static final Creator<FansEntrustReturnBean> CREATOR = new Creator<FansEntrustReturnBean>() {
        @Override
        public FansEntrustReturnBean createFromParcel(Parcel source) {
            return new FansEntrustReturnBean(source);
        }

        @Override
        public FansEntrustReturnBean[] newArray(int size) {
            return new FansEntrustReturnBean[size];
        }
    };
}
