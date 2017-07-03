package com.yundian.star.ui.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.CommentMarketBeen;
import com.yundian.star.been.EventBusMessage;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.CommentMarketAdapter;
import com.yundian.star.utils.KeyBordUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.widget.NormalTitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/5.
 */

public class AddUserCommentActivity extends BaseActivity {

    @Bind(R.id.et_add_comment)
    EditText et_add_comment ;
    @Bind(R.id.lrv)
    LRecyclerView lrv ;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 0;
    private String code;
    private ArrayList<CommentMarketBeen.CommentsinfoBean> list = new ArrayList<>();
    private ArrayList<CommentMarketBeen.CommentsinfoBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private CommentMarketAdapter commentMarketAdapter;
    private TextView tv_num;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_comment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        code = getIntent().getStringExtra(AppConstant.STAR_CODE);
        nt_title.setTitleText("评论");
        nt_title.setBackVisibility(true);
        initAdapter();
        initListener();
        getData(false,0,REQUEST_COUNT);
    }

    @OnClick(R.id.tv_send)
    public void sendButton(){
        String contont = et_add_comment.getText().toString().trim();
        NetworkAPIFactoryImpl.getInformationAPI().getAddComment(code, String.valueOf(SharePrefUtil.getInstance().getUserId()), TextUtils.isEmpty(SharePrefUtil.getInstance().getUserNickName()) ? SharePrefUtil.getInstance().getPhoneNum() : SharePrefUtil.getInstance().getUserNickName(),
                contont, TextUtils.isEmpty(SharePrefUtil.getInstance().getUserPhotoUrl())?"http://ppt.downhot.com/d/file/p/2014/03/13/0673249a8d8942271ac07b975049b531.jpg":SharePrefUtil.getInstance().getUserPhotoUrl(), new OnAPIListener<Object>() {
                    @Override
                    public void onError(Throwable ex) {
                        LogUtils.loge(ex.toString());
                        mCurrentCounter = 0;
                        commentMarketAdapter.clear();
                        getData(false,0,REQUEST_COUNT);
                        et_add_comment.setText("");
                        KeyBordUtil.hideSoftKeyboard(et_add_comment);
                        et_add_comment.clearFocus();
                        LogUtils.loge("添加成功");
                    }

                    @Override
                    public void onSuccess(Object o) {
                        LogUtils.loge(o.toString());
                    }
                }
        );


    }

    private void initAdapter() {
        commentMarketAdapter = new CommentMarketAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(commentMarketAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setNoMore(false);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        initLrvHeadView();
    }

    private void initLrvHeadView() {
        //add a HeaderView
        View header = LayoutInflater.from(this).inflate(R.layout.head_comment_list, (ViewGroup)findViewById(android.R.id.content), false);
        tv_num = (TextView) header.findViewById(R.id.tv_num);
        lRecyclerViewAdapter.addHeaderView(header);
    }

    private void initListener() {
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentMarketAdapter.clear();
                mCurrentCounter = 0;
                getData(false,0,REQUEST_COUNT);
            }
        });
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter,REQUEST_COUNT);
            }
        });

    }

    private void getData(final boolean isLoadMore,int start ,int count) {
        NetworkAPIFactoryImpl.getInformationAPI().inquiry(code, start, count, new OnAPIListener<CommentMarketBeen>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv!=null){
                    lrv.setNoMore(true);
                }
            }

            @Override
            public void onSuccess(CommentMarketBeen been) {
                LogUtils.loge("评论"+been.toString());
                if (been.getCommentsinfo()==null){
                    lrv.setNoMore(true);
                    return;
                }
                if (been.getTotal_count() > 999){
                    tv_num.setText("999+");
                }else{
                    tv_num.setText(String.valueOf(been.getTotal_count()));
                }
                if (isLoadMore){
                    loadList.clear();
                    loadList = been.getCommentsinfo();
                    loadMoreData();
                }else {
                    list.clear();
                    list = been.getCommentsinfo();
                    showData();
                }

            }
        });
    }

    public void showData() {
        commentMarketAdapter.clear();
        mCurrentCounter =list.size();
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
    public void finish() {
        EventBus.getDefault().postSticky(new EventBusMessage(111));
        super.finish();
    }
}
