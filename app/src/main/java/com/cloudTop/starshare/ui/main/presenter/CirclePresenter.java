package com.cloudTop.starshare.ui.main.presenter;

import android.view.View;

import com.cloudTop.starshare.app.CommentConfig;
import com.cloudTop.starshare.been.CircleFriendBean;
import com.cloudTop.starshare.listener.IDataRequestListener;
import com.cloudTop.starshare.ui.main.contract.CircleContract;
import com.cloudTop.starshare.ui.main.model.CircleModel;
import com.cloudTop.starshare.utils.DatasUtil;

import java.util.List;


public class CirclePresenter implements CircleContract.Presenter {
    private CircleModel circleModel;
    private CircleContract.View view;

    public CirclePresenter(CircleContract.View view) {
        circleModel = new CircleModel();
        this.view = view;
    }

    public void loadData(int loadType) {

        List<CircleFriendBean.CircleListBean> datas = DatasUtil.createCircleDatas();
        if (view != null) {
            view.update2loadData(loadType, datas);
        }
    }


    /**
     * @param circleId
     * @return void    返回类型
     * @throws
     * @Title: deleteCircle
     * @Description: 删除动态
     */
    public void deleteCircle(final String circleId) {
        circleModel.deleteCircle(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.update2DeleteCircle(circleId);
                }
            }
        });
    }

    @Override
    public void addFavort(String symbol, long circle_id,final int uid, final int circlePosition,final String user_name) {
        circleModel.addFavort(symbol, circle_id, uid, new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                CircleFriendBean.CircleListBean.ApproveListBean item = DatasUtil.createFavortItem(uid, user_name);
                if (view != null) {
                    view.update2AddFavorite(circlePosition, item);
                }
            }
        });
    }


    /**
     * @param @param circlePosition
     * @param @param favortId
     * @return void    返回类型
     * @throws
     * @Title: deleteFavort
     * @Description: 取消点赞
     */
    public void deleteFavort(final int circlePosition, final String favortId) {
        circleModel.deleteFavort(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.update2DeleteFavort(circlePosition, favortId);
                }
            }
        });
    }

    /**
     * @param content
     * @param config  CommentConfig
     * @return void    返回类型
     * @throws
     * @Title: addComment
     * @Description: 增加评论
     */
    public void addComment(final String content, final CommentConfig config) {
        if (config == null) {
            return;
        }
        circleModel.addComment(content, config, new IDataRequestListener() {
                    @Override
                    public void loadSuccess(Object object) {
                        CircleFriendBean.CircleListBean.CommentListBean newItem = null;
                        if (config.commentType == CommentConfig.Type.PUBLIC) {
                            newItem = DatasUtil.createPublicComment(content,config.symbol_name);
                        } else if (config.commentType == CommentConfig.Type.REPLY) {
                            newItem = DatasUtil.createReplyComment(content,config.symbol_name);
                        }
                        if(view!=null){
                            view.update2AddComment(config.circlePosition, newItem);
                        }

                    }
                });
    }

    /**
     * @param @param circlePosition
     * @param @param commentId
     * @return void    返回类型
     * @throws
     * @Title: deleteComment
     * @Description: 删除评论
     */
    public void deleteComment(final int circlePosition, final String commentId) {
        circleModel.deleteComment(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.update2DeleteComment(circlePosition, commentId);
                }
            }

        });
    }

    /**
     * @param commentConfig
     */
    public void showEditTextBody(CommentConfig commentConfig) {
        if (view != null) {
            view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }
    }


    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle() {
        this.view = null;
    }
}
