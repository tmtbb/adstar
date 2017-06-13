package com.yundian.star.ui.main.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yundian.star.R;
import com.yundian.star.widget.indicator.IconPagerAdapter;

import java.util.List;


/**
 * Created by yaowang on 2017/5/10.
 */

public class GridViewPageAdapter extends PagerAdapter implements IconPagerAdapter {

    private List<View> views;

    public GridViewPageAdapter(List<View> views){
        this.views = views;

    }

    public void setViews(List<View> views){
        this.views = views;
        notifyDataSetChanged();
    }

    @Override
    public int getIconResId(int index) {
        return R.drawable.iconpageindicator_sel;
    }

    @Override
    public int getCount() {
        return views == null?0:views.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}