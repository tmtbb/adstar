package com.cloudTop.starshare.ui.main.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.OrderReturnBeen;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.ui.view.ShareControlerView;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.widget.indicator.PageIndicator;
import com.netease.nimlib.jsbridge.util.LogUtil;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.been.RequestResultBean;
import com.cloudTop.starshare.been.StatServiceListBean;
import com.cloudTop.starshare.greendao.GreenDaoManager;
import com.cloudTop.starshare.greendao.StarInfo;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.GridViewPageAdapter;
import com.cloudTop.starshare.ui.main.adapter.MeetTypeAdapter;
import com.cloudTop.starshare.utils.DisplayUtil;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.JudgeIsSetPayPwd;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.TimeUtil;
import com.cloudTop.starshare.utils.timeselectutils.AddressPickTask;
import com.cloudTop.starshare.utils.timeselectutils.City;
import com.cloudTop.starshare.utils.timeselectutils.County;
import com.cloudTop.starshare.utils.timeselectutils.DatePicker;
import com.cloudTop.starshare.utils.timeselectutils.Province;
import com.cloudTop.starshare.widget.PasswordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.cloudTop.starshare.R.id.imagview;
import static com.cloudTop.starshare.R.id.tv_content;

/**
 * Created by Administrator on 2017/6/13.
 * 粉丝见面会
 */

public class MeetStarActivity extends BaseActivity {
    @Bind(R.id.nl_title)
    NormalTitleBar nl_title;
    @Bind(R.id.textView9)
    TextView textView9;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.pageindicator)
    PageIndicator pageIndicator;
    @Bind(R.id.view_pager)
    ViewPager view_pager;
    @Bind(R.id.imageView3)
    ImageView imageView3;
    @Bind(R.id.tv_to_buy)
    TextView sureToMeet;
    @Bind(R.id.comment)
    EditText comment;
    @Bind(R.id.order_price)
    TextView orderPrice;
    @Bind(R.id.iv_star_bg)
    ImageView starBg;
    @Bind(R.id.passwordView)
    PasswordView passwordView;
    @Bind(R.id.tv_meet_rule)
    TextView meetRule;
    private int current_end_year;
    private int current_end_month;
    private int current_end_day;
    private int end_year;
    private int end_end_year;
    private int end_month;
    private int end_end_month;
    private int end_day;
    private int end_end_day;
    private List<View> gridViews;
    private int type;
    private String wid;
    private String code;
    private String head_url;
    private String back_url;
    private String name;
    private List<StatServiceListBean.ListBean> typeList;
    private List<List<StatServiceListBean.ListBean>> lists;
    private Dialog mDetailDialog;
    private TextView tv_state;
    private TextView order_info;
    private String price = "";
    private TextView order_total;
    private String userComment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_meet_start;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nl_title.setTitleText(getString(R.string.meet_start));
        nl_title.setBackVisibility(true);
        nl_title.setRightImagVisibility(true);
        setMeetRule();
        getIntentData();
        //getDateTime();
