package com.cloudTop.starshare.networkapi;


import com.cloudTop.starshare.base.SearchReturnbeen;
import com.cloudTop.starshare.been.AdvBeen;
import com.cloudTop.starshare.been.AskToBuyReturnBeen;
import com.cloudTop.starshare.been.BuyShellReutrnBeen;
import com.cloudTop.starshare.been.CircleFriendBean;
import com.cloudTop.starshare.been.CommentMarketBeen;
import com.cloudTop.starshare.been.DanMaKuInfo;
import com.cloudTop.starshare.been.EntrustReturnBeen;
import com.cloudTop.starshare.been.ExpendLineBean;
import com.cloudTop.starshare.been.FansEntrustReturnBean;
import com.cloudTop.starshare.been.FansHotBuyReturnBeen;
import com.cloudTop.starshare.been.FansTopListBeen;
import com.cloudTop.starshare.been.HaveStarTimeBeen;
import com.cloudTop.starshare.been.HomePageInfoBean;
import com.cloudTop.starshare.been.MarketTypeBeen;
import com.cloudTop.starshare.been.NewStarVideoBean;
import com.cloudTop.starshare.been.NowPriceBean;
import com.cloudTop.starshare.been.OptionsStarListBeen;
import com.cloudTop.starshare.been.OrderCancelReturnBeen;
import com.cloudTop.starshare.been.OrderReturnBeen;
import com.cloudTop.starshare.been.RefreshStarTimeBean;
import com.cloudTop.starshare.been.ResultBeen;
import com.cloudTop.starshare.been.ShoppingStarBean;
import com.cloudTop.starshare.been.SrealSendBeen;
import com.cloudTop.starshare.been.SrealSendReturnBeen;
import com.cloudTop.starshare.been.StarBuyActReferralInfo;
import com.cloudTop.starshare.been.StarDanMuNewInfo;
import com.cloudTop.starshare.been.StarDetailInfoBean;
import com.cloudTop.starshare.been.StarExperienceBeen;
import com.cloudTop.starshare.been.StarInfoReturnBean;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.been.StarListbeen;
import com.cloudTop.starshare.been.StarMailListBeen;
import com.cloudTop.starshare.been.StarQuestionBean;
import com.cloudTop.starshare.been.StarStarAchBeen;
import com.cloudTop.starshare.been.StarTimeReaturnBean;
import com.cloudTop.starshare.been.StartShellTimeBeen;
import com.cloudTop.starshare.been.SureOrder;
import com.cloudTop.starshare.been.TimeLineBeen;
import com.cloudTop.starshare.been.TodayDealReturnBean;
import com.cloudTop.starshare.been.TodayEntrustReturnBean;
import com.cloudTop.starshare.been.TradingStatusBeen;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.ui.main.model.NewsInforModel;

import java.util.List;

/**
 * Created by ysl .
 * 信息获取
 */

public interface InformationAPI {
    void newsinfo(String name, String code, int startnum, int endnum, int all, OnAPIListener<NewsInforModel> listener);

    void advInfo(String code, int all, OnAPIListener<AdvBeen> listener);

    void searchStar(long id, String token, String message, OnAPIListener<SearchReturnbeen> listener);

    void getOptionsStarList(String phone, int startnum, int endnum, int sorttype, OnAPIListener<OptionsStarListBeen> listener);

    void getMarketKype(String phone, OnAPIListener<MarketTypeBeen> listener);

    void getStarList(long id, String token, int sort, int aType, int start, int count, OnAPIListener<StarListbeen> listener);

    void getStarBrief(String code, OnAPIListener<StarBuyActReferralInfo> listener);

    void getStarExperience(String code, OnAPIListener<StarExperienceBeen> listener);

    void getStarachive(String code, OnAPIListener<StarStarAchBeen> listener);

    void getSeekList(String code, int startnum, int endnum, OnAPIListener<FansHotBuyReturnBeen> listener);

    void getTransferList(String code, int startnum, int endnum, OnAPIListener<FansHotBuyReturnBeen> listener);

    void getFansComments(String starcode, OnAPIListener<Object> listener);

    void getStarmaillist(long id, String token, String status, int startPos, int count, OnAPIListener<StarMailListBeen> listener);

    void addFriend(String accid, String faccid, String msg, int type, OnAPIListener<Object> listener);

    void reduceTime(String phone, String starcode, long deduct_amount, OnAPIListener<ResultBeen> listener);

    void inquiry(String symbol, int startPos, int count, OnAPIListener<CommentMarketBeen> listener);

    void getTimeLine(long id, String token, String symbol, int aType, OnAPIListener<TimeLineBeen> listener);

