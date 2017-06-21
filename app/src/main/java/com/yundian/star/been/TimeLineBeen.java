package com.yundian.star.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/7.
 */

public class TimeLineBeen {

    private ArrayList<PriceinfoBean> priceinfo;

    public ArrayList<PriceinfoBean> getPriceinfo() {
        return priceinfo;
    }

    public void setPriceinfo(ArrayList<PriceinfoBean> priceinfo) {
        this.priceinfo = priceinfo;
    }

    public static class PriceinfoBean {
        /**
         * change : 2.30435
         * closedYesterdayPrice : 0
         * currentPrice : 75.8292
         * openingTodayPrice : 0
         * pchg : 0
         * priceTime : 1496801709
         * symbol : 1011263498570
         */

        private double change;
        private int closedYesterdayPrice;
        private double currentPrice;
        private int openingTodayPrice;
        private float pchg;
        private int priceTime;
        private String symbol;

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        public int getClosedYesterdayPrice() {
            return closedYesterdayPrice;
        }

        public void setClosedYesterdayPrice(int closedYesterdayPrice) {
            this.closedYesterdayPrice = closedYesterdayPrice;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public int getOpeningTodayPrice() {
            return openingTodayPrice;
        }

        public void setOpeningTodayPrice(int openingTodayPrice) {
            this.openingTodayPrice = openingTodayPrice;
        }

        public float getPchg() {
            return pchg;
        }

        public void setPchg(float pchg) {
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

        @Override
        public String toString() {
            return "PriceinfoBean{" +
                    "change=" + change +
                    ", closedYesterdayPrice=" + closedYesterdayPrice +
                    ", currentPrice=" + currentPrice +
                    ", openingTodayPrice=" + openingTodayPrice +
                    ", pchg=" + pchg +
                    ", priceTime=" + priceTime +
                    ", symbol='" + symbol + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TimeLineBeen{" +
                "priceinfo=" + priceinfo +
                '}';
    }
}
