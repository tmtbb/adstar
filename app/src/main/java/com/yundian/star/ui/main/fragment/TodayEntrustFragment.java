package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.app.SocketAPIConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.TodayEntrustReturnBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.TodayEntrustAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.yundian.star.R.id.parent_view;

/**
 * Created by Administrator on 2017/5/25.
 * 当日委托
 */

public class TodayEntrustFragment extends BaseFragment {

    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(parent_view)
    FrameLayout parentView;
    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private List<TodayEntrustReturnBean> list = new ArrayList<>();
    private List<TodayEntrustReturnBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private TodayEntrustAdapter todayEntrustAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_today_entrust;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initAdapter();
        getData(false, 1);
    }

    private void initAdapter() {
        todayEntrustAdapter = new TodayEntrustAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(todayEntrustAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setNoMore(false);
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_0_5)
                .setPadding(R.dimen.dp_25)
                .setColorResource(R.color.color_dcdcdc)
                .build();
        //mRecyclerView.setHasFixedSize(true);
        lrv.addItemDecoration(divider);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter + 1);
            }
        });
    }

    private void getData(final boolean isLoadMore, int start) {
        NetworkAPIFactoryImpl.getInformationAPI().todayEntrust(start, REQUEST_COUNT, AppConstant.TODAY_ENTRUST_OPCODE, new OnAPIListener<List<TodayEntrustReturnBean>>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        todayEntrustAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
            }

            @Override
            public void onSuccess(List<TodayEntrustReturnBean> todayEntrustReturnBeen) {
                if (todayEntrustReturnBeen == null) {
                    lrv.setNoMore(true);
                    return;
                }
                if (isLoadMore) {
                    closeErrorView();
                    loadList.clear();
                    loadList = todayEntrustReturnBeen;
                    loadMoreData();
                } else {
                    list.clear();
                    list = todayEntrustReturnBeen;
                    showData();
                }
            }
        });
    }

    public void showData() {
        if (list.size() == 0) {
            showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
            return;
        } else {
            closeErrorView();
        }
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        todayEntrustAdapter.addAll(list);
        lrv.refresh();
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            todayEntrustAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }
}
