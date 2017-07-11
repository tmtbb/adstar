package com.yundian.star.ui.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.FansTopListBeen;
import com.yundian.star.ui.main.activity.StarInfoActivity;
import com.yundian.star.ui.main.adapter.StarInteractionAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11.
 * 明星互动
 */

public class StartInteractFragment extends BaseFragment {
    private LRecyclerView lrv;
    private FrameLayout fl_layout;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private ArrayList<FansTopListBeen.OrdersListBean> list = new ArrayList<>();
    private ArrayList<FansTopListBeen.OrdersListBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 1;
    private StarInteractionAdapter interactionAdapter;
    private static final int REQUEST_COUNT = 10;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_start_interact;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initFindById();
        initAdapter();
        getData(false,1,REQUEST_COUNT);
    }

    private void initFindById() {
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
        fl_layout = (FrameLayout) rootView.findViewById(R.id.fm_layout);
    }
    private void initAdapter() {
        interactionAdapter = new StarInteractionAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(interactionAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setLoadMoreEnabled(true);
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
                mCurrentCounter = 1;
                lrv.setNoMore(false);
                getData(false,1,REQUEST_COUNT);
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),StarInfoActivity.class);
                intent.putExtra(AppConstant.STAR_CODE,"1001");
                startActivity(intent);
            }
        });
    }
    private void getData(final boolean isLoadMore,int start ,int end ) {
        ArrayList < FansTopListBeen.OrdersListBean > demoList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            FansTopListBeen.OrdersListBean ordersListBean = new FansTopListBeen.OrdersListBean();
            FansTopListBeen.OrdersListBean.BuyUserBean buyUserBean= new FansTopListBeen.OrdersListBean.BuyUserBean();
            buyUserBean.setNickname("明星"+i);
            ordersListBean.setBuy_user(buyUserBean);
            demoList.add(ordersListBean);
        }
        if (demoList==null){
            lrv.setNoMore(true);
            if (!isLoadMore) {
                list.clear();
                interactionAdapter.clear();
                lrv.refreshComplete(REQUEST_COUNT);
                showErrorView(fl_layout, R.drawable.error_view_comment, "当前没有相关数据");
            }
            return;
        }
        if (isLoadMore){
            closeErrorView();
            loadList.clear();
            loadList = demoList;
            loadMoreData();
        }else {
            list.clear();
            list = demoList;
            showData();
        }
    }

    public void showData() {
        if (list.size() == 0){
            showErrorView(fl_layout, R.drawable.error_view_comment, getActivity().getResources().getString(R.string.empty_order_info));
            return;
        }else{
            closeErrorView();
        }
        interactionAdapter.clear();
        mCurrentCounter =list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        interactionAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            interactionAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }
}
