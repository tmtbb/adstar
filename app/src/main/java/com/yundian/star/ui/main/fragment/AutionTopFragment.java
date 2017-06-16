package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.FansHotBuyReturnBeen;
import com.yundian.star.ui.main.adapter.AutionTopAdapter;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/6/12.
 * 拍卖排行榜
 */

public class AutionTopFragment extends BaseFragment {
    @Bind(R.id.lrv)
    LRecyclerView lrv ;

    private static final int REQUEST_COUNT = 10;

    private LRecyclerViewAdapter lRecyclerViewAdapter;

    private ArrayList<FansHotBuyReturnBeen.ListBean> list = new ArrayList<>();
    private ArrayList<FansHotBuyReturnBeen.ListBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 1;
    private AutionTopAdapter autionTopAdapter;
    private int hotType;
    private String code;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_auction_top;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments()!=null){
            code = getArguments().getString(AppConstant.STAR_CODE);
            hotType = getArguments().getInt(AppConstant.AUCTION_TYPE);

        }
        initAdapter();
        getData(false,1,REQUEST_COUNT);
    }

    private void getData(final boolean isLoadMore,int start ,int end ) {
        if (hotType==1){
            if (isLoadMore){
                loadList.clear();
                moNiData(isLoadMore);
                loadMoreData();
            }else {
                list.clear();
                moNiData(isLoadMore);
                showData();
            }
            /*NetworkAPIFactoryImpl.getInformationAPI().getSeekList(code, start, end, new OnAPIListener<FansHotBuyReturnBeen>() {
                @Override
                public void onError(Throwable ex) {

                }

                @Override
                public void onSuccess(FansHotBuyReturnBeen fansHotBuyReturnBeen) {
                    if (fansHotBuyReturnBeen.getList()==null){
                        lrv.setNoMore(true);
                        return;
                    }
                    if (isLoadMore){
                        loadList.clear();
                        loadList = fansHotBuyReturnBeen.getList();
                        loadMoreData();
                    }else {
                        list.clear();
                        list = fansHotBuyReturnBeen.getList();
                        showData();
                    }
                }
            });*/
        }else {
            /*if (isLoadMore){
                loadList.clear();
                moNiData(isLoadMore);
                loadMoreData();
            }else {
                list.clear();
                moNiData(isLoadMore);
                showData();
            }*/
            /*NetworkAPIFactoryImpl.getInformationAPI().getTransferList(code, start, end, new OnAPIListener<FansHotBuyReturnBeen>() {
                @Override
                public void onError(Throwable ex) {

                }

                @Override
                public void onSuccess(FansHotBuyReturnBeen fansHotBuyReturnBeen) {
                    if (fansHotBuyReturnBeen.getList()==null){
                        lrv.setNoMore(true);
                        return;
                    }
                    if (isLoadMore){
                        loadList.clear();
                        loadList = fansHotBuyReturnBeen.getList();
                        loadMoreData();
                    }else {
                        list.clear();
                        list = fansHotBuyReturnBeen.getList();
                        showData();
                    }
                }
            });*/
        }

    }

    private void initAdapter() {
        autionTopAdapter = new AutionTopAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(autionTopAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
            }
        });
    }

    public void showData() {
        mCurrentCounter =list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        autionTopAdapter.addAll(list);
        lrv.refresh();
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            autionTopAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }
    private void moNiData(boolean isLoadMore){
        for (int i =0;i<10;i++){
            FansHotBuyReturnBeen.ListBean listBean = new FansHotBuyReturnBeen.ListBean();
            listBean.setName("哈哈"+i);
            listBean.setPrice(i);
            if (isLoadMore){
                loadList.add(listBean);
            }else {
                list.add(listBean);
            }
        }
    }
}
