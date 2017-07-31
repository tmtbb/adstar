package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11.
 */

public class StarListReturnBean implements Parcelable {

    private ArrayList<SymbolInfoBean> symbol_info;

    public ArrayList<SymbolInfoBean> getSymbol_info() {
        return symbol_info;
    }

    public void setSymbol_info(ArrayList<SymbolInfoBean> symbol_info) {
        this.symbol_info = symbol_info;
    }

    public static class SymbolInfoBean implements Parcelable {
        /**
         * change : 0
         * currentPrice : 0
         * home_button_pic :
         * home_pic : http://wx2.sinaimg.cn/mw690/63885668ly1fgf8f38f61j243n27ib2f.jpg
         * name : 黄晓明
         * pchg : 0
         * pic : http://tva3.sinaimg.cn/crop.0.0.640.640.180/671ee283jw8eap6aqgponj20hs0hs3yq.jpg
         * priceTime : 1499775086
         * pushlish_type : 2
         * star_type : 4
         * symbol : 1015
         * sysTime : 1499775086
         * wid : 1011730077315
         */

        private float change;
        private float currentPrice;
        private String home_button_pic;
        private String home_pic;
        private String name;
        private float pchg;
        private String pic;
        private long priceTime;
        private int pushlish_type;
        private int star_type;
        private String symbol;
        private long sysTime;
        private String wid;

        public float getChange() {
            return change;
        }

        public void setChange(float change) {
            this.change = change;
        }

        public float getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(float currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getHome_button_pic() {
            return home_button_pic;
        }

        public void setHome_button_pic(String home_button_pic) {
            this.home_button_pic = home_button_pic;
        }

        public String getHome_pic() {
            return home_pic;
        }

        public void setHome_pic(String home_pic) {
            this.home_pic = home_pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPchg() {
            return pchg;
        }

        public void setPchg(float pchg) {
            this.pchg = pchg;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public long getPriceTime() {
            return priceTime;
        }

        public void setPriceTime(long priceTime) {
            this.priceTime = priceTime;
        }

        public int getPushlish_type() {
            return pushlish_type;
        }

        public void setPushlish_type(int pushlish_type) {
            this.pushlish_type = pushlish_type;
        }

        public int getStar_type() {
            return star_type;
        }

        public void setStar_type(int star_type) {
            this.star_type = star_type;
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

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        @Override
        public String toString() {
            return "SymbolInfoBean{" +
                    "change=" + change +
                    ", currentPrice=" + currentPrice +
                    ", home_button_pic='" + home_button_pic + '\'' +
                    ", home_pic='" + home_pic + '\'' +
                    ", name='" + name + '\'' +
                    ", pchg=" + pchg +
                    ", pic='" + pic + '\'' +
                    ", priceTime=" + priceTime +
                    ", pushlish_type=" + pushlish_type +
                    ", star_type=" + star_type +
                    ", symbol='" + symbol + '\'' +
                    ", sysTime=" + sysTime +
                    ", wid='" + wid + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(this.change);
            dest.writeFloat(this.currentPrice);
            dest.writeString(this.home_button_pic);
            dest.writeString(this.home_pic);
            dest.writeString(this.name);
            dest.writeFloat(this.pchg);
            dest.writeString(this.pic);
            dest.writeLong(this.priceTime);
            dest.writeInt(this.pushlish_type);
            dest.writeInt(this.star_type);
            dest.writeString(this.symbol);
            dest.writeLong(this.sysTime);
            dest.writeString(this.wid);
        }

        public SymbolInfoBean() {
        }

        protected SymbolInfoBean(Parcel in) {
            this.change = in.readFloat();
            this.currentPrice = in.readFloat();
            this.home_button_pic = in.readString();
            this.home_pic = in.readString();
            this.name = in.readString();
            this.pchg = in.readFloat();
            this.pic = in.readString();
            this.priceTime = in.readLong();
            this.pushlish_type = in.readInt();
            this.star_type = in.readInt();
            this.symbol = in.readString();
            this.sysTime = in.readLong();
            this.wid = in.readString();
        }

        public static final Parcelable.Creator<SymbolInfoBean> CREATOR = new Parcelable.Creator<SymbolInfoBean>() {
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.symbol_info);
    }

    public StarListReturnBean() {
    }

    protected StarListReturnBean(Parcel in) {
        this.symbol_info = in.createTypedArrayList(SymbolInfoBean.CREATOR);
    }

    public static final Parcelable.Creator<StarListReturnBean> CREATOR = new Parcelable.Creator<StarListReturnBean>() {
        @Override
        public StarListReturnBean createFromParcel(Parcel source) {
            return new StarListReturnBean(source);
        }

        @Override
        public StarListReturnBean[] newArray(int size) {
            return new StarListReturnBean[size];
        }
    };
}
