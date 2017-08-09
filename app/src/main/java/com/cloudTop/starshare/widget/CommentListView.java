package com.cloudTop.starshare.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.been.CircleFriendBean;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.widget.emoji.MoonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiwei on 16/7/9.
 */
public class CommentListView extends LinearLayout {
    private int itemColor;
    private int itemSelectorColor;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private List<CircleFriendBean.CircleListBean.CommentListBean> mDatas;
    private LayoutInflater layoutInflater ;
    private String symbolName;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setDatas(List<CircleFriendBean.CircleListBean.CommentListBean> datas,String Symbol_name){
        if(datas == null ){
            datas = new ArrayList<CircleFriendBean.CircleListBean.CommentListBean>();
        }
        mDatas = datas;
        symbolName = Symbol_name;
        notifyDataSetChanged();
    }

    public List<CircleFriendBean.CircleListBean.CommentListBean> getDatas(){
        return mDatas;
    }

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PraiseListView, 0, 0);
        try {
            //textview的默认颜色
            itemColor = typedArray.getColor(R.styleable.PraiseListView_item_color, getResources().getColor(R.color.color_0092CA));
            itemSelectorColor = typedArray.getColor(R.styleable.PraiseListView_item_selector_color, getResources().getColor(R.color.color_cccccc));

        }finally {
            typedArray.recycle();
        }
    }

    public void notifyDataSetChanged(){

        removeAllViews();
        if(mDatas == null || mDatas.size() == 0){
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0; i<mDatas.size(); i++){
            final int index = i;
            View view = getView(index);
            if(view == null){
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }

    }

    private View getView(final int position){
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = layoutInflater.inflate(R.layout.item_comment, null, false);
        FrameLayout frameLayout = (FrameLayout) convertView.findViewById(R.id.fl_layout);
        EditText commentTv = (EditText) convertView.findViewById(R.id.commentTv);
        TextView tv_onclick = (TextView) convertView.findViewById(R.id.tv_onclick);
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);

        CircleFriendBean.CircleListBean.CommentListBean bean = mDatas.get(position);
        String name = bean.getUser_name();
        long id = bean.getUid();
        String toReplyName = "";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (bean.getDirection()==1){
            builder.append(setClickableSpan(symbolName, 0));
            builder.append(" 回复 ");
            builder.append(setClickableSpan(bean.getUser_name(), bean.getUid()));
        }else if (bean.getDirection()==2){
            builder.append(setClickableSpan(bean.getUser_name(), bean.getUid()));
            builder.append(" 回复 ");
            builder.append(setClickableSpan(symbolName, 0));
        }else if (bean.getDirection()==0){
            builder.append(setClickableSpan(bean.getUser_name(), bean.getUid()));
        }
        builder.append(": ");
        builder.append(bean.getContent());
        commentTv.setText(builder);
        MoonUtils.replaceEmoticons(getContext(),commentTv.getText(),0,commentTv.getText().length());
        //commentTv.setMovementMethod(circleMovementMethod);
        tv_onclick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.loge("点击了");
                if (circleMovementMethod.isPassToTv()) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(position);
                    }
                }
            }
        });
        tv_onclick.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if(onItemLongClickListener!=null){
                        onItemLongClickListener.onItemLongClick(position);
                    }
                    return true;
                }
                return false;
            }
        });

        return convertView;
    }

    @NonNull
    private SpannableString setClickableSpan(final String textStr, final long id) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable(itemColor){
                                    @Override
                                    public void onClick(View widget) {
                                        LogUtils.loge("点击了");
                                        //Toast.makeText(MyApplication.getContext(), textStr + " &id = " + id, Toast.LENGTH_SHORT).show();
                                        //ToastUtils.showShort(textStr + " &id = " + id);
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public static interface OnItemLongClickListener{
        public void onItemLongClick(int position);
    }



}
