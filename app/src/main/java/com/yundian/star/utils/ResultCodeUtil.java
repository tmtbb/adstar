package com.yundian.star.utils;

import android.text.TextUtils;

import com.yundian.star.app.AppConstant;
import com.yundian.star.been.EventBusMessage;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketAPIResponse;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;


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
        int result = -1111;
        try {
            result = socketAPIResponse.jsonObject().getInt("result");
        } catch (JSONException e) {
           // LogUtils.loge(e.toString());
            //e.printStackTrace();
        }
        if (result != -1111) {
            String msg = "";
            LogUtils.loge("错误码" + result);
            switch (result) {
                case -101:
                    msg = "异常操作";
                    EventBus.getDefault().postSticky(new EventBusMessage(AppConstant.FREEZE_MOVEMENT));
                    break;
//                case -301:
//                    msg = "用户已经存在";
//                    break;
                case -302:
                    msg = "密码错误";
                    break;
                case -303:
                    msg = "登录失效";
                    break;
                case -304:
                    msg = "用户已经注册";
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
//                case -801:
//                    msg = "没有绑定银行卡";
//                    break;
                case -802:
                    msg = "绑定银行卡失败";
                    break;
                case -803:
                    msg = "银行卡不存在";
                    break;
                case -804:
                    msg = "解除绑定银行卡失败";
                    break;
                case -907:
                    msg = "用户明星时间不足";
                    break;
//            default:
//                msg = "连接超时,请稍后重试";
//                break;
            }
            if (!TextUtils.isEmpty(msg)) {
                ToastUtils.showShort(msg);
            }

        }

    }
}