//        getMeetType();
        getMeetInfo();
        initListener();
        initPopWindow();
    }

    private void setMeetRule() {
        SpannableStringBuilder spannable = new SpannableStringBuilder(getString(R.string.meet_rule));
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_0000FF)), 81, 85, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        meetRule.setMovementMethod(LinkMovementMethod.getInstance());  //这个一定要记得设置，不然点击不生效
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
              CommonWebActivity.startAction(MeetStarActivity.this,"http://122.144.169.219:3389/meet","约见规则");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.color_0000FF));
                ds.setUnderlineText(false);
            }
        }, 81, 85, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        meetRule.setText(spannable);
    }
    private boolean isCanChoose =false;
    private void getMeetInfo() {
        NetworkAPIFactoryImpl.getDealAPI().statServiceList(code, new OnAPIListener<StatServiceListBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("明星列表失败-------------!");
            }

            @Override
            public void onSuccess(StatServiceListBean statServiceListBean) {
                if (statServiceListBean.getResult() == 1) {
                    typeList = statServiceListBean.getList();
                    if (typeList.size() == 0) {
                        return;
                    }
                    textView4.setText(typeList.get(0).getMeet_city());
                    if (null==typeList.get(0).getStartdate()||"0".equals(typeList.get(0).getStartdate())||typeList.get(0).getStartdate().length()==1||typeList.get(0).getStartdate().length()==0){
                        isCanChoose = false;
                        String nextDay = TimeUtil.getNextDay(30);
                        LogUtil.e("获取当前时间" + nextDay);
                        current_end_year = Integer.valueOf(nextDay.substring(0, 4));
                        current_end_month = Integer.valueOf(nextDay.substring(5, 7));
                        current_end_day = Integer.valueOf(nextDay.substring(8, 10));
                    }else {
                        isCanChoose = true;
                        current_end_year = Integer.valueOf(typeList.get(0).getStartdate().substring(0, 4));
                        current_end_month = Integer.valueOf(typeList.get(0).getStartdate().substring(5, 7));
                        current_end_day = Integer.valueOf(typeList.get(0).getStartdate().substring(8, 10));
                    }
                    if (null==typeList.get(0).getEnddate()||"0".equals(typeList.get(0).getEnddate())||typeList.get(0).getEnddate().length()==1||typeList.get(0).getEnddate().length()==0){
                        isCanChoose = false;
                        String nextDay = TimeUtil.getNextDay(60);
                        LogUtil.e("获取当前时间" + nextDay);
                        end_end_year = Integer.valueOf(nextDay.substring(0, 4));
                        end_end_month = Integer.valueOf(nextDay.substring(5, 7));
                        end_end_day = Integer.valueOf(nextDay.substring(8, 10));
                    }else {
                        isCanChoose = true;
                        end_end_year = Integer.valueOf(typeList.get(0).getEnddate().substring(0, 4));
                        end_end_month = Integer.valueOf(typeList.get(0).getEnddate().substring(5, 7));
                        end_end_day = Integer.valueOf(typeList.get(0).getEnddate().substring(8, 10));
                    }
                    textView9.setText(current_end_year + "-" + current_end_month + "-" + current_end_day+" — "+end_end_year+"-"+end_end_month+"-"+end_end_day);
                    getMeetType();


                }
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getIntExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE, 0);
        wid = intent.getStringExtra(AppConstant.STAR_WID);
        code = intent.getStringExtra(AppConstant.STAR_CODE);
        head_url = intent.getStringExtra(AppConstant.STAR_HEAD_URL);
        back_url = intent.getStringExtra(AppConstant.STAR_BACKGROUND_URL);
        name = intent.getStringExtra(AppConstant.STAR_NAME);
        textView6.setText(String.format(getString(R.string.name_code), name, code));
        ImageLoaderUtils.displaySmallPhoto(this, imageView3, head_url);
        if (TextUtils.isEmpty(back_url)){
            List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(code);
            if (starInfos != null && starInfos.size() != 0) {
                StarInfo starInfo = starInfos.get(0);
                ImageLoaderUtils.displayWithDefaultImg(mContext, starBg, starInfo.getPic1(),R.drawable.rec_bg);
            }
        }else {
            ImageLoaderUtils.displayWithDefaultImg(mContext, starBg, back_url,R.drawable.rec_bg);
        }

    }

    private void getDateTime() {
        String nextDay = TimeUtil.getNextDay(30);
        LogUtil.e("获取当前时间" + nextDay);
        current_end_year = Integer.valueOf(nextDay.substring(0, 4));
        current_end_month = Integer.valueOf(nextDay.substring(5, 7));
        current_end_day = Integer.valueOf(nextDay.substring(8, 10));
        textView9.setText(current_end_year + "-" + current_end_month + "-" + current_end_day);
        textView6.setText(String.format(getString(R.string.name_code), name, code));
    }

    public void onYearMonthStartTime() {
        LogUtils.loge("end_year"+end_year+"end_month"+end_month+"end_day"+end_day);
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(DisplayUtil.dip2px(20));
        picker.setRangeEnd(end_end_year,end_end_month,end_end_day);
        picker.setRangeStart(current_end_year, current_end_month, current_end_day);
        picker.setSelectedItem(current_end_year, current_end_month, current_end_day);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                LogUtils.loge(year + "-" + month + "-" + day);
                end_year = Integer.valueOf(year).intValue();
                end_month = Integer.valueOf(month).intValue();
                end_day = Integer.valueOf(day).intValue();
                textView9.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    @OnClick({R.id.imageView8, R.id.textView9})
    public void OnTimeSelectClick(View v) {
        if (isCanChoose){
            onYearMonthStartTime();
        }
    }

    /*@OnClick({R.id.imageView4, R.id.textView4})
    public void OnCitySelectClick(View v) {
        onAddressPicker();
    }*/

    @OnClick({R.id.tv_to_buy})
    public void setSureToMeet(View v) {
        if (selectPosition < 0) {
            ToastUtils.showShort("请选择一个约见类型");
            return;
        }
        userComment = comment.getText().toString().trim();
        if (TextUtils.isEmpty(userComment)) {
            ToastUtils.showShort("请输入备注内容");
            return;
        }
        showOrderInfoDialog();
//        makeSureToMeet();
    }

    //地理位置选择器
    public void onAddressPicker() {
        LogUtils.loge("点击了");
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {

            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    textView4.setText(province.getAreaName() + city.getAreaName());
                } else {
                    textView4.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute("浙江", "杭州", "上城区");
    }

    private void getMeetType() {
        gridViews = new ArrayList<View>();
        List<StatServiceListBean.ListBean> listBeen;
        lists = new ArrayList<>();

//        float pagerCount = (float)typeList.size() / 8;
        int pagerCount = (int) Math.ceil((float) typeList.size() / 8);

        for (int i = 0; i < pagerCount; i++) {
            listBeen = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                if ((i * 8 + j) < typeList.size()) {
                    listBeen.add(typeList.get(i * 8 + j));
                }
            }
            lists.add(listBeen);
        }
        LogUtils.loge("coiunt:" + pagerCount + "转换后的list:" + lists.toString());
//        for (int i = 0; i < 7; i++) {
//            MeetTypeBeen meetTypeBeen = new MeetTypeBeen();
//            meetTypeBeen.setMeetType(i);
//            listBeen.add(meetTypeBeen);
//        }
//        for (int i = 0; i < 3; i++) {
//            lists.add(listBeen);
//        }
        for (Integer i = 0; i < lists.size(); ++i) {
            GridView gridView = (GridView) LayoutInflater.from(this).inflate(R.layout.gridview_layout, null);
            MeetTypeAdapter adapter = new MeetTypeAdapter(this, lists.get(i));
            gridView.setAdapter(adapter);
            gridViews.add(gridView);
            gridView.setTag(i);
            gridView.setOnItemClickListener(onItemClickListener);
        }
        view_pager.setAdapter(new GridViewPageAdapter(gridViews));
        pageIndicator.setViewPager(view_pager, 0);
    }

    private int selectPager = -1;
    private int selectPosition = -1;
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectPager >= 0 && selectPosition >= 0) {
                GridView gridView = (GridView) gridViews.get(selectPager);
                View childAt = gridView.getChildAt(selectPosition);
                TextView textView = (TextView) childAt.findViewById(tv_content);
                ImageView img_select = (ImageView) childAt.findViewById(imagview);
                ImageLoaderUtils.displaySmallPhoto(MeetStarActivity.this,img_select,lists.get(selectPager).get(selectPosition).getUrl2());
                textView.setTextColor(mContext.getResources().getColor(R.color.color_BDC6B8));
            }
            selectPager = view_pager.getCurrentItem();
            selectPosition = position;
            LogUtils.loge("当前的position:" + selectPosition);
            TextView textView = (TextView) view.findViewById(tv_content);
            ImageView img_selects = (ImageView) view.findViewById(imagview);
            ImageLoaderUtils.displaySmallPhoto(MeetStarActivity.this,img_selects,lists.get(selectPager).get(selectPosition).getUrl1());
            textView.setTextColor(mContext.getResources().getColor(R.color.color_CB4232));
            price = lists.get(selectPager).get(selectPosition).getPrice();
            orderPrice.setText(String.format(getString(R.string.num_time_text), price));
            tv_state.setText(lists.get(selectPager).get(selectPosition).getName());
        }
    };

    private void makeSureToMeet() {
        long mid = Long.parseLong(lists.get(selectPager).get(selectPosition).getMid());
        String city = textView4.getText().toString();
        String time = textView9.getText().toString();
        userComment = comment.getText().toString().trim();

        NetworkAPIFactoryImpl.getDealAPI().starMeet(code, mid, city, time, 1, userComment, new OnAPIListener<RequestResultBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("约见失败-------------");
                ToastUtils.showStatusView("约见失败", false);
            }

            @Override
            public void onSuccess(RequestResultBean resultBean) {
                LogUtils.loge("约见成功------------------------------");
                if (resultBean.getResult() == 1) {
                    ToastUtils.showShort("约见成功，请耐心等待，并保持手机通话畅通。");
//                    ToastUtils.showStatusView("约见成功，请耐心等待，并保持手机通话畅通。", true);
                }else if (resultBean.getResult()==-100){
                    ToastUtils.showShort("格式错误");
                }else if (resultBean.getResult()==-1000){
                    ToastUtils.showShort("持有时间不足");
                }
            }
        });
    }

    private void initListener() {
//        nl_title.setOnRightImagListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share();
//            }
//        });
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
                makeSureToMeet();
            }
        });
    }

    private void share() {
        ShareControlerView controlerView = new ShareControlerView(this, mContext, umShareListener);
        String webUrl = "https://mobile.umeng.com/";
        String title = "This is web title";
        String describe = "描述描述";
        String text = "文本";
        controlerView.setText(text);
        controlerView.setWebUrl(webUrl);
        controlerView.setDescribe(describe);
        controlerView.setTitle(title);

        controlerView.showShareView(rootView);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort("分享成功啦");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort("分享失败了");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort("分享取消了");
        }
    };

    //订单详情接口
    private void showOrderInfoDialog() {
        order_info.setText(String.format(getString(R.string.name_code), name, code));
        order_total.setText(String.format(getString(R.string.num_time_text), price));
        mDetailDialog.show();
    }

    private void initPopWindow() {
        mDetailDialog = new Dialog(this, R.style.custom_dialog);
        Window window = mDetailDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        mDetailDialog.setContentView((R.layout.dialog_order_info));
        tv_state = (TextView) mDetailDialog.findViewById(R.id.tv_state);
        TextView tv_sure = (TextView) mDetailDialog.findViewById(R.id.tv_sure);
        order_info = (TextView) mDetailDialog.findViewById(R.id.order_info);
        order_total = (TextView) mDetailDialog.findViewById(R.id.order_total);
        ImageView img_close = (ImageView) mDetailDialog.findViewById(R.id.img_close);

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //KeyBordUtil.showSoftKeyboard(payPwdEditText);
                mDetailDialog.dismiss();
                if (JudgeIsSetPayPwd.isSetPwd(MeetStarActivity.this)) {
                    inputDealPwd();
                }

            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();

            }
        });

    }

    private void inputDealPwd() {
        passwordView.setVisibility(View.VISIBLE);
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
