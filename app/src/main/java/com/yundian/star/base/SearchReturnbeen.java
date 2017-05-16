package com.yundian.star.base;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class SearchReturnbeen {

    /**
     * list : [{"code":"1002","name":"andy"}]
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
         * code : 1002
         * name : andy
         */

        private String code;
        private String name;

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

        @Override
        public String toString() {
            return "ListBean{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SearchReturnbeen{" +
                "result=" + result +
                ", list=" + list +
                '}';
    }
}
