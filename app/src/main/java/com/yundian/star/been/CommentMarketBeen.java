package com.yundian.star.been;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/22.
 */

public class CommentMarketBeen {

    /**
     * commentsinfo : [{"cms_time":0,"comments":"哈哈","fans_id":"146","head_url":"http://ppt.downhot.com/d/file/p/2014/03/13/0673249a8d8942271ac07b975049b531.jpg","nick_name":"13072714518","star_code":"1000"},{"cms_time":0,"comments":"哈哈","fans_id":"146","head_url":"noPohotoUrl","nick_name":"13072714518","star_code":"1000"},{"cms_time":1496630991,"comments":"尬","fans_id":"61","head_url":"http://tva1.sinaimg.cn/crop.0.0.512.512.180/686fe7e0jw8f114yfoiqkj20e80e8glw.jpg","nick_name":"徐佳莹","star_code":"1000"}]
     * total_count : 3
     */

    private int total_count;
    private ArrayList<CommentsinfoBean> commentsinfo;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public ArrayList<CommentsinfoBean> getCommentsinfo() {
        return commentsinfo;
    }

    public void setCommentsinfo(ArrayList<CommentsinfoBean> commentsinfo) {
        this.commentsinfo = commentsinfo;
    }

    public static class CommentsinfoBean {
        /**
         * cms_time : 0
         * comments : 哈哈
         * fans_id : 146
         * head_url : http://ppt.downhot.com/d/file/p/2014/03/13/0673249a8d8942271ac07b975049b531.jpg
         * nick_name : 13072714518
         * star_code : 1000
         */

        private int cms_time;
        private String comments;
        private String fans_id;
        private String head_url;
        private String nick_name;
        private String star_code;

        public int getCms_time() {
            return cms_time;
        }

        public void setCms_time(int cms_time) {
            this.cms_time = cms_time;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getFans_id() {
            return fans_id;
        }

        public void setFans_id(String fans_id) {
            this.fans_id = fans_id;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getStar_code() {
            return star_code;
        }

        public void setStar_code(String star_code) {
            this.star_code = star_code;
        }

        @Override
        public String toString() {
            return "CommentsinfoBean{" +
                    "cms_time=" + cms_time +
                    ", comments='" + comments + '\'' +
                    ", fans_id='" + fans_id + '\'' +
                    ", head_url='" + head_url + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", star_code='" + star_code + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CommentMarketBeen{" +
                "total_count=" + total_count +
                ", commentsinfo=" + commentsinfo +
                '}';
    }
}
