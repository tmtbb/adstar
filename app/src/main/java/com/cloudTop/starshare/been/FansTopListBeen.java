package com.cloudTop.starshare.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/16.
 */

public class FansTopListBeen {

    private ArrayList<OrdersListBean> ordersList;

    public ArrayList<OrdersListBean> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(ArrayList<OrdersListBean> ordersList) {
        this.ordersList = ordersList;
    }

    public static class OrdersListBean {
        /**
         * buy_user : {"gender":0,"headUrl":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM6bsC9YzEaae6sNRb8Xozgf48q4acPcDREtFR2FicGWoy7QI0sUA6aA3UgOXOnp3Iw8JJMHBMK1UU5MUVDyVLHV2ze8n5TiaCuug/0","nickname":"标标","uid":142}
         * sell_user : {"gender":0,"headUrl":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM6bsC9YzEaae6sNRb8Xozgf48q4acPcDREtFR2FicGWoy7QI0sUA6aA3UgOXOnp3Iw8JJMHBMK1UU5MUVDyVLHV2ze8n5TiaCuug/0","nickname":"标标Gay","uid":152}
         * trades : {"amount":600,"buyUid":142,"closeTime":0,"grossProfit":0,"handle":0,"openCharge":0,"openPrice":33.33,"openTime":1497522004,"orderId":8370777693284393000,"sellUid":152,"symbol":"1001"}
         */

        private BuyUserBean buy_user;
        private SellUserBean sell_user;
        private TradesBean trades;

        public BuyUserBean getBuy_user() {
            return buy_user;
        }

        public void setBuy_user(BuyUserBean buy_user) {
            this.buy_user = buy_user;
        }

        public SellUserBean getSell_user() {
            return sell_user;
        }

        public void setSell_user(SellUserBean sell_user) {
            this.sell_user = sell_user;
        }

        public TradesBean getTrades() {
            return trades;
        }

        public void setTrades(TradesBean trades) {
            this.trades = trades;
        }

        public static class BuyUserBean {
            /**
             * gender : 0
             * headUrl : http://wx.qlogo.cn/mmopen/Q3auHgzwzM6bsC9YzEaae6sNRb8Xozgf48q4acPcDREtFR2FicGWoy7QI0sUA6aA3UgOXOnp3Iw8JJMHBMK1UU5MUVDyVLHV2ze8n5TiaCuug/0
             * nickname : 标标
             * uid : 142
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

            @Override
            public String toString() {
                return "BuyUserBean{" +
                        "gender=" + gender +
                        ", headUrl='" + headUrl + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", uid=" + uid +
                        '}';
            }
        }

        public static class SellUserBean {
            /**
             * gender : 0
             * headUrl : http://wx.qlogo.cn/mmopen/Q3auHgzwzM6bsC9YzEaae6sNRb8Xozgf48q4acPcDREtFR2FicGWoy7QI0sUA6aA3UgOXOnp3Iw8JJMHBMK1UU5MUVDyVLHV2ze8n5TiaCuug/0
             * nickname : 标标Gay
             * uid : 152
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

            @Override
            public String toString() {
                return "SellUserBean{" +
                        "gender=" + gender +
                        ", headUrl='" + headUrl + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", uid=" + uid +
                        '}';
            }
        }

        public static class TradesBean {
            /**
             * amount : 600
             * buyUid : 142
             * closeTime : 0
             * grossProfit : 0
             * handle : 0
             * openCharge : 0
             * openPrice : 33.33
             * openTime : 1497522004
             * orderId : 8370777693284393000
             * sellUid : 152
             * symbol : 1001
             */

            private int amount;
            private int buyUid;
            private int closeTime;
            private int grossProfit;
            private int handle;
            private int openCharge;
            private double openPrice;
            private int openTime;
            private long orderId;
            private int sellUid;
            private String symbol;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getBuyUid() {
                return buyUid;
            }

            public void setBuyUid(int buyUid) {
                this.buyUid = buyUid;
            }

            public int getCloseTime() {
                return closeTime;
            }

            public void setCloseTime(int closeTime) {
                this.closeTime = closeTime;
            }

            public int getGrossProfit() {
                return grossProfit;
            }

            public void setGrossProfit(int grossProfit) {
                this.grossProfit = grossProfit;
            }

            public int getHandle() {
                return handle;
            }

            public void setHandle(int handle) {
                this.handle = handle;
            }

            public int getOpenCharge() {
                return openCharge;
            }

            public void setOpenCharge(int openCharge) {
                this.openCharge = openCharge;
            }

            public double getOpenPrice() {
                return openPrice;
            }

            public void setOpenPrice(double openPrice) {
                this.openPrice = openPrice;
            }

            public int getOpenTime() {
                return openTime;
            }

            public void setOpenTime(int openTime) {
                this.openTime = openTime;
            }

            public long getOrderId() {
                return orderId;
            }

            public void setOrderId(long orderId) {
                this.orderId = orderId;
            }

            public int getSellUid() {
                return sellUid;
            }

            public void setSellUid(int sellUid) {
                this.sellUid = sellUid;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            @Override
            public String toString() {
                return "TradesBean{" +
                        "amount=" + amount +
                        ", buyUid=" + buyUid +
                        ", closeTime=" + closeTime +
                        ", grossProfit=" + grossProfit +
                        ", handle=" + handle +
                        ", openCharge=" + openCharge +
                        ", openPrice=" + openPrice +
                        ", openTime=" + openTime +
                        ", orderId=" + orderId +
                        ", sellUid=" + sellUid +
                        ", symbol='" + symbol + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "FansTopListBeen{" +
                "ordersList=" + ordersList +
                '}';
    }
}
