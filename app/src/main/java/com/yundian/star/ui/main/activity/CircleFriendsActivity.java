package com.yundian.star.ui.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.CommentConfig;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.CircleFriendBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.CircleFriendAdapter;
import com.yundian.star.ui.main.contract.CircleContract;
import com.yundian.star.ui.main.presenter.CirclePresenter;
import com.yundian.star.utils.KeyBordUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.widget.emoji.EmotionKeyboard;
import com.yundian.star.widget.emoji.EmotionLayout;
import com.yundian.star.widget.emoji.IEmotionExtClickListener;
import com.yundian.star.widget.emoji.IEmotionSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.yundian.star.R.id.etContent;
import static com.yundian.star.R.id.llContent;


/**
 * Created by Administrator on 2017/7/12.
 * 朋友圈
 */

public class CircleFriendsActivity extends BaseActivity implements CircleContract.View, IEmotionSelectedListener {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.fl_pr)
    FrameLayout fl_pr;
    @Bind(R.id.emoji_include)
    LinearLayout emoji_include;
    private CirclePresenter presenter;
    private CircleFriendAdapter circleFriendAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int selectCommentItemOffset;
    private LinearLayoutManager layoutManager;
    private CommentConfig commentConfig;
    private int selectCircleItemH;
    private static int mCurrentCounter = 0;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<CircleFriendBean.CircleListBean> list = new ArrayList<>();
    private ArrayList<CircleFriendBean.CircleListBean> loadList = new ArrayList<>();
    private LinearLayout mLlContent;
    private LinearLayout ll_inputt;
    private EditText mEtContent;
    private ImageView mIvEmo;
    private Button mBtnSend;
    private FrameLayout mFlEmotionView;
    private FrameLayout flEmotionView;
    private EmotionLayout mElEmotion;
    private EmotionKeyboard mEmotionKeyboard;

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_friend;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        presenter = new CirclePresenter(this);
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(R.string.find_star);
        initEmoji();
        initAdapter();
        getData(false, 0, REQUEST_COUNT);
    }

    private void initEmoji() {
        mLlContent = (LinearLayout) findViewById(llContent);
        ll_inputt = (LinearLayout) findViewById(R.id.ll_inputt);
        flEmotionView = (FrameLayout) findViewById(R.id.flEmotionView);
        mEtContent = (EditText) findViewById(etContent);
        mIvEmo = (ImageView) findViewById(R.id.ivEmo);
        mBtnSend = (Button) findViewById(R.id.btnSend);
        mFlEmotionView = (FrameLayout) findViewById(R.id.flEmotionView);
        mElEmotion = (EmotionLayout) findViewById(R.id.elEmotion);
        mElEmotion.attachEditText(mEtContent);
        initEmotionKeyboard();
    }

    private void initAdapter() {
        circleFriendAdapter = new CircleFriendAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(circleFriendAdapter);
        circleFriendAdapter.setCirclePresenter(presenter);
        lrv.setAdapter(lRecyclerViewAdapter);
        layoutManager = new LinearLayoutManager(this);
        lrv.setLayoutManager(layoutManager);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter, REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false, 0, REQUEST_COUNT);
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
                if (emoji_include.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                }
            }
        });

