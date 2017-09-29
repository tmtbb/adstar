package com.cloudTop.starshare.ui.main.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.ui.main.fragment.BookingStarAskByVideoFragment;
import com.cloudTop.starshare.ui.main.fragment.BookingStarMakeVoiceFragment;
import com.cloudTop.starshare.ui.main.fragment.BookingStarStatusFragment;
import com.cloudTop.starshare.ui.main.fragment.HoldingStarTimeFragment;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 预约名单
 * Created by sll on 2017/5/24.
 * Change By Shi on 2017-9-12 16:47:16
 */

public class BookingStarActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;

    //持有時間
    private HoldingStarTimeFragment holdingStarTimeFragment;
    //約見情況
    private BookingStarStatusFragment bookingStarStatusFragment;
    //視頻提問
    private BookingStarAskByVideoFragment bookingStarAskByVideoFragment;
    //語音定製
    private BookingStarMakeVoiceFragment bookingStarMakeVoiceFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_booking_star;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.booking_star_title));
        ntTitle.setBackVisibility(true);
        initFragment();
        switchTo(1);
    }

    private void initFragment() {
        holdingStarTimeFragment = new HoldingStarTimeFragment();
        bookingStarStatusFragment = new BookingStarStatusFragment();
        bookingStarAskByVideoFragment = new BookingStarAskByVideoFragment();
        bookingStarMakeVoiceFragment = new BookingStarMakeVoiceFragment();
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
            case 3:
                transaction.replace(R.id.fl_container, bookingStarAskByVideoFragment);
                transaction.commit();
                break;
            case 4:
                transaction.replace(R.id.fl_container, bookingStarMakeVoiceFragment);
                transaction.commit();
                break;
        }
    }


    @OnClick({R.id.rb_holding_times, R.id.rb_booking_starus, R.id.rb_booking_ask_by_video, R.id.rb_booking_make_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_holding_times:
                switchTo(1);
                break;
            case R.id.rb_booking_starus:
                switchTo(2);
                break;
            case R.id.rb_booking_ask_by_video:
                switchTo(3);
                break;
            case R.id.rb_booking_make_voice:
                switchTo(4);
                break;
        }
    }
}
