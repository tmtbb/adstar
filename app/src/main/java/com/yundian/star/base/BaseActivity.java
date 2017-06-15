package com.yundian.star.base;

/**
 * 基类
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.baseapp.AppManager;
import com.yundian.star.been.MatchSucessReturnBeen;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketAPIRequestManage;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketAPIResponse;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.yundian.star.ui.im.activity.SystemMessagesActivity;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.TUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.utils.daynightmodeutils.ChangeModeController;
import com.yundian.star.widget.LoadingDialog;
import com.yundian.star.widget.StatusBarCompat;

import butterknife.ButterKnife;

/***************使用例子*********************/
//1.mvp模式
//public class SampleActivity extends BaseActivity<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void initView() {
//    }
//}
//2.普通模式
//public class SampleActivity extends BaseActivity {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {
    public T mPresenter;
    public E mModel;
    public Context mContext;
    private boolean isConfigChange = false;
    protected View rootView;
    private View errorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConfigChange = false;
        doBeforeSetcontentView();
        rootView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(rootView);
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        this.initPresenter();
        this.initView();
        matchSucessListener();
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        SetStatusBarColor();

    }

    protected void setTheme() {
        //设置昼夜主题
        initTheme();
    }

    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();


    /**
     * 设置主题
     */
    private void initTheme() {
        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme);
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.color_921224));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUtils.showShort(text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUtils.showShort(resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUtils.showLong(resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUtils.showLong(text);
    }

    /**
     * 带图片的toast
     *
     * @param text
     * @param res
     */
    public void showToastWithImg(String text, int res) {
        ToastUtils.showToastWithImg(text, res);
    }

    /**
     * 网络访问错误提醒
     */
    public void showNetErrorTip() {
        ToastUtils.showToastWithImg(getText(R.string.net_error).toString(), R.drawable.ic_wifi_off);
    }

    public void showNetErrorTip(String error) {
        ToastUtils.showToastWithImg(error, R.drawable.ic_wifi_off);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isConfigChange = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketAPIRequestManage.getInstance().unboundOnMatchSucessListener();
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (!isConfigChange) {
            AppManager.getAppManager().finishActivity(this);
        }
        ButterKnife.unbind(this);
    }

    /**
     * 加载失败view
     *
     * @param parent 根布局  标题栏以下
     * @param msg    错误信息
     */
    public void showErrorView(ViewGroup parent, String msg) {
        if (parent == null) {
            return;
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater3 = LayoutInflater.from(mContext);
        errorView = inflater3.inflate(R.layout.layout_error_view, null);
        TextView errorMsg = (TextView) errorView.findViewById(R.id.tv_error_msg);
        errorMsg.setText(msg);
        errorView.setLayoutParams(lp);
        if (rootView != null) {
            ((FrameLayout) rootView.getParent()).addView(errorView);
        }
        parent.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.VISIBLE);
    }

    public void closeErrorView(ViewGroup parent) {
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        parent.setVisibility(View.VISIBLE);
    }


    private void showAlertDialog(final MatchSucessReturnBeen matchSucessReturnBeen) {
        final Dialog mPopWindowHistory = new Dialog(this, R.style.myDialog);
        mPopWindowHistory.setContentView(R.layout.mach_sucess_choose);
        TextView tvSure = (TextView) mPopWindowHistory.findViewById(R.id.btn_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindowHistory.dismiss();
                Intent intent = new Intent(BaseActivity.this, SystemMessagesActivity.class);
                //intent.putExtra(AppConstant.MATCH_SUCESS_INFO, 1);
                intent.putExtra(AppConstant.MATCH_SUCESS_ORDER_INFO,matchSucessReturnBeen);
                startActivity(intent);
            }
        });
        TextView btn_cancel = (TextView) mPopWindowHistory.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindowHistory.dismiss();
            }
        });
        mPopWindowHistory.show();
    }

     private void matchSucessListener() {
        SocketAPIRequestManage.getInstance().setOnMatchSucessListener(new SocketAPIRequestManage.OnMatchSucessListener() {
            @Override
            public void onMatchListener(SocketDataPacket socketDataPacket) {
                LogUtils.loge("撮合成功");
                SocketAPIResponse socketAPIResponse = new SocketAPIResponse(socketDataPacket);
                final MatchSucessReturnBeen matchSucessReturnBeen = JSON.parseObject(socketAPIResponse.jsonObject().toString(), MatchSucessReturnBeen.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAlertDialog(matchSucessReturnBeen);
                    }
                });
            }
        });


    }
}
