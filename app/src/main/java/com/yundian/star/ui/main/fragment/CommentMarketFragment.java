package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
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
        getData(false,1,REQUEST_COUNT);
    }

    private void initAdapter() {
        CommentMarketAdapter commentMarketAdapter = new CommentMarketAdapter(getActivity());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(commentMarketAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.dp_13)
                .setColorResource(R.color.transparent)
                .build();
        //mRecyclerView.setHasFixedSize(true);
        lrv.addItemDecoration(divider);
        lrv.setNoMore(false);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
            }
        });
    }

    private void getData(final boolean isLoadMore,int start ,int end) {
        NetworkAPIFactoryImpl.getInformationAPI().getFansComments("13072714518","1001", new OnAPIListener<Object>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(Object o) {
                        LogUtils.loge(o.toString());
                    }
                }
        );
    }
}
