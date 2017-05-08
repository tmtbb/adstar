package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.ui.main.adapter.NewsInforAdapter;
import com.yundian.star.ui.main.contract.NewInfoContract;
import com.yundian.star.ui.main.model.NewsInforModel;
import com.yundian.star.ui.main.presenter.NewsInfoPresenter;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/8.
 */

public class NewsInfoFragment extends BaseFragment<NewsInfoPresenter,NewsInforModel> implements NewInfoContract.View{
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title ;
    @Bind(R.id.lrv)
    LRecyclerView lrv ;
//    @Bind(R.id.loadingTip)
//    LoadingTip loadingTip ;
    private ArrayList<NewsInforModel> arrayList;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private NewsInforAdapter newsInfoAdapter;
    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;
    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 34;
    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        nt_title.setTvLeftVisiable(false);
        nt_title.setTitleText(getString(R.string.news_info_title));
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    int currentSize = lRecyclerViewAdapter.getItemCount();
                    mPresenter.getMoreData();
                    lrv.refreshComplete(REQUEST_COUNT);
                } else {
                    //the end
                    lrv.setNoMore(true);
                }
            }
        });
    }
    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void initDatas(ArrayList<NewsInforModel> list) {
        arrayList = list;
        newsInfoAdapter = new NewsInforAdapter(getActivity());
        newsInfoAdapter.setDataList(arrayList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(newsInfoAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.dp_1)
                .setPadding(R.dimen.dp_4)
                .setColorResource(R.color.white)
                .build();
        //mRecyclerView.setHasFixedSize(true);
        lrv.addItemDecoration(divider);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        //add a HeaderView
        final View header = LayoutInflater.from(getContext()).inflate(R.layout.sample_header,(ViewGroup)getActivity().findViewById(android.R.id.content), false);
        lRecyclerViewAdapter.addHeaderView(header);
    }

    @Override
    public void addItems(ArrayList<NewsInforModel> list) {
        newsInfoAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
}
