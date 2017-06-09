package com.yundian.star.ui.main.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.AdvBeen;
import com.yundian.star.ui.main.activity.NewsBrowserActivity;
import com.yundian.star.ui.main.activity.NewsStarBuyActivity;
import com.yundian.star.ui.main.adapter.NewsInforAdapter;
import com.yundian.star.ui.main.contract.NewInfoContract;
import com.yundian.star.ui.main.model.NewsInforModel;
import com.yundian.star.ui.main.presenter.NewsInfoPresenter;
import com.yundian.star.utils.AdViewpagerUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/8.
 */

public class NewsInfoFragment extends BaseFragment<NewsInfoPresenter, NewsInforModel> implements NewInfoContract.View {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.rl_time)
    RelativeLayout rl_time;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_am_pm)
    TextView tv_am_pm;
    @Bind(R.id.tv_time_h)
    TextView tv_time_h;
    @Bind(R.id.imageView2)
    ImageView imageView2 ;
    //    @Bind(R.id.loadingTip)
//    LoadingTip loadingTip ;
    private ArrayList<NewsInforModel.ListBean> arrayList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private NewsInforAdapter newsInfoAdapter;
    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 1;
    /**
     * 服务器端一共多少条数据
     */
    private static final int TOTAL_COUNTER = 34;
    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;
    private AdViewpagerUtil adViewpagerUtil;
    private int adv_height;
    private View header;
    private RelativeLayout rl_adroot;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        initAdpter();
        mPresenter.getAdvertisement("1", 1);
        mPresenter.getData(false, "1", "1", 1, REQUEST_COUNT, 1);
    }

    private void initAdpter() {
        newsInfoAdapter = new NewsInforAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(newsInfoAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.dp_0_5)
                .setColorResource(R.color.color_cccccc)
                .build();
        //mRecyclerView.setHasFixedSize(true);
        lrv.addItemDecoration(divider);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setPullRefreshEnabled(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getData(true, "1", "1", mCurrentCounter + 1, mCurrentCounter + REQUEST_COUNT, 1);
            }
        });

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtils.loge(position + "");
                NewsInforModel.ListBean listBean = arrayList.get(position);
                NewsBrowserActivity.startAction(getActivity(), listBean.getLink_url(), "");
            }
        });
        lrv.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {
            }

            @Override
            public void onScrollStateChanged(int state) {
                RecyclerView.LayoutManager layoutManager = lrv.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    //int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    LogUtils.loge(firstItemPosition + "....");
                    if (linearManager.findFirstVisibleItemPosition() >= 2) {
                        full(true);
                        rl_time.setVisibility(View.VISIBLE);
                        NewsInforModel.ListBean listBean = arrayList.get(firstItemPosition - 2);
                        long time = System.currentTimeMillis();
                        final Calendar mCalendar = Calendar.getInstance();
                        mCalendar.setTimeInMillis(time);
                        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
                        int m = mCalendar.get(Calendar.MINUTE);
                        int apm = mCalendar.get(Calendar.AM_PM);
                        if (apm == 1) {
                            tv_am_pm.setText(getString(R.string.PM));
                        } else {
                            tv_am_pm.setText(getString(R.string.AM));
                        }
                        if (hour>=6&&hour<18){
                            imageView2.setImageDrawable(getResources().getDrawable(R.drawable.news_day));
                        }else {
                            imageView2.setImageDrawable(getResources().getDrawable(R.drawable.news_night));
                        }
                        String stringByFormat = TimeUtil.formatDateYMD(listBean.getTimes());
                        tv_time.setText(stringByFormat);
                        tv_time_h.setText(hour + ":" + m);
                    } else {
                        full(false);
                        rl_time.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void initDatas(ArrayList<NewsInforModel.ListBean> list) {
        if (list == null) {
            return;
        }
        arrayList.clear();
        arrayList = list;
        mCurrentCounter = list.size();
        newsInfoAdapter.clear();
        //newsInfoAdapter.setDataList(arrayList);
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        newsInfoAdapter.addAll(list);
        lrv.refresh();
    }

    @Override
    public void addMoreItems(ArrayList<NewsInforModel.ListBean> list) {
        if (list == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            arrayList.addAll(list);
            newsInfoAdapter.addAll(list);
            mCurrentCounter += list.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    @Override
    public void initAdv(final AdvBeen o) {
        if (o.getList() == null || o.getList().size() == 0) {
            return;
        }
        final List<AdvBeen.ListBean> listData = o.getList();
        String adList[] = new String[listData.size()];
        for (int i = 0; i < listData.size(); i++) {
            adList[i] = o.getList().get(i).getPic_url();
        }
        LogUtils.loge("首页资讯轮播图数据" + listData.toString());
        //add a HeaderView
        header = LayoutInflater.from(getContext()).inflate(R.layout.adv_layout, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        rl_adroot = (RelativeLayout) header.findViewById(R.id.rl_adroot);
        ViewPager viewpager = (ViewPager) header.findViewById(R.id.viewpager);
        LinearLayout ly_dots = (LinearLayout) header.findViewById(R.id.ly_dots);
        adViewpagerUtil = new AdViewpagerUtil(getActivity(), viewpager, ly_dots, adList);
        adViewpagerUtil.setOnAdItemClickListener(new AdViewpagerUtil.OnAdItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String url) {
                Intent intent = new Intent(getActivity(), NewsStarBuyActivity.class);
                intent.putExtra(AppConstant.STAR_CODE, o.getList().get(position).getCode());
                LogUtils.loge("首页资讯轮播图明星code" + o.getList().get(position).getCode());
                startActivity(intent);
            }
        });
        lRecyclerViewAdapter.addHeaderView(header);
        rl_adroot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                adv_height = rl_adroot.getHeight();
                rl_adroot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    //生命周期控制
    @Override
    public void onPause() {
        super.onPause();
        if (adViewpagerUtil != null) {
            adViewpagerUtil.stopLoopViewPager();
            LogUtils.logd("广告停止");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adViewpagerUtil != null) {
            adViewpagerUtil.startLoopViewPager();
            LogUtils.logd("广告开始");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            mCurrentCounter = 1;
            lrv.setNoMore(false);
            mPresenter.getData(false, "1", "1", 1, REQUEST_COUNT, 1);
            LogUtils.loge("刷新");
        }
    }


    /* @Override
    public void onDestroy() {
        super.onDestroy();
        if (adViewpagerUtil != null) {
            adViewpagerUtil.destroyAdViewPager();
        }
    }*/

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getActivity().getWindow().setAttributes(lp);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getActivity().getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getActivity().getWindow().setAttributes(attr);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
