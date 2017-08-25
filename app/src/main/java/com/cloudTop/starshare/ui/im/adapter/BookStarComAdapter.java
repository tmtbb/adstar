package com.cloudTop.starshare.ui.im.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarMailListBeen;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.netease.nim.uikit.common.ui.drop.DropFake;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

/**
 * Created by sll on 2017/5/24.
 */

public class BookStarComAdapter extends ListBaseAdapter<StarMailListBeen.DepositsinfoBean> {
    List<RecentContact> contactList ;
    public BookStarComAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_book_star_com;
    }

    public void addRecentContactList(List<RecentContact> contacts) {
        this.contactList = contacts;
    }


    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
            TextView tv_star_name = holder.getView(R.id.tv_star_name);
            ImageView iv_star_head = holder.getView(R.id.iv_star_head);
            DropFake dropFake = holder.getView(R.id.tab_new_msg_label);
            TextView tv_star_job = holder.getView(R.id.tv_star_job);
            StarMailListBeen.DepositsinfoBean listBean= mDataList.get(position);
            LogUtils.loge("code"+listBean.getStarcode());
            if (TextUtils.isEmpty(listBean.getWork())){
                tv_star_job.setVisibility(View.GONE);
            }else {
                tv_star_job.setVisibility(View.VISIBLE);
                tv_star_job.setText(listBean.getWork());
            }
            ImageLoaderUtils.displaySmallPhoto(mContext,iv_star_head,listBean.getHead_url_tail());
            tv_star_name.setText(listBean.getStarname());

            LogUtils.loge("contactList"+contactList.size());
            if (contactList!=null&&contactList.size()!=0){
                for (RecentContact contact : contactList) {
                    LogUtils.loge("contact_adpter_contact.getUnreadCount():"+contact.getUnreadCount()+contact.getContactId());
                    if (contact.getContactId().equals(listBean.getFaccid())&&contact.getUnreadCount()>0){
                        dropFake.setVisibility(View.VISIBLE);
                        dropFake.setText(String.valueOf(contact.getUnreadCount()));
                    }else if (contact.getContactId().equals(listBean.getFaccid())&&contact.getUnreadCount()==0){
                        dropFake.setVisibility(View.GONE);
                    }
                }
            }else {
                dropFake.setVisibility(View.GONE);
            }
    }


}