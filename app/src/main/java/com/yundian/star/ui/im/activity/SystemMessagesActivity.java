package com.yundian.star.ui.im.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.Constant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.StarMailListBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.im.adapter.BookStarComAdapter;
import com.yundian.star.ui.main.activity.ResetPayPwdActivity;
import com.yundian.star.ui.view.PayPwdEditText;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.SoftKeyBoardListener;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.widget.PutPasPopupWindow;

import java.util.ArrayList;

import butterknife.Bind;

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
    private ArrayList<StarMailListBeen.DepositsinfoBean> list = new ArrayList<>();
    private ArrayList<StarMailListBeen.DepositsinfoBean> loadList = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private BookStarComAdapter starCommBookAdapter;
    private PutPasPopupWindow mPopWindow;
    private View contentView;
    private PayPwdEditText payPwdEditText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sys_message;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setBackVisibility(true);
        nt_title.setTitleText(R.string.systen_news);
        initAdapter();
        getData(false, 0, REQUEST_COUNT);
        initListener();
    }

    private void initListener() {
        contentView = LayoutInflater.from(this).inflate(R.layout.input_dialog_lyaout, null);
        intPutPasswordDialog();
        nt_title.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //showAlertDialog();
                showPasDialog();
            }

            @Override
            public void keyBoardHide(int height) {
                mPopWindow.dismiss();
            }
        });

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //KeyBordUtil.showSoftKeyboard(payPwdEditText);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

    }

    private void showPasDialog() {
        payPwdEditText.setFocusable(true);
        payPwdEditText.requestFocus();
        payPwdEditText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
        payPwdEditText.clearText();
        mPopWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    private void initAdapter() {
        starCommBookAdapter = new BookStarComAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(starCommBookAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setPullRefreshEnabled(false);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        /*lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
            }
        });*/
    }

    private void getData(final boolean isLoadMore, int start, int end) {

        for (int i = 0; i < 5; i++) {
            StarMailListBeen.DepositsinfoBean bean = new StarMailListBeen.DepositsinfoBean();
            bean.setStarname("哈哈" + i);
            list.add(bean);
        }
        showData();
        NetworkAPIFactoryImpl.getInformationAPI().getStarmaillist(SharePrefUtil.getInstance().getUserId(), SharePrefUtil.getInstance().getToken(), "123", start, end, new OnAPIListener<StarMailListBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarMailListBeen starMailListBeen) {
                LogUtils.loge(starMailListBeen.toString());
            }
        });

    }

    public void showData() {
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        starCommBookAdapter.addAll(list);
        lrv.refresh();
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            starCommBookAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    private void intPutPasswordDialog() {
        mPopWindow = new PutPasPopupWindow(this);
        mPopWindow.setContentView(contentView);
        payPwdEditText = (PayPwdEditText) contentView.findViewById(R.id.ppet);
        TextView resetPwd = (TextView) contentView.findViewById(R.id.tv_reset_pwd);
        ImageView img_view = (ImageView) contentView.findViewById(R.id.img_view);
        payPwdEditText.initStyle(R.drawable.edit_num_bg_red, 6, 0.33f, R.color.colorAccent, R.color.colorAccent, 20);
        payPwdEditText.setFocusable(true);
        payPwdEditText.requestFocus();
        payPwdEditText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
        //payPwdEditText.setFocusableInTouchMode(true);
        payPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
//                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                //校验支付密码
                LogUtils.loge("密码输入完成");
            }

            @Override
            public void onChange(String str) {

            }
        });
        resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.loge("重新设置交易密码---------------------");
                Bundle bundle4 = new Bundle();
                bundle4.putString("resetPwd", Constant.PAY_PWD);
                Intent intent = new Intent(SystemMessagesActivity.this, ResetPayPwdActivity.class);
                intent.putExtras(bundle4);
                startActivity(intent);
                payPwdEditText.clearText();
            }
        });

        img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(payPwdEditText.getWindowToken(), 0);
            }
        });
    }

}
