package com.cloudTop.starshare.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
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
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.StarDanMuNewInfo;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.utils.DisplayUtil;
import com.cloudTop.starshare.widget.CenteredImageSpan;
import com.cloudTop.starshare.widget.CircleDrawable;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.github.jdsjlzx.ItemDecoration.SpacesItemDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.netease.nimlib.jsbridge.util.LogUtil;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.FleaMarketAdapter;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.widget.BiliDanmukuParser;

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
    private MyHandler myHandler;
    private int temporary = 0;
    private int secondTime = 0;
    private boolean startSunTime = false;
    private int layoutLeft;
    private int layoutRight;
    private int layoutHeight;
    private int layoutBottom;
    private View lint;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private FleaMarketAdapter fleaMarketAdapter;
    private ArrayList<StarDanMuNewInfo.PositionsListBean> listDanMaKu = new ArrayList<>();
    private ArrayList<StarListReturnBean.SymbolInfoBean> list = new ArrayList<>();
    private ArrayList<StarListReturnBean.SymbolInfoBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 0;
    private static final int REQUEST_COUNT = 12;

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
        initAdapter();
        getKuanGao();
        setSize();
        initDanmakuView();
        initData();
    }

    private void initData() {
        if (myHandler == null) {
            myHandler = new MyHandler(this);
        }
        listDanMaKu = new ArrayList<>();
        getDanMaku();
//        for (int i = 0; i < 10; i++) {
//            DanMaKuInfo.BarrageInfoBean bean = new DanMaKuInfo.BarrageInfoBean();
//            bean.setOrder_num(1);
//            bean.setOrder_price(1.11);
//            bean.setOrder_type(1);
//            bean.setUser_name("i");
//            listDanMaKu.add(bean);
//        }
//        myHandler.sendEmptyMessage(myHandler.GRT_DATA);

    }

    private void getDanMaku() {
//        NetworkAPIFactoryImpl.getInformationAPI().getDanMaKuInfo(0,
//                50, new OnAPIListener<DanMaKuInfo>() {
//                    @Override
//                    public void onError(Throwable ex) {
//
//                        LogUtils.loge("弹幕错误码" + ex.toString());
//                    }
//
//                    @Override
//                    public void onSuccess(DanMaKuInfo danMaKuInfo) {
//                        if (danMaKuInfo!=null&&danMaKuInfo.getBarrage_info()!=null&&danMaKuInfo.getBarrage_info().size()!=0){
//                            listDanMaKu = danMaKuInfo.getBarrage_info();
//                            myHandler.sendEmptyMessage(myHandler.GRT_DATA);
//                        }
//                    }
//                });
        NetworkAPIFactoryImpl.getInformationAPI().getDanMaKuInfoNeWAll(
                50, new OnAPIListener<StarDanMuNewInfo>() {
                    @Override
                    public void onError(Throwable ex) {
                        LogUtils.loge("弹幕错误码" + ex.toString());
                    }

                    @Override
                    public void onSuccess(StarDanMuNewInfo danMaKuInfo) {
                        if (danMaKuInfo!=null&&danMaKuInfo.getPositionsList()!=null&&danMaKuInfo.getPositionsList().size()!=0){
                            listDanMaKu = danMaKuInfo.getPositionsList();
                            myHandler.sendEmptyMessage(myHandler.GRT_DATA);
                        }
                    }
                });
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
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, null); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_SPECIAL, true);
        LogUtils.loge("layoutBottom..."+layoutBottom);
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_NONE, 3).setDuplicateMergingEnabled(false)
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
                .preventOverlapping(null).setDanmakuMargin(10).setMaximumVisibleSizeInScreen(0)
        .setScrollSpeedFactor(1.5f);
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
        if (listDanMaKu.size() != 0 && listDanMaKu.get(temporary) != null) {
            addDanmaKuShowTextAndImage(listDanMaKu.get(temporary));
            if (temporary==list.size() - 1){
                temporary=0;
            }
            if (temporary < listDanMaKu.size() - 1 && myHandler != null) {
                temporary++;
                myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1 * 800);
            }
        }
    }


    private void addDanmaKuShowTextAndImage(final StarDanMuNewInfo.PositionsListBean infoBean) {
        //Math.floor(Math.random()*(max-min+1)+min);
        final BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL, mDanmakuContext);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        // Drawable drawable = getResources().getDrawable(R.drawable.ic_home_normal);
        //drawable.setBounds(0, 0, DisplayUtil.dip2px(40), DisplayUtil.dip2px(40));
        //ImageLoaderUtils.displaySmallPhoto();
        Glide.with(mContext).load(infoBean.getUser().getHeadUrl())
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
                        SpannableStringBuilder spannable = createSpannable(drawable,infoBean);
                        danmaku.text = spannable;
                        danmaku.padding = DANMU_PADDING;
                        danmaku.paintHeight=layoutBottom- DisplayUtil.dip2px(40);
                        //danmaku.setDuration(new Duration(1000 * 60));
                        danmaku.priority = 1;  // 一定会显示, 一般用于本机发送的弹幕
                        danmaku.isLive = false;
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

    private SpannableStringBuilder createSpannable(Drawable drawable,StarDanMuNewInfo.PositionsListBean infoBean) {
        //小姜求购15秒，12.32/秒
        String text = "bitmap";
        String name = "infoBean";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        //CenteredImageSpan span = new CenteredImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        CenteredImageSpan span = new CenteredImageSpan(drawable);
        //ImageSpan span = new ImageSpan(resource);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("  "+infoBean.getUser().getNickname());
        if (infoBean.getTrades().getBuySell()==-1){
            spannableStringBuilder.append("转让");
        }else {
            spannableStringBuilder.append("求购");
        }
        spannableStringBuilder.append(infoBean.getTrades().getAmount()+"秒");
        spannableStringBuilder.append(","+String.format("%.2f",infoBean.getTrades().getOpenPrice())+"/秒  ");

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
                display = 720f / widthPixels;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    lint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    lint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                Log.e("layout.....", "..." + layoutHeight + "..." + layoutBottom + "..." + "widthPixels" + widthPixels + "..." + display);
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
        if (lrv != null) {
            lrv = null;
        }
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
        super.onDestroy();
    }

    private void initAdapter() {
        fleaMarketAdapter = new FleaMarketAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(fleaMarketAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        lrv.setNoMore(false);
        lrv.setLayoutManager(manager);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setPullRefreshEnabled(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setHasFixedSize(true);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter + 1, mCurrentCounter + REQUEST_COUNT);
            }
        });
        lrv.addItemDecoration(SpacesItemDecoration.newInstance(0, DisplayUtil.dip2px(10), manager.getSpanCount(), Color.WHITE));
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                StarListReturnBean.SymbolInfoBean symbolInfoBean = list.get(position);
                Intent intent3 = new Intent(FleaMarketActivity.this, StarInfoActivity.class);
                intent3.putExtra(AppConstant.STAR_CODE, symbolInfoBean.getSymbol());
                startActivity(intent3);
            }
        });
        getData(false, 0, 12);
    }


    private void getData(final boolean isLoadMore, int start, int end) {
        NetworkAPIFactoryImpl.getInformationAPI().getStarList(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(), 4, 1, start, end, new OnAPIListener<StarListReturnBean>() {
                    @Override
                    public void onError(Throwable ex) {
                        if (lrv != null) {
                            lrv.setNoMore(true);
                            if (!isLoadMore) {
                                list.clear();
                                fleaMarketAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);

                            }
                        }
                    }

                    @Override
                    public void onSuccess(StarListReturnBean starListReturnBean) {
                        if (starListReturnBean == null || starListReturnBean.getSymbol_info() == null || starListReturnBean.getSymbol_info().size() == 0) {
                            if (!isLoadMore) {
                                list.clear();
                                fleaMarketAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                            }else {
                                lrv.setNoMore(true);
                            }

                            return;
                        }
                        if (isLoadMore) {
                            loadList.clear();
                            loadList = starListReturnBean.getSymbol_info();
                            loadMoreData();
                        } else {
                            list.clear();
                            list = starListReturnBean.getSymbol_info();
                            showData();
                        }
                    }
                });
    }

    public void showData() {
        if (list.size() == 0) {
            return;
        }
        fleaMarketAdapter.clear();
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        fleaMarketAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            fleaMarketAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }


}
