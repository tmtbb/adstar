package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/6.
 */

public class StarListbeen {

    private ArrayList<SymbolInfoBean> symbol_info;

    public ArrayList<SymbolInfoBean> getSymbol_info() {
        return symbol_info;
    }

    public void setSymbol_info(ArrayList<SymbolInfoBean> symbol_info) {
        this.symbol_info = symbol_info;
    }

    public static class SymbolInfoBean implements Parcelable {

        /**
         * change : -0.125903
         * currentPrice : 47.8624
         * name : 黄晓明
         * pic : http://chuantu.biz/t5/94/1495791849x2890174052.png
         * priceTime : 1496367101
         * symbol : 1015
         * sysTime : 1496367122
         * wid : 1011730077315

         */

        private double change;
        private double currentPrice;
        private String name;
        private String pic;
        private String pic_tail="";
        private int priceTime;
        private String symbol;
        private int sysTime;
        private String wid;
        private float pchg;

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_tail() {
            return pic_tail;
        }

        public void setPic_tail(String pic_tail) {
            this.pic_tail = pic_tail;
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

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public float getPchg() {
            return pchg;
        }

        public void setPchg(float pchg) {
            this.pchg = pchg;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.change);
            dest.writeDouble(this.currentPrice);
            dest.writeString(this.name);
            dest.writeString(this.pic);
            dest.writeString(this.pic_tail);
            dest.writeInt(this.priceTime);
            dest.writeString(this.symbol);
            dest.writeInt(this.sysTime);
            dest.writeString(this.wid);
            dest.writeFloat(this.pchg);
        }

        public SymbolInfoBean() {
        }

        protected SymbolInfoBean(Parcel in) {
            this.change = in.readDouble();
            this.currentPrice = in.readDouble();
            this.name = in.readString();
            this.pic = in.readString();
            this.pic_tail = in.readString();
            this.priceTime = in.readInt();
            this.symbol = in.readString();
            this.sysTime = in.readInt();
            this.wid = in.readString();
            this.pchg = in.readFloat();
        }

        public static final Creator<SymbolInfoBean> CREATOR = new Creator<SymbolInfoBean>() {
            @Override
            public SymbolInfoBean createFromParcel(Parcel source) {
                return new SymbolInfoBean(source);
            }

            @Override
            public SymbolInfoBean[] newArray(int size) {
                return new SymbolInfoBean[size];
            }
        };
    }

    @Override
    public String toString() {
        return "StarListbeen{" +
                "symbol_info=" + symbol_info +
                '}';
    }
}
