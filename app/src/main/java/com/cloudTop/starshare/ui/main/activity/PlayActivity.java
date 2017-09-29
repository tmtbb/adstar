package com.cloudTop.starshare.ui.main.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.widget.customVideoPlayer.CustomUIVideo;
import com.cloudTop.starshare.widget.customVideoPlayer.OnTransitionListener;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;


/**
 * 单独的视频播放页面
 * Created by shuyu on 2016/11/11.
 */
public class PlayActivity extends Activity {

    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";


    CustomUIVideo videoPlayer;

//    OrientationUtils orientationUtils;

    private boolean isTransition;

    private Transition transition;
    private String playUrl;
    private String playStarUrl;
    private String StarVideoPic;
    private String userHeadUrl;
    private String userName;
    private String userQuestion;
    private String star_code;
    private boolean haveUserPlay = false ;
    private boolean haveStarPlay = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);


//        ButterKnife.bind(this);
        videoPlayer = (CustomUIVideo) findViewById(R.id.video_player);
        videoPlayer.setOnCallBack(new CustomUIVideo.CallBack() {
            @Override
            public void onClickBack() {
                finish();
            }

            @Override
            public void onCompletion() {
                if (haveUserPlay&&haveStarPlay){
                    videoPlayer.setHaveUserVdio(true,true,true);
                    videoPlayer.setUp(playStarUrl, true, "");
                    videoPlayer.startPlayLogic();
                    haveUserPlay=false;
                    haveStarPlay=false;
                }else {
                    finish();
                }
            }

            @Override
            public void onAskQuestion() {
                Intent intent = new Intent(PlayActivity.this, AskToAnswersActivity.class);
                intent.putExtra("star_code",star_code);
                startActivity(intent);
                finish();
            }
        });
//        ImageView question = (ImageView)videoPlayer.findViewById(R.id.iv_question);
//        final LinearLayout rl_container = (LinearLayout)videoPlayer.findViewById(R.id.layout_bottom);

        isTransition = getIntent().getBooleanExtra(TRANSITION, false);
        playUrl = getIntent().getStringExtra("playUserUrl");
        playStarUrl = getIntent().getStringExtra("playStarUrl");
        StarVideoPic = getIntent().getStringExtra("StarVideoPic");
        userHeadUrl = getIntent().getStringExtra("userHeadUrl");
        userName = getIntent().getStringExtra("userName");
        userQuestion = getIntent().getStringExtra("userQuestion");
        star_code = getIntent().getStringExtra("star_code");
        haveUserPlay = getIntent().getBooleanExtra("haveUserPlay",false);
        haveStarPlay = getIntent().getBooleanExtra("haveStarPlay",false);
        init();
    }

    private void init() {
        if (haveUserPlay&&haveStarPlay){
            videoPlayer.setHaveUserVdio(true,true,false);
            videoPlayer.setUp(playUrl, true, "");
        }else if (haveUserPlay&&!haveStarPlay){
            videoPlayer.setHaveUserVdio(true,false,false);
            videoPlayer.setUp(playUrl, true, "");
        }else if (!haveUserPlay&&haveStarPlay){
            videoPlayer.setHaveUserVdio(false,true,false);
            videoPlayer.setUp(playStarUrl, true, "");
        }
        videoPlayer.setQuestionInfo(userHeadUrl,userName,userQuestion);
        //增加封面
        ImageView imageView = new ImageView(this);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoaderUtils.displayWithPreviewImg(this,imageView,StarVideoPic,R.drawable.bg_wite);
        videoPlayer.setThumbImageView(imageView);

        //增加title
//        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
//        videoPlayer.getTitleTextView().setText("测试视频");
        //videoPlayer.setShowPauseCover(false);

        //videoPlayer.setSpeed(2f);

        // TODO: 2017/8/23
        //设置返回键
//        videoPlayer.getBackButton().setVisibility(View.VISIBLE);

        //设置旋转
//        orientationUtils = new OrientationUtils(this, videoPlayer);

        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                orientationUtils.resolveByClick();
//            }
//        });

        //videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
        //videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
        //getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
        //videoPlayer.setDialogProgressColor(getResources().getColor(R.color.colorAccent), -11);


        // TODO: 2017/8/23  
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(false);

        //设置返回按键功能
//        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        //过渡动画
        initTransition();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (orientationUtils != null)
//            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
//        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//
//            videoPlayer.getFullscreenButton().performClick();
//            return;
//        }
        //释放所有
//        videoPlayer.setStandardVideoAllCallBack(null);
        GSYVideoPlayer.releaseAllVideos();
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onBackPressed();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }, 500);
        }
    }


    private void initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(videoPlayer, IMG_TRANSITION);
            addTransitionListener();
            startPostponedEnterTransition();
        } else {
            videoPlayer.startPlayLogic();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            transition.addListener(new OnTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    videoPlayer.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }
        return false;
    }

}
