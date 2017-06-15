package com.yundian.star.ui.main.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.MeetTypeBeen;
import com.yundian.star.been.RequestResultBean;
import com.yundian.star.been.StatServiceListBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.GridViewPageAdapter;
import com.yundian.star.ui.main.adapter.MeetTypeAdapter;
import com.yundian.star.utils.DisplayUtil;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.utils.timeselectutils.AddressPickTask;
import com.yundian.star.utils.timeselectutils.City;
import com.yundian.star.utils.timeselectutils.County;
import com.yundian.star.utils.timeselectutils.DatePicker;
import com.yundian.star.utils.timeselectutils.Province;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.widget.indicator.PageIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static android.R.id.list;
import static com.yundian.star.R.id.tv_content;
import static io.netty.util.concurrent.FastThreadLocal.size;

/**
 * Created by Administrator on 2017/6/13.
 * 约见名人
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
    private int current_end_year;
    private int current_end_month;
    private int current_end_day;
    private int end_year;
    private int end_month;
    private int end_day;
    private List<View> gridViews;
    private int type;
    private String wid;
    private String code;
    private String head_url;
    private String name;
    private List<StatServiceListBean.ListBean> typeList;
    private List<List<StatServiceListBean.ListBean>> lists;

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
        nl_title.setRightImagSrc(R.drawable.share);
        getIntentData();
        getDateTime();
//        getMeetType();
        getMeetInfo();
    }

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
        name = intent.getStringExtra(AppConstant.STAR_NAME);
    }

    private void getDateTime() {
        Calendar c = Calendar.getInstance();
        current_end_year = c.get(Calendar.YEAR);
        current_end_month = c.get(Calendar.MONTH) + 1;
        current_end_day = c.get(Calendar.DAY_OF_MONTH);
        textView9.setText(current_end_year + "-" + current_end_month + "-" + current_end_day);
        ImageLoaderUtils.display(this, imageView3, head_url);
        textView6.setText(String.format(getString(R.string.name_code), name, code));
    }

    public void onYearMonthStartTime() {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(DisplayUtil.dip2px(20));
        picker.setRangeStart(2017, 1, 1);
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
        onYearMonthStartTime();
    }

    @OnClick({R.id.imageView4, R.id.textView4})
    public void OnCitySelectClick(View v) {
        onAddressPicker();
    }

    @OnClick({R.id.tv_to_buy})
    public void setSureToMeet(View v) {
        makeSureToMeet();
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

        int pagerCount = typeList.size() % 8;
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
                textView.setTextColor(mContext.getResources().getColor(R.color.color_BDC6B8));
            }
            selectPager = view_pager.getCurrentItem();
            selectPosition = position;
            LogUtils.loge("当前的position:" + selectPosition);
            TextView textView = (TextView) view.findViewById(tv_content);
            textView.setTextColor(mContext.getResources().getColor(R.color.color_CB4232));
        }
    };

    private void makeSureToMeet() {

        if (selectPosition < 0) {
            ToastUtils.showShort("请选择一个约见类型");
            return;
        }
        long mid = Long.parseLong(lists.get(selectPager).get(selectPosition).getMid());
        String city = textView4.getText().toString();
        String time = textView9.getText().toString();
        String userComment = comment.getText().toString().trim();

        NetworkAPIFactoryImpl.getDealAPI().starMeet(code, mid, city, time, 1, userComment, new OnAPIListener<RequestResultBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("约见失败-------------");
                ToastUtils.showStatusView("约见失败",false);
            }

            @Override
            public void onSuccess(RequestResultBean resultBean) {
                LogUtils.loge("约见成功------------------------------");
                if (resultBean.getResult() == 1){
                    ToastUtils.showStatusView("约见成功",true);
                }
            }
        });
    }
}
