package com.yundian.star.ui.main.presenter;

import com.yundian.star.ui.main.contract.NewInfoContract;
import com.yundian.star.ui.main.model.NewsInforModel;

import java.util.ArrayList;

/**
 * Created by Null on 2017/5/6.
 */

public class NewsInfoPresenter extends NewInfoContract.Presenter {

    private ArrayList<NewsInforModel> arrayList;

    @Override
    public void onStart() {
        super.onStart();
        arrayList = new ArrayList<>();
        NewsInforModel infor= null ;
        for (int i = 0; i < 20; i++) {
            infor = new NewsInforModel();
            infor.setUsername("测试"+i);
            arrayList.add(infor);
        }
        mView.initDatas(arrayList);
    }

    @Override
    public void getMoreData() {
        ArrayList<NewsInforModel> arrayListmore = new ArrayList<>();
    //模拟组装10个数据
        for (int i = 0; i < 10; i++) {
            NewsInforModel item = new NewsInforModel();
            item.setUsername("加载更多数据"+i);
            arrayListmore.add(item);
        }
        mView.addItems(arrayListmore);
    }
}
