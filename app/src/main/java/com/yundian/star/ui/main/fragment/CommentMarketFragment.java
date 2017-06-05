package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.CommentMarketAdapter;
import com.yundian.star.utils.LogUtils;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/22.
 * 粉丝评论界面
 */

public class CommentMarketFragment extends BaseFragment {
    @Bind(R.id.lrv)
    LRecyclerView lrv ;
    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_comment_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initAdapter();
        getData(false,0,REQUEST_COUNT);
    }

    private void initAdapter() {
        CommentMarketAdapter commentMarketAdapter = new CommentMarketAdapter(getActivity());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(commentMarketAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setNoMore(false);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
    }

    private void getData(final boolean isLoadMore,int start ,int count) {
        NetworkAPIFactoryImpl.getInformationAPI().inquiry("100002", start, count, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv!=null){
                    lrv.setNoMore(true);
                }
            }

            @Override
            public void onSuccess(Object o) {
                LogUtils.loge("评论"+o.toString());
            }
        });
    }
}
