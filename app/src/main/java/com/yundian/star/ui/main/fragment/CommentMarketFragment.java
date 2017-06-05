package com.yundian.star.ui.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.FansHotBuyReturnBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.activity.AddUserCommentActivity;
import com.yundian.star.ui.main.adapter.CommentMarketAdapter;
import com.yundian.star.utils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/22.
 * 粉丝评论界面
 */

public class CommentMarketFragment extends BaseFragment {
    @Bind(R.id.lrv)
    LRecyclerView lrv ;
    @Bind(R.id.tv_add_comment)
    TextView tv_add_comment ;
    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 1;
    private String code;
    private ArrayList<FansHotBuyReturnBeen.ListBean> list = new ArrayList<>();
    private ArrayList<FansHotBuyReturnBeen.ListBean> loadList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_comment_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments()!=null){
            code = getArguments().getString(AppConstant.STAR_CODE);
            LogUtils.loge("明星code"+code);
        }
        initAdapter();
        getData(false,0,REQUEST_COUNT);
        if (list.size()==0){
            tv_add_comment.setVisibility(View.VISIBLE);
        }else {
            tv_add_comment.setVisibility(View.GONE);
        }
    }

    private void initAdapter() {
        CommentMarketAdapter commentMarketAdapter = new CommentMarketAdapter(getActivity());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(commentMarketAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setNoMore(false);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
    }

    private void getData(final boolean isLoadMore,int start ,int count) {
        NetworkAPIFactoryImpl.getInformationAPI().inquiry(code, start, count, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv!=null){
                    lrv.setNoMore(true);
                }
            }

            @Override
            public void onSuccess(Object o) {
                LogUtils.loge("评论"+o.toString());
            }
        });
    }

    @OnClick(R.id.tv_add_comment)
    public void OnclickAddComment(){
        Intent intent = new Intent(getActivity(), AddUserCommentActivity.class);
        intent.putExtra(AppConstant.STAR_CODE,code);
        getActivity().startActivity(intent);
    }
}
