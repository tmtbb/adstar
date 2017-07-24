package com.yundian.star.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/21.
 */

public class DanMaKuInfo {

    private ArrayList<BarrageInfoBean> barrage_info;

    public ArrayList<BarrageInfoBean> getBarrage_info() {
        return barrage_info;
    }

    public void setBarrage_info(ArrayList<BarrageInfoBean> barrage_info) {
        this.barrage_info = barrage_info;
    }

    public static class BarrageInfoBean {
        /**
         * head_url :
         * order_num : 10
         * order_price : 1.26
         * order_type : 0
         * user_name : 张三
         */

        private String head_url;
        private int order_num;
        private double order_price;
        private int order_type;
        private String user_name;

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public int getOrder_num() {
            return order_num;
        }

        public void setOrder_num(int order_num) {
            this.order_num = order_num;
        }

        public double getOrder_price() {
            return order_price;
        }

        public void setOrder_price(double order_price) {
            this.order_price = order_price;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        @Override
        public String toString() {
            return "BarrageInfoBean{" +
                    "head_url='" + head_url + '\'' +
                    ", order_num=" + order_num +
                    ", order_price=" + order_price +
                    ", order_type=" + order_type +
                    ", user_name='" + user_name + '\'' +
                    '}';
        }
    }
}
