package com.yundian.star.ui.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.session.SessionCustomization;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.BookStarListAdapter;
import com.yundian.star.ui.wangyi.session.activity.P2PMessageActivity;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约的明星
 * Created by sll on 2017/5/24.
 */

public class BookingStarActivity extends BaseActivity {

    private BookStarListAdapter bookStarListAdapter;
    private static final int TOTAL_COUNTER = 34;
    private static final int REQUEST_COUNT = 10;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static int mCurrentCounter = 1;
    private List<BookingStarListBean> list = new ArrayList<>();
    private List<BookingStarListBean> loadList = new ArrayList<>();
    private LRecyclerView lrv;
    private NormalTitleBar nt_title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_booking_star;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        initFindById();
        initAdapter();
        getData(false, 1, REQUEST_COUNT);
    }

    private void initFindById() {
        nt_title = (NormalTitleBar)findViewById(R.id.nt_title);
        lrv = (LRecyclerView)findViewById(R.id.lrv);
        nt_title.setTitleText(getResources().getString(R.string.booking_star_list));
        nt_title.setBackVisibility(true);
    }

    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getDealAPI().bookingStarList(start, count, new OnAPIListener<List<BookingStarListBean>>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.logd("预约明星列表错误----------");
                if (lrv != null) {
                    lrv.setNoMore(true);
                }
//
                //lrv.refreshComplete(REQUEST_COUNT);
            }

            @Override
            public void onSuccess(List<BookingStarListBean> bookingStarList) {
                LogUtils.logd("预约明星列表成功----------");

                if (bookingStarList == null || bookingStarList.size() == 0) {
                    lrv.setNoMore(false);
                    return;
                }
                if (isLoadMore) {
                    loadList.clear();
                    loadList = bookingStarList;
//                    mCurrentCounter = list.size();
                    loadMoreData();
                } else {
                    list.clear();
                    list = bookingStarList;
                    showData();
                }
            }
        });
    }

    private void initAdapter() {
        bookStarListAdapter = new BookStarListAdapter(mContext);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(bookStarListAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(mContext));
       // lrv.setPullRefreshEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter+1, REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false, 1, 10);
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                    BookingStarListBean listBean = list.get(position);
                    SessionCustomization customization = NimUIKit.getCommonP2PSessionCustomization();
                    P2PMessageActivity.start(mContext, listBean.getFaccid(),listBean.getStarcode(),listBean.getStarname(), customization, null);
            }
        });
    }

    public void showData() {
        if (lrv==null){
            return;
        }
        bookStarListAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        bookStarListAdapter.addAll(list);
        LogUtils.loge("当前刷新list:" + list.toString());
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            bookStarListAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(loadList.size());
        }
    }
}
