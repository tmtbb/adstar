package com.cloudTop.starshare.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/15.
 */

public class OrderReturnBeen {

    private ArrayList<OrdersListBean> ordersList;

    public ArrayList<OrdersListBean> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(ArrayList<OrdersListBean> ordersList) {
        this.ordersList = ordersList;
    }

    public static class OrdersListBean {
        /**
         * amount : 600
         * buyHandle : 0
         * buyUid : 142
         * closeTime : 0
         * grossProfit : 0
         * handle : 0
         * openCharge : 0
         * openPrice : 10.51
         * openTime : 1497585743
         * orderId : 1736498499577977000
         * positionId : 8632189052509148000
         * sellHandler : 0
         * sellUid : 142
         * symbol : 1001
         */

        private int amount;
        private int buyHandle;
        private int buyUid;
        private int closeTime;
        private int grossProfit;
        private int handle;
        private int openCharge;
        private double openPrice;
        private int openTime;
        private long orderId;
        private long positionId;
        private int sellHandler;
        private int sellUid;
        private String symbol;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getBuyHandle() {
            return buyHandle;
        }

        public void setBuyHandle(int buyHandle) {
            this.buyHandle = buyHandle;
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

        public long getPositionId() {
            return positionId;
        }

        public void setPositionId(long positionId) {
            this.positionId = positionId;
        }

        public int getSellHandler() {
            return sellHandler;
        }

        public void setSellHandler(int sellHandler) {
            this.sellHandler = sellHandler;
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
            return "OrdersListBean{" +
                    "amount=" + amount +
                    ", buyHandle=" + buyHandle +
                    ", buyUid=" + buyUid +
                    ", closeTime=" + closeTime +
                    ", grossProfit=" + grossProfit +
                    ", handle=" + handle +
                    ", openCharge=" + openCharge +
                    ", openPrice=" + openPrice +
                    ", openTime=" + openTime +
                    ", orderId=" + orderId +
                    ", positionId=" + positionId +
                    ", sellHandler=" + sellHandler +
                    ", sellUid=" + sellUid +
                    ", symbol='" + symbol + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderReturnBeen{" +
                "ordersList=" + ordersList +
                '}';
    }
}
