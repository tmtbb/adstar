package com.yundian.star.utils;

/**
 * Created by sll on 2017/6/16.
 */

public class BuyHandleStatuUtils {
    public static String getHandleStatu(int handle) {
        String buyStatus = "";
        switch (handle) {
            case 0:
                buyStatus = "挂单中";
                break;
            case 1:
                buyStatus = "匹配中";
                break;
            case 3:
                buyStatus = "挂单完成";
                break;
            default:
                buyStatus = "失败";
                break;
        }
        return buyStatus;
    }
}
