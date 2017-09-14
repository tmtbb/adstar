package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/7/12.
 */

public class StarDetailInfoBean {

    /**
     * resultvalue : {"acc_id":5,"back_pic":"http://img3.cache.netease.com/photo/0003/2016-03-22/BIP44I4O00B60003.jpg","head_url":"http://tva2.sinaimg.cn/crop.0.0.750.750.180/6ce2a3d1jw8f359ugioszj20ku0kudgh.jpg","portray1":"http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg","portray2":"http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg","portray3":"http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg","portray4":"http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg","star_code":"1005","star_name":"王思聪","star_tpye":0}
     */

    private ResultvalueBean resultvalue;

    public ResultvalueBean getResultvalue() {
        return resultvalue;
    }

    public void setResultvalue(ResultvalueBean resultvalue) {
        this.resultvalue = resultvalue;
    }

    public static class ResultvalueBean {
        /**
         * acc_id : 5
         * back_pic : http://img3.cache.netease.com/photo/0003/2016-03-22/BIP44I4O00B60003.jpg
         * head_url : http://tva2.sinaimg.cn/crop.0.0.750.750.180/6ce2a3d1jw8f359ugioszj20ku0kudgh.jpg
         * portray1 : http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg
         * portray2 : http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg
         * portray3 : http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg
         * portray4 : http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg
         * star_code : 1005
         * star_name : 王思聪
         * star_tpye : 0
         */

        private long acc_id;
        private String back_pic;
        private String back_pic_tail="";
        private String head_url;
        private String head_url_tail="";
        private String portray1;
        private String portray1_tail="";
        private String portray2;
        private String portray2_tail="";
        private String portray3;
        private String portray3_tail="";
        private String portray4;
        private String portray4_tail="";
        private String star_code;
        private String star_name;
        private String work;
        private int star_tpye;
        private int publish_type;

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public long getAcc_id() {
            return acc_id;
        }

        public void setAcc_id(long acc_id) {
            this.acc_id = acc_id;
        }

        public String getBack_pic_tail() {
            return back_pic_tail;
        }

        public void setBack_pic_tail(String back_pic_tail) {
            this.back_pic_tail = back_pic_tail;
        }

        public String getHead_url_tail() {
            return head_url_tail;
        }

        public void setHead_url_tail(String head_url_tail) {
            this.head_url_tail = head_url_tail;
        }

        public String getPortray1_tail() {
            return portray1_tail;
        }

        public void setPortray1_tail(String portray1_tail) {
            this.portray1_tail = portray1_tail;
        }

        public String getPortray2_tail() {
            return portray2_tail;
        }

        public void setPortray2_tail(String portray2_tail) {
            this.portray2_tail = portray2_tail;
        }

        public String getPortray3_tail() {
            return portray3_tail;
        }

        public void setPortray3_tail(String portray3_tail) {
            this.portray3_tail = portray3_tail;
        }

        public String getPortray4_tail() {
            return portray4_tail;
        }

        public void setPortray4_tail(String portray4_tail) {
            this.portray4_tail = portray4_tail;
        }

        public String getStar_code() {
            return star_code;
        }

        public void setStar_code(String star_code) {
            this.star_code = star_code;
        }

        public String getStar_name() {
            return star_name;
        }

        public void setStar_name(String star_name) {
            this.star_name = star_name;
        }

        public int getStar_tpye() {
            return star_tpye;
        }

        public void setStar_tpye(int star_tpye) {
            this.star_tpye = star_tpye;
        }

        public int getPublish_type() {
            return publish_type;
        }

        public void setPublish_type(int publish_type) {
            this.publish_type = publish_type;
        }

        @Override
        public String toString() {
            return "ResultvalueBean{" +
                    "acc_id=" + acc_id +
                    ", back_pic='" + back_pic + '\'' +
                    ", head_url='" + head_url + '\'' +
                    ", portray1='" + portray1 + '\'' +
                    ", portray2='" + portray2 + '\'' +
                    ", portray3='" + portray3 + '\'' +
                    ", portray4='" + portray4 + '\'' +
                    ", star_code='" + star_code + '\'' +
                    ", star_name='" + star_name + '\'' +
                    ", star_tpye=" + star_tpye +
                    '}';
        }
    }
}
