package com.yundian.star.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.been.MoneyDetailListBean;
import com.yundian.star.been.MyPopupMenuEntity;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.listener.OnChildViewClickListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.MoneyBagDetailAdapter;
import com.yundian.star.ui.view.MyPopupMenu;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.yundian.star.ui.wangyi.DemoCache.clear;
import static com.yundian.star.ui.wangyi.DemoCache.getContext;

/**
 * Created by sll on 2017/5/24.
 */

public class MoneyBagDetailActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    private MyPopupMenu popupMenu;
    private List<MyPopupMenuEntity> lists;
    private MoneyBagDetailAdapter moneyBagDetailAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;

    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 0;
    private List<MoneyDetailListBean> loadList = new ArrayList<>();
    private List<MoneyDetailListBean> refreshList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_money_bag_detail;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.money_bag_detail));
        ntTitle.setTvLeftVisiable(true);
        ntTitle.setRightImagSrc(R.drawable.money_screen);
        initPopupMenu();
        initAdapter();
        requestMoneyDetailData(false, 0, 10);
    }

    private void requestMoneyDetailData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getDealAPI().moneyList(0, count, start, new OnAPIListener<List<MoneyDetailListBean>>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.logd("钱包详情请求失败----");
//                    lrv.setNoMore(true);
                lrv.refreshComplete(REQUEST_COUNT);
            }

            @Override
            public void onSuccess(List<MoneyDetailListBean> list) {
                LogUtils.loge("list.size()" + list.toString() + "," + list.size());
                if (list == null || list.size() == 0) {
                    lrv.setNoMore(true);
                    return;
                }
                if (isLoadMore) {
                    loadList.clear();
                    loadList = list;
                    mCurrentCounter = refreshList.size();
                    loadMoreData();
                } else {
                    refreshList.clear();
                    refreshList = list;
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
            moneyBagDetailAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(loadList.size());
        }
    }

    public void showData() {
        moneyBagDetailAdapter.clear();
        mCurrentCounter = refreshList.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        moneyBagDetailAdapter.addAll(refreshList);
        lrv.refreshComplete(REQUEST_COUNT);

//        lrv.refresh();
    }


    private void initAdapter() {
        moneyBagDetailAdapter = new MoneyBagDetailAdapter(this);
//        moneyBagDetailAdapter.setDataList(detailList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(moneyBagDetailAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setNoMore(false);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.color_cccccc)
                .build();
        lrv.addItemDecoration(divider);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setPullRefreshEnabled(true);
        initListner();
    }

    private void initPopupMenu() {
        setData();
        ntTitle.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new MyPopupMenu(getContext(), lists);
                popupMenu.setOnChildViewClickListener(new OnChildViewClickListener() {
                    @Override
                    public void onChildViewClick(View childView, int action, Object obj) {
                        ToastUtils.showShort("子控件被点击了");
                    }
                });

                popupMenu.showAsDropDown(ntTitle.getRightImage(), 0, 0);
            }
        });
    }

    private void setData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyPopupMenuEntity entity = new MyPopupMenuEntity();
            entity.setText("日期" + i);
            lists.add(entity);
        }
    }

    private void initListner() {
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.logd("下拉刷新---------");
                requestMoneyDetailData(false, 0, 10);
            }
        });

        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LogUtils.logd("上拉加载更多----起始位置:");
                requestMoneyDetailData(true, mCurrentCounter, REQUEST_COUNT);
            }
        });

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showShort("当前被点击:" + position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("dealDetail", new BookingStarListBean());
                startActivity(BillingDetailsActivity.class, bundle);
            }
        });
    }
}
