package com.cloudTop.starshare.widget.customVideoPlayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import moe.codeest.enviews.ENDownloadView;

/**
 * Created by shuyu on 2016/12/7.
 * 注意
 * 这个播放器的demo配置切换到全屏播放器
 * 这只是单纯的作为全屏播放显示，如果需要做大小屏幕切换，请记得在这里耶设置上视频全屏的需要的自定义配置
 */

public class CustomUIVideo extends StandardGSYVideoPlayer {

    private ProgressBar topProgress;
    private ProgressBar topStarProgress;
    private boolean haveUserVdio = false ;
    private boolean isNextVdio = false ;
    private boolean haveStarVdieo = false ;
    private boolean haveOnclickQuetion = false;

    private ImageView close;
    private RelativeLayout rl_question_info;
    private ImageView iv_head;
    private TextView tv_name;
    private TextView tv_question;
    private TextView tv_to_ask;

    public CustomUIVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public CustomUIVideo(Context context) {
        super(context);
    }

    public CustomUIVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_customed;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        final ImageView question = (ImageView) findViewById(R.id.iv_question);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_to_ask = (TextView) findViewById(R.id.tv_to_ask);
        tv_question = (TextView) findViewById(R.id.tv_question);
        topProgress = (ProgressBar) findViewById(R.id.top_progressbar);
        rl_question_info = (RelativeLayout) findViewById(R.id.rl_question_info);
        topStarProgress = (ProgressBar) findViewById(R.id.top_star_progressbar);
        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                getBackButton().performClick();
                callBack.onClickBack();

            }
        });
        question.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveOnclickQuetion){
                    rl_question_info.setVisibility(INVISIBLE);
                    haveOnclickQuetion=true;
                }else {
                    rl_question_info.setVisibility(VISIBLE);
                    haveOnclickQuetion=false;
                }
            }
        });
        tv_to_ask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onAskQuestion();
            }
        });


//        final LinearLayout anchor = (LinearLayout)findViewById(R.id.anchor);


//        question.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CustomPopupWindow popupWindow = new CustomPopupWindow.Builder()
//                        .setContext(mContext) //设置 context
//                        .setContentView(R.layout.content_ask) //设置布局文件
//                        .setwidth(LinearLayout.LayoutParams.WRAP_CONTENT) //设置宽度，由于我已经在布局写好，这里就用 wrap_content就好了
//                        .setheight(LinearLayout.LayoutParams.WRAP_CONTENT) //设置高度
//                        .setFouse(true)  //设置popupwindow 是否可以获取焦点
//                        .setOutSideCancel(true) //设置点击外部取消
//                        .setAnimationStyle(R.style.popup_anim_style) //设置popupwindow动画
//                        .builder() //
//                        .showAtLocation(question); //设置popupwindow居中显示
//            }
//        });
    }

    //    @Override
//    protected void hideAllWidget() {
//        setViewShowState(mBottomContainer, INVISIBLE);
//        setViewShowState(mTopContainer, INVISIBLE);
//        setViewShowState(mBottomProgressBar, VISIBLE);
//        setViewShowState(mStartButton, INVISIBLE);
//        setViewShowState(mStartButton, INVISIBLE);
//        setViewShowState(close, INVISIBLE);
//    }
    @Override
    protected void changeUiToPlayingClear() {
        Debuger.printfLog("changeUiToPlayingClear");
//        changeUiToClear();
        setViewShowState(mBottomProgressBar, VISIBLE);
    }
    @Override
    protected void changeUiToPauseClear() {
        Debuger.printfLog("changeUiToPauseClear");
//        changeUiToClear();
        setViewShowState(mBottomProgressBar, VISIBLE);
        updatePauseCover();
    }

    @Override
    protected void changeUiToClear() {
        Debuger.printfLog("changeUiToClear");

        setViewShowState(mTopContainer, INVISIBLE);
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mLoadingProgressBar, INVISIBLE);
        setViewShowState(mThumbImageViewLayout, INVISIBLE);
        setViewShowState(mBottomProgressBar, INVISIBLE);
        setViewShowState(close, INVISIBLE);

        setViewShowState(mLockScreen, GONE);

        if (mLoadingProgressBar instanceof ENDownloadView) {
            ((ENDownloadView) mLoadingProgressBar).reset();
        }
    }

    @Override
    protected void changeUiToPlayingShow() {
        Debuger.printfLog("changeUiToPlayingShow");

        setViewShowState(mTopContainer, VISIBLE);
        setViewShowState(mBottomContainer, VISIBLE);
        setViewShowState(mStartButton, VISIBLE);
        setViewShowState(close, VISIBLE);

        setViewShowState(mLoadingProgressBar, INVISIBLE);
        setViewShowState(mThumbImageViewLayout, INVISIBLE);
        setViewShowState(mBottomProgressBar, INVISIBLE);
        setViewShowState(mLockScreen, (mIfCurrentIsFullscreen && mNeedLockFull) ? VISIBLE : GONE);

        if (mLoadingProgressBar instanceof ENDownloadView) {
            ((ENDownloadView) mLoadingProgressBar).reset();
        }
        updateStartImage();
    }

