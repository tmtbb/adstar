package com.yundian.star.utils;

import com.yundian.star.R;

import static android.R.attr.type;

/**
 * Created by sll on 2017/6/16.
 */

public class BuyHandleStatuUtils {
    /**
     * 获取委托类型
     *
     * @param handle
     * @return
     */
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

    /**
     * 获取充值方式
     *
     * @param type
     * @return
     */
    public static String getRechargeType(int type) {
        String rechargeType = "";    //1.微信 2.银行卡
        switch (type) {
            case 1:
                rechargeType = "微信";
                break;
            case 2:
                rechargeType = "银行卡";
                break;
            case 3:
                rechargeType = "支付宝";
                break;
        }

        return rechargeType;
    }

    /**
     * 获取充值状态
     *
     * @return
     */
    public static String getRechargeStatus(int status) {
        String statusType = "";
        switch (status) {
            case 1:
                statusType = "处理中";
                break;
            case 2:
                statusType = "充值成功";
                break;
            case 3:
                statusType = "充值失败";
                break;
            case 4:
                statusType = "充值取消";
                break;

        }
        return statusType;
    }

    public static int getRechargeImg(int status) {
        int imgId = 0;
        switch (status) {
            case 1:
                imgId = R.drawable.wx_logo;
                break;
            case 2:
                imgId = R.drawable.bank_icon;  //银联
                break;
            case 3:
                imgId = R.drawable.icon_alipay;
                break;
        }
        return imgId;
    }
}
