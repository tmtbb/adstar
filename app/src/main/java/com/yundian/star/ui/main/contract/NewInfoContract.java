package com.yundian.star.ui.main.contract;

import com.yundian.star.base.BaseModel;
import com.yundian.star.base.BasePresenter;
import com.yundian.star.base.BaseView;
import com.yundian.star.ui.main.model.NewsInforModel;

import java.util.ArrayList;

/**
 * Created by Null on 2017/5/6.
 */

public interface NewInfoContract {
    interface Model extends BaseModel {
    }
    interface View extends BaseView {
        public abstract void initDatas(ArrayList<NewsInforModel> list);
        public abstract void addItems(ArrayList<NewsInforModel> list);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //加载更多
        public abstract void getMoreData();
    }
}
