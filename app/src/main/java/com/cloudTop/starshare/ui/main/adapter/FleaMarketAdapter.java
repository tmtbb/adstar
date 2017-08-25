package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.ImageLoaderUtils;

/**
 * Created by Administrator on 2017/7/21.
 */

public class FleaMarketAdapter extends ListBaseAdapter<StarListReturnBean.SymbolInfoBean> {
    public FleaMarketAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_flea_market;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        StarListReturnBean.SymbolInfoBean barrageInfoBean = mDataList.get(position);
        ImageView imge = holder.getView(R.id.imge);
        TextView name = holder.getView(R.id.name);
        ImageLoaderUtils.displaySmallPhoto(mContext,imge,barrageInfoBean.getPic_tail());
        name.setText(barrageInfoBean.getName());
    }

}
