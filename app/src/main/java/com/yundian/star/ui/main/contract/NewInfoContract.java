package com.yundian.star.ui.main.contract;

import com.yundian.star.base.BaseModel;
import com.yundian.star.base.BasePresenter;
import com.yundian.star.base.BaseView;
import com.yundian.star.been.AdvBeen;
import com.yundian.star.ui.main.model.NewsInforModel;

import java.util.ArrayList;

/**
 * Created by Null on 2017/5/6.
 */

public interface NewInfoContract {
    interface Model extends BaseModel {
    }
    interface View extends BaseView {
        public abstract void initDatas(ArrayList<NewsInforModel.ListBean> list);
        public abstract void addMoreItems(ArrayList<NewsInforModel.ListBean> list);
        public abstract void initAdv(AdvBeen adv);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getData(boolean isMoreData,String name, String code,int startnum,int endnum,int all);
        public abstract void getAdvertisement(String code, int all);

    }
}
