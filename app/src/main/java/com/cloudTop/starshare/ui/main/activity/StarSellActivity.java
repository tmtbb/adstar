package com.cloudTop.starshare.ui.main.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.OrderReturnBeen;
import com.cloudTop.starshare.been.RefreshStarTimeBean;
import com.cloudTop.starshare.been.ResultBeen;
import com.cloudTop.starshare.been.ShoppingStarBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.CheckLoginUtil;
import com.cloudTop.starshare.utils.JudgeIsSetPayPwd;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.TimeUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.widget.PasswordView;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.ImageLoaderUtils;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/8.
 * 发售页面
 */

public class StarSellActivity extends BaseActivity {
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_star_job)
    TextView tv_star_job;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_num)
    TextView tv_num;
    @Bind(R.id.tv_time_start)
    TextView tv_time_start;
    @Bind(R.id.tv_time_count)
    TextView tv_time_count;
    @Bind(R.id.tv_preice)
    TextView tv_preice;
    @Bind(R.id.tv_total)
    TextView tv_total;
    @Bind(R.id.tv_sure_buy)
    TextView tv_sure_buy;
    @Bind(R.id.ed_num)
    EditText ed_num;
    @Bind(R.id.nl_title)
    NormalTitleBar nl_title;
    @Bind(R.id.iv_star_bg)
    ImageView iv_star_bg;
    @Bind(R.id.imageView_icon)
    ImageView imageView_icon;
    @Bind(R.id.passwordView)
    PasswordView passwordView;
    private String starCode;
    private String starwork;
    private MyHandler myHandler;
    private int type;
    private boolean isPresell;
    private long userId;
    private String token;
    private Integer num;
    private double ask_buy_prices;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_shell;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        userId = SharePrefUtil.getInstance().getUserId();
        token = SharePrefUtil.getInstance().getToken();
        starCode = getIntent().getStringExtra(AppConstant.STAR_CODE);
        starwork = getIntent().getStringExtra(AppConstant.AUCTION_TYPE);
        type = getIntent().getIntExtra(AppConstant.PUBLISH_TYPE, -1);
        isPresell = getIntent().getBooleanExtra(AppConstant.IS_PRESELL, false);
        if (isPresell) {
            nl_title.setTitleText(getString(R.string.pre_shells));
        } else {
            nl_title.setTitleText(getString(R.string.shells));
        }
        nl_title.setBackVisibility(true);
        nl_title.setRightImagVisibility(true);
        if (myHandler == null) {
            myHandler = new MyHandler(this);
        }
        initData();
        initListener();

    }

    private void byBuyStar() {
        NetworkAPIFactoryImpl.getInformationAPI().getByBuy(userId, token, starCode, num, ask_buy_prices, new OnAPIListener<ResultBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(ResultBeen resultBeen) {
                if (resultBeen.getResult() == 1 || resultBeen.getResult() == 2) {
                    ToastUtils.showShort("购买成功");
                } else {
                    ToastUtils.showShort("购买失败");
                }
            }
        });
    }

    private void initListener() {
        tv_sure_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPresell) {
                    ToastUtils.showShort("当前是预售阶段,无法购买,请等待");
                    return;
                }
                float total_money = (float) (num * ask_buy_prices);
                if (total_money <= 0) {
                    ToastUtils.showShort("总价不能零");
                    return;
                }
                CheckLoginUtil.checkLogin(StarSellActivity.this);
                if (JudgeIsSetPayPwd.isSetPwd(StarSellActivity.this)) {
                    passwordView.setVisibility(View.VISIBLE);
                }
                //byBuyStar();
                LogUtils.loge("ask_buy_prices:" + ask_buy_prices + "num:" + num + "total_money:" + total_money);
            }
        });
        passwordView.setOnFinishInput(new PasswordView.CheckPasCallBake() {
            @Override
            public void checkSuccess(OrderReturnBeen.OrdersListBean ordersListBean, String pwd) {

            }

            @Override
            public void checkError() {

            }

            @Override
            public void checkSuccessPwd(String pwd) {
                passwordView.setVisibility(View.GONE);
                //密码正确
                byBuyStar();
            }
        });
        imageView_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarInfoActivity.goToStarInfoActivity(StarSellActivity.this, starCode);
            }
        });
    }

    private void initData() {
        NetworkAPIFactoryImpl.getInformationAPI().getShoppingStar(starCode, new OnAPIListener<ShoppingStarBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(ShoppingStarBean shoppingStarBean) {
                showViewData(shoppingStarBean);
                getRefreshTime();
            }
        });

    }

    private void showViewData(final ShoppingStarBean shoppingStarBean) {
        ImageLoaderUtils.displayWithDefaultImg(this, iv_star_bg, shoppingStarBean.getBack_pic_url_tail(), R.drawable.rec_bg);
        ImageLoaderUtils.displaySmallPhoto(this, imageView_icon, shoppingStarBean.getHead_url_tail());
        tv_name.setText(shoppingStarBean.getStar_name());
        tv_preice.setText(String.format(getString(R.string.times_p), shoppingStarBean.getPublish_price()));
        tv_star_job.setText(starwork);
//        if (isPresell){
//            tv_time.setText(String.format(getString(R.string.presell_time), TimeUtil.formatData(TimeUtil.dateFormatYMD, shoppingStarBean.getPublish_begin_time()),
//                    TimeUtil.formatData(TimeUtil.dateFormatYMD, shoppingStarBean.getPublish_end_time())));
//            tv_num.setText(String.format(getString(R.string.presell_tolnum), shoppingStarBean.getPublish_time()));
//        }else {
        beginTime = shoppingStarBean.getPublish_begin_time();
        endTime = shoppingStarBean.getPublish_end_time();
        tv_time.setText(String.format(getString(R.string.shell_time), TimeUtil.formatData(TimeUtil.dateFormatYMD, beginTime),
                TimeUtil.formatData(TimeUtil.dateFormatYMD, endTime)));
        tv_num.setText(String.format(getString(R.string.shell_tolnum), shoppingStarBean.getPublish_time()));
//        }
        ed_num.setHint(String.format(getString(R.string.shell_tolnum_info), shoppingStarBean.getPublish_time()));
        tv_total.setText(String.format(getString(R.string.total_money_), 0f));

        ed_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    num = 0;
                } else {
                    num = Integer.valueOf(s.toString().trim());
                }
                BigDecimal bg = new BigDecimal(shoppingStarBean.getPublish_price());
                ask_buy_prices = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                float total_price = (float) (num * ask_buy_prices);
                tv_total.setText(String.format(getString(R.string.total_money_), total_price));
                LogUtils.loge(shoppingStarBean.getPublish_price() + "价格保留2位数前：" + ask_buy_prices + "后:" + String.valueOf(total_price));
                if (total_price <= 0) {
                    tv_sure_buy.setEnabled(false);
                } else {
                    tv_sure_buy.setEnabled(true);
                }
            }
        });
    }

    private void getRefreshTime() {
        secondTime = TimeUtil.getCurrentTimeInLong()/1000;
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            myHandler.sendEmptyMessage(myHandler.GRT_DATA);
        }
    }

    private static class MyHandler extends Handler {
        final private static int GRT_DATA = 111;
        private final WeakReference<StarSellActivity> mActivity;

        public MyHandler(StarSellActivity sellActivity) {
            mActivity = new WeakReference<StarSellActivity>(sellActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            StarSellActivity activity = mActivity.get();
            if (activity != null && activity.isFinishing() == false) {
                switch (msg.what) {
                    case GRT_DATA:
                        activity.refreshTime();
                        break;
                }
            }
        }
    }

    private long secondTime = 0;
    private long beginTime = 0;
    private long endTime = 0;

    private void refreshTime() {

//        if (tv_time_count != null && secondTime > 0 && myHandler != null) {
//            isPresell = true;
//            tv_time_count.setText(TimeUtil.calculatTime(secondTime));
//            secondTime--;
//            myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1 * 1000);
//        } else if (tv_time_count != null && secondTime <= 0) {
//            isPresell = false;
//            tv_time_start.setVisibility(View.GONE);
//            tv_time_count.setText("未开始");
//        }
//        if (isPresell){
//            nl_title.setTitleText(getString(R.string.pre_shells));
//        }else {
//            nl_title.setTitleText(getString(R.string.shells));
//        }

        secondTime++;
        tv_time_start.setVisibility(View.VISIBLE);
        isPresell = true;
        String title = getString(R.string.pre_shells);
        String timeDescsc = getString(R.string.begin_times);
        String timeRemainder = TimeUtil.calculatTime(beginTime - secondTime);
        if(secondTime < beginTime){
            myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1 * 1000);
        }
        if (secondTime >= beginTime) {
            timeDescsc = getString(R.string.end_times);
            timeRemainder = TimeUtil.calculatTime(endTime - secondTime);
            isPresell = false;
            title = getString(R.string.shells);
            myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1 * 1000);
        }
        if (secondTime >= endTime) {
            tv_time_start.setVisibility(View.GONE);
            timeRemainder = "已结束";
        }
        tv_time_start.setText(timeDescsc);
        tv_time_count.setText(timeRemainder);
        nl_title.setTitleText(title);
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (passwordView.getVisibility() == View.VISIBLE) {
                    passwordView.setVisibility(View.GONE);
                    return true;
                } else {
                    return super.onKeyDown(keyCode, event);
                }
        }
        return super.onKeyDown(keyCode, event);
    }

}
