package com.yundian.star.ui.main.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.StarListbeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.activity.StarTimeShareActivity;
import com.yundian.star.ui.main.adapter.MarketDetailAdapter;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/5/15.
 * 行情明星热度列表
 */

public class MarketDetailFragment extends BaseFragment {
    @Bind(R.id.ll_select_order)
    LinearLayout ll_select_order;
    @Bind(R.id.iv_select)
    ImageView iv_select;
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    MarketDetailAdapter marketDetailAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static int mCurrentCounter = 0;
    private static final int REQUEST_COUNT = 20;
    private static final int GET_DATA = 10;
    private static final int LOAD_DATA = 11;
    private int sortType = 0;
    private int ORDER = 0;
    private ArrayList<StarListbeen.SymbolInfoBean> list = new ArrayList<>();
    private String marketDetailName;
    private int marketDetailType;
    private MyHandler myHandler;
    private boolean isPrepared;

    public void showData() {
        marketDetailAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        marketDetailAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_market_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            marketDetailName = getArguments().getString(AppConstant.MARKET_DETAIL_NAME);
            marketDetailType = getArguments().getInt(AppConstant.MARKET_DETAIL_TYPE);
        }
        initAdpter();
        getData(false, 1, REQUEST_COUNT);
        myHandler = new MyHandler(this);
    }

    private void getData(final boolean isLoadMore, int start, int count) {
        if (marketDetailType == 0) {
            /*NetworkAPIFactoryImpl.getInformationAPI().getOptionsStarList(*//*SharePrefUtil.getInstance().getPhoneNum()*//*1770640+"",start,end,ORDER, new OnAPIListener<OptionsStarListBeen>() {
                @Override
                public void onError(Throwable ex) {

                }

                @Override
                public void onSuccess(OptionsStarListBeen optionsStarListBeen) {
                    if (optionsStarListBeen.getList()==null){
                        lrv.setNoMore(true);
                        return;
                    }
                    if (isLoadMore){
                        loadList.clear();
                        loadList = optionsStarListBeen.getList();
                        loadMoreData();
                    }else {
                        list.clear();
                        list = optionsStarListBeen.getList();
                        showData();
                    }
                }
            });*/
        } else {
            NetworkAPIFactoryImpl.getInformationAPI().getStarList(SharePrefUtil.getInstance().getUserId(), SharePrefUtil.getInstance().getToken(), sortType, 5, start, count, new OnAPIListener<StarListbeen>() {
                @Override
                public void onError(Throwable ex) {

                }

                @Override
                public void onSuccess(StarListbeen sarListbeen) {
                    LogUtils.loge("行情每个页面请求数据返回的retult:" + sarListbeen);
                    if (sarListbeen.getSymbol_info() == null) {
                        lrv.setNoMore(true);
                        return;
                    }
                    list.clear();
                    list = sarListbeen.getSymbol_info();
                    showData();
                }
            });
        }


    }

    private void initAdpter() {
        marketDetailAdapter = new MarketDetailAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(marketDetailAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        //mRecyclerView.setHasFixedSize(true);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setLoadMoreEnabled(false);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtils.logd(position + "");
                StarListbeen.SymbolInfoBean infoBean = list.get(position);
                Intent intent = new Intent(getActivity(), StarTimeShareActivity.class);
                intent.putExtra(AppConstant.STAR_CODE, infoBean.getSymbol());
                intent.putExtra(AppConstant.STAR_NAME, infoBean.getName());
                intent.putExtra(AppConstant.STAR_WID, infoBean.getWid());
                intent.putExtra(AppConstant.STAR_HEAD_URL, infoBean.getPic());
                startActivity(intent);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @OnClick(R.id.ll_select_order)
    public void onClickOrder() {
        if (sortType == 0) {
            iv_select.setImageDrawable(getResources().getDrawable(R.drawable.sort_nomal));
            sortType = 1;
        } else if (sortType == 1) {
            iv_select.setImageDrawable(getResources().getDrawable(R.drawable.sort_up));
            sortType = 0;
        }
        refresh();
    }

    private void refresh() {
        getData(false, 1, REQUEST_COUNT);
    }

    private static class MyHandler extends Handler {
        final private static int GRT_DATA = 111;
        private final WeakReference<MarketDetailFragment> mFragment;

        public MyHandler(MarketDetailFragment mfragment) {
            mFragment = new WeakReference<MarketDetailFragment>(mfragment);
        }

        @Override
        public void handleMessage(Message msg) {
            MarketDetailFragment fragment = mFragment.get();
            if (fragment != null) {
                switch (msg.what) {
                    case GRT_DATA:
                        fragment.refresh();
                        fragment.myHandler.sendEmptyMessageDelayed(GRT_DATA, 3 * 1000);
                        break;
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtils.loge("onHiddenChanged>> .....");
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.loge("setUserVisibleHint>>");
        if (isVisibleToUser) {
            startRefresh();
        } else {
            stopRefresh();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        setUserVisibleHint(getUserVisibleHint());
        LogUtils.loge("刷新行情明星列表onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.loge("停止刷新行情明星列表onPause");
        stopRefresh();
        super.onPause();
    }

    private void stopRefresh() {
        if (myHandler != null) {
            myHandler.removeMessages(myHandler.GRT_DATA);
            LogUtils.loge("停止刷新stopRefresh");
        }
    }

    private void startRefresh() {
        if (myHandler != null) {
            LogUtils.loge("刷新行情明星列表startRefresh");
            myHandler.removeMessages(myHandler.GRT_DATA);
            myHandler.sendEmptyMessage(myHandler.GRT_DATA);
        }
    }

}
