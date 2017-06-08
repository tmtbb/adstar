package com.yundian.star.app;

/**
 * Created by yaowang on 2017/2/20.
 */

public interface SocketAPIConstant {

    interface ReqeutType {
        byte Error = 0;
        byte User = 3;
        byte Time = 4;
        byte Deal = 5;
        byte Verify = 1;
        byte History = 6;
        byte Bank = 8;
        byte Pay = 7;
        byte Wangyi = 9;
        byte NewInfos = 10;
        byte SearchStar = 11;
        byte Inquirylist = 12;
    }

    interface OperateCode {
        Short Login = 3003;
        Short WXLogin = 3013;
        Short Products = 5001;
        Short VerifyCode = 3011;
        Short Register = 3001;
        Short DealPwd = 3005;
        Short Test = 3000;
        Short ProductList = 5005;
        Short TimeLine = 4003;
        Short CurrentPrice = 4001;
        Short KChart = 4005;
        Short Position = 5003;
        Short History = 6001;
        Short Balance = 3007;
        Short MoneyDetail = 6003;
        Short WXPay = 7033;
        Short Cash = 8001;
        Short CashOut = 7045;
        Short CashList = 6005;
        Short Token = 3009;
        Short CurrentPosition = 5007;
        Short Profit = 6007;
        Short PayResult = 1035;
        Short BankCard = 8003;
        Short BankName = 8009;
        Short BindCard = 8005;
        Short UnBindCard = 8007;
        Short RechargeRecord = 1019;
        Short UnionPay = 7039;
        Short WXBind = 3015;
        Short ResetPasswd = 3019;
        Short WangYi = 9005;
        Short NewInfo = 10013;
        Short Banner = 10015;
        Short SearchStar = 11007;
        Short OptionStarList = 11013;
        Short MarketType = 11001;
        Short MarketStar = 11003;
        Short StarBrief = 11005;
        Short StarExperience = 11009;
        Short Starachive = 11011;
        Short SeekLike = 11021;
        Short TransferList = 11019;
        Short FansComments = 10017;
        Short Starmaillist = 6013;
        Short BookingStar = 6013;
        Short Identity = 3021;
        Short AddFriend = 9099;
        Short ReduceTime = 9017;
        Short IdentityInfo = 3027;
        Short Inquiry = 12003;
        Short Starstatist = 11017;
        Short AddComment = 12001;
        Short StarList = 4007;
        Short Srealtime = 4001;


    }
}
