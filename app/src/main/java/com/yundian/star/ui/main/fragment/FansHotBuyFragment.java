package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.FansEntrustReturnBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.AutionTopAdapter;
import com.yundian.star.utils.LogUtils;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/5/22.
 * 粉丝排行榜
 */

public class FansHotBuyFragment extends BaseFragment {

    private static final int REQUEST_COUNT = 10;

    private LRecyclerViewAdapter lRecyclerViewAdapter;

    private ArrayList<FansEntrustReturnBean.PositionsListBean> list = new ArrayList<>();
    private ArrayList<FansEntrustReturnBean.PositionsListBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 1;
    private AutionTopAdapter fansHotBuyAdapter;
    private int hotType;
    private String code;
    private LRecyclerView lrv;
    private FrameLayout parentView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fans_hot_buy;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        if (getArguments()!=null){
            code = getArguments().getString(AppConstant.STAR_CODE);
            hotType = getArguments().getInt(AppConstant.FANS_HOT_TYPE);

        }
        initAdapter();
        getData(false,1,REQUEST_COUNT);
    }

    private void initData() {
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
        parentView = (FrameLayout) rootView.findViewById(R.id.parent_view);
    }

    private void getData(final boolean isLoadMore,int start ,int end ) {
        LogUtils.loge("粉丝排行榜code111"+code);
            /*NetworkAPIFactoryImpl.getInformationAPI().oederFansList(SharePrefUtil.getInstance().getUserId(),
                    SharePrefUtil.getInstance().getToken(),code,0, start, end, new OnAPIListener<FansTopListBeen>() {
                @Override
                public void onError(Throwable ex) {
                    if (lrv!=null){
                        lrv.setNoMore(true);
                        if (!isLoadMore) {
                            list.clear();
                            fansHotBuyAdapter.clear();
                            lrv.refreshComplete(REQUEST_COUNT);
                            showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                        }
                        return;
                    }
                    LogUtils.loge("粉丝排行榜错误"+ex.toString());
                }

                @Override
                public void onSuccess(FansTopListBeen fansTopListBeen) {
                    LogUtils.loge("粉丝排行榜"+fansTopListBeen.toString());
                    if (fansTopListBeen==null||fansTopListBeen.getOrdersList()==null){
                        lrv.setNoMore(true);
                        if (!isLoadMore) {
                            list.clear();
                            fansHotBuyAdapter.clear();
                            lrv.refreshComplete(REQUEST_COUNT);
                            showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                        }
                        return;
                    }
                    if (isLoadMore){
                        closeErrorView();
                        loadList.clear();
                        loadList = fansTopListBeen.getOrdersList();
                        loadMoreData();
                    }else {
                        list.clear();
                        list = fansTopListBeen.getOrdersList();
                        showData();
                    }
                }
            });*/
        int buySell = 1 ;
        if (hotType==1){
            buySell = 1 ;
        }else {
            buySell = -1 ;
        }
        NetworkAPIFactoryImpl.getInformationAPI().fansRntrust(code, buySell, start, REQUEST_COUNT, new OnAPIListener<FansEntrustReturnBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("粉丝热度失败------------------------------------------");
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        fansHotBuyAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        //showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
            }

            @Override
            public void onSuccess(FansEntrustReturnBean bean) {
                LogUtils.loge("粉丝热度成功-------" + bean.toString());
                if (bean==null||bean.getPositionsList()==null||bean.getPositionsList().size()==0){
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        fansHotBuyAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                    return;
                }
                if (isLoadMore){
                    closeErrorView();
                    loadList.clear();
                    loadList = bean.getPositionsList();
                    loadMoreData();
                }else {
                    list.clear();
                    list = bean.getPositionsList();
                    showData();
                }
            }
        });
    }

    private void initAdapter() {
        fansHotBuyAdapter = new AutionTopAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(fansHotBuyAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false, 1, REQUEST_COUNT);
            }
        });
    }

    public void showData() {
        if (list.size() == 0){
            showErrorView(parentView, R.drawable.error_view_comment, getActivity().getResources().getString(R.string.empty_order_info));
            return;
        }else{
            closeErrorView();
        }
        fansHotBuyAdapter.clear();
        mCurrentCounter =list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        fansHotBuyAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            fansHotBuyAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }


}
