package com.cloudTop.starshare.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.cloudTop.starshare.base.BaseFragment;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.been.EntrustReturnBeen;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.IndentAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/24.
 * 订单信息
 */

public class IndentFragment extends BaseFragment {

    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private IndentAdapter indentAdapter;
    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<EntrustReturnBeen.PositionsListBean> list = new ArrayList<>();
    private ArrayList<EntrustReturnBeen.PositionsListBean> loadList = new ArrayList<>();
    private LRecyclerView lrv;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_indent;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        initAdapter();
        getData(false, 1, REQUEST_COUNT);
    }

    private void initData() {
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
    }

    private void initAdapter() {
        indentAdapter = new IndentAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(indentAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setNoMore(false);
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_0_5)
                .setColorResource(R.color.color_dcdcdc)
                .build();
        //mRecyclerView.setHasFixedSize(true);
        lrv.addItemDecoration(divider);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter + 1, REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false, 1, REQUEST_COUNT);
            }
        });
    }

    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().historyEntrust(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(), start, count, new OnAPIListener<EntrustReturnBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        if (lrv != null) {
                            lrv.setNoMore(true);
                            if (!isLoadMore) {
                                list.clear();
                                indentAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                            }
                        }
                        //LogUtils.loge("当日委托返回错误码" + ex.toString());
                    }

                    @Override
                    public void onSuccess(EntrustReturnBeen entrustReturnBeen) {
                        LogUtils.loge("当日委托" + entrustReturnBeen.toString());
                        if (entrustReturnBeen == null || entrustReturnBeen.getPositionsList() == null || entrustReturnBeen.getPositionsList().size() == 0) {
                            lrv.setNoMore(true);
                            lrv.refreshComplete(REQUEST_COUNT);
                            return;
                        }
                        if (isLoadMore) {
                            loadList.clear();
                            loadList = entrustReturnBeen.getPositionsList();
                            loadMoreData();
                        } else {
                            list.clear();
                            list = entrustReturnBeen.getPositionsList();
                            showData();
                        }
                    }
                });

    }


    public void showData() {
        indentAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        indentAdapter.addAll(list);
        LogUtils.loge("当前刷新list:" + list.toString());
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            indentAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(loadList.size());
        }
    }
}
