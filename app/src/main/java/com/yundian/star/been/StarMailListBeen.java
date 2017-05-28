package com.yundian.star.been;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28.
 */

public class StarMailListBeen {

    private List<DepositsinfoBean> depositsinfo;

    public List<DepositsinfoBean> getDepositsinfo() {
        return depositsinfo;
    }

    public void setDepositsinfo(List<DepositsinfoBean> depositsinfo) {
        this.depositsinfo = depositsinfo;
    }

    public static class DepositsinfoBean {
        /**
         * uid : 10000002
         * ownseconds : 10001
         * appoint : 0
         * starcode : 1483422506
         * starname : 1
         * faccid : 0sd223kl
         * status : 1
         */

        private int uid;
        private int ownseconds;
        private int appoint;
        private int starcode;
        private int starname;
        private String faccid;
        private int status;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getOwnseconds() {
            return ownseconds;
        }

        public void setOwnseconds(int ownseconds) {
            this.ownseconds = ownseconds;
        }

        public int getAppoint() {
            return appoint;
        }

        public void setAppoint(int appoint) {
            this.appoint = appoint;
        }

        public int getStarcode() {
            return starcode;
        }

        public void setStarcode(int starcode) {
            this.starcode = starcode;
        }

        public int getStarname() {
            return starname;
        }

        public void setStarname(int starname) {
            this.starname = starname;
        }

        public String getFaccid() {
            return faccid;
        }

        public void setFaccid(String faccid) {
            this.faccid = faccid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
