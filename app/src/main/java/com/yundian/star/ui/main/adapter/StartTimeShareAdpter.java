package com.yundian.star.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yundian.star.base.BaseFragmentAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */

public class StartTimeShareAdpter extends BaseFragmentAdapter {
    public StartTimeShareAdpter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm, fragmentList);
    }

    public StartTimeShareAdpter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm, fragmentList, mTitles);
    }
}
