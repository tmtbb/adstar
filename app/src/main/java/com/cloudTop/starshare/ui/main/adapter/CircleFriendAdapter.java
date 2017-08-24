package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.CommentConfig;
import com.cloudTop.starshare.base.BaseRecycleViewAdapter;
import com.cloudTop.starshare.been.ActionItem;
import com.cloudTop.starshare.been.CircleFriendBean;
import com.cloudTop.starshare.helper.CircleViewHolder;
import com.cloudTop.starshare.ui.main.activity.StarInfoActivity;
import com.cloudTop.starshare.ui.main.presenter.CirclePresenter;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.TimeUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.CommentListView;
import com.cloudTop.starshare.widget.PraiseListView;
import com.cloudTop.starshare.widget.SnsPopupWindow;
import com.cloudTop.starshare.widget.emoji.MoonUtils;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2017/7/12.
 */

public class CircleFriendAdapter extends BaseRecycleViewAdapter{
    private final View view;
    private Context context;
    private CirclePresenter presenter;

    public void setCirclePresenter(CirclePresenter presenter){
        this.presenter = presenter;
    }

    public CircleFriendAdapter(Context context,View view) {
        this.context = context;
        this.view = view;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_circle_item, parent, false);
        return new CircleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CircleViewHolder circleViewHolder = (CircleViewHolder) holder;
        final CircleFriendBean.CircleListBean circleItem = (CircleFriendBean.CircleListBean) datas.get(position);
        final String content = circleItem.getContent();
        final List<CircleFriendBean.CircleListBean.ApproveListBean> favortDatas = circleItem.getApprove_list();
        final List<CircleFriendBean.CircleListBean.CommentListBean> commentsDatas = circleItem.getComment_list();
        boolean hasFavort = circleItem.hasFavort();
        boolean hasComment = circleItem.hasComment();
        ImageLoaderUtils.displaySmallPhoto(context,circleViewHolder.headIv,circleItem.getHead_url());
        circleViewHolder.nameTv.setText(circleItem.getSymbol_name());
        if (TextUtils.isEmpty(circleItem.getPic_url())){
            circleViewHolder.img_back.setVisibility(View.GONE);
        }else {
            //ImageLoaderUtils.displaySmallPhoto(context,circleViewHolder.img_back,circleItem.getPic_url());
            ImageLoaderUtils.displayWithDefaultImg(context, circleViewHolder.img_back, circleItem.getPic_url(),R.drawable.rec_bg);
            circleViewHolder.img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(circleItem.getPic_url());
                }
            });
        }
        if(!TextUtils.isEmpty(content)){
            circleViewHolder.contentTv.setText(content);
            MoonUtils.replaceEmoticons(context,circleViewHolder.contentTv.getText(),0,circleViewHolder.contentTv.getText().length());
        }
        circleViewHolder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        circleViewHolder.tv_time.setText(TimeUtil.getfriendlyTime(circleItem.getCreate_time()*1000));
        if(hasFavort || hasComment){
            if(hasFavort){//处理点赞列表
                circleViewHolder.praiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {

                    }
                });
                circleViewHolder.praiseListView.setDatas(favortDatas);
                circleViewHolder.praiseListView.setVisibility(View.VISIBLE);
            }else{
                circleViewHolder.praiseListView.setVisibility(View.GONE);
            }

            if(hasComment){//处理评论列表
                circleViewHolder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int commentPosition) {
                        CircleFriendBean.CircleListBean.CommentListBean commentItem = commentsDatas.get(commentPosition);
                        if(1==commentItem.getDirection()){//回复明星的评论
                            if(presenter != null){
                                CommentConfig config = new CommentConfig();
                                config.circlePosition = position;
                                config.commentPosition = commentPosition;
                                config.Circle_id = circleItem.getCircle_id();
                                config.commentType = CommentConfig.Type.REPLY;
                                config.symbol_name = circleItem.getSymbol_name();
                                config.symbol_code = circleItem.getSymbol();
                                presenter.showEditTextBody(config);
                            }
                        }
                    }
                });

                circleViewHolder.commentList.setDatas(commentsDatas,circleItem.getSymbol_name());
                circleViewHolder.commentList.setVisibility(View.VISIBLE);

            }else {
                circleViewHolder.commentList.setVisibility(View.GONE);
            }
            circleViewHolder.digCommentBody.setVisibility(View.VISIBLE);
        }else{
            circleViewHolder.digCommentBody.setVisibility(View.GONE);
        }
        circleViewHolder.digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);

        final SnsPopupWindow snsPopupWindow = circleViewHolder.snsPopupWindow;
        snsPopupWindow.getmActionItems().get(0).mTitle = String.format("赞(%d秒)",circleItem.getApprove_dec_time());
        snsPopupWindow.getmActionItems().get(1).mTitle = String.format("评论(%d秒)",circleItem.getComment_dec_time());
        snsPopupWindow.update();
        snsPopupWindow.setmItemClickListener(new PopupItemClickListener(position, circleItem, SharePrefUtil.getInstance().getUserId(),SharePrefUtil.getInstance().getUserNickName()));
        circleViewHolder.snsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出popupwindow
                snsPopupWindow.showPopupWindow(view);
            }
        });
        circleViewHolder.headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarInfoActivity.goToStarInfoActivity(context,circleItem.getSymbol());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }


    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener{
        private int mFavorId;
        //动态在列表中的位置
        private int mCirclePosition;
        private long mLasttime = 0;
        private String mUserName ;
        private CircleFriendBean.CircleListBean mCircleItem ;

        public PopupItemClickListener(int circlePosition, CircleFriendBean.CircleListBean circleItem, int favorId,String user_name){
            this.mFavorId = favorId;
            this.mCirclePosition = circlePosition;
            this.mCircleItem = circleItem;
            this.mUserName = user_name;
        }

        @Override
        public void onItemClick(ActionItem actionitem, int position) {
            switch (position) {
                case 0://点赞、取消点赞
                    if(System.currentTimeMillis()-mLasttime<700)//防止快速点击操作
                        return;
                    mLasttime = System.currentTimeMillis();
                    if(presenter != null){
                        //判断是否已点赞
                        int curUserFavortId = mCircleItem.getCurUserFavortId(SharePrefUtil.getInstance().getUserId());
                        if (curUserFavortId!=mFavorId) {
                            presenter.addFavort(mCircleItem.getSymbol(),mCircleItem.getCircle_id(),SharePrefUtil.getInstance().getUserId(),mCirclePosition,mUserName);
                        } else {//取消点赞
                            //presenter.deleteFavort(mCirclePosition, mFavorId);
                            ToastUtils.showShort("您已经赞过");
                        }
                    }
                    break;
                case 1://发布评论
                    if(presenter != null){
                        CommentConfig config = new CommentConfig();
                        config.circlePosition = mCirclePosition;
                        config.commentType = CommentConfig.Type.PUBLIC;
                        config.Circle_id = mCircleItem.getCircle_id();
                        config.symbol_name = mCircleItem.getSymbol_name();
                        config.symbol_code = mCircleItem.getSymbol();
                        presenter.showEditTextBody(config);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void showPopupWindow(String prc_url) {
        View popView = LayoutInflater.from(context).inflate(R.layout.popwindow_imegview, null);
        PhotoView photoView = (PhotoView) popView.findViewById(R.id.iv_photo);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(popView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        //ImageLoaderUtils.displayWithDefaultImg(context, photoView, prc_url, R.drawable.rec_bg);
        ImageLoaderUtils.displayWithPreviewImg(context, photoView, prc_url, R.drawable.rec_bg);
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

}
