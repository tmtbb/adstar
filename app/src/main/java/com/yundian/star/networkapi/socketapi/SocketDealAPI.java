package com.yundian.star.networkapi.socketapi;


import com.yundian.star.app.SocketAPIConstant;
import com.yundian.star.been.AliPayReturnBean;
import com.yundian.star.been.AssetDetailsBean;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.been.IdentityInfoBean;
import com.yundian.star.been.MoneyDetailListBean;
import com.yundian.star.been.RequestResultBean;
import com.yundian.star.been.StatServiceListBean;
import com.yundian.star.been.WXPayReturnEntity;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.DealAPI;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;

import java.util.HashMap;
import java.util.List;

import static com.yundian.star.R.string.nickname;
import static com.yundian.star.R.string.price;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketDealAPI extends SocketBaseAPI implements DealAPI {
//    @Override
//    public void products(OnAPIListener<List<ProductEntity>> listener) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        LogUtil.d("商品列表请求数据--------");
//        map.put("pid", 1002);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Products,
//                SocketAPIConstant.ReqeutType.Deal, map);
//        requestEntitys(socketDataPacket, "goodsinfo", ProductEntity.class, listener);
//    }
//
//    //分时数据
//    @Override
//    public void timeline(String exchangeName, String platformName, String symbol, int aType,
//                         OnAPIListener<List<CurrentTimeLineReturnEntity>> listener) {
//        LogUtil.d("----------------请求分时数据");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("exchangeName", exchangeName);
//        map.put("platformName", platformName);
//        map.put("symbol", symbol);
//        map.put("aType", aType);  //4
//        map.put("start", 1);
//        map.put("count", 50);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.TimeLine,
//                SocketAPIConstant.ReqeutType.Time, map);
//        requestEntitys(socketDataPacket, "priceinfo", CurrentTimeLineReturnEntity.class, listener);
//    }
//
//    //当前报价
//    @Override
//    public void currentPrice(List<SymbolInfosEntity> symbolInfos
//            , OnAPIListener<List<CurrentPriceReturnEntity>> listener) {
//        LogUtil.d("----------请求当前报价数据");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(symbolInfos.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        map.put("symbolInfos", jsonArray);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.CurrentPrice,
//                SocketAPIConstant.ReqeutType.Time, map);
//        requestEntitys(socketDataPacket, "priceinfo", CurrentPriceReturnEntity.class, listener);
////        requestEntity(socketDataPacket, CurrentPriceReturnEntity.class, listener);
//    }
//
//    @Override
//    public void kchart(String exchangeName, String platformName, String symbol, int aType, int chartType, OnAPIListener<List<CurrentTimeLineReturnEntity>> listener) {
//        LogUtil.d("请求K线数据");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("exchangeName", exchangeName);
//        map.put("platformName", platformName);
//        map.put("symbol", symbol);
////        map.put("aType", aType);  //4
//        map.put("chartType", chartType);//K线类型	60-1分钟K线，300-5分K线，900-15分K线，1800-30分K线，3600-60分K线，5-日K线
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.KChart,
//                SocketAPIConstant.ReqeutType.Time, map);
//        requestEntitys(socketDataPacket, "priceinfo", CurrentTimeLineReturnEntity.class, listener);
//    }
//
//
//    @Override
//    public void openPosition(long codeId, int buySell, double amount, double price, boolean isDeferred, OnAPIListener<CurrentPositionListReturnEntity> listener) {
//        LogUtil.d("请求建仓数据");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("codeId", codeId);
//        map.put("buySell", buySell);  //建仓方向
//        map.put("amount", amount);
//        map.put("deferred", isDeferred);
//        map.put("price", price);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Position,
//                SocketAPIConstant.ReqeutType.Deal, map);
//        requestEntity(socketDataPacket, CurrentPositionListReturnEntity.class, listener);
//    }
//
//    @Override
//    public void currentPositionList(int start, int count, OnAPIListener<List<CurrentPositionListReturnEntity>> listener) {
//        LogUtil.d("当前仓位列表请求数据");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("start", start);
//        map.put("count", count);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.ProductList,
//                SocketAPIConstant.ReqeutType.Time, map);
//        requestEntitys(socketDataPacket, "positioninfo", CurrentPositionListReturnEntity.class, listener);
//    }
//
//    @Override
//    public void historyPositionList(int start, int count, OnAPIListener<List<HistoryPositionListReturnEntity>> listener) {
//        LogUtil.d("仓位历史记录请求数据");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("start", start);
//        map.put("count", count);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.History,
//                SocketAPIConstant.ReqeutType.History, map);
//        requestEntitys(socketDataPacket, "positioninfo", HistoryPositionListReturnEntity.class, listener);
//    }
//
//    @Override
//    public void historyDealList(int start, int count, String symbol, OnAPIListener<List<HistoryPositionListReturnEntity>> listener) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("start", start);  //!!!!
//        map.put("count", count);
//        map.put("symbol", symbol);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.History,
//                SocketAPIConstant.ReqeutType.History, map);
//        requestEntitys(socketDataPacket, "positioninfo", HistoryPositionListReturnEntity.class, listener);
//    }
//
//    @Override
//    public void totalDealInfo(OnAPIListener<TotalDealInfoEntity> listener) {
//        LogUtil.d("请求交易总概况数据");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Total,
//                SocketAPIConstant.ReqeutType.History, map);
//        requestEntity(socketDataPacket, TotalDealInfoEntity.class, listener);
//    }

    @Override
    public void weixinPay(String title, double price, OnAPIListener<WXPayReturnEntity> listener) {
        LogUtils.logd("请求微信支付");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("title", title);
        map.put("price", price);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.WXPay,
                SocketAPIConstant.ReqeutType.Pay, map);
        requestEntity(socketDataPacket, WXPayReturnEntity.class, listener);
    }

    @Override
    public void moneyList(String time,int status, int count, int startPos, OnAPIListener<List<MoneyDetailListBean>> listener) {
        LogUtils.logd("请求钱包明细");
        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", SharePrefUtil.getInstance().getUserId());
//        map.put("token", SharePrefUtil.getInstance().getToken());
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("token", SharePrefUtil.getInstance().getToken()); //临时写死
        map.put("status", 0); //(1:处理中,2:成功,3:失败),不传则查所有状态
        map.put("count", count);
        map.put("startPos", startPos);
        map.put("time", time);  //time  不传是获取所有  1 2 3
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.MoneyDetail,
                SocketAPIConstant.ReqeutType.History, map);
        requestEntitys(socketDataPacket, "depositsinfo", MoneyDetailListBean.class, listener);
    }

    @Override
    public void bookingStarList(int startPos, int count, OnAPIListener<List<BookingStarListBean>> listener) {
        LogUtils.logd("请求预约明星列表");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("token", SharePrefUtil.getInstance().getToken());
//        map.put("id", 120);
//        map.put("token", "deef1f3d463139a1c50d366c63b22687");
        map.put("count", count);
        map.put("startPos", startPos);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.BookingStar,
                SocketAPIConstant.ReqeutType.History, map);
        requestEntitys(socketDataPacket, "depositsinfo", BookingStarListBean.class, listener);
    }

    @Override
    public void identityAuthentication(String realname, String id_card, OnAPIListener<RequestResultBean> listener) {
        LogUtils.logd("实名认证--");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("realname", realname);
        map.put("id_card", id_card);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Identity,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, RequestResultBean.class, listener);
    }

    @Override
    public void dealPwd(String phone, String vToken, String vCode, long timestamp, int type, String pwd, OnAPIListener<RequestResultBean> listener) {
        LogUtils.logd("交易密码--");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
//        map.put("vToken", SharePrefUtil.getInstance().getToken());
        map.put("phone", phone);
        map.put("vToken", vToken);
        map.put("vCode", vCode);
        map.put("timestamp", timestamp);  //时间戳
        map.put("type", type);
        map.put("pwd", pwd);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.DealPwd,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, RequestResultBean.class, listener);
