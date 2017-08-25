package com.cloudTop.starshare.ui.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.HistoryVoiceAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.netease.nim.uikit.common.ui.recyclerview.decoration.SpacingDecoration;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/17.
 * 历史定制语音
 */

public class HistoryVoiceActivity extends BaseActivity {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.fl_auction_content)
    FrameLayout parentView;
    @Bind(R.id.radio_group)
    RadioGroup radio_group;
    private static final int REQUEST_COUNT = 10;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<StarListReturnBean.SymbolInfoBean> list = new ArrayList<>();
    private List<StarListReturnBean.SymbolInfoBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 0;
    private HistoryVoiceAdapter autionTopAdapter;
    private int hotType = 1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_history_answers;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(getString(R.string.history_custom));
        initAdapter();
        SwitchTo(0);
    }


    @OnClick({R.id.rb_1, R.id.rb_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_1:
                SwitchTo(0);
                break;
            case R.id.rb_2:  //选择充值方式
                SwitchTo(1);
                break;
        }
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        switch (position) {
            case 0:
                hotType = 1;
                getLrvData(false, 0, REQUEST_COUNT);

                break;
            case 1:
                hotType = -1;
                getLrvData(false, 0, REQUEST_COUNT);
                break;
            default:
                break;
        }
    }

    private void getLrvData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().getStarList(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(), 5, 1, start, count, new OnAPIListener<StarListReturnBean>() {
                    @Override
                    public void onError(Throwable ex) {
                        if (lrv != null) {
                            lrv.setNoMore(true);
                            if (!isLoadMore) {
                                list.clear();
                                autionTopAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                                showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
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
                                autionTopAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                                showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
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

    private void initAdapter() {
        autionTopAdapter = new HistoryVoiceAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(autionTopAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.addItemDecoration(new SpacingDecoration(ScreenUtil.dip2px(10), ScreenUtil.dip2px(10), true));
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setPullRefreshEnabled(true);
        lrv.setLoadMoreEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getLrvData(true,mCurrentCounter,REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                lrv.setNoMore(false);
                getLrvData(false, 0,REQUEST_COUNT);
            }
        });
    }

    public void showData() {
        if (list != null && list.size() == 0) {
            //showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
            return;
        } else {
            closeErrorView();
        }
        autionTopAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        autionTopAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
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
