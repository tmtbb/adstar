package com.cloudTop.starshare.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.widget.CommentListView;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.widget.PraiseListView;
import com.cloudTop.starshare.widget.SnsPopupWindow;


/**
 * Created by yiw on 2016/8/16.
 */
public class CircleViewHolder extends RecyclerView.ViewHolder  {

//    public final static int TYPE_URL = 1;
//    public final static int TYPE_IMAGE = 2;
//    public final static int TYPE_VIDEO = 3;
//
//    public int viewType;

    public ImageView headIv;
    public TextView nameTv;
    public TextView urlTipTv;
    /** 动态的内容 */
    public EditText contentTv;
    public TextView timeTv;
    public TextView tv_time;
    public TextView deleteBtn;
    public ImageView snsBtn;
    /** 点赞列表*/
    public PraiseListView praiseListView;

    public LinearLayout digCommentBody;
    public View digLine;
    public ImageView img_back;

    /** 评论列表 */
    public CommentListView commentList;
    // ===========================
    public SnsPopupWindow snsPopupWindow;

    public CircleViewHolder(View itemView) {
        super(itemView);
        //this.viewType = viewType;

        //ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);

        //initSubView(viewType, viewStub);

        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        digLine = itemView.findViewById(R.id.lin_dig);
        img_back = (ImageView)itemView.findViewById(R.id.img_back);

        contentTv = (EditText) itemView.findViewById(R.id.contentTv);
        //urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);
        //timeTv = (TextView) itemView.findViewById(R.id.timeTv);
       // deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn);
        praiseListView = (PraiseListView) itemView.findViewById(R.id.praiseListView);
        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        commentList = (CommentListView)itemView.findViewById(R.id.commentList);
        snsPopupWindow = new SnsPopupWindow(itemView.getContext());

    }

    //public abstract void initSubView(int viewType, ViewStub viewStub);

}
