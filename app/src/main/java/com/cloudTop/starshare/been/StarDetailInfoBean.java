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
        private String head_url;
        private String portray1;
        private String portray2;
        private String portray3;
        private String portray4;
        private String star_code;
        private String star_name;
        private int star_tpye;

        public long getAcc_id() {
            return acc_id;
        }

        public void setAcc_id(long acc_id) {
            this.acc_id = acc_id;
        }

        public String getBack_pic() {
            return back_pic;
        }

        public void setBack_pic(String back_pic) {
            this.back_pic = back_pic;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getPortray1() {
            return portray1;
        }

        public void setPortray1(String portray1) {
            this.portray1 = portray1;
        }

        public String getPortray2() {
            return portray2;
        }

        public void setPortray2(String portray2) {
            this.portray2 = portray2;
        }

        public String getPortray3() {
            return portray3;
        }

        public void setPortray3(String portray3) {
            this.portray3 = portray3;
        }

        public String getPortray4() {
            return portray4;
        }

        public void setPortray4(String portray4) {
            this.portray4 = portray4;
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
    }
}
