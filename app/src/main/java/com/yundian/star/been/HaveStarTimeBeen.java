package com.yundian.star.been;

/**
 * Created by Administrator on 2017/6/16.
 */

public class HaveStarTimeBeen {

    /**
     * result : 1
     * star_time : 500
     */

    private int result;
    private int star_time;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getStar_time() {
        return star_time;
    }

    public void setStar_time(int star_time) {
        this.star_time = star_time;
    }

    @Override
    public String toString() {
        return "HaveStarTimeBeen{" +
                "result=" + result +
                ", star_time=" + star_time +
                '}';
    }
}
