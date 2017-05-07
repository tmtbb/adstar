package com.yundian.star.ui.main.contract;

import com.yundian.star.base.BaseModel;
import com.yundian.star.base.BasePresenter;
import com.yundian.star.base.BaseView;
import com.yundian.star.ui.main.model.TestModel;

/**
 * Created by Null on 2017/5/6.
 */

public interface TestContract {
    interface Model extends BaseModel {
    }
    interface View extends BaseView {
        void showUserInfo(TestModel userInfoModel);//将网络请求得到的用户信息回调
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //登陆请求
        //public abstract void loadUserInfo();
    }
}
