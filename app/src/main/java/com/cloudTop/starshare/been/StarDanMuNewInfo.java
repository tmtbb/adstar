package com.cloudTop.starshare.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/26.
 */

public class StarDanMuNewInfo {

    private ArrayList<PositionsListBean> positionsList;

    public ArrayList<PositionsListBean> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(ArrayList<PositionsListBean> positionsList) {
        this.positionsList = positionsList;
    }

    public static class PositionsListBean {
        /**
         * trades : {"amount":600,"buySell":1,"handle":-2,"id":234,"openCharge":0,"openPrice":0,"positionId":1659566747246688867,"positionTime":1501061793,"rtAmount":0,"symbol":"10002"}
         * user : {"gender":0,"headUrl":"http://tva2.sinaimg.cn/crop.0.0.180.180.180/71bf6552jw1e8qgp5bmzyj2050050aa8.jpg","nickname":"123","uid":234}
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
             * buySell : 1
             * handle : -2
             * id : 234
             * openCharge : 0
             * openPrice : 0
             * positionId : 1659566747246688867
             * positionTime : 1501061793
             * rtAmount : 0
             * symbol : 10002
             */

            private int amount;
            private int buySell;
            private int handle;
            private long id;
            private float openCharge;
            private float openPrice;
            private long positionId;
            private long positionTime;
            private long rtAmount;
            private String symbol;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
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

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public float getOpenCharge() {
                return openCharge;
            }

            public void setOpenCharge(float openCharge) {
                this.openCharge = openCharge;
            }

            public float getOpenPrice() {
                return openPrice;
            }

            public void setOpenPrice(float openPrice) {
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

            public long getRtAmount() {
                return rtAmount;
            }

            public void setRtAmount(long rtAmount) {
                this.rtAmount = rtAmount;
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
             * headUrl : http://tva2.sinaimg.cn/crop.0.0.180.180.180/71bf6552jw1e8qgp5bmzyj2050050aa8.jpg
             * nickname : 123
             * uid : 234
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
}
