package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yundian.star.R;
import com.yundian.star.utils.DisplayUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.yundian.star.widget.infinitecycleviewpager.HorizontalPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class HorizontalPagerFragment extends Fragment {

    private int screenWidth;
    private HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics dm =new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        horizontalInfiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        initPagerData();

    }
    private List<Integer> mList = new ArrayList<>();
    private void initPagerData() {
        for (int i = 0; i < 10; i++) {
            mList.add(R.drawable.pic4);
        }
        LogUtils.loge("screenWidth:"+screenWidth);
        HorizontalPagerAdapter adapter = new HorizontalPagerAdapter(getContext(), mList);
        horizontalInfiniteCycleViewPager.setAdapter(adapter);
        horizontalInfiniteCycleViewPager.setMaxPageScale(0.85F);
        horizontalInfiniteCycleViewPager.setMinPageScale(0.6F);
        horizontalInfiniteCycleViewPager.setMinPageScaleOffset(-(screenWidth*0.6f)+ DisplayUtil.dip2px(50));
//        horizontalInfiniteCycleViewPager.setOnInfiniteCyclePageTransformListener();

//        horizontalInfiniteCycleViewPager.setCurrentItem(
//                horizontalInfiniteCycleViewPager.getRealItem() + 1
//        );
    }
}
