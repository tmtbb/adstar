package com.yundian.star.ui.main.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;

import com.yundian.star.been.AssetDetailsBean;
import com.yundian.star.been.EventBusMessage;

import com.yundian.star.been.IdentityInfoBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;

import com.yundian.star.ui.main.activity.BookingStarActivity;
import com.yundian.star.ui.main.activity.CustomerServiceActivity;
import com.yundian.star.ui.main.activity.GeneralSettingsActivity;
import com.yundian.star.ui.main.activity.UserAssetsManageActivity;
import com.yundian.star.ui.main.activity.UserSettingActivity;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.utils.FormatUtil;
import com.yundian.star.utils.ImageLoaderUtils;

import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/5/5.
 */

public class UserInfoFragment extends BaseFragment {


    @Bind(R.id.iv_user_info_bg)
    ImageView userInfoBg;
    @Bind(R.id.tv_user_name)
    TextView userName;
    @Bind(R.id.tv_user_total_assets)
    TextView userTotalAssets;
    @Bind(R.id.tv_order_star)
    TextView userOrderStar;
    @Bind(R.id.headImage)
    RoundImageView headImage;
    @Bind(R.id.ll_user_money_bag)
    LinearLayout moneyBag;
    @Bind(R.id.ll_user_order_star)
    LinearLayout orderStar;
    @Bind(R.id.ll_customer_service)
    LinearLayout customerService;
    @Bind(R.id.ll_common_problem)
    LinearLayout commonProblem;
    @Bind(R.id.ll_general_settings)
    LinearLayout generalSettings;
    @Bind(R.id.btn_my_referee)
    Button myReferee;
    private boolean flag = true;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
//        if (!TextUtils.isEmpty(SharePrefUtil.getInstance().getPhoneNum())) {
//            initData();
//            LogUtils.loge("---登陆成功了,更新数据和请求余额");
//            requestBalance();
//        }
    }

    private void initData() {
        String referee = SharePrefUtil.getInstance().getUserReferee();
        if (referee == null) {
            myReferee.setText(getString(R.string.my_referee));
        } else {
            myReferee.setText(String.format(getString(R.string.dialog_title_referee2), referee));
        }
        String userPhotoUrl = SharePrefUtil.getInstance().getUserPhotoUrl();
        if (TextUtils.isEmpty(userPhotoUrl)) {
            headImage.setImageResource(R.drawable.user_default_head);
        } else {
            ImageLoaderUtils.display(getContext(), headImage, userPhotoUrl);
        }
        String userNickName = SharePrefUtil.getInstance().getUserNickName();
        if (TextUtils.isEmpty(userNickName)) {
            userName.setText(SharePrefUtil.getInstance().getPhoneNum());
        } else {
            userName.setText(userNickName);
        }

        userOrderStar.setText(SharePrefUtil.getInstance().getOrderStar() + "");
    }


    @OnClick({R.id.iv_user_info_bg, R.id.headImage, R.id.ll_user_money_bag, R.id.ll_user_order_star, R.id.ll_customer_service, R.id.ll_common_problem, R.id.ll_general_settings, R.id.btn_my_referee})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_info_bg:
//                requestBalance();
                break;
            case R.id.headImage:
                startActivity(UserSettingActivity.class);
                break;
            case R.id.ll_user_money_bag:
                ToastUtils.showShort("我的钱包");
                startActivity(UserAssetsManageActivity.class);
                break;
            case R.id.ll_user_order_star:
                startActivity(BookingStarActivity.class);
                break;
            case R.id.ll_customer_service:
                startActivity(CustomerServiceActivity.class);
                break;
            case R.id.ll_common_problem:
                break;
            case R.id.ll_general_settings:
                startActivity(GeneralSettingsActivity.class);
                break;
            case R.id.btn_my_referee:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogLayout = inflater.inflate(R.layout.dialog_user_referee, null);
        final EditText edtInput = (EditText) dialogLayout.findViewById(R.id.et_input_referee);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setView(dialogLayout)
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (TextUtils.isEmpty(edtInput.getText().toString().trim())) {
                                    ToastUtils.showShort(getResources().getString(R.string.dialog_input_tip));
                                } else {
                                    SharePrefUtil.getInstance().putUserReferee(edtInput.getText().toString().trim());
                                    myReferee.setText(String.format(getString(R.string.dialog_title_referee2), edtInput.getText().toString().trim()));
                                    dialog.dismiss();
                                }
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });
        builder.show();
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessageEventBus(EventBusMessage eventBusMessage) {
        switch (eventBusMessage.Message) {
            case 1:  //成功
                LogUtils.loge("用户登录成功了，请刷新数据----------");
                initData();
                requestBalance();
                requestIdentity();
                break;
        }
    }

    private void requestIdentity() {
        NetworkAPIFactoryImpl.getDealAPI().identity(new OnAPIListener<IdentityInfoBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("实名信息失败-----------");
            }

            @Override
            public void onSuccess(IdentityInfoBean identityInfoBean) {
                LogUtils.loge("实名信息成功-----------" + identityInfoBean.toString());
                SharePrefUtil.getInstance().setRealName(identityInfoBean.getRealname());
                SharePrefUtil.getInstance().setIdnum(identityInfoBean.getId_card());
            }
        });
    }

    private void requestBalance() {
        NetworkAPIFactoryImpl.getDealAPI().balance(new OnAPIListener<AssetDetailsBean>() {
            @Override
            public void onSuccess(AssetDetailsBean bean) {
                LogUtils.loge("余额请求成功:" + bean.toString());
                userTotalAssets.setText(bean.getBalance() + "");
                SharePrefUtil.getInstance().saveAssetInfo(bean);
            }

            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("余额请求失败:" + ex.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flag) {
            EventBus.getDefault().register(this); // EventBus注册广播()
            flag = false;//更改标记,使其不会再进行多次注册
        }
        if (!TextUtils.isEmpty(SharePrefUtil.getInstance().getPhoneNum())) {
            initData();
            requestBalance();
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        EventBus.getDefault().removeAllStickyEvents();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