//        systemMessageAdapter = new SystemMessageAdapter(this,list, SharePrefUtil.getInstance().getUserId());
//        lRecyclerViewAdapter = new LRecyclerViewAdapter(systemMessageAdapter);
//        lrv.setAdapter(lRecyclerViewAdapter);
//        lrv.setLayoutManager(new LinearLayoutManager(this));
//        lrv.setNoMore(false);
//        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        /*lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
//            }
//        });*/
//        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                getData(true, mCurrentCounter + 1, REQUEST_COUNT);
//            }
//        });
//        lrv.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getData(false, 1, REQUEST_COUNT);
//            }
//        });
    }


    @Override
    public void update2DeleteCircle(String circleId) {

    }

    @Override
    public void update2AddFavorite(int circlePosition, CircleFriendBean.CircleListBean.ApproveListBean addItem) {
        if (addItem != null) {
            CircleFriendBean.CircleListBean item = (CircleFriendBean.CircleListBean) circleFriendAdapter.getDatas().get(circlePosition);
            item.getApprove_list().add(addItem);
            circleFriendAdapter.notifyDataSetChanged();
            //circleAdapter.notifyItemChanged(circlePosition+1);
        }
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {

    }

    @Override
    public void update2AddComment(int circlePosition, CircleFriendBean.CircleListBean.CommentListBean addItem) {

    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {

    }

    //发表评论吊起
    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        if (View.GONE == visibility && mEtContent.getVisibility() == View.VISIBLE) {
            mEtContent.setText("");
        }
        ll_inputt.setVisibility(visibility);
        if (View.VISIBLE == visibility) {
            mEtContent.requestFocus();
            //弹出键盘
            KeyBordUtil.showSoftInput(mEtContent.getContext(), mEtContent);
        } else if (View.GONE == visibility) {
            if (mFlEmotionView.getVisibility() == View.VISIBLE) {
                mFlEmotionView.setVisibility(View.GONE);
            }
            //隐藏键盘
            KeyBordUtil.hideSoftInput(mEtContent.getContext(), mEtContent);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void update2loadData(int loadType, List<CircleFriendBean.CircleListBean> datas) {

    }


    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().getAllCircleInfo(start, count, new OnAPIListener<CircleFriendBean>() {
            @Override
            public void onError(Throwable ex) {
                if (lrv != null) {
                    lrv.setNoMore(true);
                    if (!isLoadMore) {
                        list.clear();
                        circleFriendAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(fl_pr, R.drawable.error_view_comment, "当前没有相关数据");
                    }
                }
            }

            @Override
            public void onSuccess(CircleFriendBean circleFriendBean) {
                LogUtils.loge("圈子反馈" + circleFriendBean.toString());
                if (circleFriendBean == null || circleFriendBean.getCircle_list() == null || circleFriendBean.getCircle_list().size() == 0) {
                    if (!isLoadMore) {
                        list.clear();
                        circleFriendAdapter.clear();
                        lrv.refreshComplete(REQUEST_COUNT);
                        showErrorView(fl_pr, R.drawable.error_view_comment, "当前没有相关数据");
                    } else {
                        lrv.setNoMore(true);
                    }

                    return;
                }
                if (isLoadMore) {
                    loadList.clear();
                    loadList = circleFriendBean.getCircle_list();
                    loadMoreData();
                } else {
                    list.clear();
                    list = circleFriendBean.getCircle_list();
                    showData();
                }
            }
        });

    }

    public void showData() {
        if (list.size() == 0) {
            showErrorView(fl_pr, R.drawable.error_view_comment, "暂无相关数据");
            return;
        } else {
            closeErrorView();
        }
        circleFriendAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        circleFriendAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            circleFriendAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.recycle();
        }
        super.onDestroy();
    }

    private void initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.with(this);
        mEmotionKeyboard.bindToEditText(mEtContent);
        mEmotionKeyboard.bindToContent(mLlContent);
        mEmotionKeyboard.setEmotionLayout(mFlEmotionView);
        mEmotionKeyboard.bindToEmotionButton(mIvEmo);
        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
            @Override
            public boolean onEmotionButtonOnClickListener(View view) {
                switch (view.getId()) {
                    case R.id.ivEmo:
                        if (!mElEmotion.isShown()) {
                            /*if (mLlMore.isShown()) {
                                showEmotionLayout();
                                hideMoreLayout();
                                hideAudioButton();
                                return true;
                            }*/
                        } else if (mElEmotion.isShown()) {
                            mIvEmo.setImageResource(R.drawable.ic_cheat_emo);
                            return false;
                        }
                        showEmotionLayout();
                        break;
                }
                return false;
            }
        });

    }


    private void showEmotionLayout() {
        mElEmotion.setVisibility(View.VISIBLE);
        mIvEmo.setImageResource(R.drawable.ic_cheat_keyboard);
    }

    private void hideEmotionLayout() {
        mElEmotion.setVisibility(View.GONE);
        mIvEmo.setImageResource(R.drawable.ic_cheat_emo);
    }

    /*private void showMoreLayout() {
        mLlMore.setVisibility(View.VISIBLE);
    }

    private void hideMoreLayout() {
        mLlMore.setVisibility(View.GONE);
    }*/

    private void closeBottomAndKeyboard() {
        mElEmotion.setVisibility(View.GONE);
        //mLlMore.setVisibility(View.GONE);
        if (mEmotionKeyboard != null) {
            mEmotionKeyboard.interceptBackPress();
        }
    }

    @Override
    public void onBackPressed() {
        if (mElEmotion.isShown()) {
            mEmotionKeyboard.interceptBackPress();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onEmojiSelected(String key) {
        Log.e("onEmojiSelected>>>", key);
        Log.e("CSDN_LQR", "onEmojiSelected : " + key);
    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
        Log.e("onStickerSelected>>>", categoryName + "..." + stickerName + "..." + stickerBitmapPath);
        Toast.makeText(getApplicationContext(), stickerBitmapPath, Toast.LENGTH_SHORT).show();
        Log.e("CSDN_LQR", "stickerBitmapPath : " + stickerBitmapPath);
    }

    public void initListener() {
        mElEmotion.setEmotionSelectedListener(this);
        mElEmotion.setEmotionAddVisiable(false);
        mElEmotion.setEmotionSettingVisiable(false);
        mElEmotion.setEmotionExtClickListener(new IEmotionExtClickListener() {
            @Override
            public void onEmotionAddClick(View view) {
                Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEmotionSettingClick(View view) {
                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
            }
        });
        mLlContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        closeBottomAndKeyboard();
                        break;
                }
                return false;
            }
        });

        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if (mEtContent.getText().toString().trim().length() > 0) {
                    mBtnSend.setVisibility(View.VISIBLE);
                    //mIvMore.setVisibility(View.GONE);
                } else {
                    mBtnSend.setVisibility(View.GONE);
                    //mIvMore.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mEtContent>>>", mEtContent.getText().toString());
                mEtContent.setText("");
                Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
