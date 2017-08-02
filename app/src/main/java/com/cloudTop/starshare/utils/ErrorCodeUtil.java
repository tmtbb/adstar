package com.cloudTop.starshare.utils;

import android.text.TextUtils;

import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.been.EventBusMessage;
import com.cloudTop.starshare.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2017/4/13.
 */
public class ErrorCodeUtil {
    private static int num = 2 ;
    /**
     * 弹出错误信息
     */
    public static void showEeorMsg(int errorCode) {
        if (!SocketAPINettyBootstrap.getInstance().isOpen()&&num>=0) {
            ToastUtils.showLong("网络连接失败,请检查网络");
            num--;
            return;
        }
        String msg = "";
        switch (errorCode) {
            case -101:
                msg = "异常操作";
                EventBus.getDefault().postSticky(new EventBusMessage(AppConstant.FREEZE_MOVEMENT));
                break;
            case -300:
                msg = "数据库错误";
                break;
            case -301:
                msg = "用户已经存在";
                break;
            case -302:
                msg = "密码错误";
                break;
            case -303:
                msg = "登录失效";
                break;
            case -304:
                msg = "用户已经注册";
                break;
//            case -601:
//                msg = "没有历史数据";
//                break;
//            case -602:
//                msg = "没有处理的历史数据";
//                break;
//            case -603:
//                msg = "没有balance";
//                break;
//            case -604:
//                msg = "没有用户信息";
//                break;
//            case -605:
//                msg = "银行信息失败";
//                break;
//            case -606:
//                msg = "没有现金历史数据";
//                break;
//            case -607:
//                msg = "没有历史交易数据";
//                break;
//            case -658:
//                msg = "没有历史明星数据";
//                break;
//            case -700:
//                msg = "数据库错误";
//                break;
//            case -701:
//                msg = "微信创建订单号失败";
//                break;
//            case -702:
//                msg = "微信创建交易记录失败";
//                break;
//            case -704:
//                msg = "创建Unionpay订单失败";
//                break;
//            case -801:
//                msg = "没有绑定银行卡";
//                break;
            case -802:
                msg = "绑定银行卡失败";
                break;
            case -803:
                msg = "银行卡不存在";
                break;
            case -804:
                msg = "解除绑定银行卡失败";
                break;
//            case -1101:
//                msg = "数据库无数据";
//                break;
//            case -1102:
//                msg = "获取明星信息失败";
//                break;
//            case -1103:
//                msg = "获取明星求购列表失败";
//                break;
//            case -1104:
//                msg = "添加明星成交订单信息失败";
//                break;
//            case -1105:
//                msg = "获取明星成交订单列表失败";
//                break;
            case -1106:
                msg = "获取明星成就失败";
                break;
            case -1107:
                msg = "获取明星经历失败";
                break;
            case -1108:
                msg = "搜索明星列表失败";
//                break;
//            case -1110:
//                msg = "获取特定类型的明星信息失败";
//                break;
//            case -1109:
//                msg = "获取明星类型失败";
//                break;
//            case -1201:
//                msg = "没有联系人数据";
//                break;
//            case -1202:
//                msg = "添加联系人数据失败";
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
