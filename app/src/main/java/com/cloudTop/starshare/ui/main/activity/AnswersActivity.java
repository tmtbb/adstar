package com.cloudTop.starshare.ui.main.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.AnswersAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.netease.nim.uikit.common.ui.recyclerview.decoration.SpacingDecoration;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/16.
 * 明星问答
 */

public class AnswersActivity extends BaseActivity {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.parent_view)
    FrameLayout parentView;

    private AnswersAdapter marketDetailAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static int mCurrentCounter = 0;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<StarListReturnBean.SymbolInfoBean> list = new ArrayList<>();
    private ArrayList<StarListReturnBean.SymbolInfoBean> loadList = new ArrayList<>();
    private String code;
    private String star_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_answers;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        code = intent.getStringExtra("star_code");
        star_name = intent.getStringExtra("star_name");
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(star_name);
        nt_title.setRightTitle("历史提问记录");
        nt_title.setRightTitleVisibility(true);
        initAdpter();
        initListener();
        getData(false, 0, REQUEST_COUNT);
    }

    private void initListener() {
        nt_title.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HistoryAnswersActivity.class);
            }
        });

    }

    //获取数据
    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().getStarList(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(), 5, 1, start, count, new OnAPIListener<StarListReturnBean>() {
                    @Override
                    public void onError(Throwable ex) {
                        if (lrv != null) {
                            lrv.setNoMore(true);
                            if (!isLoadMore) {
                                list.clear();
                                marketDetailAdapter.clear();
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
                                marketDetailAdapter.clear();
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

    public void showData() {
        if (list.size() == 0) {
            showErrorView(parentView, R.drawable.error_view_comment, "暂无相关数据");
            return;
        } else {
            closeErrorView();
        }
        if (marketDetailAdapter!=null){
            marketDetailAdapter.clear();
        }
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        marketDetailAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            marketDetailAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    @Override
    public void onDestroy() {
        if (lrv!=null){
            lrv = null ;
        }
        if (marketDetailAdapter!=null){
            marketDetailAdapter=null;
        }
        super.onDestroy();
    }
    private void initAdpter() {
        marketDetailAdapter = new AnswersAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(marketDetailAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(this));

        lrv.addItemDecoration(new SpacingDecoration(ScreenUtil.dip2px(10), ScreenUtil.dip2px(10), true));
        lrv.setLoadMoreEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
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
    }
    @OnClick({R.id.btn_recharge_sure})
    public void onClickSwitch(View view){
        switch (view.getId()){
            case R.id.btn_recharge_sure:
                Intent intent = new Intent(this,AskToAnswersActivity.class);
                intent.putExtra("star_code",code);
                startActivity(intent);
                break;
        }
    }
}
