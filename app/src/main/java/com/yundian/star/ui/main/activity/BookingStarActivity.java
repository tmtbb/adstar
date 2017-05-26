package com.yundian.star.ui.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.ui.main.adapter.BookStarListAdapter;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yundian.star.R.id.nt_title;

/**
 * 预约的明星
 * Created by sll on 2017/5/24.
 */

public class BookingStarActivity extends BaseActivity {


    @Bind(nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    private ArrayList<BookingStarListBean> arrayList;
    private BookStarListAdapter bookStarListAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static int mCurrentCounter = 1;
    private static final int TOTAL_COUNTER = 34;

    @Override
    public int getLayoutId() {
        return R.layout.activity_booking_star;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.booking_star_list));
        ntTitle.setBackVisibility(true);
        initData();
    }

    private void initData() {
        arrayList = new ArrayList<>();
        BookingStarListBean infor = null;
        for (int i = 0; i < 20; i++) {
            infor = new BookingStarListBean();
            arrayList.add(infor);
        }

        bookStarListAdapter = new BookStarListAdapter(this);
        bookStarListAdapter.setDataList(arrayList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(bookStarListAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.color_cccccc)
                .build();
        //mRecyclerView.setHasFixedSize(true);
        lrv.addItemDecoration(divider);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setPullRefreshEnabled(true);
        initListner();
    }

    private void initListner() {
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookStarListAdapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                mCurrentCounter = 1;
//                startProgressDialog("刷新中");
                requestData();
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showShort("当前被点击:" + position);
//                SessionHelper.startP2PSession(StarCommunicationBookActivity.this, "17682310986");
            }
        });

    }

    private void requestData() {
        int currentSize = bookStarListAdapter.getItemCount();
        //模拟组装10个数据
        ArrayList<BookingStarListBean> newList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (newList.size() + currentSize >= TOTAL_COUNTER) {
                break;
            }

            BookingStarListBean item = new BookingStarListBean();
            newList.add(item);
        }

        addItems(newList);
        lrv.refreshComplete(currentSize);
    }

    public void addItems(ArrayList<BookingStarListBean> list) {
        bookStarListAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
}
