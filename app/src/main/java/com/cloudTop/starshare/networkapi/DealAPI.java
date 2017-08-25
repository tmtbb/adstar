package com.cloudTop.starshare.networkapi;


import com.cloudTop.starshare.been.AliPayReturnBean;
import com.cloudTop.starshare.been.AssetDetailsBean;
import com.cloudTop.starshare.been.BankCardBean;
import com.cloudTop.starshare.been.BankInfoBean;
import com.cloudTop.starshare.been.BookingStarListBean;
import com.cloudTop.starshare.been.IdentityInfoBean;
import com.cloudTop.starshare.been.MeetStarStatusBean;
import com.cloudTop.starshare.been.MoneyDetailListBean;
import com.cloudTop.starshare.been.RequestResultBean;
import com.cloudTop.starshare.been.ResultCodeBeen;
import com.cloudTop.starshare.been.ReturnAmountBean;
import com.cloudTop.starshare.been.StatServiceListBean;
import com.cloudTop.starshare.been.WXPayReturnEntity;
import com.cloudTop.starshare.been.WithDrawCashHistoryBean;
import com.cloudTop.starshare.been.WithDrawCashReturnBean;
import com.cloudTop.starshare.listener.OnAPIListener;

import java.util.List;


/**
 * Created by yaowang on 2017/2/20.
 * 交易 行情相关接口
 */

public interface DealAPI {
    //    void products(OnAPIListener<List<ProductEntity>> listener);
//
//    //当前分时数据
//    void timeline(String exchangeName, String platformName, String symbol, int aType, OnAPIListener<List<CurrentTimeLineReturnEntity>> listener);
////    void timeline(String exchangeName, String platformName, String symbol, int aType, OnAPIListener<CurrentTimeLineReturnEntity> listener);
//
//    //当前报价
//    void currentPrice(List<SymbolInfosEntity> symbolInfos, OnAPIListener<List<CurrentPriceReturnEntity>> listener);
//
//    //加载Kchart
//    void kchart(String exchangeName, String platformName, String symbol, int aType, int chartType, OnAPIListener<List<CurrentTimeLineReturnEntity>> listener);
//
//    //建仓
//    void openPosition(long codeId, int buySell, double amount, double price, boolean isDeferred, OnAPIListener<CurrentPositionListReturnEntity> listener);
//
//    //当前仓位列表
//    void currentPositionList(int start, int count, OnAPIListener<List<CurrentPositionListReturnEntity>> listener);
//
//    //历史记录(处理/已处理)
//    void historyPositionList(int start, int count, OnAPIListener<List<HistoryPositionListReturnEntity>> listener);
//
//    //交易明细--历史记录(根据symbol)
//    void historyDealList(int start, int count, String symbol, OnAPIListener<List<HistoryPositionListReturnEntity>> listener);
//
//    //交易总概况
//    void totalDealInfo(OnAPIListener<TotalDealInfoEntity> listener);
//
//    //微信支付
    void weixinPay(String title, double price, OnAPIListener<WXPayReturnEntity> listener);

    //
//    void unionPay(String title, double price, OnAPIListener<Object> listener);  //银联支付
//
//    void payment(String outTradeNo, long amount, String content, String payType, OnAPIListener<UnionPayReturnEntity> listener);  //第三方支付
//
//    //提现
//    void cash(double money, long cardId, String pwd, OnAPIListener<WithDrawCashReturnEntity> listener);
//
//    //第三方  提现
//    void cashOut(long bid, long amount, String receiverBankName,
//                 String receiverBranchBankName, String receiverCardNo, String receiverAccountName,
//                 OnAPIListener<CashOutReturnEntity> listener);
//
//    //提现列表
    void cashList(int status, int startPos, int count, OnAPIListener<List<WithDrawCashHistoryBean>> listener);

    //
//    //提现列表
//    void currentPosition(double pid, OnAPIListener<CurrentPositionEntity> listener);
//
//    //提现列表
//    void profit(long tid, int handle, OnAPIListener<HistoryPositionListReturnEntity> listener);
//
//    void wxpayResult(String rid, int payResult, OnAPIListener<WXPayResultEntity> listener);//支付结果
//
    void bankCardList(OnAPIListener<BankCardBean> listener);//银行卡列表

    //
    void bankCardInfo(String cardNo, OnAPIListener<BankInfoBean> listener);//获取银行账户信息

    //
    void bindCard(String bankUsername, String account, OnAPIListener<BankInfoBean> listener);//获取银行账户信息

    void unBindCard(OnAPIListener<ResultCodeBeen> listener);//解绑操作

    //
//    void rechargeList(int startPos, int count, OnAPIListener<List<RechargeRecordItemEntity>> listener);//解绑操作
    void moneyList(String time, int status, int count, int startPos, OnAPIListener<List<MoneyDetailListBean>> listener);//资金明细

    void bookingStarList(int startPos, int count, OnAPIListener<List<BookingStarListBean>> listener);//预约明星列表

    void identityAuthentication(String realname, String id_card, OnAPIListener<RequestResultBean> listener);

    void dealPwd(String phone, String vToken, String vCode, long timestamp, int type, String pwd, OnAPIListener<RequestResultBean> listener);//预约明星列表

    void test(String title, double price, OnAPIListener<Object> listener);//预约明星列表

    void balance(OnAPIListener<AssetDetailsBean> listener);//余额

    void identity(OnAPIListener<IdentityInfoBean> listener);//身份

    void nikeName(String nickname, OnAPIListener<RequestResultBean> listener);//身份

    void starMeet(String starcode, long mid, String city_name, String appoint_time, int meet_type, String comment, OnAPIListener<RequestResultBean> listener);

    void statServiceList(String starcode, OnAPIListener<StatServiceListBean> listener);

    void alipay(String title, double price, OnAPIListener<AliPayReturnBean> listener);

    void cancelPay(String rid, int payResult, OnAPIListener<Object> listener);

    void cashOut(double price, String withdrawPwd, OnAPIListener<WithDrawCashReturnBean> listener);

    void meetStatus(int pos, int count, OnAPIListener<MeetStarStatusBean> listener);
    void getReturnAmount(long uid,OnAPIListener<ReturnAmountBean> listener);
}
