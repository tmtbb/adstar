package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/7/25.
 */

public class NowPriceBean {

    /**
     * change : 0
     * currentPrice : 0
     * home_button_pic :
     * home_pic : http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg
     * name : 林志玲
     * pchg : 0
     * pic : http://tva2.sinaimg.cn/crop.0.0.512.512.180/4e39d498jw8f9zphpwmorj20e80e80t6.jpg
     * priceTime : 1500973481
     * pushlish_type : 0
     * star_type : 1
     * symbol : 1001
     * sysTime : 1500973481
     * wid : 1011312412824
     */

    private float change;
    private float currentPrice;
    private String home_button_pic;
    private String home_button_pic_tail="";
    private String home_pic;
    private String home_pic_tail="";
    private String name;
    private float pchg;
    private String pic;
    private String pic_tail="";
    private long priceTime;
    private int pushlish_type;
    private int star_type;
    private String symbol;
    private long sysTime;
    private String wid;
    private String work;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

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

    public String getHome_button_pic_tail() {
        return home_button_pic_tail;
    }

    public void setHome_button_pic_tail(String home_button_pic_tail) {
        this.home_button_pic_tail = home_button_pic_tail;
    }

    public String getHome_pic_tail() {
        return home_pic_tail;
    }

    public void setHome_pic_tail(String home_pic_tail) {
        this.home_pic_tail = home_pic_tail;
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

    public String getPic_tail() {
        return pic_tail;
    }

    public void setPic_tail(String pic_tail) {
        this.pic_tail = pic_tail;
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
        return "NowPriceBean{" +
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
