package com.cloudTop.starshare.been;

/**
 * Created by Administrator on 2017/8/19.
 */

public class QiNiuAdressBean {

    /**
     * QINIU_URL_HUABEI : http://out9d2vy4.bkt.clouddn.com/
     * QINIU_URL_HUADONG : http://out97p5uv.bkt.clouddn.com/
     * QINIU_URL_HUANAN : http://ouim6qew1.bkt.clouddn.com/
     */

    private String QINIU_URL_HUABEI;
    private String QINIU_URL_HUADONG;
    private String QINIU_URL_HUANAN;

    public String getQINIU_URL_HUABEI() {
        return QINIU_URL_HUABEI;
    }

    public void setQINIU_URL_HUABEI(String QINIU_URL_HUABEI) {
        this.QINIU_URL_HUABEI = QINIU_URL_HUABEI;
    }

    public String getQINIU_URL_HUADONG() {
        return QINIU_URL_HUADONG;
    }

    public void setQINIU_URL_HUADONG(String QINIU_URL_HUADONG) {
        this.QINIU_URL_HUADONG = QINIU_URL_HUADONG;
    }

    public String getQINIU_URL_HUANAN() {
        return QINIU_URL_HUANAN;
    }

    public void setQINIU_URL_HUANAN(String QINIU_URL_HUANAN) {
        this.QINIU_URL_HUANAN = QINIU_URL_HUANAN;
    }

    @Override
    public String toString() {
        return "QiNiuAdressBean{" +
                "QINIU_URL_HUABEI='" + QINIU_URL_HUABEI + '\'' +
                ", QINIU_URL_HUADONG='" + QINIU_URL_HUADONG + '\'' +
                ", QINIU_URL_HUANAN='" + QINIU_URL_HUANAN + '\'' +
                '}';
    }
}
