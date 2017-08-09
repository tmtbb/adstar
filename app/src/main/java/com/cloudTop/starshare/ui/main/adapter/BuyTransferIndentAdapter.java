package com.cloudTop.starshare.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cloudTop.starshare.base.BaseFragmentAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class BuyTransferIndentAdapter extends BaseFragmentAdapter {
    public BuyTransferIndentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm, fragmentList);
    }

    public BuyTransferIndentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm, fragmentList, mTitles);
    }
}
