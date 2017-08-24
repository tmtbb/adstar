package com.cloudTop.starshare.ui.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.cloudTop.starshare.ui.main.contract.NewInfoContract;

import java.util.ArrayList;

/**
 * Created by Null on 2017/5/6.
 */

public class NewsInforModel implements NewInfoContract.Model, Parcelable {


    /**
     * list : [{"id":1,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"bb","showpic_url":"http://tupian.enterdesk.com/2012/0514/zyz/03/08.jpg","starcode":"4","starname":"柳岩","subject_name":"网友偶遇刘强东章泽天 女方露甜美笑容男方玩手机","times":"2017-05-11 00:00:00"},{"id":2,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"gg","showpic_url":"http://www.wanhuajing.com/pic/1605/1815/3129838/7_640_312.jpg","starcode":"2","starname":"刘诗诗","subject_name":"70岁老夫妻邮轮上拍婚纱照 拿到相册后气得想烧掉","times":"2017-05-10 00:00:00"},{"id":3,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"tt","showpic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg","starcode":"3","starname":"测试","subject_name":"外交大家钱其琛:美国人站起说不谈了 他纹丝不动","times":"2017-05-09 00:00:00"},{"id":4,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"bb","showpic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg","starcode":"5","starname":"测试","subject_name":"网友偶遇刘强东章泽天 女方露甜美笑容男方玩手机","times":"2017-05-08 00:00:00"},{"id":5,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"bb","showpic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg","starcode":"6","starname":"测试","subject_name":"网友偶遇刘强东章泽天 女方露甜美笑容男方玩手机","times":"2017-05-07 00:00:00"},{"id":6,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"bb","showpic_url":"http://www.wanhuajing.com/pic/1605/1815/3129838/7_640_312.jpg","starcode":"7","starname":"测试","subject_name":"网友偶遇刘强东章泽天 女方露甜美笑容男方玩手机","times":"2017-05-06 00:00:00"},{"id":7,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"bb","showpic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg","starcode":"8","starname":"测试","subject_name":"网友偶遇刘强东章泽天 女方露甜美笑容男方玩手机","times":"2017-05-11 00:00:00"},{"id":8,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"bb","showpic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg","starcode":"9","starname":"测试","subject_name":"70岁老夫妻邮轮上拍婚纱照 拿到相册后气得想烧掉","times":"2017-05-11 00:00:00"},{"id":9,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"bb","showpic_url":"http://www.wanhuajing.com/pic/1605/1815/3129838/7_640_312.jpg","starcode":"10","starname":"da","subject_name":"70岁老夫妻邮轮上拍婚纱照 拿到相册后气得想烧掉","times":"2017-05-11 00:00:00"},{"id":10,"link_url":"http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg","remarks":"bb","showpic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg","starcode":"11","starname":"as","subject_name":"网友偶遇刘强东章泽天 女方露甜美笑容男方玩手机","times":"2017-05-12 00:00:00"}]
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

    public static class ListBean implements Parcelable {
        /**
         * id : 1
         * link_url : http://mp.weixin.qq.com/s/UEUf6qnf7sy2s3e86GNpHg
         * remarks : bb
         * showpic_url : http://tupian.enterdesk.com/2012/0514/zyz/03/08.jpg
         * starcode : 4
         * starname : 柳岩
         * subject_name : 网友偶遇刘强东章泽天 女方露甜美笑容男方玩手机
         * times : 2017-05-11 00:00:00
         */

        private int id;
        private String link_url;
        private String link_url_tail="";
        private String remarks;
        private String showpic_url;
        private String showpic_url_tail="";
        private String starcode;
        private String starname;
        private String subject_name;
        private String times;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink_url() {
            return link_url_tail;
        }

        public void setLink_url(String link_url) {
            this.link_url_tail = link_url;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getShowpic_url() {
            return showpic_url_tail;
        }

        public void setShowpic_url(String showpic_url) {
            this.showpic_url_tail = showpic_url;
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

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.link_url);
            dest.writeString(this.remarks);
            dest.writeString(this.showpic_url);
            dest.writeString(this.starcode);
            dest.writeString(this.starname);
            dest.writeString(this.subject_name);
            dest.writeString(this.times);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readInt();
            this.link_url = in.readString();
            this.remarks = in.readString();
            this.showpic_url = in.readString();
            this.starcode = in.readString();
            this.starname = in.readString();
            this.subject_name = in.readString();
            this.times = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeList(this.list);
    }

    public NewsInforModel() {
    }

    protected NewsInforModel(Parcel in) {
        this.result = in.readInt();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<NewsInforModel> CREATOR = new Parcelable.Creator<NewsInforModel>() {
        @Override
        public NewsInforModel createFromParcel(Parcel source) {
            return new NewsInforModel(source);
        }

        @Override
        public NewsInforModel[] newArray(int size) {
            return new NewsInforModel[size];
        }
    };
}
