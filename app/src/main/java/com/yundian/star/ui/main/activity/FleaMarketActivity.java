package com.yundian.star.ui.main.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.netease.nimlib.jsbridge.util.LogUtil;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.utils.DisplayUtil;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.widget.BiliDanmukuParser;
import com.yundian.star.widget.CenteredImageSpan;
import com.yundian.star.widget.CircleDrawable;
import com.yundian.star.widget.NormalTitleBar;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
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
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by Administrator on 2017/7/20.
 * 跳蚤市场
 */

public class FleaMarketActivity extends BaseActivity {

    private DanmakuView mDanmakuView;
    private LRecyclerView lrv;
    private DanmakuContext mDanmakuContext;
    private BaseDanmakuParser mParser;
    private int widthPixels;
    private int heightPixels;
    private ArrayList<String> list;
    private MyHandler myHandler;
    private int temporary = 0;
    private int secondTime = 0;
    private boolean startSunTime = false;
    private int layoutLeft;
    private int layoutRight;
    private int layoutHeight;
    private int layoutBottom;
    private View lint;

    @Override
    public int getLayoutId() {
        return R.layout.activity_flea_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initFindById();
        getKuanGao();
        setSize();
        initDanmakuView();
        initData();
    }

    private void initData() {
        if (myHandler == null) {
            myHandler = new MyHandler(this);
        }
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("i");
        }
        myHandler.sendEmptyMessage(myHandler.GRT_DATA);
    }

    private void initFindById() {
        NormalTitleBar nt_title = (NormalTitleBar) findViewById(R.id.nt_title);
        mDanmakuView = (DanmakuView) findViewById(R.id.sv_danmaku);
        lint = (View) findViewById(R.id.lint);
        lrv = (LRecyclerView) findViewById(R.id.lrv);
        nt_title.setTitleText(R.string.flea_market);
        nt_title.setBackVisibility(true);
        mDanmakuContext = DanmakuContext.create();
    }

    private static class MyHandler extends Handler {
        final private static int GRT_DATA = 111;
        private final WeakReference<FleaMarketActivity> mActivity;

        public MyHandler(FleaMarketActivity dealActivity) {
            mActivity = new WeakReference<FleaMarketActivity>(dealActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            FleaMarketActivity activity = mActivity.get();
            if (activity != null && activity.isFinishing() == false) {
                switch (msg.what) {
                    case GRT_DATA:
                        activity.refreshAnim();
                        break;
                }
            }
        }
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
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_NONE, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(0)
                .setScaleTextSize(1.2f).setDanmakuTransparency(0.8f).setSpecialDanmakuVisibility(true)
                .setCacheStuffer(new SpannedCacheStuffer(), new BaseCacheStuffer.Proxy() {
                    @Override
                    public void prepareDrawing(BaseDanmaku danmaku, boolean fromWorkerThread) {

                    }

                    @Override
                    public void releaseResource(BaseDanmaku danmaku) {

                    }
                }) // 图文混排使用SpannedCacheStuffer
                .setCacheStuffer(new BackgroundCacheStuffer(), mCacheStufferAdapter)  // 绘制背景使用BackgroundCacheStuffer
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
                myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1 * 500);
            }
        }
    }


    private void addDanmaKuShowTextAndImage(String content, final boolean islive) {
        //Math.floor(Math.random()*(max-min+1)+min);
        final BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SPECIAL, mDanmakuContext);
        float floor = (float) Math.floor(Math.random() * (limt - 1 - 0 + 1) + 0);
        float floorY = (float) Math.floor(Math.random() * (5 - 0 + 1) + 0);
        float dH = floor * DisplayUtil.dip2px(30);
        float dY = floorY * DisplayUtil.dip2px(10);
        float d = (dH + dY);
        float dDisplayY = display>0.7f?2:3.5f;
        float dDisplayT = display>0.7f?9.4f:8.2f;
        Log.e("floor:", floor + "");
        Log.e("floorY:", floorY + "");
        mDanmakuContext.mDanmakuFactory.fillTranslationData(danmaku, widthPixels,
                d, (float) (-widthPixels*dDisplayY), d, (long) (widthPixels*dDisplayT + dH + dY), 0, 1, 1);
        Log.e("(limt)判断:", limt + "");
        Log.e("(layoutHeight+dH+dY)1:", layoutHeight + dH + dY + "");
        Log.e("display:", display + "");
        // Log.e("(long)3:", (long) Math.sqrt(Math.pow(d, 2.0)) * 3 + "");
        Log.e("-3*widthPixels*display:", -3 * widthPixels*display+ "");
        //(long) (7*widthPixels + (floor > 0 ? floor * (widthPixels + dH + dY): floor * 100))
        //mDanmakuContext.mDanmakuFactory.fillAlphaData(danmaku, AlphaValue.MAX * 1, AlphaValue.MAX * 0, 1000 * 30);
        mDanmakuContext.setMaximumVisibleSizeInScreen(30);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        // Drawable drawable = getResources().getDrawable(R.drawable.ic_home_normal);
        //drawable.setBounds(0, 0, DisplayUtil.dip2px(40), DisplayUtil.dip2px(40));
        String url = "http://tva2.sinaimg.cn/crop.0.1.510.510.180/48e837eejw8ex30o7eoylj20e60e8wet.jpg";
        //ImageLoaderUtils.displaySmallPhoto();
        Glide.with(mContext).load(url)
                .asBitmap()
                .placeholder(R.drawable.user_default_head)
                .error(R.drawable.user_default_head)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bitmap = ImageLoaderUtils.getDefaultBitmap(resource, 45, BITMAP_WIDTH, BITMAP_HEIGHT);
                        CircleDrawable drawable = new CircleDrawable(bitmap);
                        //BitmapDrawable drawable=new BitmapDrawable(resource);
                        //Drawable drawable = getResources().getDrawable(R.drawable.ic_home_normal);
                        LogUtil.e("danmaku" + danmaku.getLeft() + "");
                        drawable.setBounds(0, 0, BITMAP_WIDTH, BITMAP_HEIGHT);
                        SpannableStringBuilder spannable = createSpannable(drawable);
                        danmaku.text = spannable;
                        danmaku.padding = DANMU_PADDING;
                        danmaku.setDuration(new Duration(1000 * 60*6));
                        danmaku.priority = 1;  // 一定会显示, 一般用于本机发送的弹幕
                        danmaku.isLive = islive;
                        danmaku.setTime(mDanmakuView.getCurrentTime());
                        danmaku.textSize = DANMU_TEXT_SIZE;
                        danmaku.textColor = 0xfafafafa;
                        danmaku.underlineColor = 0;
                        danmaku.textShadowColor = 0; // 重要：如果有图文混排，最好不要设置描边(设textShadowColor=0)，否则会进行两次复杂的绘制导致运行效率降低
                        LogUtil.e("mDanmakuView.addDanmaku(danmaku);:");
                        mDanmakuView.addDanmaku(danmaku);
                    }
                });
    }

    private SpannableStringBuilder createSpannable(Drawable drawable) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        //CenteredImageSpan span = new CenteredImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        CenteredImageSpan span = new CenteredImageSpan(drawable);
        //ImageSpan span = new ImageSpan(resource);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("   图文混排                   ");
        //spannableStringBuilder.setSpan(new TextAppearanceSpan(this, R.style.style_pingjie), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#fafafa")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }

    private float display;
    private int limt;

    private void getKuanGao() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        lint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutHeight = lint.getHeight();
                layoutBottom = lint.getBottom();
                limt = (layoutBottom - DisplayUtil.dip2px(48)) / DisplayUtil.dip2px(30);
                display = 720f/widthPixels;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    lint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    lint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                Log.e("layout.....", "..." + layoutHeight + "..." + layoutBottom+"..."+"widthPixels"+widthPixels+"..."+display);
            }
        });
        Log.e("widthPixels", widthPixels + "...heightPixels" + heightPixels);

    }


    //这两个用来控制两行弹幕之间的间距
    private int BITMAP_WIDTH = 26;//头像的大小
    private int BITMAP_HEIGHT = 26;
    private float DANMU_TEXT_SIZE = 14f;//弹幕字体的大小
    private int DANMU_PADDING = 10;
    private int DANMU_PADDING_INNER = 7;
    private int DANMU_RADIUS = 15;//圆角半径
    private int VIEW_HEIGHT = 30;//view高
    private int random = 10;//view高

    /**
     * 绘制背景(自定义弹幕样式)
     */
    private class BackgroundCacheStuffer extends SpannedCacheStuffer {
        // 通过扩展SimpleTextCacheStuffer或SpannedCacheStuffer个性化你的弹幕样式
        Paint paint = new Paint();
        int[] colors = {0xFFE69A17, 0xFFFF3052, 0xFF4FBEDC, 0xFFAA3FBD};

        @Override
        public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
//            danmaku.padding = 20;  // 在背景绘制模式下增加padding
            super.measure(danmaku, paint, fromWorkerThread);
        }

        @Override
        public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
            paint.setAntiAlias(true);
            paint.setColor(getResources().getColor(R.color.color_7f000000));
            if (danmaku.isGuest) {//如果是赞 就不要设置背景
                paint.setColor(Color.TRANSPARENT);
            }
            canvas.drawRoundRect(new RectF(left + DANMU_PADDING_INNER, top + DANMU_PADDING_INNER
                            , left + danmaku.paintWidth - DANMU_PADDING_INNER + 6,
                            top + danmaku.paintHeight - DANMU_PADDING_INNER + 6),//+6 主要是底部被截得太厉害了，+6是增加padding的效果
                    DANMU_RADIUS, DANMU_RADIUS, paint);
        }

        @Override
        public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
            // 禁用描边绘制
        }

    }

    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {

        @Override
        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
//            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
//            }
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
            if (danmaku.text instanceof Spanned) {
                danmaku.text = "";
            }
        }
    };

    /**
     * 对数值进行转换，适配手机，必须在初始化之前，否则有些数据不会起作用
     */
    private void setSize() {
        BITMAP_WIDTH = DisplayUtil.dp2pxConvertInt(BITMAP_HEIGHT);
        BITMAP_HEIGHT = DisplayUtil.dp2pxConvertInt(BITMAP_HEIGHT);
//        EMOJI_SIZE = DpOrSp2PxUtil.dp2pxConvertInt(context, EMOJI_SIZE);
        DANMU_PADDING = DisplayUtil.dp2pxConvertInt(DANMU_PADDING);
        DANMU_PADDING_INNER = DisplayUtil.dp2pxConvertInt(DANMU_PADDING_INNER);
        DANMU_RADIUS = DisplayUtil.dp2pxConvertInt(DANMU_RADIUS);
        VIEW_HEIGHT = DisplayUtil.dp2pxConvertInt(VIEW_HEIGHT);
        DANMU_TEXT_SIZE = DisplayUtil.sp2px(DANMU_TEXT_SIZE);
        random = DisplayUtil.sp2px(random);
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

    private boolean isMusre = false;
    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
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
