package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.OrderReturnBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.AlreadyBoughtAdapter;
import com.yundian.star.utils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/24.
 * 已购
 */

public class AlreadyBoughtFragment extends BaseFragment {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private AlreadyBoughtAdapter alreadyBoughtAdapter;
    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<OrderReturnBeen.OrdersListBean> list = new ArrayList<>();
    private ArrayList<OrderReturnBeen.OrdersListBean> loadList = new ArrayList<>();


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_already_bought_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initAdapter();
        getHistoryOrderData(false, 1, REQUEST_COUNT);
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
        NetworkAPIFactoryImpl.getInformationAPI().historyOrder(142/*SharePrefUtil.getInstance().getUserId()*/,
                "adc28ac69625652b46d5c00b"/*SharePrefUtil.getInstance().getToken()*/, 3, start, count, new OnAPIListener<OrderReturnBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        if (lrv != null) {
                            lrv.setNoMore(true);
                        }
                        LogUtils.loge("当日订单返回错误码" + ex.toString());
                    }

                    @Override
                    public void onSuccess(OrderReturnBeen orderReturnBeen) {
                        LogUtils.loge("当日订单" + orderReturnBeen.toString());
                        if (orderReturnBeen.getOrdersList() == null || orderReturnBeen.getOrdersList().size() == 0) {
                            lrv.setNoMore(true);
                            return;
                        }
                        if (isLoadMore) {
                            loadList.clear();
                            loadList = orderReturnBeen.getOrdersList();
                            loadMoreData();
                        } else {
                            list.clear();
                            list = orderReturnBeen.getOrdersList();
                            showData();
                        }
                    }
                });

    }


    public void showData() {
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
