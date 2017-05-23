package com.yundian.star.been;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class StarStarAchBeen {

    /**
     * list : [{"achive":"发了发达了房间爱立法来发掘"},{"achive":"李经理发觉了附近拉飞机拉法"}]
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
         * achive : 发了发达了房间爱立法来发掘
         */

        private String achive;

        public String getAchive() {
            return achive;
        }

        public void setAchive(String achive) {
            this.achive = achive;
        }
    }
}
