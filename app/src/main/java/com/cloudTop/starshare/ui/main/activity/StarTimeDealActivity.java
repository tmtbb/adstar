package com.cloudTop.starshare.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.NowPriceBean;
import com.cloudTop.starshare.been.StarDanMuNewInfo;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.widget.CenteredImageSpan;
import com.netease.nimlib.jsbridge.util.LogUtil;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.been.TradingStatusBeen;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.CheckLoginUtil;
import com.cloudTop.starshare.utils.DisplayUtil;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.TimeUtil;
import com.cloudTop.starshare.widget.BiliDanmukuParser;
import com.cloudTop.starshare.widget.CircleDrawable;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

import static com.cloudTop.starshare.R.id.tv_preice;

/**
 * #75
 * #76
 * Created by Administrator on 2017/7/18.
 * 明星时间交易
 */

public class StarTimeDealActivity extends BaseActivity implements View.OnClickListener {

    private int widthPixels;
    private int heightPixels;
    private TextView tv_back;
    private TextView tv_title;
    private TextView tv_right;
    private TextView tv_transfer;
    private TextView tv_ask_to_buy;
    private IDanmakuView mDanmakuView;
    private BaseDanmakuParser mParser;
    private DanmakuContext mDanmakuContext;
    private static MyHandler myHandler;
    private int temporary = 0;
    private int[] random_bg = {
            R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5
            , R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10
            , R.drawable.bg_11
    };
    private ImageView qiu;
    private TextView tv_info;
    private TextView tv_time;
    private TextView tv_price;
    private StarListReturnBean.SymbolInfoBean symbolInfoBean;
    private ArrayList<StarDanMuNewInfo.PositionsListBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_time_deal;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        symbolInfoBean = getIntent().getParcelableExtra(AppConstant.SYMBOL_INFO_BEAN);
        getData();
        getKuanGao();
        initFindById();
        setSize();
        getNowPrice();
    }
    //private boolean isFirest = true ;
    private void getNowPrice() {
        NetworkAPIFactoryImpl.getInformationAPI().getNowPrice(SharePrefUtil.getInstance().getUserId(), SharePrefUtil.getInstance().getToken(), symbolInfoBean.getSymbol(), 5, new OnAPIListener<NowPriceBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(NowPriceBean nowPriceBean) {
                tv_price.setText(String.format("%.2f", nowPriceBean.getCurrentPrice()));
                tv_info.setText(nowPriceBean.getWork());
                LogUtils.loge("实时报价接口。。" + nowPriceBean.toString());
            }
        });
    }

    private void getData() {
        list = new ArrayList<>();
        getDanMaku();
        if (SharePrefUtil.getInstance().getStatusNav_5()==0){
            SharePrefUtil.getInstance().setStatusNav_5(1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPopupWindow();
                }
            },500);
        }
    }

    private void initFindById() {
        mDanmakuView = (IDanmakuView) findViewById(R.id.sv_danmaku);
        FrameLayout fl_cont = (FrameLayout) findViewById(R.id.fl_cont);
        ViewGroup.LayoutParams params = fl_cont.getLayoutParams();
        params.height = 2 * heightPixels;
        fl_cont.setLayoutParams(params);
        qiu = (ImageView) findViewById(R.id.qiu);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_transfer = (TextView) findViewById(R.id.tv_transfer);
        tv_ask_to_buy = (TextView) findViewById(R.id.tv_ask_to_buy);
        tv_title.setText(getString(R.string.time_deal));
        tv_right.setText(getString(R.string.flea_market));
        ImageView img_head = (ImageView) findViewById(R.id.img_head);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_price = (TextView) findViewById(tv_preice);
        tv_time = (TextView) findViewById(R.id.tv_time);
        ImageLoaderUtils.displaySmallPhoto(mContext, img_head, symbolInfoBean.getPic());
        tv_name.setText(symbolInfoBean.getName());
        RelativeLayout rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        int i = new Random().nextInt(11);
        rl_bg.setBackgroundResource(random_bg[i]);
        img_head.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_transfer.setOnClickListener(this);
        tv_ask_to_buy.setOnClickListener(this);
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

        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.6f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                // .setMarginTop(40)
                .setCacheStuffer(new BackgroundCacheStuffer(), mCacheStufferAdapter)  // 绘制背景使用BackgroundCacheStuffer
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
        if (tv_time != null && secondTime >= 0 && myHandler != null && startSunTime) {
            tv_time.setText(TimeUtil.calculatTime(secondTime));
            secondTime--;
            if (myHandler != null) {
                myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1000);
            }
        } else if (tv_time != null && secondTime < 0) {
            tv_time.setText("未开始");
        }
        if (list.size() != 0 && list.get(temporary) != null) {
            addDanmaKuShowTextAndImage(list.get(temporary), false);
            if (temporary==list.size() - 1){
                temporary=0;
            }
            if (temporary < list.size() - 1 && myHandler != null) {
                temporary++;
            }
        }
    }


    private void addDanmaKuShowTextAndImage(final StarDanMuNewInfo.PositionsListBean bean, final boolean islive) {
        //Math.floor(Math.random()*(max-min+1)+min);
        final BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SPECIAL, mDanmakuContext);
        danmaku.setDuration(new Duration(60 * 1000));
        float floor = (float) Math.floor(Math.random() * (6 + 4 + 1) - 3);
        float floorY = (float) Math.floor(Math.random() * (10 - 1 + 1) + 1);
        float dH = floor * 100 * display;
        float dY = floorY * 20 * display;
        float d = (widthPixels + dH + dY);
        long time = (long) (d * (display < 1.6 ? 24 : 12));
        Log.e("floor:", floor + "");
        mDanmakuContext.mDanmakuFactory.fillTranslationData(danmaku, 0,
                0, 0, 0, (long) (d * 24 / display), 0, 1, 1);

        float[][] points = new float[2][2];
        points[0][0] = d;
        points[0][1] = 0;
        points[1][0] = (float) (-2 * d);
        points[1][1] = (float) (3 * d);
        mDanmakuContext.mDanmakuFactory.fillLinePathData(danmaku, points, 1f, 1f);
        mDanmakuContext.setMaximumVisibleSizeInScreen(30);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.rotationY = -1;
        danmaku.rotationZ = -45;
        Glide.with(mContext).load(bean.getUser().getHeadUrl())
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
                        SpannableStringBuilder spannable = createSpannable(drawable, bean);
                        danmaku.text = spannable;
                        danmaku.padding = DANMU_PADDING;
                        //danmaku.setDuration(new Duration(1000 * 60));
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

    private SpannableStringBuilder createSpannable(Drawable drawable, StarDanMuNewInfo.PositionsListBean infoBean) {
        //小姜求购15秒，12.32/秒
        String text = "bitmap";
        String name = "infoBean";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        CenteredImageSpan span = new CenteredImageSpan(drawable);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("  "+infoBean.getUser().getNickname());
        if (infoBean.getTrades().getBuySell() == -1) {
            spannableStringBuilder.append("转让 ");
        } else {
            spannableStringBuilder.append("求购 ");
        }
        spannableStringBuilder.append(String.format("%.2f", infoBean.getTrades().getOpenPrice()) + "/秒    ");

        //spannableStringBuilder.setSpan(new TextAppearanceSpan(this, R.style.style_pingjie), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#fafafa")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                LogUtil.e("tv_back点击了");
                finish();
                break;
            case R.id.tv_right:
                if (CheckLoginUtil.checkLogin(this) == false) {
                    return;
                }
                startActivity(FleaMarketActivity.class);
                break;
            case R.id.tv_transfer:
                if (CheckLoginUtil.checkLogin(this) == false) {
                    return;
                }
                Intent intent = new Intent(this, BuyTransferIndentActivity.class);
                intent.putExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE, 1);
                intent.putExtra(AppConstant.STAR_WID, symbolInfoBean.getWid());
                intent.putExtra(AppConstant.STAR_NAME, symbolInfoBean.getName());
                intent.putExtra(AppConstant.STAR_CODE, symbolInfoBean.getSymbol());
                intent.putExtra(AppConstant.STAR_HEAD_URL, symbolInfoBean.getPic());
                startActivity(intent);
                break;
            case R.id.tv_ask_to_buy:
                if (CheckLoginUtil.checkLogin(this) == false) {
                    return;
                }
                Intent intent2 = new Intent(this, BuyTransferIndentActivity.class);
                intent2.putExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE, 0);
                intent2.putExtra(AppConstant.STAR_WID, symbolInfoBean.getWid());
                intent2.putExtra(AppConstant.STAR_NAME, symbolInfoBean.getName());
                intent2.putExtra(AppConstant.STAR_CODE, symbolInfoBean.getSymbol());
                intent2.putExtra(AppConstant.STAR_HEAD_URL, symbolInfoBean.getPic());
                startActivity(intent2);
                break;
            case R.id.img_head:
                if (CheckLoginUtil.checkLogin(this) == false) {
                    return;
                }
                Intent intent3 = new Intent(this, StarInfoActivity.class);
                intent3.putExtra(AppConstant.STAR_CODE, symbolInfoBean.getSymbol());
                startActivity(intent3);
                break;
        }
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
        super.onPause();
        stopRefresh();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRefresh();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
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

    //这两个用来控制两行弹幕之间的间距
    private int BITMAP_WIDTH = 40;//头像的大小
    private int BITMAP_HEIGHT = 40;
    private float DANMU_TEXT_SIZE = 14f;//弹幕字体的大小
    private int DANMU_PADDING = 10;
    private int DANMU_PADDING_INNER = 7;
    private int DANMU_RADIUS = 11;//圆角半径

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
            int i = new Random().nextInt(4);
            LinearGradient mLinearGradient = new LinearGradient(0, 0, danmaku.paintWidth, 0, colors[i], 0x00fafafa, Shader.TileMode.CLAMP);
            //new LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, TileMode tile)
            paint.setShader(mLinearGradient);
            LogUtil.e("danmaku" + left + "");
            canvas.drawRoundRect(new RectF(BITMAP_WIDTH / 2 + left + DANMU_PADDING_INNER, top + DANMU_PADDING_INNER
                            , left + danmaku.paintWidth - DANMU_PADDING_INNER + 6 + BITMAP_WIDTH / 2,
                            top + danmaku.paintHeight - DANMU_PADDING_INNER + 6),//+6 主要是底部被截得太厉害了，+6是增加padding的效果
                    0, 0, paint);
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
        DANMU_TEXT_SIZE = DisplayUtil.sp2px(DANMU_TEXT_SIZE);
    }

    private int secondTime = 0;
    private boolean startSunTime = false;

    //获取交易时间倒计时
    private void initTradingStatus(final boolean sendHandler) {
        NetworkAPIFactoryImpl.getInformationAPI().getTradingStatus(SharePrefUtil.getInstance().getUserId(), SharePrefUtil.getInstance().getToken(), symbolInfoBean.getSymbol(), new OnAPIListener<TradingStatusBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(TradingStatusBeen tradingStatusBeen) {
                if (tradingStatusBeen != null) {
                    if (tradingStatusBeen.isStatus()) {
                        startSunTime = true;
                        secondTime = tradingStatusBeen.getRemainingTime();
                        if (myHandler != null) {
                            myHandler.removeCallbacksAndMessages(null);
                            myHandler.sendEmptyMessage(myHandler.GRT_DATA);
                        }
                        //startTime(tradingStatusBeen.getRemainingTime());
                    } else {
                        tv_time.setText("未开始");
                        if (sendHandler && myHandler != null) {
                            myHandler.removeCallbacksAndMessages(null);
                            myHandler.sendEmptyMessage(myHandler.GRT_DATA);
                        }
                    }
                }
                LogUtils.loge(tradingStatusBeen.toString());
            }
        });
    }


    private void stopRefresh() {
        if (myHandler != null) {
            startSunTime = false;
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
            LogUtils.loge("停止刷新stopRefresh");
        }
    }

    private void startRefresh() {
        if (myHandler != null) {
            LogUtils.loge("刷新startRefresh");
            myHandler.removeCallbacksAndMessages(null);
            initTradingStatus(false);
        } else {
            LogUtils.loge("刷新startRefresh_myHandler=null");
            myHandler = new MyHandler(this);
            myHandler.removeCallbacksAndMessages(null);
            initTradingStatus(false);
        }
    }

    //获取弹幕数据
    private void getDanMaku() {
        NetworkAPIFactoryImpl.getInformationAPI().getDanMaKuInfoNeW(symbolInfoBean.getSymbol(),
                50, new OnAPIListener<StarDanMuNewInfo>() {
                    @Override
                    public void onError(Throwable ex) {

                        LogUtils.loge("弹幕错误码" + ex.toString());
                    }

                    @Override
                    public void onSuccess(StarDanMuNewInfo danMaKuInfo) {
                        LogUtils.loge("弹幕错误码" + danMaKuInfo.toString());
                        if (danMaKuInfo!=null&&danMaKuInfo.getPositionsList()!=null&&danMaKuInfo.getPositionsList().size()!=0){
                            list = danMaKuInfo.getPositionsList();
                            if (list.size() > 0) {
                                qiu.setImageAlpha(125);
                            }
                        }
                    }
                });
    }
    //引导图
    private void showPopupWindow() {
        View popView = LayoutInflater.from(this).inflate(R.layout.popwindow_navijation_2, null);
        final ImageView imageView = (ImageView) popView.findViewById(R.id.navigation_5_2);
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(popView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x33000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }
}
