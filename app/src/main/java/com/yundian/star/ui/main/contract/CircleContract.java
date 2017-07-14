package com.yundian.star.ui.main.contract;


import com.yundian.star.app.CommentConfig;
import com.yundian.star.been.CircleFriendBean;

import java.util.List;


/**
 * Created by suneee on 2016/7/15.
 */
public interface CircleContract {

    interface View  {
        void update2DeleteCircle(String circleId);
        void update2AddFavorite(int circlePosition, CircleFriendBean.CircleListBean.ApproveListBean addItem);
        void update2DeleteFavort(int circlePosition, String favortId);
        void update2AddComment(int circlePosition, CircleFriendBean.CircleListBean.CommentListBean addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, List<CircleFriendBean.CircleListBean> datas);
    }

    interface Presenter {
        void loadData(int loadType);
        void deleteCircle(final String circleId);
        void addFavort(String symbol,long circle_id,int uid,final int circlePosition,String user_name);
        void deleteFavort(final int circlePosition, final String favortId);
        void deleteComment(final int circlePosition, final String commentId);

    }
}
