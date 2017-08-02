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
         * faccid : 13072714518
         * ownseconds : 111
         * starcode : 111
         * starname : 17682310986
         * uid : 124
         */

        private int appoint;
        private String faccid;
        private int ownseconds;
        private String starcode;
        private String starname;
        private int uid;

        public int getAppoint() {
            return appoint;
        }

        public void setAppoint(int appoint) {
            this.appoint = appoint;
        }

        public String getFaccid() {
            return faccid;
        }

        public void setFaccid(String faccid) {
            this.faccid = faccid;
        }

        public int getOwnseconds() {
            return ownseconds;
        }

        public void setOwnseconds(int ownseconds) {
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

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        @Override
        public String toString() {
            return "DepositsinfoBean{" +
                    "appoint=" + appoint +
                    ", faccid='" + faccid + '\'' +
                    ", ownseconds=" + ownseconds +
                    ", starcode='" + starcode + '\'' +
                    ", starname='" + starname + '\'' +
                    ", uid=" + uid +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StarMailListBeen{" +
                "depositsinfo=" + depositsinfo +
                '}';
    }
}
