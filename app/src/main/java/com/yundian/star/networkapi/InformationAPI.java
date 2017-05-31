package com.yundian.star.networkapi;


import com.yundian.star.base.SearchReturnbeen;
import com.yundian.star.been.AdvBeen;
import com.yundian.star.been.FansHotBuyReturnBeen;
import com.yundian.star.been.MarketTypeBeen;
import com.yundian.star.been.OptionsStarListBeen;
import com.yundian.star.been.StarBuyActReferralInfo;
import com.yundian.star.been.StarExperienceBeen;
import com.yundian.star.been.StarMailListBeen;
import com.yundian.star.been.StarStarAchBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.ui.main.model.NewsInforModel;

/**
 * Created by ysl .
 * 信息获取
 *
 */

public interface InformationAPI {
    void newsinfo(String name, String code,int startnum,int endnum,int all, OnAPIListener<NewsInforModel> listener);
    void advInfo(String code,int all, OnAPIListener<AdvBeen> listener);
    void searchStar(String code,OnAPIListener<SearchReturnbeen> listener);
    void getOptionsStarList(String phone,int startnum,int endnum,int sorttype,OnAPIListener<OptionsStarListBeen> listener);
    void getMarketKype(String phone,OnAPIListener<MarketTypeBeen> listener);
    void getMarketstar(int type,int startnum,int endnum,int sorttype,OnAPIListener<OptionsStarListBeen> listener);
    void getStarBrief(String code,OnAPIListener<StarBuyActReferralInfo> listener);
    void getStarExperience(String code,OnAPIListener<StarExperienceBeen> listener);
    void getStarachive(String code,OnAPIListener<StarStarAchBeen> listener);
    void getSeekList(String code,int startnum,int endnum,OnAPIListener<FansHotBuyReturnBeen> listener);
    void getTransferList(String code,int startnum,int endnum,OnAPIListener<FansHotBuyReturnBeen> listener);
    void getFansComments(String starcode,OnAPIListener<Object> listener);
    void getStarmaillist(long id ,String token,String status,int startPos,int count,OnAPIListener<StarMailListBeen> listener);
    void addFriend(String accid,String faccid,String msg,int type,OnAPIListener<Object> listener);

}
