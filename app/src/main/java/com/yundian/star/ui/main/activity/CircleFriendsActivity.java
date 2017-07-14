package com.yundian.star.ui.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.CommentConfig;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.CircleFriendBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.CircleFriendAdapter;
import com.yundian.star.ui.main.contract.CircleContract;
import com.yundian.star.ui.main.presenter.CirclePresenter;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.CommentListView;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/12.
 * 朋友圈
 */

public class CircleFriendsActivity extends BaseActivity implements CircleContract.View {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.fl_pr)
    FrameLayout fl_pr;
    private CirclePresenter presenter;
    private CircleFriendAdapter circleFriendAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int selectCommentItemOffset;
    private LinearLayoutManager layoutManager;
    private CommentConfig commentConfig;
    private int selectCircleItemH;
    private static int mCurrentCounter = 0;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<CircleFriendBean.CircleListBean> list = new ArrayList<>();
    private ArrayList<CircleFriendBean.CircleListBean> loadList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_friend;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        presenter = new CirclePresenter(this);
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(R.string.find_star);
        initAdapter();
        getData(false, 0, REQUEST_COUNT);
    }

    private void initAdapter() {

        circleFriendAdapter = new CircleFriendAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(circleFriendAdapter);
        circleFriendAdapter.setCirclePresenter(presenter);
        lrv.setAdapter(lRecyclerViewAdapter);
        layoutManager = new LinearLayoutManager(this);
        lrv.setLayoutManager(layoutManager);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter, REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false, 0, REQUEST_COUNT);
            }
        });


//        systemMessageAdapter = new SystemMessageAdapter(this,list, SharePrefUtil.getInstance().getUserId());
//        lRecyclerViewAdapter = new LRecyclerViewAdapter(systemMessageAdapter);
//        lrv.setAdapter(lRecyclerViewAdapter);
//        lrv.setLayoutManager(new LinearLayoutManager(this));
//        lrv.setNoMore(false);
//        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        /*lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
//            }
//        });*/
//        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                getData(true, mCurrentCounter + 1, REQUEST_COUNT);
//            }
//        });
//        lrv.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getData(false, 1, REQUEST_COUNT);
//            }
//        });
    }


    @Override
    public void update2DeleteCircle(String circleId) {

    }

    @Override
    public void update2AddFavorite(int circlePosition, CircleFriendBean.CircleListBean.ApproveListBean addItem) {
        if (addItem != null) {
            CircleFriendBean.CircleListBean item = (CircleFriendBean.CircleListBean) circleFriendAdapter.getDatas().get(circlePosition);
            item.getApprove_list().add(addItem);
            circleFriendAdapter.notifyDataSetChanged();
            //circleAdapter.notifyItemChanged(circlePosition+1);
        }
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {

    }

    @Override
    public void update2AddComment(int circlePosition, CircleFriendBean.CircleListBean.CommentListBean addItem) {

    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {

    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        //edittextbody.setVisibility(visibility);

        measureCircleItemHighAndCommentItemOffset(commentConfig);

//        if(View.VISIBLE==visibility){
//            editText.requestFocus();
//            //弹出键盘
//            CommonUtils.showSoftInput( editText.getContext(),  editText);
//
//        }else if(View.GONE==visibility){
//            //隐藏键盘
//            CommonUtils.hideSoftInput( editText.getContext(),  editText);
//        }
    }

    @Override
    public void update2loadData(int loadType, List<CircleFriendBean.CircleListBean> datas) {

    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;

        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition);

        if (selectCircleItem != null) {
            selectCircleItemH = selectCircleItem.getHeight();
        }

        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if (commentLv != null) {
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if (selectCommentItem != null) {
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if (parentView != null) {
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }


    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().getAllCircleInfo(start, count, new OnAPIListener<CircleFriendBean>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        circleFriendAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(fl_pr, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
            }

            @Override
            public void onSuccess(CircleFriendBean circleFriendBean) {
                LogUtils.loge("圈子反馈" + circleFriendBean.toString());
                if (circleFriendBean == null || circleFriendBean.getCircle_list() == null || circleFriendBean.getCircle_list().size() == 0) {
                    if (!isLoadMore) {
                        list.clear();
                        circleFriendAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(fl_pr, R.drawable.error_view_comment, "当前没有相关数据");
                    } else {
                        lrv.setNoMore(true);
                    }

                    return;
                }
                if (isLoadMore) {
                    loadList.clear();
                    loadList = circleFriendBean.getCircle_list();
                    loadMoreData();
                } else {
                    list.clear();
                    list = circleFriendBean.getCircle_list();
                    showData();
                }
            }
        });

    }

    public void showData() {
        if (list.size() == 0) {
            showErrorView(fl_pr, R.drawable.error_view_comment, "暂无相关数据");
            return;
        } else {
            closeErrorView();
        }
        circleFriendAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        circleFriendAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            circleFriendAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    @Override
    protected void onDestroy() {
        if(presenter !=null){
            presenter.recycle();
        }
        super.onDestroy();
    }
}
