package com.cloudTop.starshare.been;

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
        private float closedYesterdayPrice;
        private float currentPrice;
        private float highPrice;
        private float lowPrice;
        private float openingTodayPrice;
        private float pchg;
        private long priceTime;
        private String symbol;
        private long sysTime;

        public float getChange() {
            return change;
        }

        public void setChange(float change) {
            this.change = change;
        }

        public float getClosedYesterdayPrice() {
            return closedYesterdayPrice;
        }

        public void setClosedYesterdayPrice(float closedYesterdayPrice) {
            this.closedYesterdayPrice = closedYesterdayPrice;
        }

        public float getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(float currentPrice) {
            this.currentPrice = currentPrice;
        }

        public float getHighPrice() {
            return highPrice;
        }

        public void setHighPrice(float highPrice) {
            this.highPrice = highPrice;
        }

        public float getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(float lowPrice) {
            this.lowPrice = lowPrice;
        }

        public float getOpeningTodayPrice() {
            return openingTodayPrice;
        }

        public void setOpeningTodayPrice(float openingTodayPrice) {
            this.openingTodayPrice = openingTodayPrice;
        }

        public float getPchg() {
            return pchg;
        }

        public void setPchg(float pchg) {
            this.pchg = pchg;
        }

        public long getPriceTime() {
            return priceTime;
        }

        public void setPriceTime(long priceTime) {
            this.priceTime = priceTime;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public long getSysTime() {
            return sysTime;
        }

        public void setSysTime(long sysTime) {
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
