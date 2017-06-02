package com.yundian.star.been;

import java.util.List;

/**
 * Created by Null on 2017/5/14.
 */

public class AdvBeen {

    /**
     * list : [{"code":"111","name":"云","pic_url":"http://pic27.nipic.com/20130320/3822951_105204803000_2.jpg","type":1},{"code":"222","name":"科技","pic_url":"http://pic27.nipic.com/20130320/3822951_105204803000_2.jpg","type":2},{"code":"333","name":"啊","pic_url":"http://pic27.nipic.com/20130320/3822951_105204803000_2.jpg","type":1},{"code":"444","name":"啊啊","pic_url":"http://pic27.nipic.com/20130320/3822951_105204803000_2.jpg","type":1}]
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
         * code : 111
         * name : 云
         * pic_url : http://pic27.nipic.com/20130320/3822951_105204803000_2.jpg
         * type : 1
         */

        private String code;
        private String name;
        private String pic_url;
        private int type;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AdvBeen{" +
                "result=" + result +
                ", list=" + list +
                '}';
    }
}
