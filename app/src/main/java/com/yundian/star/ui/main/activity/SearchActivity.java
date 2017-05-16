package com.yundian.star.ui.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.base.SearchReturnbeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.SearchListAdapter;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/16.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.iv_cancel)
    ImageView iv_cancel;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title ;
    private String textsearch;
    private SearchListAdapter searchListAdapter;
    private LRecyclerViewAdapter recyclerViewAdapter;
    private List<SearchReturnbeen.ListBean> list =new ArrayList<SearchReturnbeen.ListBean>(){};


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setTitleText(R.string.search);
        nt_title.setBackVisibility(true);
        initListener();
        initAdapter();
    }

    private void initAdapter() {
        searchListAdapter = new SearchListAdapter(this);
        recyclerViewAdapter = new LRecyclerViewAdapter(searchListAdapter);
        lrv.setAdapter(recyclerViewAdapter);
        //mRecyclerView.setHasFixedSize(true);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setPullRefreshEnabled(false);
        lrv.setLoadMoreEnabled(false);
        recyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    private void initListener() {
        iv_cancel.setOnClickListener(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())){
                    list.clear();
                    searchListAdapter.clear();
                    recyclerViewAdapter.notifyDataSetChanged();
                    lrv.refresh();
                    return;
                }
                NetworkAPIFactoryImpl.getInformationAPI().searchStar(s.toString(), new OnAPIListener<SearchReturnbeen>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(SearchReturnbeen searchReturnbeen) {
                        LogUtils.loge(searchReturnbeen.toString());
                        list.clear();
                        searchListAdapter.clear();
                        if (searchReturnbeen.getResult()==1){
                            list = searchReturnbeen.getList();
                            //newsInfoAdapter.setDataList(arrayList);
                            recyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                            LogUtils.loge(list.toString());
                            searchListAdapter.addAll(list);
                        }else {
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                        lrv.refresh();
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                et_search.setText("");
                break;
        }
    }
}
