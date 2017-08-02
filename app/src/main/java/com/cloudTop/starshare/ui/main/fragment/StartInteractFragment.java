package com.cloudTop.starshare.ui.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.ui.main.adapter.StarInteractionAdapter;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.activity.CircleFriendsActivity;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11.
 * 明星互动
 */

public class StartInteractFragment extends BaseFragment {
    private LRecyclerView lrv;
    private FrameLayout fl_layout;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private ArrayList<StarListReturnBean.SymbolInfoBean> list = new ArrayList<>();
    private ArrayList<StarListReturnBean.SymbolInfoBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 0;
    private StarInteractionAdapter interactionAdapter;
    private static final int REQUEST_COUNT = 10;
    private long userId;
    private String token;

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
        getData(false, 0, REQUEST_COUNT);
    }

    private void initFindById() {
        userId = SharePrefUtil.getInstance().getUserId();
        token = SharePrefUtil.getInstance().getToken();
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
        fl_layout = (FrameLayout) rootView.findViewById(R.id.fl_layout);
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
                getData(true, mCurrentCounter,REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                lrv.setNoMore(false);
                getData(false, 0, REQUEST_COUNT);
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getActivity(), StarInfoActivity.class);
//                StarListReturnBean.SymbolInfoBean symbolInfoBean = list.get(position);
//                intent.putExtra(AppConstant.STAR_CODE, symbolInfoBean.getSymbol());
//                startActivity(intent);
                Intent intent0 = new Intent(getActivity(),CircleFriendsActivity.class);
                StarListReturnBean.SymbolInfoBean symbolInfoBean = list.get(position);
                intent0.putExtra(AppConstant.STAR_CODE,symbolInfoBean.getSymbol());
                intent0.putExtra(AppConstant.STAR_NAME,symbolInfoBean.getName());
                intent0.putExtra(AppConstant.STAR_HEAD_URL,symbolInfoBean.getPic());
                intent0.putExtra(AppConstant.IS_ONE,true);
                getActivity().startActivity(intent0);
            }
        });
    }

    private void getData(final boolean isLoadMore, int start, int end) {
        NetworkAPIFactoryImpl.getInformationAPI().getStarList(userId,
                token, 4, 0, start, end, new OnAPIListener<StarListReturnBean>() {
                    @Override
                    public void onError(Throwable ex) {
                        if (lrv != null) {
                            lrv.setNoMore(true);
                            if (!isLoadMore) {
                                list.clear();
                                interactionAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                                showErrorView(fl_layout, R.drawable.error_view_comment, "当前没有相关数据");
                            }
                        }
                        LogUtils.loge("明星互动返回错误码" + ex.toString());
                    }

                    @Override
                    public void onSuccess(StarListReturnBean starListReturnBean) {
                        LogUtils.loge("互动列表" + starListReturnBean.toString());
                        if (starListReturnBean == null || starListReturnBean.getSymbol_info() == null || starListReturnBean.getSymbol_info().size() == 0) {
                            if (!isLoadMore) {
                                list.clear();
                                interactionAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                                showErrorView(fl_layout, R.drawable.error_view_comment, "当前没有相关数据");
                            }else {
                                lrv.setNoMore(true);
                            }
                            return;
                        }
                        if (isLoadMore) {
                            loadList.clear();
                            loadList = starListReturnBean.getSymbol_info();
                            loadMoreData();
                        } else {
                            list.clear();
                            list = starListReturnBean.getSymbol_info();
                            showData();
                        }
                    }
                });
    }

    public void showData() {
        if (list.size() == 0) {
            showErrorView(fl_layout, R.drawable.error_view_comment, "暂无相关数据");
            return;
        } else {
            closeErrorView();
        }
        interactionAdapter.clear();
        mCurrentCounter = list.size();
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

    @Override
    public void onDestroy() {
        if (lrv!=null){
            lrv = null;
        }
        if (interactionAdapter!=null){
            interactionAdapter=null;
        }
        super.onDestroy();
    }
}
