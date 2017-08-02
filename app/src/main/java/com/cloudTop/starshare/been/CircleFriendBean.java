package com.cloudTop.starshare.been;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class CircleFriendBean {

    private ArrayList<CircleListBean> circle_list;

    public ArrayList<CircleListBean> getCircle_list() {
        return circle_list;
    }

    public void setCircle_list(ArrayList<CircleListBean> circle_list) {
        this.circle_list = circle_list;
    }

    public static class CircleListBean {
        /**
         * approve_list : [{"uid":166,"user_name":""},{"uid":0,"user_name":""}]
         * circle_id : 10001
         * comment_list : [{"content":"111111111","direction":0,"priority":1,"uid":0,"user_name":""},{"content":"111111111","direction":0,"priority":2,"uid":0,"user_name":""},{"content":"111111111","direction":0,"priority":1,"uid":0,"user_name":""}]
         * content : 第一个动态
         * create_time : 0
         * head_url : http://tva2.sinaimg.cn/crop.0.0.512.512.180/4e39d498jw8f9zphpwmorj20e80e80t6.jpg
         * pic_url :
         * symbol : 1001
         * symbol_name : 林志玲
         */

        private long circle_id;
        private int approve_dec_time;
        private int comment_dec_time;
        private String content;
        private long create_time;
        private String head_url;
        private String pic_url;
        private String symbol;
        private String symbol_name;
        private List<ApproveListBean> approve_list;
        private List<CommentListBean> comment_list;

        public int getApprove_dec_time() {
            return approve_dec_time;
        }

        public void setApprove_dec_time(int approve_dec_time) {
            this.approve_dec_time = approve_dec_time;
        }

        public int getComment_dec_time() {
            return comment_dec_time;
        }

        public void setComment_dec_time(int comment_dec_time) {
            this.comment_dec_time = comment_dec_time;
        }

        public long getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(long circle_id) {
            this.circle_id = circle_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
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

        public List<ApproveListBean> getApprove_list() {
            return approve_list;
        }

        public void setApprove_list(List<ApproveListBean> approve_list) {
            this.approve_list = approve_list;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }


        public boolean hasFavort(){
            if(approve_list!=null && approve_list.size()>0){
                return true;
            }
            return false;
        }

        public boolean hasComment(){
            if(comment_list!=null && comment_list.size()>0){
                return true;
            }
            return false;
        }

        public int getCurUserFavortId(int curUserId){
            int favortid = -1;
            if(curUserId>0 && hasFavort()){
                for(CircleFriendBean.CircleListBean.ApproveListBean item : approve_list){
                    if(curUserId==item.getUid()){
                        favortid = (int)item.getUid();
                        return favortid;
                    }
                }
            }
            return favortid;
        }










        public static class ApproveListBean {
            /**
             * uid : 166
             * user_name :
             */

            private long uid;
            private String user_name;

            public long getUid() {
                return uid;
            }

            public void setUid(long uid) {
                this.uid = uid;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }

        public static class CommentListBean {
            /**
             * content : 111111111
             * direction : 0
             * priority : 1
             * uid : 0
             * user_name :
             */

            private String content;
            private int direction;
            private int priority;
            private long uid;
            private String user_name;
            private String symbol_name;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getDirection() {
                return direction;
            }

            public void setDirection(int direction) {
                this.direction = direction;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public long getUid() {
                return uid;
            }

            public void setUid(long uid) {
                this.uid = uid;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getSymbol_name() {
                return symbol_name;
            }

            public void setSymbol_name(String symbol_name) {
                this.symbol_name = symbol_name;
            }
        }
    }
}
