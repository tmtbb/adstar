package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.session.SessionCustomization;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.ui.main.activity.MeetStarActivity;
import com.yundian.star.ui.main.activity.StarInfoActivity;
import com.yundian.star.ui.wangyi.session.activity.P2PMessageActivity;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.JudgeIdentityUtils;
import com.yundian.star.utils.LogUtils;

import java.util.List;

public class CommentExpandAdapter extends ExpandableRecyclerAdapter<BookingStarListBean> {
    public static final int TYPE_PERSON = 1001;

    private LRecyclerView recyclerView;
//    private Context context;

    public CommentExpandAdapter(Context context, LRecyclerView recyclerView) {
        super(context);
//        this.context = context;
        this.recyclerView = recyclerView;
        //setItems(getSampleItems());
    }


    public class CommentViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView holdingTime;
        ImageView iv_star_head;
        TextView starName;

        public CommentViewHolder(View view, LRecyclerView recyclerView) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow), recyclerView);
            iv_star_head = (ImageView) view.findViewById(R.id.iv_star_head);
            holdingTime = (TextView) view.findViewById(R.id.tv_holding_time);
            starName = (TextView) view.findViewById(R.id.tv_star_name);
        }

        public void bind(int position) {
            super.bind(position);
            final BookingStarListBean item = visibleItems.get(position);

            String format = String.format(mContext.getResources().getString(R.string.holding_times_count, item.getOwnseconds()) + "");
            SpannableStringBuilder spannable = new SpannableStringBuilder(format);
            spannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_FB9938)), 5, format.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            starName.setText(item.getStarname());
            holdingTime.setText(spannable);
            List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(item.getStarcode());
            if (starInfos != null && starInfos.size() != 0) {
                StarInfo starInfo = starInfos.get(0);
                ImageLoaderUtils.display(mContext, iv_star_head, starInfo.getPic_url());
            }
            iv_star_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StarInfoActivity.goToStarInfoActivity(mContext,item.getStarcode());
                }
            });
        }
    }

    public class CommentChildViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView tv_type_title;

        View newView;

        public CommentChildViewHolder(View view) {
            super(view);
            newView = view;
            tv_type_title = (TextView) view.findViewById(R.id.tv_type_title);
        }

        public void bind(final int position) {
            LogUtils.loge("CommentChildViewHolder----------------visibleItems:" + visibleItems.toString());

            final BookingStarListBean bean = visibleItems.get(position);
            tv_type_title.setText(bean.getTypeTitle());
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (JudgeIdentityUtils.isIdentityed(mContext)) {
                        if (bean.getTypeTitle().equals("与TA聊天")) {
//                        String starname = ;
                            BookingStarListBean bean1 = visibleItems.get(position - 1);
                            LogUtils.loge("与TA聊天:" + bean1.getStarname());
                            SessionCustomization customization = NimUIKit.getCommonP2PSessionCustomization();
                            P2PMessageActivity.start(mContext, bean1.getFaccid(), bean1.getStarcode(), bean1.getStarname(), customization, null);
                        } else if (bean.getTypeTitle().equals("与TA约见")) {
                            BookingStarListBean bean2 = visibleItems.get(position - 2);
                            String pic_url = "";
                            List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(bean2.getStarcode());
                            if (starInfos != null && starInfos.size() != 0) {
                                StarInfo starInfo = starInfos.get(0);
                                pic_url = starInfo.getPic_url();
                            }

                            Intent intent3 = new Intent(mContext, MeetStarActivity.class);
                            intent3.putExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE, 1);
                            intent3.putExtra(AppConstant.STAR_WID, bean2.getUid());
                            intent3.putExtra(AppConstant.STAR_NAME, bean2.getStarname());
                            intent3.putExtra(AppConstant.STAR_CODE, bean2.getStarcode());
                            intent3.putExtra(AppConstant.STAR_HEAD_URL, pic_url);
                            mContext.startActivity(intent3);
                        }
                    }

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                //header中的箭头默认隐藏，如有需要，item_arrow设置为visible即可
                return new CommentViewHolder(inflate(R.layout.adapter_book_star_list, parent), recyclerView);
            case TYPE_PERSON:
                return new CommentChildViewHolder(inflate(R.layout.item_child_comment, parent));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((CommentViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
                ((CommentChildViewHolder) holder).bind(position);
            default:
                break;
        }
    }
}
