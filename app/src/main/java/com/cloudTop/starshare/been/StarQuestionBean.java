package com.cloudTop.starshare.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/29.
 */

public class StarQuestionBean {

    private ArrayList<CircleListBean> circle_list;

    public ArrayList<CircleListBean> getCircle_list() {
        return circle_list;
    }

    public void setCircle_list(ArrayList<CircleListBean> circle_list) {
        this.circle_list = circle_list;
    }

    public static class CircleListBean {
        /**
         * a_type : 2
         * answer_t : 0
         * ask_t : 1503972157
         * c_type : 0
         * headUrl : http://wx.qlogo.cn/mmopen/3Lqm1xHojtYnUQ1ic7CEE14diaNPRYbsfb07fubPCdrFRVufnHsDaulEOltibQ9NuHicZdecA3CFlVA63PGpVUhlSA/0
         * id : 9
         * nickName : BreakBlade
         * p_type : 1
         * purchased : 0
         * s_total : 0
         * sanswer : NULL
         * starcode : 1001
         * uask : 231
         * uid : 146
         * video_url :
         */

        private int a_type;
        private long answer_t;
        private long ask_t;
        private int c_type;
        private String headUrl;
        private long id;
        private String nickName;
        private int p_type;
        private int purchased;
        private long s_total;
        private String sanswer;
        private String starcode;
        private String uask;
        private long uid;
        private String video_url;

        public int getA_type() {
            return a_type;
        }

        public void setA_type(int a_type) {
            this.a_type = a_type;
        }

        public long getAnswer_t() {
            return answer_t;
        }

        public void setAnswer_t(long answer_t) {
            this.answer_t = answer_t;
        }

        public long getAsk_t() {
            return ask_t;
        }

        public void setAsk_t(long ask_t) {
            this.ask_t = ask_t;
        }

        public int getC_type() {
            return c_type;
        }

        public void setC_type(int c_type) {
            this.c_type = c_type;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getP_type() {
            return p_type;
        }

        public void setP_type(int p_type) {
            this.p_type = p_type;
        }

        public int getPurchased() {
            return purchased;
        }

        public void setPurchased(int purchased) {
            this.purchased = purchased;
        }

        public long getS_total() {
            return s_total;
        }

        public void setS_total(long s_total) {
            this.s_total = s_total;
        }

        public String getSanswer() {
            return sanswer;
        }

        public void setSanswer(String sanswer) {
            this.sanswer = sanswer;
        }

        public String getStarcode() {
            return starcode;
        }

        public void setStarcode(String starcode) {
            this.starcode = starcode;
        }

        public String getUask() {
            return uask;
        }

        public void setUask(String uask) {
            this.uask = uask;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }
    }
}
