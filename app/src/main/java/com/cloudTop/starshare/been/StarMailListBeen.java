package com.cloudTop.starshare.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/28.
 */

public class StarMailListBeen {

    private ArrayList<DepositsinfoBean> depositsinfo;

    public ArrayList<DepositsinfoBean> getDepositsinfo() {
        return depositsinfo;
    }

    public void setDepositsinfo(ArrayList<DepositsinfoBean> depositsinfo) {
        this.depositsinfo = depositsinfo;
    }

    public static class DepositsinfoBean {
        /**
         * appoint : 0
         * faccid : 18657195470
         * head_url : http://ouim6qew1.bkt.clouddn.com/170810015649598bf5a1a6943.jpg
         * head_url_tail : 170810015649598bf5a1a6943.jpg
         * ownseconds : 339
         * starcode : 10001
         * starname : 程媛媛
         * uid : 241
         * work : 演员
         */

        private long appoint;
        private String faccid;
        private String head_url;
        private String head_url_tail;
        private long ownseconds;
        private String starcode;
        private String starname;
        private long uid;
        private String work;

        public long getAppoint() {
            return appoint;
        }

        public void setAppoint(long appoint) {
            this.appoint = appoint;
        }

        public String getFaccid() {
            return faccid;
        }

        public void setFaccid(String faccid) {
            this.faccid = faccid;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getHead_url_tail() {
            return head_url_tail;
        }

        public void setHead_url_tail(String head_url_tail) {
            this.head_url_tail = head_url_tail;
        }

        public long getOwnseconds() {
            return ownseconds;
        }

        public void setOwnseconds(long ownseconds) {
            this.ownseconds = ownseconds;
        }

        public String getStarcode() {
            return starcode;
        }

        public void setStarcode(String starcode) {
            this.starcode = starcode;
        }

        public String getStarname() {
            return starname;
        }

        public void setStarname(String starname) {
            this.starname = starname;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }
    }
}
