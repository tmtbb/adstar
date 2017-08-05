package com.cloudTop.starshare.ui.main.fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.been.BookingStarListBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.CommentExpandAdapter;
import com.cloudTop.starshare.ui.main.adapter.ExpandableRecyclerAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.cloudTop.starshare.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 持有明星时间
 * Created by sll on 2017/7/4.
 */

public class HoldingStarTimeFragment extends BaseFragment {

    private LRecyclerView lrv;
    private static final int REQUEST_COUNT = 10;
    private CommentExpandAdapter mDataAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private static int mCurrentCounter = 1;
    private List<BookingStarListBean> list = new ArrayList<>();
    private List<BookingStarListBean> loadList = new ArrayList<>();


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_holding_time;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        initFindById();
        initNewAdapter();
        getData(false, 1, REQUEST_COUNT);
    }

    private void initFindById() {
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
    }

    private void initNewAdapter() {
        mDataAdapter = new CommentExpandAdapter(getActivity(), lrv);
        mDataAdapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        lrv.setAdapter(mLRecyclerViewAdapter);

        lrv.setLayoutManager(new LinearLayoutManager(getActivity()));

        lrv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                getData(false, 1, REQUEST_COUNT);  //下拉刷新
            }
        });

        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter + 1, REQUEST_COUNT);
            }
        });
        lrv.refresh();
    }


    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getDealAPI().bookingStarList(start, count, new OnAPIListener<List<BookingStarListBean>>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.logd("预约明星列表错误----------");
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        mDataAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                    }
                }
            }

            @Override
            public void onSuccess(List<BookingStarListBean> bookingStarList) {
                LogUtils.logd("预约明星列表成功----------");

                if (bookingStarList == null || bookingStarList.size() == 0) {
                    if (!isLoadMore) {
                        list.clear();
                        mDataAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                    }else {
                        lrv.setNoMore(true);
                    }
                    return;
                }
                if (isLoadMore) {
                    loadList.clear();
                    loadList = bookingStarList;
                    loadMoreData();
                } else {
                    list.clear();
                    list = bookingStarList;
                    showData();
                }
            }
        });
    }
    public void showData() {
        if (lrv == null ||list.size() == 0 ) {
            return;
        }
        mDataAdapter.setItems(getSampleItems());
        mCurrentCounter = list.size();
        lrv.refreshComplete(REQUEST_COUNT);
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            mDataAdapter.setItems(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    public List<BookingStarListBean> getSampleItems() {
        List<BookingStarListBean> items = new ArrayList<>();
        BookingStarListBean bean1;
        BookingStarListBean bean2;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setItemType(ExpandableRecyclerAdapter.TYPE_HEADER);
            items.add(list.get(i));

            bean1 = new BookingStarListBean();
            bean1.setTypeTitle("与TA聊天");
            bean1.setItemType(CommentExpandAdapter.TYPE_PERSON);
            items.add(bean1);

            bean2 = new BookingStarListBean();
            bean2.setTypeTitle("与TA约见");
            bean2.setItemType(CommentExpandAdapter.TYPE_PERSON);
            items.add(bean2);
        }
        return items;
    }
}
