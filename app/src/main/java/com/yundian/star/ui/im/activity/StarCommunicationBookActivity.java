package com.yundian.star.ui.im.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.ui.im.adapter.StarCommBookAdapter;
import com.yundian.star.ui.main.model.NewsInforModel;
import com.yundian.star.ui.wangyi.session.SessionHelper;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/10.
 */

public class StarCommunicationBookActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    private ArrayList<NewsInforModel> arrayList;
    private StarCommBookAdapter sarCommBookAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static int mCurrentCounter = 0;
    private static final int TOTAL_COUNTER = 34;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_com_book;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setTvLeftVisiable(true);
        nt_title.setTitleText(getString(R.string.famous_address_book));
        initData();
        initDatas(arrayList);
    }

    private void initData() {
        arrayList = new ArrayList<>();
        NewsInforModel infor= null ;
        for (int i = 0; i < 20; i++) {
            infor = new NewsInforModel();
            infor.setUsername("测试"+i);
            arrayList.add(infor);
        }
    }


    public void initDatas(ArrayList<NewsInforModel> list) {
        arrayList = list;
        sarCommBookAdapter = new StarCommBookAdapter(this);
        sarCommBookAdapter.setDataList(arrayList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(sarCommBookAdapter);
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

                sarCommBookAdapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;
                //startProgressDialog("刷新中");
                requestData();
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SessionHelper.startP2PSession(StarCommunicationBookActivity.this, "17682310986");
            }
        });

    }

    private void requestData() {
        int currentSize = sarCommBookAdapter.getItemCount();

        //模拟组装10个数据
        ArrayList<NewsInforModel> newList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (newList.size() + currentSize >= TOTAL_COUNTER) {
                break;
            }

            NewsInforModel item = new NewsInforModel();
            item.setUsername("刷新数据" + i);
            newList.add(item);
        }

        addItems(newList);
        lrv.refreshComplete(currentSize);
    }

    public void addItems(ArrayList<NewsInforModel> list) {
        sarCommBookAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
}
