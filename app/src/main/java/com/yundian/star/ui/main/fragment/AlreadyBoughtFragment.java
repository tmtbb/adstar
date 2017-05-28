package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.FansHotBuyReturnBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.AlreadyBoughtAdapter;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/24.
 * 已购
 */

public class AlreadyBoughtFragment extends BaseFragment {
    @Bind(R.id.lrv)
    LRecyclerView lrv ;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private AlreadyBoughtAdapter alreadyBoughtAdapter;
    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<FansHotBuyReturnBeen.ListBean> list = new ArrayList<>();
    private ArrayList<FansHotBuyReturnBeen.ListBean> loadList = new ArrayList<>();


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_already_bought_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initAdapter();
        //getData(false,1,REQUEST_COUNT);
    }


    private void initAdapter() {
        alreadyBoughtAdapter = new AlreadyBoughtAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(alreadyBoughtAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setNoMore(false);
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_0_5)
                .setPadding(R.dimen.dp_25)
                .setColorResource(R.color.color_dcdcdc)
                .build();

        //mRecyclerView.setHasFixedSize(true);
        lrv.addItemDecoration(divider);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
            }
        });
    }

    private void getData(final boolean isLoadMore,int start ,int end ) {
            NetworkAPIFactoryImpl.getInformationAPI().getSeekList("1001", start, end, new OnAPIListener<FansHotBuyReturnBeen>() {
                @Override
                public void onError(Throwable ex) {

                }

                @Override
                public void onSuccess(FansHotBuyReturnBeen fansHotBuyReturnBeen) {
                    if (fansHotBuyReturnBeen.getList()==null){
                        lrv.setNoMore(true);
                        return;
                    }
                    if (isLoadMore){
                        loadList.clear();
                        loadList = fansHotBuyReturnBeen.getList();
                        loadMoreData();
                    }else {
                        list.clear();
                        list = fansHotBuyReturnBeen.getList();
                        showData();
                    }
                }
            });


    }

    public void showData() {
        mCurrentCounter =list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        alreadyBoughtAdapter.addAll(list);
        lrv.refresh();
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            alreadyBoughtAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }
}
