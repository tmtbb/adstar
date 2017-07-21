package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.StarListReturnBean;
import com.yundian.star.utils.ImageLoaderUtils;

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
        ImageLoaderUtils.displaySmallPhoto(mContext,imge,barrageInfoBean.getPic());
        name.setText(barrageInfoBean.getName());
    }

}
