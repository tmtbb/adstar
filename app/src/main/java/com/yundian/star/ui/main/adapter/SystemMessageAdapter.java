package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.been.OrderReturnBeen;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/6/15.
 */

public class SystemMessageAdapter extends RecyclerView.Adapter {
    private ArrayList<OrderReturnBeen.OrdersListBean> listData;
    private Context mContext ;
    private long uid ;

    public SystemMessageAdapter(Context context , ArrayList<OrderReturnBeen.OrdersListBean> list,long id) {
        this.listData = list;
        this.mContext = context ;
        this.uid=id;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_system_message, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderReturnBeen.OrdersListBean bean = listData.get(position);
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.tv_time.setText(TimeUtil.formatData(TimeUtil.dateFormatYMDHM, bean.getOpenTime()));
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(listData.get(position).getSymbol());
        String ss = null ;
        if (bean.getBuyUid()==uid){
            ss = "求购," ;
            if (bean.getBuyHandle()==0&&bean.getHandle()!=-1){
                ss = "求购,匹配成功。";
                viewHolder.tv_check.setVisibility(View.VISIBLE);
                viewHolder.tv_check.setText("未确认");
                viewHolder.tv_status.setVisibility(View.GONE);
                viewHolder.tv_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnImgClickLitener.onImgClick(v,position);
                    }
                });
            }else if (bean.getHandle()!=-1){
                viewHolder.tv_check.setVisibility(View.GONE);
                viewHolder.tv_status.setVisibility(View.VISIBLE);
                if (bean.getHandle()==2){
                    viewHolder.tv_status.setText("交易完成。");
                }else if (bean.getHandle()==1){
                    viewHolder.tv_status.setText("已确认。");
                }else if (bean.getHandle()==0){
                    viewHolder.tv_status.setText("订单生成");
                }else if (bean.getHandle()==-2){
                    ss = "求购时间，";
                    viewHolder.tv_status.setText("对方时间不足。");
                }else if (bean.getHandle()==-3){
                    ss = "求购时间，";
                    viewHolder.tv_status.setText("您金额不足。");
                }else if (bean.getHandle()==-4){
                    viewHolder.tv_status.setText("交易失败。");
                }
            }else if (bean.getHandle()==-1){
                viewHolder.tv_check.setVisibility(View.GONE);
                viewHolder.tv_status.setVisibility(View.VISIBLE);
                viewHolder.tv_status.setText("取消订单。");
            }
        }else {
            ss = "转让，" ;
            if (bean.getSellHandler()==0&&bean.getHandle()!=-1){
                ss = "转让,匹配成功。";
                viewHolder.tv_check.setVisibility(View.VISIBLE);
                viewHolder.tv_check.setText("未确认");
                viewHolder.tv_status.setVisibility(View.GONE);
                viewHolder.tv_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnImgClickLitener.onImgClick(v,position);
                    }
                });
            }else if (bean.getHandle()!=-1){
                viewHolder.tv_check.setVisibility(View.GONE);
                viewHolder.tv_status.setVisibility(View.VISIBLE);
                if (bean.getHandle()==2){
                    viewHolder.tv_status.setText("交易完成。");
                }else if (bean.getHandle()==1){
                    viewHolder.tv_status.setText("已确认。");
                }else if (bean.getHandle()==0){
                    viewHolder.tv_status.setText("订单生成");
                }else if (bean.getHandle()==-2){
                    ss = "转让时间，";
                    viewHolder.tv_status.setText("您时间不足。");
                }else if (bean.getHandle()==-3){
                    ss = "转让时间，";
                    viewHolder.tv_status.setText("对方金额不足。");
                }else if (bean.getHandle()==-4){
                    viewHolder.tv_status.setText("交易失败。");
                }
            }else if (bean.getHandle()==-1){
                viewHolder.tv_check.setVisibility(View.GONE);
                viewHolder.tv_status.setVisibility(View.VISIBLE);
                viewHolder.tv_status.setText("取消订单。");
            }

        }
        if (starInfos.size()!=0){
            StarInfo starInfo = starInfos.get(0);
            viewHolder.tv_content.setText(String.format(mContext.getString(R.string.name_code),starInfo.getName(),starInfo.getCode())+ss);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_check)
        TextView tv_check;
        @Bind(R.id.tv_status)
        TextView tv_status;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //回调接口
    public interface OnImgClickLitener {
        void onImgClick(View view ,int position);
    }

    private OnImgClickLitener mOnImgClickLitener;

    public void setOnImgClickLitener(OnImgClickLitener mOnImgClickLitener) {
        this.mOnImgClickLitener = mOnImgClickLitener;
    }
    public void addAll(Collection<OrderReturnBeen.OrdersListBean> list) {
        int lastIndex = this.listData.size();
        if (this.listData.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }
    public void clear() {
        listData.clear();
        notifyDataSetChanged();
    }
}
