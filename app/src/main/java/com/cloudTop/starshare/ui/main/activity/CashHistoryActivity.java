package com.cloudTop.starshare.ui.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.WithDrawCashHistoryBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.CashHistoryAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.cloudTop.starshare.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by sll on 2017/5/24.
 */

public class CashHistoryActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;

    @Bind(R.id.parent_view)
    FrameLayout parent_view;

    @Bind(R.id.lrv)
    LRecyclerView lrv;
    private CashHistoryAdapter cashHistoryAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private long exitNow;

    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 1;
    private List<WithDrawCashHistoryBean> loadList = new ArrayList<>();
    private List<WithDrawCashHistoryBean> refreshList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_money_bag_detail;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.cash_history));
        ntTitle.setTvLeftVisiable(true);
        initAdapter();
        getData(false, 1, 10);
        setResult(RESULT_OK);
    }


    private void getData(final boolean isLoadMore, int start, int count) {
        int status = 0;
        NetworkAPIFactoryImpl.getDealAPI().cashList(status, start, count, new OnAPIListener<List<WithDrawCashHistoryBean>>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        refreshList.clear();
                        cashHistoryAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                    }
                }
                LogUtils.loge("提现记录失败---------------");
            }

            @Override
            public void onSuccess(List<WithDrawCashHistoryBean> listBean) {
                closeErrorView();
                LogUtils.loge("list.size()" + listBean.toString() + "," + listBean.size());
                if (listBean == null || listBean.size() == 0) {
                    lrv.setNoMore(true);
                    return;
                }
                if (isLoadMore) {
                    loadList.clear();
                    loadList = listBean;
                    mCurrentCounter = refreshList.size();
                    loadMoreData();
                } else {
                    refreshList.clear();
                    refreshList = listBean;
                    showData();
                }
            }
        });
    }

    private void loadMoreData() {
        if (loadList == null || refreshList.size() == 0) {
            lrv.setNoMore(true);
        } else {
            refreshList.addAll(loadList);
            cashHistoryAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(loadList.size());
        }
    }

    public void showData() {
        cashHistoryAdapter.clear();
        mCurrentCounter = refreshList.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        cashHistoryAdapter.addAll(refreshList);
        lrv.refreshComplete(REQUEST_COUNT);
    }


    private void initAdapter() {
        cashHistoryAdapter = new CashHistoryAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(cashHistoryAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setNoMore(false);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.color_cccccc)
                .setLeftPadding(R.dimen.dp_12)
                .build();
        lrv.addItemDecoration(divider);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setPullRefreshEnabled(true);
        initListner();
    }

    private void initListner() {
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.logd("下拉刷新---------");
                getData(false, 1, 10);
            }
        });

        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LogUtils.logd("上拉加载更多----起始位置:");
                getData(true, mCurrentCounter + 1, REQUEST_COUNT);
            }
        });

//        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                preventConcurrency();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("dealDetail", refreshList.get(position));
//                startActivity(BillingDetailsActivity.class, bundle);
//            }
//        });
    }

    /**
     * 防止并发
     */
    private void preventConcurrency() {
        if ((System.currentTimeMillis() - exitNow) < 3000) {
            return;
        }
        exitNow = System.currentTimeMillis();
    }
}
