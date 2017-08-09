package com.cloudTop.starshare.been;

import java.util.List;

/**
 * Created by Null on 2017/5/14.
 */

public class AdvBeen {

    /**
     * list : [{"code":"1001","name":"林志玲","pic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg"},{"code":"1008","name":"迪丽热巴","pic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg"},{"code":"1018","name":"angelababy ","pic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg"},{"code":"1016","name":"李沁","pic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg"},{"code":"1017","name":"黄渤","pic_url":"http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg"}]
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
         * code : 1001
         * name : 林志玲
         * pic_url : http://news.xinhuanet.com/fashion/2015-02/14/1114366926_14238179822181n.jpg
         */

        private String code;
        private String name;
        private String pic_url;

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

        @Override
        public String toString() {
            return "ListBean{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    '}';
        }
    }
}
