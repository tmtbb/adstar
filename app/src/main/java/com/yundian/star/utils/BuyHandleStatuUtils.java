package com.yundian.star.utils;

/**
 * Created by sll on 2017/6/16.
 */

public class BuyHandleStatuUtils {
    public static String getHandleStatu(int handle) {
        String buyStatus = "";
        switch (handle) {
            case 0:
                buyStatus = "委托中";
                break;
            case 1:
                buyStatus = "委托中";
                break;
            case 2:
                buyStatus = "委托成功";
                break;
            case -1:
                buyStatus = "委托失败";
                break;
            case -2:
                buyStatus = "委托失败";
                break;
            default:
                buyStatus = "委托失败";
                break;
        }
        return buyStatus;
    }

    public static String getRechargeType(int type) {
        String rechargeType = "";    //1.微信 2.银行卡
        switch (type) {
            case 1:
                rechargeType = "微信";
                break;
            case 2:
                rechargeType = "银行卡";
                break;
            case 0:
                rechargeType = "支付宝";
                break;
        }

        return rechargeType;
    }
}
