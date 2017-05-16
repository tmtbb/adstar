package com.yundian.star.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.ui.main.adapter.MarketDetailAdapter;
import com.yundian.star.ui.main.model.TestModel;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/5/15.
 */

public class MarketDetailFragment extends BaseFragment {
    @Bind(R.id.ll_select_order)
    LinearLayout ll_select_order ;
    @Bind(R.id.iv_select)
    ImageView iv_select ;
    @Bind(R.id.lrv)
    LRecyclerView lrv ;
    MarketDetailAdapter marketDetailAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<TestModel> arrayList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_market_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initAdpter();
        initData();
    }

    private void initData() {

       // arrayList.clear();
        //arrayList = list;
        for (int i=0;i<20;i++){
            TestModel testModel = new TestModel();
            testModel.setUsername(i+"ren");
            arrayList.add(testModel);
        }
        //mCurrentCounter = list.size();
        //newsInfoAdapter.clear();
        //newsInfoAdapter.setDataList(arrayList);
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        marketDetailAdapter.addAll(arrayList);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void initAdpter() {
        marketDetailAdapter = new MarketDetailAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(marketDetailAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        //mRecyclerView.setHasFixedSize(true);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setLoadMoreEnabled(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }
}
