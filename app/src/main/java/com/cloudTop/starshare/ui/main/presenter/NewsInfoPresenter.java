package com.cloudTop.starshare.ui.main.presenter;

import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.ui.main.contract.NewInfoContract;
import com.cloudTop.starshare.been.AdvBeen;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.model.NewsInforModel;
import com.cloudTop.starshare.utils.LogUtils;

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
