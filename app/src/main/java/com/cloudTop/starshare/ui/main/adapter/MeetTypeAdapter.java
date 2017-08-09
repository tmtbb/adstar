package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.been.StatServiceListBean;
import com.cloudTop.starshare.utils.ImageLoaderUtils;

import java.util.List;


/**
 * Created by sll on 2017/5/24.
 */

public class MeetTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<StatServiceListBean.ListBean> list;
    public MeetTypeAdapter(Context context, List<StatServiceListBean.ListBean> list) {
        this.mContext = context;
        this.list = list ;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_meet_type_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemImg = (ImageView) convertView.findViewById(R.id.imagview);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_content.setText(list.get(position).getName());
        ImageLoaderUtils.display(mContext,viewHolder.itemImg,list.get(position).getUrl2());
//        viewHolder.itemImg.setBackgroundResource(R.drawable.sort_nomal);
        return convertView;
    }
    class ViewHolder {
        ImageView itemImg;
        TextView tv_content;
    }

}