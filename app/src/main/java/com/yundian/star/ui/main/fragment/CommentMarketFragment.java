package com.yundian.star.ui.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.CommentMarketBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.activity.AddUserCommentActivity;
import com.yundian.star.ui.main.adapter.CommentMarketAdapter;
import com.yundian.star.utils.CheckLoginUtil;
import com.yundian.star.utils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/22.
 * 粉丝评论界面
 */

public class CommentMarketFragment extends BaseFragment {

    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 0;
    private String code;
    private ArrayList<CommentMarketBeen.CommentsinfoBean> list = new ArrayList<>();
    private ArrayList<CommentMarketBeen.CommentsinfoBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private CommentMarketAdapter commentMarketAdapter;
    private LRecyclerView lrv;
    private TextView tv_add_comment;
    private FrameLayout parentView;
    private TextView tv_num;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_comment_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        if (getArguments() != null) {
            code = getArguments().getString(AppConstant.STAR_CODE);
            LogUtils.loge("明星code" + code);
        }
        initAdapter();
        getData(false, 1, REQUEST_COUNT);
        initListener();
    }

    private void initData() {
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
        tv_add_comment = (TextView) rootView.findViewById(R.id.tv_add_comment);
        parentView = (FrameLayout) rootView.findViewById(R.id.parent_view);
    }

    private void initListener() {
        /*lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentMarketAdapter.clear();
                mCurrentCounter = 0;
                getData(false,0,REQUEST_COUNT);
            }
        });*/
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter + 1, REQUEST_COUNT);
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                boolean login = CheckLoginUtil.checkLogin(getActivity());
                if (login) {
                    Intent intent = new Intent(getActivity(), AddUserCommentActivity.class);
                    intent.putExtra(AppConstant.STAR_CODE, code);
                    getActivity().startActivity(intent);
                }

            }
        });

    }

    private void initAdapter() {
        commentMarketAdapter = new CommentMarketAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(commentMarketAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setNoMore(false);
        lrv.setPullRefreshEnabled(false);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        initLrvHeadView();
    }

    private void initLrvHeadView() {
        //add a HeaderView
        View header = LayoutInflater.from(getContext()).inflate(R.layout.head_comment_list, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        tv_num = (TextView) header.findViewById(R.id.tv_num);
        lRecyclerViewAdapter.addHeaderView(header);
    }

    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().inquiry(code, start, count, new OnAPIListener<CommentMarketBeen>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("刷新评论onError");
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        commentMarketAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
            }

            @Override
            public void onSuccess(CommentMarketBeen been) {
                LogUtils.loge("评论" + been.toString());
                LogUtils.loge("刷新评论onSuccess"+been.toString());
                if (been.getCommentsinfo() == null) {
                    lrv.refreshComplete(REQUEST_COUNT);
                    return;
                }
                tv_num.setText(been.getTotal_count()+"");
                if (isLoadMore) {
                    closeErrorView();
                    loadList.clear();
                    loadList = been.getCommentsinfo();
                    loadMoreData();
                } else {
                    list.clear();
                    list = been.getCommentsinfo();
                    showData();
                }

            }
        });
    }

    @OnClick(R.id.tv_add_comment)
    public void OnclickAddComment() {
        Intent intent = new Intent(getActivity(), AddUserCommentActivity.class);
        intent.putExtra(AppConstant.STAR_CODE, code);
        getActivity().startActivityForResult(intent,1);
    }

    public void showData() {
        if (list.size() == 0) {
            showErrorView(parentView, R.drawable.error_view_comment, "当前还没有相关数据");
            tv_add_comment.setVisibility(View.VISIBLE);
            return;
        } else {
            closeErrorView();
            tv_add_comment.setVisibility(View.GONE);
        }
        commentMarketAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        commentMarketAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            commentMarketAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(loadList.size());
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible()) {
            getData(false, 1, REQUEST_COUNT);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onResume() {
        setUserVisibleHint(getUserVisibleHint());
        LogUtils.loge("刷新onResume");
        super.onResume();
    }

}
