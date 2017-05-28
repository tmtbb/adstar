package com.yundian.star.ui.im.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.ui.im.adapter.BookStarComAdapter;
import com.yundian.star.ui.wangyi.session.SessionHelper;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/10.
 */

public class StarCommunicationBookActivity extends BaseActivity {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;

    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<BookingStarListBean.ListBean> list = new ArrayList<>();
    private ArrayList<BookingStarListBean.ListBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private BookStarComAdapter starCommBookAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_com_book;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(R.string.famous_address_book);
        initAdapter();
        getData(false,1,REQUEST_COUNT);
        initListener();
    }

    private void initListener() {
        nt_title.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SessionHelper.startP2PSession(StarCommunicationBookActivity.this,"17682310986");
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

            for (int i =0 ;i<100;i++){
                BookingStarListBean.ListBean bean = new BookingStarListBean.ListBean();
                bean.setStarname("明星"+i);
                list.add(bean);
            }
            showData();

        /*NetworkAPIFactoryImpl.getInformationAPI().getSeekList("1001", start, end, new OnAPIListener<FansHotBuyReturnBeen>() {
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
        });*/


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
