package com.yundian.star.ui.main.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.utils.DisplayUtil;
import com.yundian.star.widget.BiliDanmukuParser;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;

/**
 * Created by Administrator on 2017/7/18.
 * 明星时间交易
 */

public class StarTimeDealActivity extends BaseActivity {

    private IDanmakuView mDanmakuView;
    private DanmakuContext mDanmakuContext;
    private BaseDanmakuParser mParser;
    private int widthPixels;
    private int heightPixels;
    private ArrayList<String> list;
    private MyHandler myHandler;
    private int temporary = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_time_deal;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initFindById();
        getKuanGao();
        getData();
    }

    private void getData() {
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("i");
        }
        myHandler.sendEmptyMessage(MyHandler.GRT_DATA);
    }

    private void initFindById() {
        mDanmakuView = (IDanmakuView) findViewById(R.id.sv_danmaku);
        mDanmakuContext = DanmakuContext.create();
        if (myHandler == null) {
            myHandler = new MyHandler(this);
        }
        initDanmakuView();
    }

    private void initDanmakuView() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_SPECIAL, true);
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), new BaseCacheStuffer.Proxy() {
                    @Override
                    public void prepareDrawing(BaseDanmaku danmaku, boolean fromWorkerThread) {

                    }

                    @Override
                    public void releaseResource(BaseDanmaku danmaku) {

                    }
                }) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair).setDanmakuMargin(40);
        if (mDanmakuView != null) {
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
//                    Log.d("DFM", "danmakuShown(): text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });
            mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {

                @Override
                public boolean onDanmakuClick(IDanmakus danmakus) {
                    Log.d("DFM", "onDanmakuClick: danmakus size:" + danmakus.size());
                    BaseDanmaku latest = danmakus.last();
                    if (null != latest) {
                        Log.d("DFM", "onDanmakuClick: text of latest danmaku:" + latest.text);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onDanmakuLongClick(IDanmakus danmakus) {
                    return false;
                }

                @Override
                public boolean onViewClick(IDanmakuView view) {
                    return false;
                }
            });
            mDanmakuView.prepare(mParser, mDanmakuContext);


            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }

    private void refreshAnim() {
        if (list.size() != 0 && list.get(temporary) != null) {
            addDanmaKuShowTextAndImage(list.get(temporary), false);
            if (temporary < list.size() - 1 && myHandler != null) {
                temporary++;
                myHandler.sendEmptyMessageDelayed(MyHandler.GRT_DATA, 1000);
            }
        }
        ;
    }


    private void addDanmaKuShowTextAndImage(String content, boolean islive) {
        //Math.floor(Math.random()*(max-min+1)+min);
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SPECIAL, mDanmakuContext);
        float floor = (float) Math.floor(Math.random() * (6 + 4 + 1) - 3);
        float floorY = (float) Math.floor(Math.random() * (10 - 1 + 1) + 1);
        float dH = floor * 100 * display;
        float dY = floorY * 20 * display;
        Log.e("floor:", floor + "");
        mDanmakuContext.mDanmakuFactory.fillTranslationData(danmaku, widthPixels + dH + dY,
                0, -1 * (widthPixels + dH + dY), 2 * (widthPixels + dH + dY), (long) (Math.sqrt(Math.pow(widthPixels + dH + dY, 2.0)) * 4 + dH), 0, 1, 1);
        Log.e("(long)判断:", widthPixels + ".." + heightPixels);
        Log.e("(long)1:", (float) (-0.8 * (widthPixels + dH + dY)) + "");
        Log.e("(long)2:", (float) (2 * (widthPixels + dH + dY)) + "");
        Log.e("(long)3:", (long) Math.sqrt(Math.pow(widthPixels + dH + dY, 2.0)) * 3 + "");
        Log.e("(long)4:", (long) (7 * widthPixels + (floor > 0 ? floor * (widthPixels + dH + dY) : floor * 100)) + "");
        //(long) (7*widthPixels + (floor > 0 ? floor * (widthPixels + dH + dY): floor * 100))

        mDanmakuContext.mDanmakuFactory.fillAlphaData(danmaku, AlphaValue.MAX * 1, AlphaValue.MAX * 1, 1000 * 600);
        mDanmakuContext.setMaximumVisibleSizeInScreen(30);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }

        danmaku.rotationY = -1;
        danmaku.rotationZ = -45;
        Drawable drawable = getResources().getDrawable(R.drawable.ic_home_normal);
        drawable.setBounds(0, 0, DisplayUtil.dip2px(20), DisplayUtil.dip2px(20));
        SpannableStringBuilder spannable = createSpannable(drawable);
        danmaku.text = spannable;
        danmaku.padding = 5;
        danmaku.setDuration(new Duration(120000));
        danmaku.priority = 1;  // 一定会显示, 一般用于本机发送的弹幕
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime());
        danmaku.textSize = DisplayUtil.sp2px(20);
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = 0; // 重要：如果有图文混排，最好不要设置描边(设textShadowColor=0)，否则会进行两次复杂的绘制导致运行效率降低
        danmaku.underlineColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);
    }

    private SpannableStringBuilder createSpannable(Drawable drawable) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ImageSpan span = new ImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("图文混排");
        spannableStringBuilder.setSpan(new TextAppearanceSpan(this, R.style.style_pingjie), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }

    private float display;

    private void getKuanGao() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        display = widthPixels / 720;
        Log.e("widthPixels", widthPixels + "...heightPixels" + heightPixels);
    }


    private static class MyHandler extends Handler {
        final private static int GRT_DATA = 111;
        private final WeakReference<StarTimeDealActivity> mActivity;

        public MyHandler(StarTimeDealActivity dealActivity) {
            mActivity = new WeakReference<StarTimeDealActivity>(dealActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            StarTimeDealActivity activity = mActivity.get();
            if (activity != null && activity.isFinishing() == false) {
                switch (msg.what) {
                    case GRT_DATA:
                        activity.refreshAnim();
                        break;
                }
            }
        }
    }

    @Override
    protected void onPause() {
        Log.e("onPause", "onPause");
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        Log.e("onResume", "onResume");
        super.onResume();
    }

    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
}
