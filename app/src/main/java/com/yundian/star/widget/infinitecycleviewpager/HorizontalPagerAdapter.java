package com.yundian.star.widget.infinitecycleviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yundian.star.R;

import java.util.List;


/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List mList;
    public HorizontalPagerAdapter(Context context, List list) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

//    @Override
//    public int getItemPosition(final Object object) {
//        return POSITION_NONE;
//    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.view_card_item, container, false);
        ImageView img_item = (ImageView)view.findViewById(R.id.img_item);
        LinearLayout ll_content = (LinearLayout)view.findViewById(R.id.ll_content);
        img_item.setImageResource(R.drawable.pic4);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
