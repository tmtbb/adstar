package com.yundian.star.ui.main.presenter;

import com.yundian.star.been.AdvBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.contract.NewInfoContract;
import com.yundian.star.ui.main.model.NewsInforModel;
import com.yundian.star.utils.LogUtils;

/**
 * Created by Null on 2017/5/6.
 */

public class NewsInfoPresenter extends NewInfoContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getData(final boolean isMoreData,String name, String code,int startnum,int endnum,int all) {
        mView.showLoading("刷新中");
        NetworkAPIFactoryImpl.getInformationAPI().newsinfo(name, code, startnum, endnum, all, new OnAPIListener<NewsInforModel>() {
            @Override
            public void onSuccess(NewsInforModel o) {
                if (isMoreData){
                    mView.addMoreItems(o.getList());
                }else {
                    mView.initDatas(o.getList());
                }
                LogUtils.logd(o.toString());
                LogUtils.loge("----------------请求成功----");
                mView.stopLoading();
            }

            @Override
            public void onError(Throwable ex) {
                LogUtils.logd(ex.toString());
//                mView.stopLoading();
                LogUtils.loge("----------------请求错诶");
                if (!isMoreData){
                    mView.showErrorTip(ex.getMessage());
                }
            }
        });
    }

    @Override
    public void getAdvertisement(String code, int all) {
        NetworkAPIFactoryImpl.getInformationAPI().advInfo(code, all, new OnAPIListener<AdvBeen>() {
            @Override
            public void onSuccess(AdvBeen adv) {
                if(adv==null||adv.getList()==null||adv.getList().size()==0){
                    return;
                }
                mView.initAdv(adv);
            }

            @Override
            public void onError(Throwable ex) {

            }
        });
    }
}
