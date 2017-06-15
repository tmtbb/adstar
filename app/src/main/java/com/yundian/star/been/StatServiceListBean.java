package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sll on 2017/6/15.
 */

public class StatServiceListBean implements Parcelable{

    /**
     * list : [{"mid":"10","url1":"http://wx.qlogo.cn/mmopen/VUhlSA/0","url2":"http://wx.qlogo.cn/mmopen/VA63PGpVUhlSA/1","name":"服务1","price":"100"},{"mid":"11","url1":"http://wx.qlogo.cn/mmopen/FlVA63PGpVUhlSA/2","url2":"http://wx.qlogo.cn/mmopen/3CFlVA63PGpVUhlSA/3","name":"服务2","price":"200"}]
     * result : 1
     */

    private int result;
    private List<ListBean> list;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * mid : 10
         * url1 : http://wx.qlogo.cn/mmopen/VUhlSA/0
         * url2 : http://wx.qlogo.cn/mmopen/VA63PGpVUhlSA/1
         * name : 服务1
         * price : 100
         */

        private String mid;
        private String url1;
        private String url2;
        private String name;
        private String price;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getUrl2() {
            return url2;
        }

        public void setUrl2(String url2) {
            this.url2 = url2;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "mid='" + mid + '\'' +
                    ", url1='" + url1 + '\'' +
                    ", url2='" + url2 + '\'' +
                    ", name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
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

    public StatServiceListBean() {
    }

    protected StatServiceListBean(Parcel in) {
        this.result = in.readInt();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Creator<StatServiceListBean> CREATOR = new Creator<StatServiceListBean>() {
        @Override
        public StatServiceListBean createFromParcel(Parcel source) {
            return new StatServiceListBean(source);
        }

        @Override
        public StatServiceListBean[] newArray(int size) {
            return new StatServiceListBean[size];
        }
    };

    @Override
    public String toString() {
        return "StatServiceListBean{" +
                "result=" + result +
                ", list=" + list +
                '}';
    }
}
