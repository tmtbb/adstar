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
        byte Search = 13;
        byte BuyOrSell = 5;
    }

    interface OperateCode {
        Short Products = 5001;
        Short Login = 3003;
        Short WXLogin = 3013;
        Short VerifyCode = 3011;
        Short Register = 3001;
        Short DealPwd = 3005;
        Short Test = 3000;
        Short ProductList = 5005;
        Short TimeLine = 4003;
        Short CurrentPrice = 4001;
        Short KChart = 4005;
        Short Position = 5003;
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
        Short SearchStar = 13001;
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
        Short AskToBuy = 5001;
        Short IsRegister = 3029;
        Short NikeName = 3031;
        Short StartCount = 10023;
        Short StarType = 10019;
        Short StarMeet = 10021;
        Short TradingStatus = 5005;
        Short CancelOrder = 5009;
        Short CheckPayPas = 7011;
        Short SureOrder = 5007;

        Short TheDayOrder = 6007;
        Short HistoryOrder = 6009;
        Short HistoryEntur = 6005;
        Short OrderFansList = 6015;

        Short TodayEntrust = 6001;
        Short HistoryEntrust = 6005;
        Short TodayDeal = 6007;
        Short HistoryDeal = 6009;
        Short Fans = 6011;
        Short StarInfo = 10001;
        Short ALiPay = 7049;
        Short HaveStarTime = 10025;
        Short StarShellTime = 10027;
        Short BuyShellCom = 6017;
        Short Update = 3033;
        Short CancelPay = 7055;



    }
}
