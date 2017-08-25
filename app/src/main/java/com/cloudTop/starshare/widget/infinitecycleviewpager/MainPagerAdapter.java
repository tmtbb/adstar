package com.cloudTop.starshare.widget.infinitecycleviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cloudTop.starshare.ui.main.fragment.HorizontalPagerFragment;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final static int COUNT = 1;

    private final static int HORIZONTAL = 0;
    private final static int TWO_WAY = 1;

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
//        switch (position) {
//            case TWO_WAY:
//                return new TwoWayPagerFragment();
//            case HORIZONTAL:
//            default:
//                return new HorizontalPagerFragment();
//        }
        return new HorizontalPagerFragment();
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
