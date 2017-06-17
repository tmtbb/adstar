package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.BuyShellReutrnBeen;
import com.yundian.star.been.HaveStarTimeBeen;
import com.yundian.star.been.StarBuyActReferralInfo;
import com.yundian.star.been.StartShellTimeBeen;
import com.yundian.star.been.TradingStatusBeen;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.view.MySeekBar;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/5/22.
 * 粉丝拍卖
 */

public class AuctionMarketFragment extends BaseFragment {

    @Bind(R.id.iv_src)
    ImageView iv_src;
    @Bind(R.id.tv_residue_time)
    TextView tv_residue_time;
    @Bind(R.id.tv_have_name)
    TextView tv_have_name;
    @Bind(R.id.tv_have_time)
    TextView tv_have_time;
    @Bind(R.id.tv_total_second)
    TextView tv_total_second;
    @Bind(R.id.tv_shell_out)
    TextView tv_shell_out;
    @Bind(R.id.tv_buy_in)
    TextView tv_buy_in;
    @Bind(R.id.seekBar)
    MySeekBar seekBar;
    @Bind(R.id.press)
    MySeekBar press;
    @Bind(R.id.fl_auction_content)
    FrameLayout fl_auction_content;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.rb_1)
    RadioButton radioButton1;
    private AutionTopFragment aution_buy;
    private AutionTopFragment aution_shell;
    private String code;
    private String pic_url;
    private boolean flag = true;
    private int userId;
    private String token;
    private CountDownTimer timer;
    private int secondTime = 0;
    private MyHandler1 myHandler;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_auction_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initName();
//        initData();
        initListener();
    }

    private void initName() {
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(code);
        if (starInfos.size()!=0){
            StarInfo starInfo = starInfos.get(0);
            tv_have_name.setText(String.format(getString(R.string.auction_have_time),starInfo.getName(),starInfo.getCode()));
        }
        getHaveCodeTime();
        getStartHaveTime();
    }

    private void initData() {
        NetworkAPIFactoryImpl.getInformationAPI().getTradingStatus(userId, token, "1001", new OnAPIListener<TradingStatusBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(TradingStatusBeen tradingStatusBeen) {
                if (tradingStatusBeen != null) {
                    if (tradingStatusBeen.isStatus()) {
                        startSunTime = true;
                        secondTime = tradingStatusBeen.getRemainingTime();
                        myHandler.sendEmptyMessage(myHandler.GRT_DATA);
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
        if (flag) {
            EventBus.getDefault().register(this); // EventBus注册广播()
            flag = false;//更改标记,使其不会再进行多次注册
        }
        userId = SharePrefUtil.getInstance().getUserId();
        token = SharePrefUtil.getInstance().getToken();
        if (myHandler == null) {
            myHandler = new MyHandler1(this);
        }
        LogUtils.loge("走一次了");

        initFragment(savedInstanceState);

    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            Bundle bundle1 = new Bundle();
            bundle1.putString(AppConstant.STAR_CODE, code);
            bundle1.putInt(AppConstant.AUCTION_TYPE, 1);
            aution_buy = new AutionTopFragment();
            aution_buy.setArguments(bundle1);
            Bundle bundle2 = new Bundle();
            bundle2.putString(AppConstant.STAR_CODE, code);
            bundle2.putInt(AppConstant.AUCTION_TYPE, 2);
            aution_shell = new AutionTopFragment();
            aution_shell.setArguments(bundle2);
            transaction.add(R.id.fl_auction_content, aution_buy, "AutionBuy");
            transaction.add(R.id.fl_auction_content, aution_shell, "AutionShell");
        } else {
            aution_buy = (AutionTopFragment) getChildFragmentManager().findFragmentByTag("AutionBuy");
            aution_shell = (AutionTopFragment) getChildFragmentManager().findFragmentByTag("AutionShell");
        }
        transaction.commit();

    }

    private void initListener() {
        radioGroup.check(radioButton1.getId());
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
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.hide(aution_shell);
                transaction.show(aution_buy);
                transaction.commit();
                break;
            case 1:
                transaction.hide(aution_buy);
                transaction.show(aution_shell);
                transaction.commit();
                break;
            default:
                break;
        }
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveEventBus(StarBuyActReferralInfo eventBusMessage) {
        if (eventBusMessage != null && !TextUtils.isEmpty(eventBusMessage.getPic_url())) {
            if (TextUtils.isEmpty(pic_url) && iv_src != null) {
                pic_url = eventBusMessage.getPic_url();
                ImageLoaderUtils.display(getActivity(), iv_src, pic_url);
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
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
    private int cycleTime = 0 ;
    private void refreshTime() {
        if (tv_residue_time != null && secondTime >= 0 && myHandler != null && startSunTime) {
            tv_residue_time.setText(TimeUtil.getHourMinuteSecond(secondTime * 1000));
            secondTime--;
            if (cycleTime==3){
                cycleTime=0;
            }
            if (cycleTime==0){
                getBuyShellData();
            }
            cycleTime++;

            myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1 * 1000);
        } else if (tv_residue_time != null && secondTime < 0) {
            tv_residue_time.setText("未开始");
        }
    }

    private void getBuyShellData() {
        NetworkAPIFactoryImpl.getInformationAPI().getBuyShellData(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(),code, new OnAPIListener<BuyShellReutrnBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        LogUtils.loge("买卖比例错误"+ex.toString());
                    }

                    @Override
                    public void onSuccess(BuyShellReutrnBeen buyShellReutrnBeen) {
                        LogUtils.loge("买卖比例"+buyShellReutrnBeen.toString());
                        if ((buyShellReutrnBeen.getBuyCount() + buyShellReutrnBeen.getSellCount())!=0){
                            int i = 100*buyShellReutrnBeen.getBuyCount()/(buyShellReutrnBeen.getBuyCount() + buyShellReutrnBeen.getSellCount());
                            LogUtils.loge("比例"+i+"..."+buyShellReutrnBeen.getBuyCount()+"..."+buyShellReutrnBeen.getSellCount()+"...." +
                                    "..."+(buyShellReutrnBeen.getBuyCount() + buyShellReutrnBeen.getSellCount()));
                            press.setProgress(i);
                          tv_buy_in.setText(String.format(getActivity().getString(R.string.buy_in),buyShellReutrnBeen.getBuyCount()*10));
                          tv_shell_out.setText(String.format(getActivity().getString(R.string.shell_out),buyShellReutrnBeen.getSellCount()*10));
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
    private void getHaveCodeTime() {
        NetworkAPIFactoryImpl.getInformationAPI().getHaveStarTime(SharePrefUtil.getInstance().getUserId(),
                code, new OnAPIListener<HaveStarTimeBeen>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(HaveStarTimeBeen haveStarTimeBeen) {
                        LogUtils.loge("持有时间"+haveStarTimeBeen.toString());
                        tv_have_time.setText(String.format(getString(R.string.num_time),haveStarTimeBeen.getStar_time()));
                    }
                });
    }
    //总流通时间
    private void getStartHaveTime() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarShellTime("143", new OnAPIListener<StartShellTimeBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StartShellTimeBeen startShellTimeBeen) {
                LogUtils.loge("明星流通时间"+startShellTimeBeen.toString());
                tv_total_second.setText(String.valueOf(startShellTimeBeen.getStar_time())+"秒");
            }
        });
    }


}
