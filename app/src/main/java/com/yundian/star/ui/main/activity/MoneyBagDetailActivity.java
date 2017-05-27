package com.yundian.star.ui.main.activity;

import android.os.Bundle;
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
import com.yundian.star.been.MyPopupMenuEntity;
import com.yundian.star.listener.OnChildViewClickListener;
import com.yundian.star.ui.main.adapter.MoneyBagDetailAdapter;
import com.yundian.star.ui.view.MyPopupMenu;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.yundian.star.ui.wangyi.DemoCache.getContext;

/**
 * Created by sll on 2017/5/24.
 */

public class MoneyBagDetailActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    private MyPopupMenu popupMenu;
    private List<MyPopupMenuEntity> lists;

    @Override
    public int getLayoutId() {
        return R.layout.activity_money_bag_detail;
    }

    @Override
    public void initPresenter() {


    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.money_bag_detail));
        ntTitle.setTvLeftVisiable(true);
        ntTitle.setRightImagSrc(R.drawable.about_logo);
        initPopupMenu();
        setListData();
    }

    private void initPopupMenu() {
        setData();
        ntTitle.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new MyPopupMenu(getContext(), lists);
                popupMenu.setOnChildViewClickListener(new OnChildViewClickListener() {
                    @Override
                    public void onChildViewClick(View childView, int action, Object obj) {
                        ToastUtils.showShort("子控件被点击了");
                    }
                });

                popupMenu.showAsDropDown(ntTitle.getRightImage(), 0, 0);
            }
        });
    }

    private void setData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyPopupMenuEntity entity = new MyPopupMenuEntity();
            entity.setText("日期" + i);
            lists.add(entity);
        }
    }

    private ArrayList<BookingStarListBean> arrayList;
    private MoneyBagDetailAdapter moneyBagDetailAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static int mCurrentCounter = 1;
    private static final int TOTAL_COUNTER = 34;



    private void setListData() {
        arrayList = new ArrayList<>();
        BookingStarListBean infor = null;
        for (int i = 0; i < 20; i++) {
            infor = new BookingStarListBean();
            arrayList.add(infor);
        }

        moneyBagDetailAdapter = new MoneyBagDetailAdapter(this);
        moneyBagDetailAdapter.setDataList(arrayList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(moneyBagDetailAdapter);
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
                moneyBagDetailAdapter.clear();
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
                Bundle bundle = new Bundle();
                bundle.putParcelable("dealDetail", new BookingStarListBean());
                startActivity(BillingDetailsActivity.class,bundle);
//                SessionHelper.startP2PSession(StarCommunicationBookActivity.this, "17682310986");
            }
        });

    }

    private void requestData() {
        int currentSize = moneyBagDetailAdapter.getItemCount();
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
        moneyBagDetailAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
}
