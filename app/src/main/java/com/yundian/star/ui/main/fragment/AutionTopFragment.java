package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
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
import java.util.List;

import butterknife.Bind;

import static com.yundian.star.R.id.lrv;

/**
 * Created by Administrator on 2017/6/12.
 * 拍卖排行榜  mai
 */

public class AutionTopFragment extends BaseFragment {


    private static final int REQUEST_COUNT = 10;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<FansEntrustReturnBean.PositionsListBean> list = new ArrayList<>();
    private List<FansEntrustReturnBean.PositionsListBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 1;
    private AutionTopAdapter autionTopAdapter;
    private int hotType;
    private String code;
    int buySell = 0;
    private LRecyclerView lrv;
    private FrameLayout parentView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_auction_top;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        if (getArguments() != null) {
            code = getArguments().getString(AppConstant.STAR_CODE);
            hotType = getArguments().getInt(AppConstant.AUCTION_TYPE);
        }
        if (hotType == 1) {
            buySell = 1;  //mai
        } else {
            buySell = -1;
        }
        initAdapter();
        getData(false, 1);
    }

    private void initData() {
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
        parentView = (FrameLayout) rootView.findViewById(R.id.parent_view);
    }


    private void getData(final boolean isLoadMore, int start) {

        NetworkAPIFactoryImpl.getInformationAPI().fansRntrust(code, buySell, start, REQUEST_COUNT, new OnAPIListener<FansEntrustReturnBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("粉丝热度失败------------------------------------------");
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        autionTopAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
            }

            @Override
            public void onSuccess(FansEntrustReturnBean bean) {
                LogUtils.loge("粉丝热度成功-------" + bean.toString());
                if (bean == null || bean.getPositionsList() == null || bean.getPositionsList().size() == 0) {
                    lrv.setNoMore(true);
                    return;
                }
                if (isLoadMore) {
                    closeErrorView();
                    loadList.clear();
                    loadList = bean.getPositionsList();
                    loadMoreData();
                } else {
                    list.clear();
                    list = bean.getPositionsList();
                    showData();
                }
            }
        });

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
                getData(true, mCurrentCounter + 1);
            }
        });
    }

    public void showData() {
        if (list != null && list.size() == 0) {
            showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
            return;
        } else {
            closeErrorView();
        }
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
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

}
