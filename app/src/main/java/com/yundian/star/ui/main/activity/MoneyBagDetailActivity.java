package com.yundian.star.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
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

import static android.R.id.list;
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
    private long exitNow;

    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 1;
    private List<MoneyDetailListBean> loadList = new ArrayList<>();
    private List<MoneyDetailListBean> refreshList = new ArrayList<>();
    private String time = "";

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
        requestMoneyDetailData(false, 1, 10);
    }


    private void requestMoneyDetailData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getDealAPI().moneyList(time, 0, count, start, new OnAPIListener<List<MoneyDetailListBean>>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        refreshList.clear();
                        moneyBagDetailAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        ToastUtils.showShort("当前月份暂无数据");
                    }
                }
                LogUtils.logd("钱包详情请求失败----");
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
        popupMenu = new MyPopupMenu(getContext(), lists);
        ntTitle.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);

                popupMenu.showAsDropDown(ntTitle.getRightImage(), 0, 0);
            }
        });
        popupMenu.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                time = action + 1 + "";
                requestMoneyDetailData(false, 1, 10);
            }
        });


        popupMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                LogUtils.loge("关闭----------------");
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void setData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            MyPopupMenuEntity entity = new MyPopupMenuEntity();
            entity.setText(i + 1 + "月");
            lists.add(entity);
        }
    }

    private void initListner() {
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.logd("下拉刷新---------");
                requestMoneyDetailData(false, 1, 10);
            }
        });

        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LogUtils.logd("上拉加载更多----起始位置:");
                requestMoneyDetailData(true, mCurrentCounter + 1, REQUEST_COUNT);
            }
        });

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                ToastUtils.showShort("当前被点击:" + position);
                preventConcurrency();
                Bundle bundle = new Bundle();
                bundle.putParcelable("dealDetail", refreshList.get(position));
                startActivity(BillingDetailsActivity.class, bundle);
            }
        });
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
