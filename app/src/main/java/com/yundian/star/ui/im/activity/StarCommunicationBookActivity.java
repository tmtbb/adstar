package com.yundian.star.ui.im.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.drop.DropFake;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.StarMailListBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.im.adapter.BookStarComAdapter;
import com.yundian.star.ui.wangyi.session.activity.P2PMessageActivity;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/10.
 */

public class StarCommunicationBookActivity extends BaseActivity {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tab_new_msg_label)
    DropFake tab_new_msg_label;
    @Bind(R.id.parent_view)
    FrameLayout parentView;


    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<StarMailListBeen.DepositsinfoBean> list = new ArrayList<>();
    private ArrayList<StarMailListBeen.DepositsinfoBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private BookStarComAdapter starCommBookAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_com_book;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        checkunReadMsg();
        tv_title.setText(R.string.famous_address_book);
        initAdapter();
        getData(false, 1, REQUEST_COUNT);
        initListener();
    }

    private void initListener() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                SessionCustomization customization = NimUIKit.getCommonP2PSessionCustomization();
                P2PMessageActivity.start(StarCommunicationBookActivity.this, list.get(position).getFaccid(), list.get(position).getStarcode(), customization, null);
                //SessionHelper.startP2PSession(StarCommunicationBookActivity.this,list.get(position).getFaccid());
            }
        });
    }

    private void initAdapter() {
        starCommBookAdapter = new BookStarComAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(starCommBookAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter + 1, REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false, 1, REQUEST_COUNT);
            }
        });
    }

    private void getData(final boolean isLoadMore, int start, int end) {

            /*for (int i =0 ;i<5;i++){
                BookingStarListBean.ListBean bean = new BookingStarListBean.ListBean();
                bean.setStarname("明星"+i);
                list.add(bean);
            }
            showData();*/
        NetworkAPIFactoryImpl.getInformationAPI().getStarmaillist(SharePrefUtil.getInstance().getUserId(), SharePrefUtil.getInstance().getToken(), "123", start, REQUEST_COUNT, new OnAPIListener<StarMailListBeen>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        starCommBookAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_contact, getResources().getString(R.string.empty_view_contacts));
                    }
                }
            }

            @Override
            public void onSuccess(StarMailListBeen starMailListBeen) {
                LogUtils.loge(starMailListBeen.toString());
                if (starMailListBeen.getDepositsinfo() == null) {
                    lrv.setNoMore(true);
                    return;
                }
                if (isLoadMore) {
                    closeErrorView();
                    loadList.clear();
                    loadList = starMailListBeen.getDepositsinfo();
                    loadMoreData();
                } else {
                    list.clear();
                    list = starMailListBeen.getDepositsinfo();
                    showData();
                }
            }
        });

    }

    public void showData() {
        if (list.size() == 0) {
            showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
            return;
        } else {
            closeErrorView();
        }
        starCommBookAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        starCommBookAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            starCommBookAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    private void checkunReadMsg() {
        int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        if (unreadNum > 0) {
            tab_new_msg_label.setVisibility(View.VISIBLE);
            if (unreadNum > 99) {
                tab_new_msg_label.setText("99+");
            } else {
                tab_new_msg_label.setText(String.valueOf(unreadNum));
            }
        } else {
            tab_new_msg_label.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerObservers(true);
    }

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
    }

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            checkunReadMsg();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
    }

    @Override
    public void clickImg() {
        super.clickImg();
        ToastUtils.showShort("点击了错误图片!");
    }
}