//        requestJsonObject(socketDataPacket,listener);
    }

    @Override
    public void test(String title, double price, OnAPIListener<Object> listener) {
        LogUtils.logd("测试端口");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("token", SharePrefUtil.getInstance().getToken());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Products,
                SocketAPIConstant.ReqeutType.Deal, map);
        requestJsonObject(socketDataPacket, listener);
    }

    @Override
    public void balance(OnAPIListener<AssetDetailsBean> listener) {
        LogUtils.logd("请求资产明细---");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("token", SharePrefUtil.getInstance().getToken());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Balance,
                SocketAPIConstant.ReqeutType.User, map);
      requestEntity(socketDataPacket,AssetDetailsBean.class,listener);
//        requestJsonObject(socketDataPacket, listener);
    }

    @Override
    public void identity(OnAPIListener<IdentityInfoBean> listener) {
        LogUtils.loge("身份信息");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("token", SharePrefUtil.getInstance().getToken());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.IdentityInfo,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket,IdentityInfoBean.class,listener);
    }

    @Override
    public void nikeName(String nickname, OnAPIListener<RequestResultBean> listener) {
        LogUtils.loge("设置昵称--------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", SharePrefUtil.getInstance().getUserId());
        map.put("token", SharePrefUtil.getInstance().getToken());
        map.put("nickname", nickname);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.NikeName,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket,RequestResultBean.class,listener);
    }

    @Override
    public void starMeet(String starcode, long mid, String city_name, String appoint_time, int meet_type, String comment, OnAPIListener<RequestResultBean> listener) {
        LogUtils.loge("设置开始约见--------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", SharePrefUtil.getInstance().getUserId());
        map.put("mid", mid);
        map.put("starcode", starcode);
        map.put("city_name", city_name);
        map.put("appoint_time", appoint_time);
        map.put("meet_type", meet_type);
        map.put("comment", comment);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.StarMeet,
                SocketAPIConstant.ReqeutType.NewInfos, map);
        requestEntity(socketDataPacket,RequestResultBean.class,listener);
    }

    @Override
    public void statServiceList(String starcode, OnAPIListener<StatServiceListBean> listener) {
        LogUtils.loge("明星类型列表--------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("starcode", starcode);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.StarType,
                SocketAPIConstant.ReqeutType.NewInfos, map);
        requestEntity(socketDataPacket,StatServiceListBean.class,listener);
    }

    @Override
    public void alipay(String title, double price, OnAPIListener<AliPayReturnBean> listener) {
        LogUtils.loge("支付宝----------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("token", SharePrefUtil.getInstance().getToken());
        map.put("title", title);
        map.put("price", price);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.ALiPay,
                SocketAPIConstant.ReqeutType.Pay, map);
        requestEntity(socketDataPacket,AliPayReturnBean.class,listener);
    }

    @Override
    public void cancelPay(String  rid, int payResult, OnAPIListener<Object> listener) {
        LogUtils.loge("取消支付----------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharePrefUtil.getInstance().getUserId());
        map.put("rid", rid);
        map.put("payResult", payResult);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.CancelPay,
                SocketAPIConstant.ReqeutType.Pay, map);
//        requestEntity(socketDataPacket,RequestResultBean.class,listener);
        requestJsonObject(socketDataPacket,listener);
    }


