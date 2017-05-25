package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.ui.main.activity.BookingStarActivity;
import com.yundian.star.ui.main.activity.CustomerServiceActivity;
import com.yundian.star.ui.main.activity.GeneralSettingsActivity;
import com.yundian.star.ui.main.activity.RegisterUserActivity;
import com.yundian.star.ui.main.activity.UserAssetsManageActivity;
import com.yundian.star.ui.main.activity.UserSettingActivity;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yundian.star.R.string.customer_service;

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

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.iv_user_info_bg, R.id.headImage, R.id.ll_user_money_bag, R.id.ll_user_order_star, R.id.ll_customer_service, R.id.ll_common_problem, R.id.ll_general_settings, R.id.btn_my_referee})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_info_bg:
                ToastUtils.showShort("点击背景");
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
                break;
        }
    }
}
