package com.yundian.star.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/16.
 */

public class OptionsStarListBeen {

    /**
     * list : [{"head":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1843302634,2859276068&fm=23&gp=0.jpg","name":"林志玲","price":100.5,"starcode":"1001","updown":-0.34},{"head":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494927203507&di=bca0ba6fad25a411b6f398b80f96d2d1&imgtype=0&src=http%3A%2F%2Fawb.img.xmtbang.com%2Fimg%2Fuploadnew%2F201510%2F22%2F118751c34e2e426f999d35f9b982f9c7.jpg","name":"andy","price":100.5,"starcode":"1002","updown":-0.34}]
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
         * head : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1843302634,2859276068&fm=23&gp=0.jpg
         * name : 林志玲
         * price : 100.5
         * starcode : 1001
         * updown : -0.34

         */

        private String head;
        private String name;
        private double price;
        private String starcode;
        private double updown;

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

        public String getStarcode() {
            return starcode;
        }

        public void setStarcode(String starcode) {
            this.starcode = starcode;
        }

        public double getUpdown() {
            return updown;
        }

        public void setUpdown(double updown) {
            this.updown = updown;
        }

    }
}
