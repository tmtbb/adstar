package com.yundian.star.ui.main.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.ui.main.fragment.BookingStarStatusFragment;
import com.yundian.star.ui.main.fragment.HoldingStarTimeFragment;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 预约的明星
 * Created by sll on 2017/5/24.
 */

public class BookingStarActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    private HoldingStarTimeFragment holdingStarTimeFragment;
    private BookingStarStatusFragment bookingStarStatusFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_booking_star;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.booking_star_list));
        ntTitle.setBackVisibility(true);
        initFragment();
        switchTo(1);
    }

    private void initFragment() {
        holdingStarTimeFragment = new HoldingStarTimeFragment();
        bookingStarStatusFragment = new BookingStarStatusFragment();
    }


    private void switchTo(int type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case 1:
                transaction.replace(R.id.fl_container, holdingStarTimeFragment);
                transaction.commit();
                break;
            case 2:
                transaction.replace(R.id.fl_container, bookingStarStatusFragment);
                transaction.commit();
                break;
        }
    }


    @OnClick({R.id.rb_holding_times, R.id.rb_booking_starus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_holding_times:
                switchTo(1);
                break;
            case R.id.rb_booking_starus:
                switchTo(2);
                break;
        }
    }
}
