package com.yundian.star.ui.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.been.StarStarAchBeen;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class StarBuyAchAdapter extends BaseAdapter {
    private Context context;
    private List<StarStarAchBeen.ListBean> starBuyExcList;

    public StarBuyAchAdapter(Activity context, List<StarStarAchBeen.ListBean> starBuyExcList) {
        this.context = context;
        this.starBuyExcList = starBuyExcList;
    }

    @Override
    public int getCount() {
        return starBuyExcList.size();
    }

    @Override
    public Object getItem(int position) {
        return starBuyExcList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_exc_and_ach, null);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content.setText(starBuyExcList.get(position).getAchive());
        return convertView;
    }
    public static class ViewHolder {
        TextView tv_content;
    }
}
