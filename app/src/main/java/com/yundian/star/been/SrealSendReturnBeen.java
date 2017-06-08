package com.yundian.star.been;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class SrealSendReturnBeen {

    private List<PriceinfoBean> priceinfo;

    public List<PriceinfoBean> getPriceinfo() {
        return priceinfo;
    }

    public void setPriceinfo(List<PriceinfoBean> priceinfo) {
        this.priceinfo = priceinfo;
    }

    public static class PriceinfoBean {
        /**
         * change : -5.271
         * closedYesterdayPrice : 0
         * currentPrice : 71.7504
         * highPrice : 0
         * lowPrice : 0
         * openingTodayPrice : 0
         * pchg : 0
         * priceTime : 1496801187
         * symbol : 1011263498570
         * sysTime : 1496840426
         */

        private float change;
        private int closedYesterdayPrice;
        private float currentPrice;
        private int highPrice;
        private int lowPrice;
        private int openingTodayPrice;
        private int pchg;
        private int priceTime;
        private String symbol;
        private int sysTime;

        public float getChange() {
            return change;
        }

        public void setChange(float change) {
            this.change = change;
        }

        public int getClosedYesterdayPrice() {
            return closedYesterdayPrice;
        }

        public void setClosedYesterdayPrice(int closedYesterdayPrice) {
            this.closedYesterdayPrice = closedYesterdayPrice;
        }

        public float getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(float currentPrice) {
            this.currentPrice = currentPrice;
        }

        public int getHighPrice() {
            return highPrice;
        }

        public void setHighPrice(int highPrice) {
            this.highPrice = highPrice;
        }

        public int getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(int lowPrice) {
            this.lowPrice = lowPrice;
        }

        public int getOpeningTodayPrice() {
            return openingTodayPrice;
        }

        public void setOpeningTodayPrice(int openingTodayPrice) {
            this.openingTodayPrice = openingTodayPrice;
        }

        public int getPchg() {
            return pchg;
        }

        public void setPchg(int pchg) {
            this.pchg = pchg;
        }

        public int getPriceTime() {
            return priceTime;
        }

        public void setPriceTime(int priceTime) {
            this.priceTime = priceTime;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getSysTime() {
            return sysTime;
        }

        public void setSysTime(int sysTime) {
            this.sysTime = sysTime;
        }

        @Override
        public String toString() {
            return "PriceinfoBean{" +
                    "change=" + change +
                    ", closedYesterdayPrice=" + closedYesterdayPrice +
                    ", currentPrice=" + currentPrice +
                    ", highPrice=" + highPrice +
                    ", lowPrice=" + lowPrice +
                    ", openingTodayPrice=" + openingTodayPrice +
                    ", pchg=" + pchg +
                    ", priceTime=" + priceTime +
                    ", symbol='" + symbol + '\'' +
                    ", sysTime=" + sysTime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SrealSendReturnBeen{" +
                "priceinfo=" + priceinfo +
                '}';
    }
}
