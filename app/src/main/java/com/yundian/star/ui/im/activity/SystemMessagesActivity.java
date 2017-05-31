package com.yundian.star.ui.im.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.StarMailListBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.im.adapter.BookStarComAdapter;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/10.
 */

public class SystemMessagesActivity extends BaseActivity {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;

    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<StarMailListBeen.DepositsinfoBean> list = new ArrayList<>();
    private ArrayList<StarMailListBeen.DepositsinfoBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private BookStarComAdapter starCommBookAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sys_message;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(R.string.systen_news);
        initAdapter();
        getData(false,0,REQUEST_COUNT);
        initListener();
    }

    private void initListener() {
        nt_title.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initAdapter() {
        starCommBookAdapter = new BookStarComAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(starCommBookAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setPullRefreshEnabled(false);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        /*lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
            }
        });*/
    }

    private void getData(final boolean isLoadMore,int start ,int end ) {

            for (int i =0 ;i<5;i++){
                StarMailListBeen.DepositsinfoBean bean = new StarMailListBeen.DepositsinfoBean();
                bean.setStarname(i);
                list.add(bean);
            }
            showData();
        NetworkAPIFactoryImpl.getInformationAPI().getStarmaillist(SharePrefUtil.getInstance().getUserId(), SharePrefUtil.getInstance().getToken(),"123", start, end, new OnAPIListener<StarMailListBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarMailListBeen starMailListBeen) {
                LogUtils.loge(starMailListBeen.toString());
            }
        });

    }

    public void showData() {
        mCurrentCounter =list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        starCommBookAdapter.addAll(list);
        lrv.refresh();
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            starCommBookAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }
}
