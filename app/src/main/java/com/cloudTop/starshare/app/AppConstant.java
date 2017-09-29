package com.cloudTop.starshare.app;

/**
 * des:
 * Created by ysl
 * on 2016.09.10:44
 */
public class AppConstant {
   //网红code
    public static final String STAR_CODE="STAR_CODE";
    public static final String PUBLISH_TYPE="PUBLISH_TYPE";
    public static final String IS_PRESELL="IS_PRESELL";
    public static final String STAR_NAME="STAR_NAME";
    public static final String STAR_WID="STAR_WID";
    public static final String STAR_HEAD_URL="STAR_HEAD_URL";
    public static final String STAR_BACKGROUND_URL="STAR_BACKGROUND_URL";
    public static final String MATCH_SUCESS_INFO="MATCH_SUCESS_INFO";
    public static final String MATCH_SUCESS_ORDER_INFO="MATCH_SUCESS_ORDER_INFO";
    public static final String HOME_CURRENT_TAB_POSITION="HOME_CURRENT_TAB_POSITION";
    public static final String MENU_SHOW_HIDE="MENU_SHOW_HIDE";
    public static final String AUCTION_TYPE="AUCTION_TYPE";
    public static final String SYMBOL_INFO_BEAN="SYMBOL_INFO_BEAN";

   /*行情*/
    public static final String MARKET_DETAIL_NAME = "market_detail_name";
    public static final String MARKET_DETAIL_TYPE= "market_detail_type";
    public static final String MARKET_DETAIL_IN_TYPE= "market_detail_in_type";
    public static final String MARKET_STARTIME_TYPE= "market_star_time_type";
    public static final String FANS_HOT_TYPE= "fans_hot_type";
    public static final String BUY_TRANSFER_INTENT_TYPE= "buy_transfer_indent_type";


    public static final String NEWS_TYPE = "news_type";
    public static final String CHANNEL_POSITION = "channel_position";
    public static final String CHANNEL_MINE = "CHANNEL_MINE";
    public static final String CHANNEL_MORE = "CHANNEL_MORE";
    public static final String CHANNEL_SWAP = "CHANNEL_SWAP";
    public static final String NEWS_CHANNEL_CHANGED = "NEWS_CHANNEL_CHANGED";

    /* 视频*/
    public static final String VIDEO_TYPE = "VIDEO_TYPE";

    public static String NEWS_LIST_TO_TOP = "NEWS_LIST_TO_TOP";//列表返回顶部
    public static String ZONE_PUBLISH_ADD = "ZONE_PUBLISH_ADD";//发布说说

    public static String NEWS_POST_ID = "NEWS_POST_ID";//新闻详情id
    public static String NEWS_LINK = "NEWS_LINK";
    public static String NEWS_TITLE = "NEWS_TITLE";

    public static final String PHOTO_DETAIL_IMGSRC = "photo_detail_imgsrc";
    public static final String PHOTO_DETAIL = "photo_detail";
    public static final String PHOTO_TAB_CLICK = "PHOTO_TAB_CLICK";
    public static final String VIDEO_PIC_PATH = "VIDEO_PIC_PATH";
    public static final String VIDEO_PATH = "VIDEO_PATH";
    public static final String VIDEO_TIME = "VIDEO_TIME";

    public static final String NEWS_IMG_RES = "news_img_res";
    public static final String TRANSITION_ANIMATION_NEWS_PHOTOS = "transition_animation_news_photos";


    public static final Short TODAY_ENTRUST_OPCODE = SocketAPIConstant.OperateCode.TodayEntrust;
    public static final Short HISTORY_ENTRUST_OPCODE =  SocketAPIConstant.OperateCode.HistoryEntrust;
    public static final Short HISTORY_DEAL_OPCODE = SocketAPIConstant.OperateCode.HistoryDeal;
    public static final Short TODAY_DEAL_OPCODE = SocketAPIConstant.OperateCode.TodayDeal;

    //冻结用户操作
    public static final Short FREEZE_MOVEMENT = -1000;
    public static final String IS_ONE = "IS_ONE";

    //首页信息
    public static final String HOME_PAGE_STAR_INFO = "HOME_PAGE_STAR_INFO";

}
