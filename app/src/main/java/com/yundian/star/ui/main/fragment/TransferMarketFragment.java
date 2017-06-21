package com.yundian.star.ui.main.fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.AskToBuyReturnBeen;
import com.yundian.star.been.EventBusMessage;
import com.yundian.star.been.HaveStarTimeBeen;
import com.yundian.star.been.LoginReturnInfo;
import com.yundian.star.been.SrealSendBeen;
import com.yundian.star.been.SrealSendReturnBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NumberBoubleButton;
import com.yundian.star.widget.NumberButton;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/24.
 * 转让
 */

public class TransferMarketFragment extends BaseFragment {
    @Bind(R.id.but_buy_price)
    NumberBoubleButton but_buy_price;
    @Bind(R.id.but_buy_num)
    NumberButton but_buy_num;
    @Bind(R.id.tv_sure_trans)
    TextView tv_sure_trans;
    @Bind(R.id.img_head)
    ImageView img_head;
    @Bind(R.id.tv_name_code)
    TextView tv_name_code;
    @Bind(R.id.tv_current_price)
    TextView tv_current_price;
    @Bind(R.id.tv_up_down_money)
    TextView tv_up_down_money;
    @Bind(R.id.tv_up_down_range)
    TextView tv_up_down_range;
    @Bind(R.id.tv_content_limit)
    TextView tv_content_limit;
    @Bind(R.id.tv_total)
    TextView tv_total;
    @Bind(R.id.tv_have_star_time)
    TextView tv_have_star_time;
    private double buy_price = 0.01;
    private int buy_num = 600;
    private double total_prices = 0;
    private String code;
    private String head_url;
    private String wid;
    private String name;
    private SrealSendBeen sendBeen;
    private List<SrealSendBeen> symbolInfos = new ArrayList<>();
    private boolean isCanBuy = false;
    private MyHandler2 myHandler;
    private boolean isFirst = true;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transfer;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            code = getArguments().getString(AppConstant.STAR_CODE);
            head_url = getArguments().getString(AppConstant.STAR_HEAD_URL);
            wid = getArguments().getString(AppConstant.STAR_WID);
            name = getArguments().getString(AppConstant.STAR_NAME);
            ImageLoaderUtils.display(getContext(), img_head, head_url);
            tv_name_code.setText(String.format(getContext().getResources().getString(R.string.name_code), name, code));
        }
        getHaveCodeTime();
        getData();
        initListener();
        myHandler = new MyHandler2(this);
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
                        tv_have_star_time.setText(String.valueOf(haveStarTimeBeen.getStar_time()));
                    }
                });
    }

    private void getData() {
        symbolInfos.clear();
        sendBeen = new SrealSendBeen();
        sendBeen.setAType(5);
        sendBeen.setSymbol(wid);
        symbolInfos.add(sendBeen);
        LogUtils.loge("事实数据" + symbolInfos.toString());
        NetworkAPIFactoryImpl.getInformationAPI().getSrealtime(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(), symbolInfos, new OnAPIListener<SrealSendReturnBeen>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(SrealSendReturnBeen been) {
                        LogUtils.loge("事实数据2" + been.toString());
                        if (been.getPriceinfo() == null || been.getPriceinfo().size() == 0) {
                            return;
                        }
                        refresh(been);
                    }
                });
    }

    private void refresh(SrealSendReturnBeen been) {
        if (been==null||tv_current_price==null){
            return;
        }
        SrealSendReturnBeen.PriceinfoBean priceinfoBean = been.getPriceinfo().get(0);
        tv_current_price.setText(String.format("%.2f", priceinfoBean.getCurrentPrice()));
        if (priceinfoBean.getChange() >= 0) {
            tv_up_down_money.setTextColor(getContext().getResources().getColor(R.color.color_CB4232));
            tv_up_down_range.setTextColor(getContext().getResources().getColor(R.color.color_CB4232));

        } else {
            tv_up_down_money.setTextColor(getContext().getResources().getColor(R.color.color_18B03F));
            tv_up_down_range.setTextColor(getContext().getResources().getColor(R.color.color_18B03F));
        }
        DecimalFormat format = new DecimalFormat("0.00%");
        String updown = format.format(priceinfoBean.getPchg() / 100);
        tv_up_down_money.setText(String.format("%.2f", priceinfoBean.getChange()));
        tv_up_down_range.setText(updown);
        /*if (priceinfoBean.getSysTime()<=priceinfoBean.getPriceTime()){
            if (priceinfoBean.getPriceTime()-priceinfoBean.getSysTime()<=60){
                isCanBuy =true;
            }else {
                isCanBuy = false;
            }
        }else {
            if (priceinfoBean.getSysTime()-priceinfoBean.getPriceTime()<=60){
                isCanBuy =true;
            }else {
                isCanBuy = false;
            }
        }*/
        double b = (double) (Math.round(priceinfoBean.getCurrentPrice() * 100)) / 100;
        if (isFirst) {
            but_buy_price.setCurrentNumber(b);
            isFirst = false;
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tv_total.setText(String.format("%.2f", total_prices));
        }
    };

    private void initListener() {
        tv_total.postDelayed(runnable, 100);
        but_buy_price.setBuyMin(0.01)
                .setContext(getActivity())
                .setOnWarnListener(new NumberBoubleButton.OnWarnListener() {
                    @Override
                    public void onWarningForInventory(double inventory) {

                    }

                    @Override
                    public void onWarningForBuyMax(double buyMax) {
                        tv_content_limit.setText("求购价格不能高于" + buyMax);
                    }

                    @Override
                    public void onWarningForBuyMin(double min) {
                        tv_content_limit.setText("求购价格不能低于" + min);
                    }
                });

        but_buy_num.setContext(getActivity())
                .setCurrentNumber(600)
                .setBuyMin(1)
                .setOnWarnListener(new NumberButton.OnWarnListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {

                    }

                    @Override
                    public void onWarningForBuyMax(int buyMax) {
                        tv_content_limit.setText("求购数量不能高于" + buyMax);
                    }

                    @Override
                    public void onWarningForBuyMin(int min) {
                        tv_content_limit.setText("求购数量不能低于" + min);
                    }
                });

        tv_sure_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!JudgeIsSetPayPwd.isSetPwd(getActivity())) {
//                    return;
//                }
                judgeIsLogin();
                LogUtils.loge("获取数值" + total_prices);
                BigDecimal bg = new BigDecimal(buy_price);
                double ask_buy_prices = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                LogUtils.loge("获取数值总价" + total_prices + "转换后的数据" + ask_buy_prices * buy_num);
                NetworkAPIFactoryImpl.getInformationAPI().getAskToBuy(SharePrefUtil.getInstance().getUserId(),
                        SharePrefUtil.getInstance().getToken(), 1, code,-1, buy_num, ask_buy_prices,
                        new OnAPIListener<AskToBuyReturnBeen>() {
                            @Override
                            public void onError(Throwable ex) {

                            }

                            @Override
                            public void onSuccess(AskToBuyReturnBeen askToBuyReturnBeen) {
                                LogUtils.loge("挂单"+askToBuyReturnBeen.toString());
                                if (!TextUtils.isEmpty(askToBuyReturnBeen.getSymbol())){
                                    ToastUtils.showShort("挂单成功");
                                    LogUtils.loge("转让成功"+askToBuyReturnBeen.toString());
                                }
                            }
                        });
            }
        });

        but_buy_price.setOnChangeContent(new NumberBoubleButton.OnChangeContentListener() {
            @Override
            public void onChange(double price) {
                buy_price = price;
                total_prices = buy_price * buy_num;
                tv_total.setText(String.format("%.2f", total_prices));
                LogUtils.loge("获取输入价值" + buy_price);
            }
        });
        but_buy_num.setOnChangeContent(new NumberButton.OnChangeContentListener() {
            @Override
            public void onChange(int num) {
                buy_num = num;
                total_prices = buy_price * buy_num;
                tv_total.setText(String.format("%.2f", total_prices));
                LogUtils.loge("获取输入数量" + buy_num);
            }
        });
    }

    private static class MyHandler2 extends Handler {
        final private static int GRT_DATA_2 = 111;
        private final WeakReference<TransferMarketFragment> mFragment;

        public MyHandler2(TransferMarketFragment mfragment) {
            mFragment = new WeakReference<TransferMarketFragment>(mfragment);
        }

        @Override
        public void handleMessage(Message msg) {
            TransferMarketFragment fragment = mFragment.get();
            if (fragment != null) {
                switch (msg.what) {
                    case GRT_DATA_2:
                        fragment.getData();
                        fragment.myHandler.sendEmptyMessageDelayed(GRT_DATA_2, 3 * 1000);
                        break;
                }
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.loge("setUserVisibleHint>>");
        if (isVisibleToUser) {
            startRefresh();
        } else {
            stopRefresh();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        setUserVisibleHint(getUserVisibleHint());
        LogUtils.loge("刷新实时报价2onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.loge("停止刷新实时报价2onPause");
        stopRefresh();
        super.onPause();
    }

    private void stopRefresh() {
        if (myHandler != null) {
            myHandler.removeMessages(myHandler.GRT_DATA_2);
            LogUtils.loge("停止刷新实时报价2stopRefresh");
        }
    }

    private void startRefresh() {
        if (myHandler != null) {
            isFirst = true;
            LogUtils.loge("刷新实时报价2startRefresh");
            myHandler.removeMessages(myHandler.GRT_DATA_2);
            myHandler.sendEmptyMessage(myHandler.GRT_DATA_2);
        }
    }

    private void judgeIsLogin() {
        if (!TextUtils.isEmpty(SharePrefUtil.getInstance().getToken())) {
            LogUtils.loge("已经登录,开始校验token");
            NetworkAPIFactoryImpl.getUserAPI().loginWithToken(new OnAPIListener<LoginReturnInfo>() {
                @Override
                public void onError(Throwable ex) {
                    ex.printStackTrace();
                    LogUtils.loge("-----------登录失败.token已经失效");
                    //logout();
                }

                @Override
                public void onSuccess(LoginReturnInfo loginReturnEntity) {
                    LogUtils.loge("------------------登录成功，保存信息"+loginReturnEntity.toString());
                    //服务器问题,先token登录不保存信息
                    //SharePrefUtil.getInstance().saveLoginUserInfo(loginReturnEntity);
                    if (!TextUtils.isEmpty(loginReturnEntity.getToken())){
                        SharePrefUtil.getInstance().setToken(loginReturnEntity.getToken());
                    }
                    EventBus.getDefault().postSticky(new EventBusMessage(1));  //登录成功消息
                }
            });
        }
    }
}
