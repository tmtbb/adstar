package com.cloudTop.starshare.ui.main.model;

import com.cloudTop.starshare.listener.IDataRequestListener;
import com.cloudTop.starshare.app.CommentConfig;
import com.cloudTop.starshare.been.ResultBeen;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.ToastUtils;


public class CircleModel {
	
	
	public CircleModel(){
		//
	}

	public void loadData(final IDataRequestListener listener){
		requestServer(listener);
	}
	
	public void deleteCircle( final IDataRequestListener listener) {
		requestServer(listener);

	}

	public void addFavort(String symbol, long circle_id, int uid, final IDataRequestListener listener) {
		NetworkAPIFactoryImpl.getInformationAPI().getPraisestar(symbol, circle_id, uid, new OnAPIListener<ResultBeen>() {
			@Override
			public void onError(Throwable ex) {
				ToastUtils.showShort("点赞失败");
			}

			@Override
			public void onSuccess(ResultBeen resultBeen) {
				if (resultBeen.getResult()==1){
					requestServer(listener);
				}else if (resultBeen.getResult()==-1506){
					ToastUtils.showShort("您未持有该明星的时间，不能点赞");
				}else if (resultBeen.getResult()==-1505){
					ToastUtils.showShort("您持有该明星的时间不够");
				}
			}
		});

	}

	public void deleteFavort(final IDataRequestListener listener) {
		requestServer(listener);
	}

	public void addComment(String content,CommentConfig config,final IDataRequestListener listener) {
		int type = -1 ;
		if (config.commentType==CommentConfig.Type.PUBLIC){
			type = 0;
		}else if (config.commentType==CommentConfig.Type.REPLY){
			type = 2 ;
		}
		NetworkAPIFactoryImpl.getInformationAPI().getUserAddComment(config.symbol_code, config.Circle_id,
				SharePrefUtil.getInstance().getUserId(), type, content, new OnAPIListener<ResultBeen>() {
					@Override
					public void onError(Throwable ex) {
						ToastUtils.showShort("评论失败");
					}

					@Override
					public void onSuccess(ResultBeen resultBeen) {
						if (resultBeen.getResult()==1){
							requestServer(listener);
						}else if (resultBeen.getResult()==-1506){
							ToastUtils.showShort("您未持有该明星的时间，不能评论");
						}else if (resultBeen.getResult()==-1505){
							ToastUtils.showShort("您持有该明星的时间不够");
						}
					}
				});

	}

	public void deleteComment( final IDataRequestListener listener) {
		requestServer(listener);
	}
	
	/**
	 * 
	* @Title: requestServer 
	* @Description: 与后台交互, 因为demo是本地数据，不做处理
	* @param  listener    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void requestServer(final IDataRequestListener listener) {
				listener.loadSuccess(1);
	}
}
