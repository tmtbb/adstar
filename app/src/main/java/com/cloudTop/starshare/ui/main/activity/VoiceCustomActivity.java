package com.cloudTop.starshare.ui.main.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConfig;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.ResultBeen;
import com.cloudTop.starshare.been.StarQuestionBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.VoiceCustomAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.AudioPlayer;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.netease.nim.uikit.common.ui.recyclerview.decoration.SpacingDecoration;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/18.
 * 语音定制列表
 */

public class VoiceCustomActivity extends BaseActivity {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.parent_view)
    FrameLayout parentView;

    private VoiceCustomAdapter marketDetailAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<StarQuestionBean.CircleListBean> list = new ArrayList<>();
    private ArrayList<StarQuestionBean.CircleListBean> loadList = new ArrayList<>();
    private String code;
    private String star_name;
    private boolean mIsPlay = false;
    private AudioPlayer audioPlayer;
    // TODO: 2017/8/25 修改为手机里aac的路径,暂时只能兼容aac音频和mp4文件
    private static final String DEFAULT_TEST_FILE = "http://ouim6qew1.bkt.clouddn.com/voice1503974801.mp3";
    private int currentPlayingPosition = -1;
    private ImageView voicePalyImagview;
    private AnimationDrawable voiceBackground;

    //    private static final String DEFAULT_TEST_FILE = "/storage/emulated/0/ShortVideo/pl-section-1503281446703.mp4";
    @Override
    public int getLayoutId() {
        return R.layout.activity_voice_custom;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        code = intent.getStringExtra("star_code");
        star_name = intent.getStringExtra("star_name");
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(star_name);
        nt_title.setRightTitle("历史定制");
        nt_title.setRightTitleVisibility(true);
        initAdpter();
        initListener();
        getData(false, 0, REQUEST_COUNT);

    }

    private void initListener() {
        nt_title.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoiceCustomActivity.this, HistoryVoiceActivity.class);
                intent.putExtra("star_code", code);
                startActivity(intent);
            }
        });

        audioPlayer = new AudioPlayer(this, new Handler() {
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

    //获取数据
    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().getStarQuestionsInfo(SharePrefUtil.getInstance().getUserId(),code, start, count, SharePrefUtil.getInstance().getToken(), 2, 1, new OnAPIListener<StarQuestionBean>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        marketDetailAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
                LogUtils.loge("网红互动返回错误码" + ex.toString());
            }

            @Override
            public void onSuccess(StarQuestionBean bean) {
                if (bean == null || bean.getCircle_list() == null || bean.getCircle_list().size() == 0) {
                    if (!isLoadMore) {
                        list.clear();
                        marketDetailAdapter.clear();
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

    public void showData() {
        if (list.size() == 0) {
            showErrorView(parentView, R.drawable.error_view_comment, "暂无相关数据");
            return;
        } else {
            closeErrorView();
        }
        if (marketDetailAdapter != null) {
            marketDetailAdapter.clear();
        }
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        marketDetailAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            marketDetailAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    @Override
    public void onDestroy() {
        if (lrv != null) {
            lrv = null;
        }
        if (marketDetailAdapter != null) {
            marketDetailAdapter = null;
        }
        if (audioPlayer != null) {
            audioPlayer.stop();
        }
        super.onDestroy();
    }

    private void initAdpter() {
        marketDetailAdapter = new VoiceCustomAdapter(this, new VoiceCustomAdapter.OnClickListenListener() {
            @Override
            public void onClickListen(View view, final int position, final ImageView imageView) {
                final StarQuestionBean.CircleListBean listBean = list.get(position);
                if (listBean.getPurchased() == 1) {
                    voicePalyDoing(position, imageView, listBean);
                } else if (listBean.getPurchased() == 0) {
                    NetworkAPIFactoryImpl.getInformationAPI().toBuyQuestion(SharePrefUtil.getInstance().getUserId(),
                            listBean.getId(), code, listBean.getC_type(),listBean.getUid(), new OnAPIListener<ResultBeen>() {
                                @Override
                                public void onError(Throwable ex) {
                                    ToastUtils.showShort("您持有的该网红时间不足，请购买");
                                }

                                @Override
                                public void onSuccess(ResultBeen been) {
                                    if (been.getResult() == 1) {
                                        ToastUtils.showShort("您持有的该网红时间不足，请购买");
                                    } else if (been.getResult() == 0) {
                                        voicePalyDoing(position, imageView, listBean);
                                        listBean.setPurchased(1);
                                        marketDetailAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                }


            }
        });
        lRecyclerViewAdapter = new LRecyclerViewAdapter(marketDetailAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(this));

        lrv.addItemDecoration(new SpacingDecoration(ScreenUtil.dip2px(10), ScreenUtil.dip2px(10), true));
        lrv.setLoadMoreEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter+1, REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 1;
                lrv.setNoMore(false);
                getData(false, 1, REQUEST_COUNT);
            }
        });
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

    @OnClick({R.id.btn_recharge_sure})
    public void onClickSwitch(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge_sure:
                Intent intent = new Intent(this, AskToVoiceActivity.class);
                intent.putExtra("star_code", code);
                startActivity(intent);
                break;
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
    protected void onPause() {
        if (mIsPlay) {
            audioPlayer.pause();
            audioPlayer.stopPlay();
        }
        super.onPause();
    }
}
