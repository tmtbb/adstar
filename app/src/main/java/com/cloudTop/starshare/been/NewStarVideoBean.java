package com.cloudTop.starshare.been;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class NewStarVideoBean {

    private List<CirclesBean> circles;
    private List<QuestionsBean> questions;

    public List<CirclesBean> getCircles() {
        return circles;
    }

    public void setCircles(List<CirclesBean> circles) {
        this.circles = circles;
    }

    public List<QuestionsBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionsBean> questions) {
        this.questions = questions;
    }

    public static class CirclesBean {
        /**
         * approve_dec_time : 3
         * approve_list : []
         * circle_id : 10034
         * comment_dec_time : 23
         * comment_list : []
         * content :
         * create_time : 1504082587
         * head_url : http://tva2.sinaimg.cn/crop.0.0.512.512.180/4e39d498jw8f9zphpwmorj20e80e80t6.jpg
         * head_url_tail : 4e39d498jw8f9zphpwmorj20e80e80t6.jpg
         * pic_url : null1504483079610.aac
         * pic_url_tail : null1504483079610.aac
         * symbol : 1001
         * symbol_name :
         */

        private int approve_dec_time;
        private int circle_id;
        private int comment_dec_time;
        private String content;
        private int create_time;
        private String head_url;
        private String head_url_tail;
        private String pic_url;
        private String pic_url_tail;
        private String symbol;
        private String symbol_name;
        private List<?> approve_list;
        private List<?> comment_list;

        public int getApprove_dec_time() {
            return approve_dec_time;
        }

        public void setApprove_dec_time(int approve_dec_time) {
            this.approve_dec_time = approve_dec_time;
        }

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
        }

        public int getComment_dec_time() {
            return comment_dec_time;
        }

        public void setComment_dec_time(int comment_dec_time) {
            this.comment_dec_time = comment_dec_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
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

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getPic_url_tail() {
            return pic_url_tail;
        }

        public void setPic_url_tail(String pic_url_tail) {
            this.pic_url_tail = pic_url_tail;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol_name() {
            return symbol_name;
        }

        public void setSymbol_name(String symbol_name) {
            this.symbol_name = symbol_name;
        }

        public List<?> getApprove_list() {
            return approve_list;
        }

        public void setApprove_list(List<?> approve_list) {
            this.approve_list = approve_list;
        }

        public List<?> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<?> comment_list) {
            this.comment_list = comment_list;
        }
    }

    public static class QuestionsBean {
        /**
         * a_type : 1
         * answer_t : 1504257016
         * ask_t : 1504256277
         * c_type : 0
         * headUrl : http://wx.qlogo.cn/mmopen/3Lqm1xHojtYnUQ1ic7CEE14diaNPRYbsfb07fubPCdrFRVufnHsDaulEOltibQ9NuHicZdecA3CFlVA63PGpVUhlSA/0
         * id : 65
         * nickName : BreakBlade
         * p_type : 1
         * purchased : 1
         * s_total : 0
         * sanswer : short_video1504257002.mp4
         * starcode : 1001
         * thumbnail : 1504663816610frame.jpg
         * thumbnailS : video1504257001.png
         * uask : 哈哈
         * uid : 146
         * videoTime : 15
         * videoTimeS : 0
         * video_url : 1504752371651record.mp4
         */

        private int a_type;
        private int answer_t;
        private int ask_t;
        private int c_type;
        private String headUrl;
        private int id;
        private String nickName;
        private int p_type;
        private int purchased;
        private int s_total;
        private String sanswer;
        private String starcode;
        private String thumbnail;
        private String thumbnailS;
        private String uask;
        private int uid;
        private int videoTime;
        private int videoTimeS;
        private String video_url;

        public int getA_type() {
            return a_type;
        }

        public void setA_type(int a_type) {
            this.a_type = a_type;
        }

        public int getAnswer_t() {
            return answer_t;
        }

        public void setAnswer_t(int answer_t) {
            this.answer_t = answer_t;
        }

        public int getAsk_t() {
            return ask_t;
        }

        public void setAsk_t(int ask_t) {
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public int getS_total() {
            return s_total;
        }

        public void setS_total(int s_total) {
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

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getThumbnailS() {
            return thumbnailS;
        }

        public void setThumbnailS(String thumbnailS) {
            this.thumbnailS = thumbnailS;
        }

        public String getUask() {
            return uask;
        }

        public void setUask(String uask) {
            this.uask = uask;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(int videoTime) {
            this.videoTime = videoTime;
        }

        public int getVideoTimeS() {
            return videoTimeS;
        }

        public void setVideoTimeS(int videoTimeS) {
            this.videoTimeS = videoTimeS;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }
    }
}
