package com.cloudTop.starshare.ui.main.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConfig;
import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.been.StarQuestionBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.BookStarVoiceAdapter;
import com.cloudTop.starshare.ui.main.adapter.HistoryVoiceAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.widget.AudioPlayer;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.netease.nim.uikit.common.ui.recyclerview.decoration.SpacingDecoration;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 定制语音
 * Created by SHI 2017-9-12 17:53:23
 */

public class BookingStarMakeVoiceFragment extends BaseFragment {

    @Bind(R.id.lrv)
    LRecyclerView lrv;

    @Bind(R.id.fl_auction_content)
    FrameLayout parentView;

    @Bind(R.id.radio_group)
    RadioGroup radio_group;

    private static final int REQUEST_COUNT = 10;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<StarQuestionBean.CircleListBean> list = new ArrayList<>();
    private List<StarQuestionBean.CircleListBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 1;
    private BookStarVoiceAdapter autionTopAdapter;
    private int hotType = 1;
    private AudioPlayer audioPlayer;
    private static final String DEFAULT_TEST_FILE = "http://ouim6qew1.bkt.clouddn.com/voice1503974801.mp3";
    private int currentPlayingPosition = -1;
    private ImageView voicePalyImagview;
    private AnimationDrawable voiceBackground;
    private boolean mIsPlay = false;
    private String code = "" ;



    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_booking_make_voice;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initAdapter();
        SwitchTo(0);
        initListener();
    }

    private void initListener() {
        audioPlayer = new AudioPlayer(getActivity().getApplicationContext(), new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case AudioPlayer.HANDLER_CUR_TIME://更新的时间
                        break;
                    case AudioPlayer.HANDLER_COMPLETE://播放结束
                        if (voicePalyImagview != null && voiceBackground != null) {
                            voiceBackground.stop();
                            voicePalyImagview.setBackground(getResources().getDrawable(R.drawable.voice_icon));
                        }
                        mIsPlay = false;
                        break;
                    case AudioPlayer.HANDLER_PREPARED://播放开始
                        break;
                    case AudioPlayer.HANDLER_ERROR://播放错误
                        break;
                }

            }
        });
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
        switch (position) {
            case 0:
                hotType = 1;
                getLrvData(false, 1, REQUEST_COUNT);

                break;
            case 1:
                hotType = 0;
                getLrvData(false, 1, REQUEST_COUNT);
                break;
            default:
                break;
        }
    }

    private void getLrvData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().getUserQuestionsInfo(code, SharePrefUtil.getInstance().getUserId(),start, count, SharePrefUtil.getInstance().getToken(), 2, hotType, new OnAPIListener<StarQuestionBean>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        autionTopAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
                LogUtils.loge("明星互动返回错误码" + ex.toString());
            }

            @Override
            public void onSuccess(StarQuestionBean bean) {
                if (bean == null || bean.getCircle_list() == null || bean.getCircle_list().size() == 0) {
                    if (!isLoadMore) {
                        list.clear();
                        autionTopAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    } else {
                        lrv.setNoMore(true);
                    }

                    return;
                }
                if (isLoadMore) {
                    loadList.clear();
                    loadList = bean.getCircle_list();
                    loadMoreData();
                } else {
                    list.clear();
                    list = bean.getCircle_list();
                    showData();
                }
            }
        });
    }

    private void initAdapter() {
//        BookStarVideoAdapter

        autionTopAdapter = new BookStarVoiceAdapter(getActivity().getApplicationContext(), new BookStarVoiceAdapter.OnClickListenListener() {
            @Override
            public void onClickListen(View view, int position, ImageView imageVoiceView) {
                final StarQuestionBean.CircleListBean listBean = list.get(position);
                if (listBean.getAnswer_t()==0){
                    return;
                }else {
                    voicePalyDoing(position, imageVoiceView, listBean);
                }
            }
        });
        lRecyclerViewAdapter = new LRecyclerViewAdapter(autionTopAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.addItemDecoration(new SpacingDecoration(ScreenUtil.dip2px(10), ScreenUtil.dip2px(10), true));
        lrv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        lrv.setPullRefreshEnabled(true);
        lrv.setLoadMoreEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getLrvData(true,mCurrentCounter+1,REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 1;
                lrv.setNoMore(false);
                getLrvData(false, 1,REQUEST_COUNT);
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

    private void voicePalyDoing(int position, ImageView imageView, StarQuestionBean.CircleListBean listBean) {
        if (audioPlayer != null && (currentPlayingPosition != position)) {
            if (voicePalyImagview != null && voiceBackground != null) {
                voiceBackground.stop();
                voicePalyImagview.setBackground(getResources().getDrawable(R.drawable.voice_icon));
            }
            if (!TextUtils.isEmpty(listBean.getSanswer())){
                voicePalyImagview = imageView;
                voicePalyImagview.setBackground(getResources().getDrawable(R.drawable.animation_voice_paly));
                voiceBackground = (AnimationDrawable) voicePalyImagview.getBackground();
                voiceBackground.start();
                if (mIsPlay) {
                    audioPlayer.stopPlay();
                }
                resolvePlayRecord(AppConfig.QI_NIU_PIC_ADRESS+listBean.getSanswer());
            }
            currentPlayingPosition = position;
        } else if (audioPlayer != null && (currentPlayingPosition == position)) {
            if (voicePalyImagview != null && voiceBackground != null) {
                voiceBackground.stop();
                voicePalyImagview.setBackground(getResources().getDrawable(R.drawable.voice_icon));
            }
            audioPlayer.stopPlay();
            currentPlayingPosition = -1;
        }
    }


    /**
     * 播放
     */
    private void resolvePlayRecord(String paly_path) {
        mIsPlay = true;
        audioPlayer.playUrl(paly_path);
    }

    @Override
    public void onPause() {
        if (mIsPlay) {
            audioPlayer.pause();
            audioPlayer.stopPlay();
        }
        super.onPause();
    }


    @Override
    public void onDestroy() {
        if (lrv != null) {
            lrv = null;
        }
        if (autionTopAdapter != null) {
            autionTopAdapter = null;
        }
        if (audioPlayer != null) {
            audioPlayer.stop();
        }
        super.onDestroy();
    }
}
