package com.cloudTop.starshare.been;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class StarExperienceBeen {

    /**
     * list : [{"experience":"afafafafafa"},{"experience":"fafdafafafafafaafaf"}]
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
         * experience : afafafafafa
         */

        private String experience;

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }
    }
}