    void getAddComment(String symbol, String fans_id, String nick_name, String comments, String head_url, OnAPIListener<Object> listener);

    void getSrealtime(long id, String token, List<SrealSendBeen> symbolInfos, OnAPIListener<SrealSendReturnBeen> listener);

    void getAskToBuy(long id, String token, int sort, String symbol, int buySell, int amount, double price, OnAPIListener<AskToBuyReturnBeen> listener);

    void getTradingStatus(long id, String token, String symbol, OnAPIListener<TradingStatusBeen> listener);

    void cancelOrder(long id, String token, long orderId, OnAPIListener<OrderCancelReturnBeen> listener);

    void checkPayPas(long id, String token, String paypwd, OnAPIListener<ResultBeen> listener);

    void sureOrder(long id, String token, long orderId, long positionId, OnAPIListener<SureOrder> listener);

    void theDayOrder(long id, String token, int status, int start, int count, OnAPIListener<OrderReturnBeen> listener);

    void historyOrder(long id, String token, int status, int start, int count, OnAPIListener<OrderReturnBeen> listener);

    void historyEntrust(long id, String token, int start, int count, OnAPIListener<EntrustReturnBeen> listener);

    void oederFansList(long id, String token, String symbol, int buySell, int start, int count, OnAPIListener<FansTopListBeen> listener);

    void todayEntrust(int start, int count, Short opcode, OnAPIListener<List<TodayEntrustReturnBean>> listener);

    void todayDeal(int status, int start, int count, Short opcode, OnAPIListener<List<TodayDealReturnBean>> listener);

    void fansRntrust(String symbol, int buySell, int start, int count, OnAPIListener<FansEntrustReturnBean> listener);

    void starInfo(String phone, String code, int all, OnAPIListener<StarInfoReturnBean> listener);

    void getHaveStarTime(long uid, String starcode, OnAPIListener<HaveStarTimeBeen> listener);

    void getStarTime(long uid, String star_code, OnAPIListener<StarTimeReaturnBean> listener);

    void getStarShellTime(String starcode, OnAPIListener<StartShellTimeBeen> listener);

    void getBuyShellData(long id, String token, String symbol, OnAPIListener<BuyShellReutrnBeen> listener);

    void getHomePage(long id, String token, long aType, OnAPIListener<HomePageInfoBean> listener);

    void getShoppingStar(String symbol, OnAPIListener<ShoppingStarBean> listener);

    void getRefreshStar(long uid, String token, String symbol, OnAPIListener<RefreshStarTimeBean> listener);

    void getStarList(long id, String token, long aType, long sort, int pos, int count, OnAPIListener<StarListReturnBean> listener);

    void getStarDetailInfo(String star_code, OnAPIListener<StarDetailInfoBean> listener);

    void getAllCircleInfo(int pos, int count, OnAPIListener<CircleFriendBean> listener);

    void getAllCircleIsOne(int pos, int count, String star_code, OnAPIListener<CircleFriendBean> listener);

    void getDanMaKuInfo(int pos, int count, OnAPIListener<DanMaKuInfo> listener);

    void getDanMaKuInfoNeW(String star_code, long count, OnAPIListener<StarDanMuNewInfo> listener);

    void getDanMaKuInfoNeWAll(long count, OnAPIListener<StarDanMuNewInfo> listener);

    void getPraisestar(String star_code, long circle_id, long uid, OnAPIListener<ResultBeen> listener);

    void getUserAddComment(String star_code, long circle_id, long uid, int direction, String content, OnAPIListener<ResultBeen> listener);

    void getByBuy(long uid, String token, String symbol, long amount, double price, OnAPIListener<ResultBeen> listener);

    void getNowPrice(long id, String token, String starcode, int aType, OnAPIListener<NowPriceBean> listener);

    void getExpendLine(String param_code, OnAPIListener<ExpendLineBean> listener);

    void postQuestion(long uid, String starcode, String token, int aType, int pType, int cType, String uask, String videoUrl,long videoTime,String thumbnail, OnAPIListener<ResultBeen> listener);

    void getStarQuestionsInfo(long uid,String starcode, int pos, int count, String token,int aType,int pType, OnAPIListener<StarQuestionBean> listener);

    void getUserQuestionsInfo(String starcode,long uid,int pos, int count, String token,int aType,int pType, OnAPIListener<StarQuestionBean> listener);

    void toBuyQuestion(long uid, long qid,String starcode,int cType,OnAPIListener<ResultBeen> listener);

    void getNewStarVdieo(String starcode, long uid,int aType,int pType,int pos,int count,OnAPIListener<NewStarVideoBean> listener);
}
