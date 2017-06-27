package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.StarMailListBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.AlreadyBoughtAdapter;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/24.
 * 已购
 */

public class AlreadyBoughtFragment extends BaseFragment {

    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private AlreadyBoughtAdapter alreadyBoughtAdapter;
    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<StarMailListBeen.DepositsinfoBean> list = new ArrayList<>();
    private ArrayList<StarMailListBeen.DepositsinfoBean> loadList = new ArrayList<>();
    private LRecyclerView lrv;
    private FrameLayout parentView;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_already_bought_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        initAdapter();
        getHistoryOrderData(false, 1, REQUEST_COUNT);
    }

    private void initData() {
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
        parentView = (FrameLayout) rootView.findViewById(R.id.parent_view);
    }


    private void initAdapter() {
        alreadyBoughtAdapter = new AlreadyBoughtAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(alreadyBoughtAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setNoMore(false);
//        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
//                .setHeight(R.dimen.dp_0_5)
//                .setColorResource(R.color.color_dcdcdc)
//                .build();
        //mRecyclerView.setHasFixedSize(true);
        //lrv.addItemDecoration(divider);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getHistoryOrderData(true, mCurrentCounter + 1, REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHistoryOrderData(false, 1, REQUEST_COUNT);
            }
        });
    }


    private void getHistoryOrderData(final boolean isLoadMore, int start, int count) {
//        NetworkAPIFactoryImpl.getInformationAPI().historyOrder(142/*SharePrefUtil.getInstance().getUserId()*/,
//                "adc28ac69625652b46d5c00b"/*SharePrefUtil.getInstance().getToken()*/, 3, start, count, new OnAPIListener<OrderReturnBeen>() {
//                    @Override
//                    public void onError(Throwable ex) {
//                        if (lrv != null) {
//                            lrv.setNoMore(true);
//                        }
//                        LogUtils.loge("当日订单返回错误码" + ex.toString());
//                    }
//
//                    @Override
//                    public void onSuccess(OrderReturnBeen orderReturnBeen) {
//                        LogUtils.loge("当日订单" + orderReturnBeen.toString());
//                        if (orderReturnBeen.getOrdersList() == null || orderReturnBeen.getOrdersList().size() == 0) {
//                            lrv.setNoMore(true);
//                            return;
//                        }
//                        if (isLoadMore) {
//                            loadList.clear();
//                            loadList = orderReturnBeen.getOrdersList();
//                            loadMoreData();
//                        } else {
//                            list.clear();
//                            list = orderReturnBeen.getOrdersList();
//                            showData();
//                        }
//                    }
//                });
        NetworkAPIFactoryImpl.getInformationAPI().getStarmaillist(SharePrefUtil.getInstance().getUserId(), SharePrefUtil.getInstance().getToken(),"123", start, REQUEST_COUNT, new OnAPIListener<StarMailListBeen>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv!=null){
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        alreadyBoughtAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, getActivity().getResources().getString(R.string.empty_order_info));
                    }
                }
            }

            @Override
            public void onSuccess(StarMailListBeen starMailListBeen) {
                LogUtils.loge(starMailListBeen.toString());
                if (starMailListBeen.getDepositsinfo()==null||starMailListBeen.getDepositsinfo()==null){
                    lrv.setNoMore(true);
                    lrv.refreshComplete(REQUEST_COUNT);
                    return;
                }
                if (isLoadMore){
                    closeErrorView();
                    loadList.clear();
                    loadList = starMailListBeen.getDepositsinfo();
                    loadMoreData();
                }else {
                    list.clear();
                    list = starMailListBeen.getDepositsinfo();
                    showData();
                }
            }
        });

    }


    public void showData() {
        if (list.size() == 0){
            showErrorView(parentView, R.drawable.error_view_comment, getActivity().getResources().getString(R.string.empty_view_comment));
            return;
        }else{
            closeErrorView();
        }
        alreadyBoughtAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        alreadyBoughtAdapter.addAll(list);
        LogUtils.loge("当前刷新list:" + list.toString());
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            alreadyBoughtAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(loadList.size());
        }
    }
}
