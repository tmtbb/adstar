package com.cloudTop.starshare.been;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MarketTypeBeen {

    /**
     * list : [{"name":"网红","type":1},{"name":"娱乐网红","type":2},{"name":"体育网红","type":3}]
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
         * name : 网红
         * type : 1
         */

        private String name;
        private int type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
