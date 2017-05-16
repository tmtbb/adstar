package com.yundian.star.networkapi;


import com.yundian.star.base.SearchReturnbeen;
import com.yundian.star.been.AdvBeen;
import com.yundian.star.been.MarketTypeBeen;
import com.yundian.star.been.OptionsStarListBeen;
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
    void getOptionsStarList(String phone,int startnum,int endnum,OnAPIListener<OptionsStarListBeen> listener);
    void getMarketKype(String phone,OnAPIListener<MarketTypeBeen> listener);
    void getMarketstar(int type,int startnum,int endnum,OnAPIListener<OptionsStarListBeen> listener);

}
