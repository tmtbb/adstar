package com.cloudTop.starshare.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.TUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.LoadingDialog;

import butterknife.ButterKnife;

/**
 * des:基类fragment
 * Created by ysl
 */

/***************使用例子*********************/
//1.mvp模式
//public class SampleFragment extends BaseFragment<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
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
//public class SampleFragment extends BaseFragment {
//    @Override
//    public int getLayoutResource() {
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
public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    protected View rootView;
    public T mPresenter;
    public E mModel;
    private LinearLayout errorView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        initPresenter();
        initView();
        return rootView;
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView();


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
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
    }

    /**
     * 停止加载进度条
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
    public void onDestroyView() {
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    /**
     * 加载失败view  空白页
     * <p>
     * 根布局  标题栏以下
     *
     * @param msg 错误信息
     */
    public void showErrorView(FrameLayout parentView, int drawableId, String msg) {
        try {
            if (parentView == null) {
                return;
            }
            if (errorView != null) {
//                parent.removeView(errorView);
                parentView.removeView(errorView);
                errorView = null;
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            LayoutInflater inflater3 = LayoutInflater.from(parentView.getContext());
            errorView = (LinearLayout) inflater3.inflate(R.layout.layout_error_view, null);
            errorView.setLayoutParams(lp);
            TextView errorMsg = (TextView) errorView.findViewById(R.id.tv_error_msg);
            ImageView errorImg = (ImageView) errorView.findViewById(R.id.iv_error_icon);
            errorImg.setImageResource(drawableId);
            errorMsg.setText(msg);

            if (parentView != null) {
                parentView.addView(errorView);
                LogUtils.loge("添加view----------------");
            }
            errorView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭空白页
     */
    public void closeErrorView() {
        if (errorView != null&&errorView.getVisibility()==View.VISIBLE) {
            LogUtils.loge("关闭页面-----------------");
            errorView.setVisibility(View.GONE);
        }
    }
}
