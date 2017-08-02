package com.cloudTop.starshare.been;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class HomePageInfoBean  {
    private String home_last_pic;
    private List<SymbolInfoBean> symbol_info;

    public List<SymbolInfoBean> getSymbol_info() {
        return symbol_info;
    }

    public void setSymbol_info(List<SymbolInfoBean> symbol_info) {
        this.symbol_info = symbol_info;
    }

    public String getHome_last_pic() {
        return home_last_pic;
    }

    public void setHome_last_pic(String home_last_pic) {
        this.home_last_pic = home_last_pic;
    }

    public static class SymbolInfoBean {
        /**
         * change : 0
         * currentPrice : 0
         * home_button_pic :
         * home_pic : http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg
         * name : 林志玲
         * pchg : 0
         * pic : http://tva2.sinaimg.cn/crop.0.0.512.512.180/4e39d498jw8f9zphpwmorj20e80e80t6.jpg
         * priceTime : 1499741959
         * pushlish_type : 0
         * star_type : 1
         * symbol : 1001
         * sysTime : 1499741959
         * wid : 1011312412824
         */

        private int change;
        private int currentPrice;
        private String home_button_pic;
        private String home_pic;
        private String name;
        private int pchg;
        private String pic;
        private int priceTime;
        private int pushlish_type;
        private int star_type;
        private String symbol;
        private int sysTime;
        private String wid;

        public int getChange() {
            return change;
        }

        public void setChange(int change) {
            this.change = change;
        }

        public int getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(int currentPrice) {
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

        public int getPchg() {
            return pchg;
        }

        public void setPchg(int pchg) {
            this.pchg = pchg;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getPriceTime() {
            return priceTime;
        }

        public void setPriceTime(int priceTime) {
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
    }

    @Override
    public String toString() {
        return "HomePageInfoBean{" +
                "home_last_pic='" + home_last_pic + '\'' +
                ", symbol_info=" + symbol_info +
                '}';
    }
}
