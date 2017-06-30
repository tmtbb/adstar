package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.BuyShellReutrnBeen;
import com.yundian.star.been.FansEntrustReturnBean;
import com.yundian.star.been.HaveStarTimeBeen;
import com.yundian.star.been.StartShellTimeBeen;
import com.yundian.star.been.TradingStatusBeen;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.AutionTopAdapter;
import com.yundian.star.ui.view.MySeekBar;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.TimeUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;


/**
 * Created by Administrator on 2017/5/22.
 * 粉丝拍卖
 */

public class AuctionMarketFragment extends BaseFragment {
    private String code;
    private String pic_url;
    private int userId;
    private String token;
    private CountDownTimer timer;
    private int secondTime = 0;
    private MyHandler1 myHandler;
    private TextView tv_residue_time;
    private TextView tv_have_name;
    private TextView tv_have_time;
    private TextView tv_total_second;
    private TextView tv_shell_out;
    private TextView tv_buy_in;
    private ImageView iv_src;
    private MySeekBar seekBar;
    private MySeekBar press;
    private FrameLayout fl_auction_content;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private LRecyclerView lrv;
    private static final int REQUEST_COUNT = 10;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<FansEntrustReturnBean.PositionsListBean> list = new ArrayList<>();
    private List<FansEntrustReturnBean.PositionsListBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 1;
    private AutionTopAdapter autionTopAdapter;
    private int hotType = 1;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_auction_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initFindById();
        initName();
        initAdapter();
//        initData();
        initListener();
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(code);
        if (starInfos.size() != 0) {
            StarInfo starInfo = starInfos.get(0);
            ImageLoaderUtils.displayWithDefaultImg(getActivity(), iv_src, starInfo.getPic1(), R.drawable.infos_news_defolat);
        }
    }

    private void initFindById() {
        tv_residue_time = (TextView) rootView.findViewById(R.id.tv_residue_time);
        tv_have_name = (TextView) rootView.findViewById(R.id.tv_have_name);
        tv_have_time = (TextView) rootView.findViewById(R.id.tv_have_time);
        tv_total_second = (TextView) rootView.findViewById(R.id.tv_total_second);
        tv_shell_out = (TextView) rootView.findViewById(R.id.tv_shell_out);
        tv_buy_in = (TextView) rootView.findViewById(R.id.tv_buy_in);
        iv_src = (ImageView) rootView.findViewById(R.id.iv_src);
        seekBar = (MySeekBar) rootView.findViewById(R.id.seekBar);
        press = (MySeekBar) rootView.findViewById(R.id.press);
        fl_auction_content = (FrameLayout) rootView.findViewById(R.id.fl_auction_content);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);
        radioButton1 = (RadioButton) rootView.findViewById(R.id.rb_1);
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
    }

    private void initName() {
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(code);
        if (starInfos.size() != 0) {
            StarInfo starInfo = starInfos.get(0);
            tv_have_name.setText(String.format(getActivity().getString(R.string.auction_have_time), starInfo.getName(), starInfo.getCode()));
        }
        getHaveCodeTime();
        getStartHaveTime();
    }

    private void initData() {
        NetworkAPIFactoryImpl.getInformationAPI().getTradingStatus(userId, token, code, new OnAPIListener<TradingStatusBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(TradingStatusBeen tradingStatusBeen) {
                if (tradingStatusBeen != null) {
                    if (tradingStatusBeen.isStatus()) {
                        startSunTime = true;
                        secondTime = tradingStatusBeen.getRemainingTime();
                        if (myHandler != null) {
                            myHandler.sendEmptyMessage(myHandler.GRT_DATA);
                        }
                        //startTime(tradingStatusBeen.getRemainingTime());
                    } else {
                        tv_residue_time.setText("未开始");
                    }
                }
                LogUtils.loge(tradingStatusBeen.toString());
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getString(AppConstant.STAR_CODE);
        }
        userId = SharePrefUtil.getInstance().getUserId();
        token = SharePrefUtil.getInstance().getToken();
        if (myHandler == null) {
            myHandler = new MyHandler1(this);
        }
        LogUtils.loge("走一次了");


    }


    private void initListener() {
        onViewClicked(radioButton1);
    }

    @OnClick({R.id.rb_1, R.id.rb_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_1:
                SwitchTo(0);
                break;
            case R.id.rb_2:  //选择充值方式
                SwitchTo(1);
                break;
        }
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                hotType = 1;
                getLrvData(false, 1);
                //transaction.hide(aution_shell);
                //transaction.show(aution_buy);
                //transaction.commit();
                break;
            case 1:
                hotType = -1;
                getLrvData(false, 1);
                //transaction.hide(aution_buy);
                //transaction.show(aution_shell);
                //transaction.commit();
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && startSunTime == false) {
            startSunTime = true;
            LogUtils.loge("setUserVisibleHint>>vi");
            startRefresh();
        } else {
            stopRefresh();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onResume() {
        setUserVisibleHint(getUserVisibleHint());
        LogUtils.loge("刷新onResume");
        super.onResume();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onPause() {
        LogUtils.loge("停止onPause");
        //stopRefresh();
        setUserVisibleHint(getUserVisibleHint());
        super.onPause();
    }

    private void stopRefresh() {
        if (myHandler != null) {
            startSunTime = false;
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
            LogUtils.loge("停止刷新stopRefresh");
        }
    }

    private void startRefresh() {
        LogUtils.loge("刷新startRefresh" + myHandler);
        if (myHandler != null) {
            LogUtils.loge("刷新startRefresh");
            myHandler.removeCallbacksAndMessages(null);
            initData();
        } else {
            LogUtils.loge("刷新startRefresh,handler=null");
            /*myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case GRT_DATA:
                            refreshTime();
                            break;
                    }
                }
            };*/
            myHandler = new MyHandler1(this);
            myHandler.removeCallbacksAndMessages(null);
            initData();
        }
    }

    private int cycleTime = 0;

    private void refreshTime() {
        if (tv_residue_time != null && secondTime >= 0 && myHandler != null && startSunTime) {
            tv_residue_time.setText(TimeUtil.getHMS(secondTime * 1000));
            secondTime--;
            if (cycleTime == 3) {
                cycleTime = 0;
            }
            if (cycleTime == 0) {
                getBuyShellData();
                getLrvData(false, 1);
            }
            cycleTime++;
            if (myHandler != null) {
                myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1 * 1000);
            }
        } else if (tv_residue_time != null && secondTime < 0) {
            tv_residue_time.setText("未开始");
        }
    }

    private void getBuyShellData() {
        NetworkAPIFactoryImpl.getInformationAPI().getBuyShellData(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(), code, new OnAPIListener<BuyShellReutrnBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        LogUtils.loge("买卖比例错误" + ex.toString());
                    }

                    @Override
                    public void onSuccess(BuyShellReutrnBeen buyShellReutrnBeen) {
                        tv_buy_in.setText("买入：0人");
                        tv_shell_out.setText("卖出：0人");
                        LogUtils.loge("买卖比例" + buyShellReutrnBeen.toString() + "总时间：" + totalTime);
                        if ((buyShellReutrnBeen.getBuyCount() + buyShellReutrnBeen.getSellCount()) != 0) {
                            int i = 100 * buyShellReutrnBeen.getBuyCount() / (buyShellReutrnBeen.getBuyCount() + buyShellReutrnBeen.getSellCount());
                            LogUtils.loge("比例" + i + "..." + buyShellReutrnBeen.getBuyCount() + "..." + buyShellReutrnBeen.getSellCount() + "...." +
                                    "..." + (buyShellReutrnBeen.getBuyCount() + buyShellReutrnBeen.getSellCount()));
                            press.setProgress(i);
                            tv_buy_in.setText(String.format(getActivity().getString(R.string.buy_in), buyShellReutrnBeen.getBuyCount()));
                            tv_shell_out.setText(String.format(getActivity().getString(R.string.shell_out), buyShellReutrnBeen.getSellCount()));
                        }
                        if (buyShellReutrnBeen.getSellTime() != 0 && totalTime != 0) {
                            int pressData = 100;
                            if (buyShellReutrnBeen.getSellTime() > totalTime) {
                                LogUtils.loge("1买卖SellTime()" + buyShellReutrnBeen.getSellTime() + "总时间：" + totalTime);
                                pressData = pressData * totalTime / buyShellReutrnBeen.getSellTime();
                            } else {
                                pressData = pressData * buyShellReutrnBeen.getSellTime() / totalTime;
                                LogUtils.loge("2买卖SellTime()" + buyShellReutrnBeen.getSellTime() + "总时间：" + totalTime);
                            }

                            seekBar.setProgress(pressData);
                        }
                    }
                });
    }

    private boolean startSunTime = false;

    //final private static int GRT_DATA = 112;
    /*private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GRT_DATA:
                    refreshTime();
                    break;
            }
        }
    };*/
    private static class MyHandler1 extends Handler {
        final private static int GRT_DATA = 112;
        private final WeakReference<AuctionMarketFragment> mFragment;

        public MyHandler1(AuctionMarketFragment mfragment) {
            mFragment = new WeakReference<AuctionMarketFragment>(mfragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AuctionMarketFragment fragment = mFragment.get();
            if (fragment != null) {
                switch (msg.what) {
                    case GRT_DATA:
                        fragment.refreshTime();
                        break;
                }
            }
        }
    }

    //持有改明星的时间
    private void getHaveCodeTime() {
        NetworkAPIFactoryImpl.getInformationAPI().getHaveStarTime(SharePrefUtil.getInstance().getUserId(),
                code, new OnAPIListener<HaveStarTimeBeen>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(HaveStarTimeBeen haveStarTimeBeen) {
                        LogUtils.loge("持有时间" + haveStarTimeBeen.toString());
                        tv_have_time.setText(String.format(getActivity().getString(R.string.num_time), haveStarTimeBeen.getStar_time()));
                    }
                });
    }

    private int totalTime = 0;

    //总流通时间
    private void getStartHaveTime() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarShellTime(code, new OnAPIListener<StartShellTimeBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StartShellTimeBeen startShellTimeBeen) {
                totalTime = startShellTimeBeen.getStar_time();
                LogUtils.loge("明星流通时间" + startShellTimeBeen.toString());
                tv_total_second.setText(String.valueOf(startShellTimeBeen.getStar_time()) + "秒");
            }
        });
    }


    private void getLrvData(final boolean isLoadMore, int start) {
        NetworkAPIFactoryImpl.getInformationAPI().fansRntrust(code, hotType, start, REQUEST_COUNT, new OnAPIListener<FansEntrustReturnBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("粉丝热度失败------------------------------------------");
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        autionTopAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        //showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
            }

            @Override
            public void onSuccess(FansEntrustReturnBean bean) {
                LogUtils.loge("粉丝热度成功-------" + bean.toString());
                if (bean == null || bean.getPositionsList() == null || bean.getPositionsList().size() == 0) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        autionTopAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        //showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                    return;
                }
                if (isLoadMore) {
                    closeErrorView();
                    loadList.clear();
                    loadList = bean.getPositionsList();
                    loadMoreData();
                } else {
                    list.clear();
                    list = bean.getPositionsList();
                    showData();
                }
            }
        });

    }

    private void initAdapter() {
        autionTopAdapter = new AutionTopAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(autionTopAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getLrvData(true, mCurrentCounter + 1);
            }
        });
    }

    public void showData() {
        if (list != null && list.size() == 0) {
            //showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
            return;
        } else {
            closeErrorView();
        }
        autionTopAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();
        autionTopAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            autionTopAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }


}
