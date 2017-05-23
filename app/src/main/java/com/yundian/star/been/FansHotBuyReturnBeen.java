package com.yundian.star.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/22.
 */

public class FansHotBuyReturnBeen {

    /**
     * list : [{"head":"","name":"","price":99,"time":"2017-05-17 15:37:49"},{"head":"headerUrl","name":"nickname","price":98,"time":"2017-05-17 15:21:49"}]
     * result : 1
     */

    private int result;
    private ArrayList<ListBean> list;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ArrayList<ListBean> getList() {
        return list;
    }

    public void setList(ArrayList<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * head :
         * name :
         * price : 99.0
         * time : 2017-05-17 15:37:49
         */

        private String head;
        private String name;
        private double price;
        private String time;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
