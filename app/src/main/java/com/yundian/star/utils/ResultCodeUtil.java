package com.yundian.star.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yundian.star.been.ResultCodeBeen;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketAPIResponse;


/**
 * Created by Administrator on 2017/4/13.
 */
public class ResultCodeUtil {
    /**
     * 弹出Result信息
     */
    public static void showEeorMsg(SocketAPIResponse socketAPIResponse) {
//        if (!SocketAPINettyBootstrap.getInstance().isOpen()) {
//            ToastUtils.showShort("网络连接失败,请检查网络");
//            return;
//        }
        //0,1这种不确定的不要在此工具类中显示
        ResultCodeBeen resultCodeBeen = JSON.parseObject(socketAPIResponse.jsonObject().toString(), ResultCodeBeen.class);
        String msg = "";
        switch (resultCodeBeen.getResult()) {
            case -101:
                msg = "异常操作";
                break;
//            case -402:
//                msg = "没有对应的分时数据";
//                break;
//            case -404:
//                msg = "没有对应的";
//                break;
            case -501:
                msg = "未到交易時間";
                break;
            case -502:
                msg = "該明星不能交易";
                break;
            case -503:
                msg = "订单不存在";
                break;
            case -504:
                msg = "订单已经被取消";
                break;
            case -505:
                msg = "更新订单状态失败";
                break;
            case -506:
                msg = "订单数据异常";
                break;
            case -507:
                msg = "订单取消失败";
                break;
//            case -601:
//                msg = "没有当天数据";
//                break;
//            case -602:
//                msg = "没有历史数据";
//                break;
//            case -603:
//                msg = "没有委托数据";
//                break;
//            case -604:
//                msg = "没有交易数据";
//                break;
//            default:
//                msg = "连接超时,请稍后重试";
//                break;
        }
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.showShort(msg);
        }
    }
}