//    @Override
//    public void unionPay(String title, double price, OnAPIListener<Object> listener) {
//        LogUtil.d("请求银联支付");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("title", title);
//        map.put("price", price);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.UnionPay,
//                SocketAPIConstant.ReqeutType.Verify, map);
//        requestJsonObject(socketDataPacket, listener);
//    }
//
//    @Override
//    public void payment(String outTradeNo, long amount, String content, String payType, OnAPIListener<UnionPayReturnEntity> listener) {
//        LogUtil.d("调用第三方支付");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("merchantNo", "merchantNo");  //商户号
//        map.put("outTradeNo", outTradeNo);  //订单号
//        map.put("amount", amount);
//        map.put("content", content);  //描述
//        map.put("payType", payType);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.UnionPay,
//                SocketAPIConstant.ReqeutType.Pay, map);
//        requestEntity(socketDataPacket, UnionPayReturnEntity.class, listener);
//    }
//
//    @Override
//    public void cash(double money, long cardId, String password, OnAPIListener<WithDrawCashReturnEntity> listener) {
//        LogUtil.d("请求提现--");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("money", money);
//        map.put("cardId", cardId);
//        map.put("pwd", "");
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Cash,
//                SocketAPIConstant.ReqeutType.Bank, map);
//        requestEntity(socketDataPacket, WithDrawCashReturnEntity.class, listener);
//    }
//
//    @Override
//    public void cashOut(long bid, long amount, String receiverBankName, String receiverBranchBankName,
//                        String receiverCardNo, String receiverAccountName, OnAPIListener<CashOutReturnEntity> listener) {
//        LogUtil.d("第三方提现");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
////        map.put("merchantNo", "");  //商户号
////        map.put("outPayNo", "");  //外部支付号
////        map.put("payPassword", "");
//        map.put("amount", amount);
//        map.put("bid", bid);
//        map.put("receiverBankName", receiverBankName);
//        map.put("receiverBranchBankName", receiverBranchBankName);
//        map.put("receiverCardNo", receiverCardNo);
//        map.put("receiverAccountName", receiverAccountName);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.CashOut,
//                SocketAPIConstant.ReqeutType.Pay, map);
//        requestEntity(socketDataPacket, CashOutReturnEntity.class, listener);
//    }
//
//    @Override
//    public void cashList(int status, int startPos, int count, OnAPIListener<List<WithDrawCashReturnEntity>> listener) {
//        LogUtil.d("提现列表请求网络");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("startPos", startPos);
//        map.put("count", count);
//        map.put("status", status);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.CashList,
//                SocketAPIConstant.ReqeutType.History, map);
//        requestEntitys(socketDataPacket, "withdrawList", WithDrawCashReturnEntity.class, listener);
//    }
//
//    @Override
//    public void currentPosition(double pid, OnAPIListener<CurrentPositionEntity> listener) {
//        LogUtil.d("当前的仓位详情");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("gid", pid);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.CurrentPosition,
//                SocketAPIConstant.ReqeutType.Deal, map);
//        requestEntity(socketDataPacket, CurrentPositionEntity.class, listener);
//    }
//
//    @Override
//    public void profit(long tid, int handle, OnAPIListener<HistoryPositionListReturnEntity> listener) {
//        LogUtil.d("盈利方式");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("tid", tid);
//        map.put("handle", handle);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Profit,
//                SocketAPIConstant.ReqeutType.History, map);
//        requestEntity(socketDataPacket, HistoryPositionListReturnEntity.class, listener);
//    }
//
//    @Override
//    public void wxpayResult(String rid, int payResult, OnAPIListener<WXPayResultEntity> listener) {
//        LogUtil.d("支付返回结果");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("rid", rid);
//        map.put("payResult", payResult);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.PayResult,
//                SocketAPIConstant.ReqeutType.Verify, map);
//        requestEntity(socketDataPacket, WXPayResultEntity.class, listener);
//    }
//
//    @Override
//    public void bankCardList(OnAPIListener<List<BankCardEntity>> listener) {
//        LogUtil.d("请求银行卡列表");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.BankCard,
//                SocketAPIConstant.ReqeutType.Bank, map);
//        requestEntitys(socketDataPacket, "cardList", BankCardEntity.class, listener);
//    }
//
//    @Override
//    public void bankName(String cardNo, OnAPIListener<BankInfoEntity> listener) {
//        LogUtil.d("获取银行账户信息");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("cardNo", cardNo);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.BankName,
//                SocketAPIConstant.ReqeutType.Bank, map);
//        requestEntity(socketDataPacket, BankInfoEntity.class, listener);
//    }
//
//    @Override
//    public void bindCard(long bankId, String bankName, String branchBank, String cardNO, String name, OnAPIListener<BankInfoEntity> listener) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("bankId", bankId);
//        map.put("branchBank", branchBank);
//        map.put("cardNO", cardNO);
//        map.put("bankName", bankName);
//        map.put("name", name);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.BindCard,
//                SocketAPIConstant.ReqeutType.Bank, map);
//        requestEntity(socketDataPacket, BankInfoEntity.class, listener);
//    }
//
//    @Override
//    public void unBindCard(long bankCardId, String verCode, OnAPIListener<Object> listener) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("bankCardId", bankCardId);
//        map.put("verCode", verCode);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.UnBindCard,
//                SocketAPIConstant.ReqeutType.Bank, map);
//        requestJsonObject(socketDataPacket, listener);
//    }
//
//    @Override
//    public void rechargeList(int startPos, int count, OnAPIListener<List<RechargeRecordItemEntity>> listener) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
//        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
//        map.put("startPos", startPos);
//        map.put("count", count);
//        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Total,
//                SocketAPIConstant.ReqeutType.History, map);
//        requestEntitys(socketDataPacket, "depositsinfo", RechargeRecordItemEntity.class, listener);
//    }
}
