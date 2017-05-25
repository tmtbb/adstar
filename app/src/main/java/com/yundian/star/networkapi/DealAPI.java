package com.yundian.star.networkapi;


import com.yundian.star.been.WXPayReturnEntity;
import com.yundian.star.listener.OnAPIListener;

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
//    void cashList(int status, int startPos, int count, OnAPIListener<List<WithDrawCashReturnEntity>> listener);
//
//    //提现列表
//    void currentPosition(double pid, OnAPIListener<CurrentPositionEntity> listener);
//
//    //提现列表
//    void profit(long tid, int handle, OnAPIListener<HistoryPositionListReturnEntity> listener);
//
//    void wxpayResult(String rid, int payResult, OnAPIListener<WXPayResultEntity> listener);//支付结果
//
//    void bankCardList(OnAPIListener<List<BankCardEntity>> listener);//银行卡列表
//
//    void bankName(String cardNo, OnAPIListener<BankInfoEntity> listener);//获取银行账户信息
//
//    void bindCard(long bankId, String bankName, String branchBank, String cardNo, String name, OnAPIListener<BankInfoEntity> listener);//获取银行账户信息
//
//    void unBindCard(long bankCardId, String verCode, OnAPIListener<Object> listener);//解绑操作
//
//    void rechargeList(int startPos, int count, OnAPIListener<List<RechargeRecordItemEntity>> listener);//解绑操作
}
