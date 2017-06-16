package com.yundian.star.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/15.
 */

public class EntrustReturnBeen  {

    private ArrayList<PositionsListBean> positionsList;

    public ArrayList<PositionsListBean> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(ArrayList<PositionsListBean> positionsList) {
        this.positionsList = positionsList;
    }

    public static class PositionsListBean {
        /**
         * amount : 595
         * buySell : 1
         * handle : 0
         * id : 142
         * openCharge : 0
         * openPrice : 12.01
         * positionId : 3737841261180068050
         * positionTime : 1497536644
         * symbol : 1001
         */

        private int amount;
        private int buySell;
        private int handle;
        private int id;
        private int openCharge;
        private double openPrice;
        private long positionId;
        private int positionTime;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public long getPositionId() {
            return positionId;
        }

        public void setPositionId(long positionId) {
            this.positionId = positionId;
        }

        public int getPositionTime() {
            return positionTime;
        }

        public void setPositionTime(int positionTime) {
            this.positionTime = positionTime;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return "PositionsListBean{" +
                    "amount=" + amount +
                    ", buySell=" + buySell +
                    ", handle=" + handle +
                    ", id=" + id +
                    ", openCharge=" + openCharge +
                    ", openPrice=" + openPrice +
                    ", positionId=" + positionId +
                    ", positionTime=" + positionTime +
                    ", symbol='" + symbol + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EntrustReturnBeen{" +
                "positionsList=" + positionsList +
                '}';
    }
}
