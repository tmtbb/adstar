package com.cloudTop.starshare.ui.main.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.app.CommentConfig;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.CircleFriendBean;
import com.cloudTop.starshare.been.StarExperienceBeen;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.CircleFriendAdapter;
import com.cloudTop.starshare.ui.main.contract.CircleContract;
import com.cloudTop.starshare.ui.main.presenter.CirclePresenter;
import com.cloudTop.starshare.ui.view.ShareControlerView;
import com.cloudTop.starshare.utils.KeyBordUtil;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.widget.emoji.EmotionLayout;
import com.cloudTop.starshare.widget.emoji.IEmotionSelectedListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.widget.emoji.EmotionKeyboard;
import com.cloudTop.starshare.widget.emoji.IEmotionExtClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.cloudTop.starshare.R.id.etContent;
import static com.cloudTop.starshare.R.id.llContent;


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
    private LinearLayout cv_info;
    private TextView close_info;
    private FrameLayout mFlEmotionView;
    private FrameLayout flEmotionView;
    private EmotionLayout mElEmotion;
    private EmotionKeyboard mEmotionKeyboard;
    private String code;
    private String starName;
    private String starUrl;
    private boolean isOne;
    private String describe="";
    private ShareControlerView controlerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_friend;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        code = intent.getStringExtra(AppConstant.STAR_CODE);
        starName = intent.getStringExtra(AppConstant.STAR_NAME);
        starUrl = intent.getStringExtra(AppConstant.STAR_HEAD_URL);
        isOne = intent.getBooleanExtra(AppConstant.IS_ONE, false);
        presenter = new CirclePresenter(this);
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(R.string.find_star);
        initEmoji();
        initAdapter();
        initListener();
        getData(false, 0, REQUEST_COUNT);
        getStarExperience();
    }

    private void initEmoji() {
        mLlContent = (LinearLayout) findViewById(llContent);
        ll_inputt = (LinearLayout) findViewById(R.id.ll_inputt);
        flEmotionView = (FrameLayout) findViewById(R.id.flEmotionView);
        mEtContent = (EditText) findViewById(etContent);
        mIvEmo = (ImageView) findViewById(R.id.ivEmo);
        mBtnSend = (Button) findViewById(R.id.btnSend);
        cv_info = (LinearLayout) findViewById(R.id.cv_info);
        close_info = (TextView) findViewById(R.id.close_info);
        mFlEmotionView = (FrameLayout) findViewById(R.id.flEmotionView);
        mElEmotion = (EmotionLayout) findViewById(R.id.elEmotion);
        mElEmotion.attachEditText(mEtContent);
        if (isOne){
            nt_title.setRightImagSrc(R.drawable.share);
            nt_title.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        }
        initEmotionKeyboard();
    }

    private void initAdapter() {
        circleFriendAdapter = new CircleFriendAdapter(this,rootView);
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
                if (ll_inputt.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                }
            }
        });

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
        if(addItem != null){
            CircleFriendBean.CircleListBean item = (CircleFriendBean.CircleListBean)circleFriendAdapter.getDatas().get(circlePosition);
            item.getComment_list().add(addItem);
            circleFriendAdapter.notifyDataSetChanged();
            ToastUtils.showShort("评论发布成功");
            //circleAdapter.notifyItemChanged(circlePosition+1);
        }
        //清空评论文本
        updateEditTextBodyVisible(View.GONE,null);
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
        if (View.VISIBLE == visibility) {
            ll_inputt.setVisibility(View.VISIBLE);
            mEtContent.requestFocus();
            //弹出键盘
            KeyBordUtil.showSoftInput(mEtContent.getContext(), mEtContent);
        } else if (View.GONE == visibility) {
            if (mFlEmotionView.getVisibility() == View.VISIBLE) {
                mFlEmotionView.setVisibility(View.GONE);
            }
            //隐藏键盘
            KeyBordUtil.hideSoftInput(mEtContent.getContext(), mEtContent);
            ll_inputt.setVisibility(View.GONE);
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
        if (isOne){
            NetworkAPIFactoryImpl.getInformationAPI().getAllCircleIsOne(start, count,code, new OnAPIListener<CircleFriendBean>() {
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
        }else {
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


    }
    private boolean isHint = false;
    public void showData() {
        if (list.size() == 0) {
            showErrorView(fl_pr, R.drawable.error_view_comment, "暂无相关数据");
            return;
        } else {
            closeErrorView();
        }
        if (!isHint){
            cv_info.setVisibility(View.VISIBLE);
            isHint = true ;
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
        /*mLlContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        closeBottomAndKeyboard();
                        break;
                }
                return false;
            }
        });*/

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    //发布评论
                    String content =  mEtContent.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                        ToastUtils.showShort("评论内容不能为空...");
                        return;
                    }
                    presenter.addComment(content, commentConfig);
                }
                mEtContent.setText("");
            }
        });
        close_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv_info.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if(ll_inputt != null && ll_inputt.getVisibility() == View.VISIBLE){
                //edittextbody.setVisibility(View.GONE);
                updateEditTextBodyVisible(View.GONE, null);
                return true;
            }
            if (controlerView!=null&&controlerView.isOpen() ==true) {
                controlerView.closeShareView();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void share() {
        controlerView = new ShareControlerView(this, mContext, umShareListener);
        String webUrl = "http://star.grwme.com/"+"?uid="+ SharePrefUtil.getInstance().getUserId()
                +"&star_code="+code;
        String title = starName+" 正在星享时光出售TA的时间";
        String text = "文本";
        controlerView.setText(text);
        controlerView.setWebUrl(webUrl);
        controlerView.setDescribe(describe);
        controlerView.setTitle(title);
        controlerView.setImageurl(starUrl);
        controlerView.setStarName(starName);
        controlerView.setStarWork("明星");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    private void getStarExperience() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarExperience(code, new OnAPIListener<StarExperienceBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarExperienceBeen o) {
                if (o.getResult() == 1 && o.getList() != null) {
                    describe = o.getList().get(0).getExperience().toString();
                }
            }
        });
    }
}