//    @Override
//    protected void changeUiToNormal() {
//        Debuger.printfLog("changeUiToNormal");
//
//        setViewShowState(mTopContainer, VISIBLE);
//        setViewShowState(mBottomContainer, INVISIBLE);
//        setViewShowState(mStartButton, VISIBLE);
//        setViewShowState(mLoadingProgressBar, INVISIBLE);
//        setViewShowState(mThumbImageViewLayout, VISIBLE);
//        setViewShowState(mBottomProgressBar, INVISIBLE);
//        setViewShowState(mLockScreen, (mIfCurrentIsFullscreen && mNeedLockFull) ? VISIBLE : GONE);
//
//        updateStartImage();
//        if (mLoadingProgressBar instanceof ENDownloadView) {
//            ((ENDownloadView) mLoadingProgressBar).reset();
//        }
//    }

    @Override
    protected void setProgressAndTime(int progress, int secProgress, int currentTime, int totalTime) {
        LogUtils.logd("progress:" + progress);
        if (secProgress > 94) secProgress = 100;
        if (haveUserVdio==true&&isNextVdio==false&&haveStarVdieo==false&&topStarProgress != null){
            if (progress != 0) {
                topStarProgress.setProgress(progress);
            }
            if (secProgress != 0 && !mCacheFile){
                topStarProgress.setSecondaryProgress(secProgress);
            }
            LogUtils.loge("progress_1"+".."+progress+".."+secProgress);
        }else if (haveUserVdio==true&&isNextVdio==false&&haveStarVdieo==true&&topProgress != null){
            if (progress != 0) {
                topProgress.setProgress(progress);
            }
            if (secProgress != 0 && !mCacheFile){
                topProgress.setSecondaryProgress(secProgress);
            }
            LogUtils.loge("progress_2"+".."+progress+".."+secProgress);
        }else if (haveUserVdio==true&&isNextVdio==true&&haveStarVdieo==true&&topStarProgress != null){
            if (progress != 0) {
                topStarProgress.setProgress(progress);
            }
            if (secProgress != 0 && !mCacheFile){
                topStarProgress.setSecondaryProgress(secProgress);
            }
            LogUtils.loge("progress_3"+".."+progress+".."+secProgress);
        }else if (haveUserVdio==false&&isNextVdio==false&&haveStarVdieo==true&&topStarProgress != null){
            if (progress != 0) {
                topStarProgress.setProgress(progress);
            }
            if (secProgress != 0 && !mCacheFile){
                topStarProgress.setSecondaryProgress(secProgress);
            }
            LogUtils.loge("progress_4"+".."+progress+".."+secProgress);
        }
    }
//    @Override
//    protected void setTextAndProgress(int secProgress) {
//        int position = this.getCurrentPositionWhenPlaying();
//        int duration = this.getDuration();
//
////        int progress = position * 100 / (duration == 0?1:duration);
//        LogUtils.logd("当前状态:"+mCurrentState);
//        LogUtils.logd("position:"+position+"duration:"+duration);
//        if(mCurrentState == CURRENT_STATE_AUTO_COMPLETE){
//            this.setProgressAndTime(100, secProgress, position, duration);
//            LogUtils.logd("进来了吗");
//        }else{
//            super.setTextAndProgress(secProgress);
//        }
//    }

    @Override
    protected void updateStartImage() {
        if (mStartButton instanceof ImageView) {
            ImageView imageView = (ImageView) mStartButton;
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                imageView.setImageResource(R.drawable.video_click_pause_selector);
            } else if (mCurrentState == CURRENT_STATE_ERROR) {
                imageView.setImageResource(R.drawable.video_click_play_selector);
            } else {
                imageView.setImageResource(R.drawable.video_click_play_selector);
            }
        }
    }

    @Override
    public void onAutoCompletion() {
        int position = this.getCurrentPositionWhenPlaying();
        int duration = this.getDuration();
        LogUtils.logd("我播完了");
        setProgressAndTime(100, 0, position, duration);
        super.onAutoCompletion();
        callBack.onCompletion();
    }


    CallBack callBack;

    public interface CallBack {
        void onClickBack();
        void onCompletion();
        void onAskQuestion();
    }

    public void setOnCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void startDismissControlViewTimer() {
        cancelDismissControlViewTimer();
//        mDismissControlViewTimer = new Timer();
//        mDismissControlViewTimerTask = new DismissControlViewTimerTask();
//        mDismissControlViewTimer.schedule(mDismissControlViewTimerTask, mDismissControlTime);
    }
    public void setHaveUserVdio(boolean haveUserVdieo,boolean haveStarVdieo,boolean isNextVdio){
        this.haveUserVdio = haveUserVdieo ;
        this.isNextVdio = isNextVdio ;
        this.haveStarVdieo = haveStarVdieo ;
        if (haveUserVdieo==false&&haveStarVdieo==true&&isNextVdio==false){
            topProgress.setVisibility(GONE);
        }else if (haveUserVdieo==true&&haveStarVdieo==false&&isNextVdio==false){
            topProgress.setVisibility(GONE);
        }
    }
    public void setQuestionInfo(String userHeadUrl,String userName,String userQuestion){
        ImageLoaderUtils.displaySmallPhotoRound(mContext,iv_head,userHeadUrl);
        tv_name.setText(userName);
        tv_question.setText(userQuestion);
    }

}
