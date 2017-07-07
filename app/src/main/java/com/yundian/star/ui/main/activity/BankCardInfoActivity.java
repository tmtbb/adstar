package com.yundian.star.ui.main.activity;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.BankCardBean;
import com.yundian.star.been.BankInfoBean;
import com.yundian.star.been.ResultCodeBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.ui.view.SwipeListLayout;
import com.yundian.star.utils.FormatUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 银行卡信息
 */
public class BankCardInfoActivity extends BaseActivity {


    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.iconView)
    RoundImageView iconView;
    @Bind(R.id.titleView)
    TextView titleView;
    @Bind(R.id.typeView)
    TextView typeView;
    @Bind(R.id.numberView)
    TextView numberView;
    @Bind(R.id.iv_close_img)
    ImageView ivCloseImg;
    @Bind(R.id.rl_bank)
    RelativeLayout rlBank;
    //    @Bind(R.id.bank_info_view)
//    LinearLayout bankInfoView;
    @Bind(R.id.iconView2)
    RoundImageView iconView2;
    @Bind(R.id.titleView2)
    TextView titleView2;
    @Bind(R.id.bank_info_empty)
    RelativeLayout bankInfoEmpty;
    @Bind(R.id.fl_card_view)
    FrameLayout flCardView;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @Bind(R.id.swipeLayout)
    SwipeListLayout swipeLayout;
    @Bind(R.id.deleteView)
    TextView deleteView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initData();
        requestBankCards();
        initListener();
    }

    private void requestBankCards() {
        NetworkAPIFactoryImpl.getDealAPI().bankCardList(new OnAPIListener<BankCardBean>() {
            @Override
            public void onSuccess(BankCardBean bankCardBeen) {
                srlRefresh.setRefreshing(false);
                if (TextUtils.isEmpty(bankCardBeen.getCardNo()) || TextUtils.isEmpty(bankCardBeen.getBankUsername())) {
                    LogUtils.loge("银行卡列表失败----------------------------------------------");
//                    bankInfoEmpty.setVisibility(View.VISIBLE);
//                    swipeLayout.setVisibility(View.GONE);
                } else {
                    LogUtils.loge("银行卡列表----------------成功");
//                    bankInfoEmpty.setVisibility(View.GONE);
                    requestBankCardInfo(bankCardBeen);

                }

            }

            @Override
            public void onError(Throwable ex) {
                srlRefresh.setRefreshing(false);
                ex.printStackTrace();
                LogUtils.loge("银行卡错误------------------------");
//                bankInfoEmpty.setVisibility(View.VISIBLE);
//                swipeLayout.setVisibility(View.GONE);
            }
        });
    }

    private void requestBankCardInfo(final BankCardBean bankCardBeen) {
        String cardNo = bankCardBeen.getCardNo();
        NetworkAPIFactoryImpl.getDealAPI().bankCardInfo(cardNo, new OnAPIListener<BankInfoBean>() {
            @Override
            public void onSuccess(BankInfoBean bankInfoBean) {

                if (TextUtils.isEmpty(bankInfoBean.getCardNO()) || TextUtils.isEmpty(bankInfoBean.getBankName())) {
//                    LogUtils.loge("银行卡列表失败----------------------------------------------");
//                    ToastUtils.showShort("请先绑定银行卡");
//                    startActivity(BankCardInfoActivity.class);
                } else {
                    LogUtils.loge("银行卡信息----------------成功");
                    swipeLayout.setVisibility(View.VISIBLE);
                    SharePrefUtil.getInstance().saveCardNo(bankInfoBean.getCardNO());
                    titleView.setText(bankInfoBean.getBankName());
                    typeView.setText(bankInfoBean.getCardName());
                    numberView.setText(FormatUtil.formatCard(bankInfoBean.getCardNO()));

                }

            }

            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("银行卡错误------------------------");
//                ToastUtils.showShort("请先绑定银行卡");
//                startActivity(BankCardInfoActivity.class);
            }

        });
    }


    private void initData() {
        ntTitle.setTitleText(getString(R.string.bank_info));
    }


    @OnClick({R.id.iv_close_img, R.id.bank_info_view, R.id.bank_info_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close_img:  //解绑
                showDialogs();
                break;
            case R.id.bank_info_view:
                break;
            case R.id.bank_info_empty:
                //添加银行卡
                startActivity(AddBankCardActvivity.class);
                break;
        }
    }

    /**
     * 解除绑定银行卡
     */
    private void unBindcard() {
        NetworkAPIFactoryImpl.getDealAPI().unBindCard(new OnAPIListener<ResultCodeBeen>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtils.loge("解绑失败");
                stopProgressDialog();
            }

            @Override
            public void onSuccess(ResultCodeBeen been) {
                stopProgressDialog();
                if (been.getResult() == 1) {
                    ToastUtils.showShort("解绑成功");
                    SharePrefUtil.getInstance().saveCardNo("");
//                    swipeLayout.setVisibility(View.GONE);
//                    bankInfoEmpty.setVisibility(View.VISIBLE);
                    swipeLayout.close();
                    finish();
                }

            }
        });
    }


    //存放所有已经打开的菜单
    private List<SwipeListLayout> openList = new ArrayList<SwipeListLayout>();

    protected void initListener() {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bankInfoEmpty.setVisibility(View.GONE);
                swipeLayout.setVisibility(View.GONE);
                requestBankCards();
            }
        });

        swipeLayout.setSwipeChangeListener(new SwipeListLayout.OnSwipeChangeListener() {
            @Override
            public void onStartOpen(SwipeListLayout mSwipeLayout) {
                for (SwipeListLayout layout : openList) {
                    layout.close();
                }
                openList.clear();
            }

            @Override
            public void onStartClose(SwipeListLayout mSwipeLayout) {
            }

            @Override
            public void onOpen(SwipeListLayout mSwipeLayout) {
                openList.add(mSwipeLayout);
            }

            @Override
            public void onDraging(SwipeListLayout mSwipeLayout) {
            }

            @Override
            public void onClose(SwipeListLayout mSwipeLayout) {
                openList.remove(mSwipeLayout);
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogs(); //解除绑定的银行卡
            }
        });
    }

    /**
     * 解绑弹窗
     */
    private void showDialogs() {
        final Dialog mDialog = new Dialog(this, R.style.myDialog);
        mDialog.setContentView(R.layout.dialog_sure_order);
        TextView tvSure = (TextView) mDialog.findViewById(R.id.btn_sure);
        TextView title = (TextView) mDialog.findViewById(R.id.tv_title);
        title.setText("确定解绑银行卡？");
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                unBindcard();
            }
        });
        TextView btn_cancel = (TextView) mDialog.findViewById(R.id.btn_cancel);
        btn_cancel.setText("取消");
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
}
