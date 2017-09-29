package com.cloudTop.starshare.ui.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConfig;
import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.been.StarQuestionBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.activity.PlayActivity;
import com.cloudTop.starshare.ui.main.adapter.BookStarVideoAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
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
 * 視頻問答歷史
 * Created by Shi 2017-9-12 16:50:54
 */

public class BookingStarAskByVideoFragment extends BaseFragment {

    @Bind(R.id.lrv)
    LRecyclerView lrv;

    @Bind(R.id.fl_auction_content)
    FrameLayout parentView;

    @Bind(R.id.radio_group)
    RadioGroup radio_group;

    private static final int REQUEST_COUNT = 10;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<StarQuestionBean.CircleListBean> list = new ArrayList<>();
    private List<StarQuestionBean.CircleListBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 1;
    private BookStarVideoAdapter autionTopAdapter;
    private int hotType = 1;
    private String star_code = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_booking_ask_by_video;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        initAdapter();
        initListener();
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
                getLrvData(false, 1, REQUEST_COUNT);
                break;
            case 1:
                hotType = 0;
                getLrvData(false, 1, REQUEST_COUNT);
                break;
            default:
                break;
        }
    }
    private void initListener() {

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                StarQuestionBean.CircleListBean circleListBean = list.get(position);
                if (circleListBean != null&& TextUtils.isEmpty(circleListBean.getSanswer())&&TextUtils.isEmpty(circleListBean.getVideo_url())){
                    ToastUtils.showShort("网红未回复");
                }else if (circleListBean != null){
                    Intent intent = new Intent(getActivity(), PlayActivity.class);
                    intent.putExtra("playUserUrl", AppConfig.QI_NIU_PIC_ADRESS + circleListBean.getVideo_url());
                    intent.putExtra("playStarUrl", AppConfig.QI_NIU_PIC_ADRESS + circleListBean.getSanswer());
                    intent.putExtra("StarVideoPic", circleListBean.getThumbnailS());
                    intent.putExtra("userHeadUrl", circleListBean.getHeadUrl());
                    intent.putExtra("userName", circleListBean.getNickName());
                    intent.putExtra("userQuestion", circleListBean.getUask());
                    if (!TextUtils.isEmpty(circleListBean.getSanswer())){
                        intent.putExtra("haveStarPlay", true);
                    }
                    if (!TextUtils.isEmpty(circleListBean.getVideo_url())){
                        intent.putExtra("haveUserPlay",true);
                    }
                    startActivity(intent);
                }
            }
        });
    }

    private void getLrvData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().getUserQuestionsInfo(star_code, SharePrefUtil.getInstance().getUserId(),start, count, SharePrefUtil.getInstance().getToken(), 1, hotType, new OnAPIListener<StarQuestionBean>() {
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
                LogUtils.loge("网红互动返回错误码" + ex.toString());
            }

            @Override
            public void onSuccess(StarQuestionBean bean) {
                if (bean == null || bean.getCircle_list() == null || bean.getCircle_list().size() == 0) {
                    if (!isLoadMore) {
                        list.clear();
                        autionTopAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    } else {
                        lrv.setNoMore(true);
                    }

                    return;
                }
                if (isLoadMore) {
                    loadList.clear();
                    loadList = bean.getCircle_list();
                    loadMoreData();
                } else {
                    list.clear();
                    list = bean.getCircle_list();
                    showData();
                }
            }
        });
    }

    private void initAdapter() {
        autionTopAdapter = new BookStarVideoAdapter(getActivity().getApplicationContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(autionTopAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.addItemDecoration(new SpacingDecoration(ScreenUtil.dip2px(10), ScreenUtil.dip2px(10), true));
        lrv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        lrv.setPullRefreshEnabled(true);
        lrv.setLoadMoreEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getLrvData(true,mCurrentCounter+1,REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 1;
                lrv.setNoMore(false);
                getLrvData(false, 1,REQUEST_COUNT);
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
