package com.yundian.star.ui.im.activity;

import android.app.Dialog;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.AskToBuyReturnBeen;
import com.yundian.star.been.MatchSucessReturnBeen;
import com.yundian.star.been.OrderCancelReturnBeen;
import com.yundian.star.been.OrderReturnBeen;
import com.yundian.star.been.SureOrder;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.SystemMessageAdapter;
import com.yundian.star.ui.view.PayDialog;
import com.yundian.star.ui.view.PayPwdEditText;
import com.yundian.star.utils.JudgeIsSetPayPwd;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.SoftKeyBoardListener;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.widget.PutPasPopupWindow;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yundian.star.R.string.buy_price;

/**
 * Created by Administrator on 2017/5/10.
 * 系统消息
 */

public class SystemMessagesActivity extends BaseActivity {
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;

    private static int mCurrentCounter = 1;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<OrderReturnBeen.OrdersListBean> list = new ArrayList<>();
    private ArrayList<OrderReturnBeen.OrdersListBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private SystemMessageAdapter systemMessageAdapter;
    private PutPasPopupWindow mPopWindow;
    private View contentView;
    private PayPwdEditText payPwdEditText;
    private boolean flag = true;
    private MatchSucessReturnBeen matchSucessReturnBeen;
    private AskToBuyReturnBeen toBuyReturnBeen;
    private long userId;
    private PayDialog payDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sys_message;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        matchSucessReturnBeen = getIntent().getParcelableExtra(AppConstant.MATCH_SUCESS_ORDER_INFO);
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(R.string.systen_news);
        initAdapter();
        getData(false, 1, REQUEST_COUNT);
        initListener();
        userId = SharePrefUtil.getInstance().getUserId();
    }

    private void initListener() {
        contentView = LayoutInflater.from(this).inflate(R.layout.input_dialog_lyaout, null);
        //intPutPasswordDialog();
        payDialog = new PayDialog(this);
        payDialog.setCheckPasCallBake(new PayDialog.checkPasCallBake() {
            @Override
            public void checkSuccess(OrderReturnBeen.OrdersListBean ordersListBean) {
                sureOrder(ordersListBean);
            }

            @Override
            public void checkError() {

            }
        });
        nt_title.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                payDialog.setLayoutHigh(height);
            }

            @Override
            public void keyBoardHide(int height) {
                payDialog.dismiss();
            }
        });

        systemMessageAdapter.setOnImgClickLitener(new SystemMessageAdapter.OnImgClickLitener() {
            @Override
            public void onImgClick(View view, int position) {
                //ToastUtils.showShort("position"+position);
                if (!JudgeIsSetPayPwd.isSetPwd(SystemMessagesActivity.this)) {
                    return;
                }
                    showDialogs(position);
            }
        });
    }


    private void initAdapter() {
        systemMessageAdapter = new SystemMessageAdapter(this,list,SharePrefUtil.getInstance().getUserId());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(systemMessageAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        /*lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
            }
        });*/
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter + 1, REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false, 1, REQUEST_COUNT);
            }
        });
    }

    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().historyOrder(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(), 3, start, count, new OnAPIListener<OrderReturnBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        if (lrv != null) {
                            lrv.setNoMore(true);
                            if (!isLoadMore) {
                                list.clear();
                                systemMessageAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                            }
                        }
                        LogUtils.loge("当日订单返回错误码" + ex.toString());
                    }

                    @Override
                    public void onSuccess(OrderReturnBeen orderReturnBeen) {
                        LogUtils.loge("当日订单" + orderReturnBeen.toString());
                        if (orderReturnBeen==null||orderReturnBeen.getOrdersList() == null || orderReturnBeen.getOrdersList().size() == 0) {
                            lrv.refreshComplete(REQUEST_COUNT);
                            return;
                        }
                        if (isLoadMore) {
                            loadList.clear();
                            loadList = orderReturnBeen.getOrdersList();
                            loadMoreData();
                        } else {
                            list.clear();
                            list = orderReturnBeen.getOrdersList();
                            showData();
                        }
                    }
                });


    }

    public void showData() {
        systemMessageAdapter.clear();
        mCurrentCounter = list.size();
        systemMessageAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            systemMessageAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    private void sureOrder(OrderReturnBeen.OrdersListBean ordersListBean) {
        NetworkAPIFactoryImpl.getInformationAPI().sureOrder(SharePrefUtil.getInstance().getUserId(),
                SharePrefUtil.getInstance().getToken(), ordersListBean.getOrderId(), ordersListBean.getPositionId(), new OnAPIListener<SureOrder>() {
                    @Override
                    public void onError(Throwable ex) {
                        getData(false, 1, REQUEST_COUNT);
                        ToastUtils.showLong("订单确认失败");
                        LogUtils.loge("订单确认失败"+ex.toString());
                    }

                    @Override
                    public void onSuccess(SureOrder sureOrder) {
                        if (sureOrder.getStatus()==1){
                            ToastUtils.showLong("确认成功");
                        }else if (sureOrder.getStatus()==2){
                            ToastUtils.showLong("双方确认成功");
                        } else if (sureOrder.getStatus()==3){
                            ToastUtils.showLong("交易完成");
                        }
                        LogUtils.loge("订单确认成功"+sureOrder.toString());
                        new Handler().postDelayed(runnable2,300);

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //订单确认取消弹窗
    private void showDialogs(final int position) {
        final Dialog mPopWindowHistory = new Dialog(this, R.style.myDialog);
        mPopWindowHistory.setContentView(R.layout.dialog_sure_order);
        TextView tvSure = (TextView) mPopWindowHistory.findViewById(R.id.btn_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindowHistory.dismiss();
                showOrderInfoDialog(position);
            }
        });
        TextView btn_cancel = (TextView) mPopWindowHistory.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindowHistory.dismiss();
                NetworkAPIFactoryImpl.getInformationAPI().cancelOrder(SharePrefUtil.getInstance().getUserId()
                        , SharePrefUtil.getInstance().getToken(),list.get(position).getOrderId(), new OnAPIListener<OrderCancelReturnBeen>() {
                            @Override
                            public void onError(Throwable ex) {
                                LogUtils.loge("取消订单失败"+ex.toString());
                                getData(false, 1, REQUEST_COUNT);
                                LogUtils.loge("取消订单失败");
                                ToastUtils.showLong("取消订单失败");
                            }

                            @Override
                            public void onSuccess(OrderCancelReturnBeen returnBeen) {
                                LogUtils.loge("取消订单"+returnBeen.toString());
                                if (returnBeen!=null&&returnBeen.getOrderId()!=0){
                                    LogUtils.loge("取消订单"+returnBeen.toString());
                                    getData(false, 1, REQUEST_COUNT);
                                    LogUtils.loge("取消订单成功");
                                    ToastUtils.showLong("取消订单成功");
                                }
                            }
                        });
            }
        });
        mPopWindowHistory.show();
    }

    //订单详情接口
     private void showOrderInfoDialog(int position) {
        final Dialog mDetailDialog = new Dialog(this, R.style.custom_dialog);
        //获得dialog的window窗口
        Window window = mDetailDialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        mDetailDialog.setContentView((R.layout.dialog_order_sure));
        TextView tv_state = (TextView) mDetailDialog.findViewById(R.id.tv_state);
        TextView tv_sure = (TextView) mDetailDialog.findViewById(R.id.tv_sure);
        TextView order_info = (TextView) mDetailDialog.findViewById(R.id.order_info);
        TextView order_preice = (TextView) mDetailDialog.findViewById(R.id.order_preice);
        TextView transfer_num = (TextView) mDetailDialog.findViewById(R.id.transfer_num);
        TextView order_total = (TextView) mDetailDialog.findViewById(R.id.order_total);
        ImageView img_close = (ImageView) mDetailDialog.findViewById(R.id.img_close);
         final OrderReturnBeen.OrdersListBean ordersListBean = list.get(position);
        if (userId==ordersListBean.getBuyUid()){
            tv_state.setText(R.string.ask_to_buy);
        }else {
            tv_state.setText(R.string.transfer);
        }
        order_preice.setText(String.format(getString(buy_price), ordersListBean.getOpenPrice()));
        transfer_num.setText(String.format(getString(R.string.num_time), ordersListBean.getAmount()));
        order_total.setText(String.format(getString(R.string.price_total), ordersListBean.getOpenPrice()*ordersListBean.getAmount()));
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //KeyBordUtil.showSoftKeyboard(payPwdEditText);
                mDetailDialog.dismiss();
                payDialog.setOrdersListBean(ordersListBean);
                payDialog.show();
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();

            }
        });

        mDetailDialog.show();
    }

    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            LogUtils.loge("刷新");
            getData(false, 1, REQUEST_COUNT);
        }
    };
}
