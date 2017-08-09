package com.cloudTop.starshare.ui.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.ThirdPartLoginControler;


/**
 * Created by dawuhan-0001 on 2016/12/16.
 */

public class ShareControler implements View.OnClickListener {
    private static final String TAG = "UMENG_SHARE";

    private PopupWindow mPopWindow;

    private ShareModle smd;
    private Activity activity;
    public ShareAction saction;
    private UMShareListener umShareListener;

    public ShareControler(Activity act,UMShareListener umShareListener) {
        activity = act;
        this.umShareListener = umShareListener;
        initSharePopupWindow();
        saction = new ShareAction(act);
    }

    public void initShareData(ShareModle smd) {
        this.smd = smd;
    }

    public void setShareCallback(UMShareListener listener) {
        shareListener = listener;
    }

    public void setActionMedia(UMImage actionMedia) {
        if (saction != null) {
            saction.withMedia(actionMedia);
        }

    }

    public interface ShareResultCallback {
        public void onSuccess();

        public void onFailed();
    }

    private ShareResultCallback resultCallback;
    public UMShareListener shareListener;

    public void setShareResultCallback(ShareResultCallback callback) {
        resultCallback = callback;
    }

    /**
     * 分享弹窗
     */
    public void initSharePopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(activity).inflate(R.layout.dialog_live_share, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        TextView pyq = (TextView) contentView.findViewById(R.id.tv_cancel);
        LinearLayout wx = (LinearLayout) contentView.findViewById(R.id.ll_wx);
        LinearLayout wb = (LinearLayout) contentView.findViewById(R.id.ll_wb);
        LinearLayout kj = (LinearLayout) contentView.findViewById(R.id.ll_kj);
        LinearLayout qq = (LinearLayout) contentView.findViewById(R.id.ll_qq);
//        TextView shareCancel = (TextView) contentVickListener(this);
        pyq.setOnClickListener(this);
        wx.setOnClickListener(this);
        wb.setOnClickListener(this);
        kj.setOnClickListener(this);
        qq.setOnClickListener(this);
        // shareCancel.setOnCliView.findViewById(R.id.ll_pyq);
        mPopWindow.setFocusable(false);
        /*mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(false);*/
    }

    //显示PopupWindow
    public void showSharePopupWindow(View parent) {
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    public void hideSharePopupWindow() {
        mPopWindow.dismiss();
    }

//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
////            //社交统计
////            int shareMode;
////            UMPlatformData.UMedia umedia;
////            if (SHARE_MEDIA.WEIXIN == platform) {
////                umedia = UMPlatformData.UMedia.WEIXIN_FRIENDS;
////                shareMode = 2;
////            } else if (SHARE_MEDIA.WEIXIN_CIRCLE == platform) {
////                umedia = UMPlatformData.UMedia.WEIXIN_CIRCLE;
////                shareMode = 1;
////            } else if (SHARE_MEDIA.SINA == platform) {
////                umedia = UMPlatformData.UMedia.SINA_WEIBO;
////                shareMode = 3;
////            } else if (SHARE_MEDIA.QZONE == platform) {
////                umedia = UMPlatformData.UMedia.TENCENT_QZONE;
////                shareMode = 4;
////            } else /*if (SHARE_MEDIA.QQ == platform) */ {
////                umedia = UMPlatformData.UMedia.TENCENT_QQ;
////                shareMode = 5;
////            }
////            UMPlatformData platformData = new UMPlatformData(umedia, SharePrefUtil.getInstance().getUserId());
////            int sex = SharePrefUtil.getInstance().getUserSex();
////            if (sex == 1) {
////                platformData.setGender(UMPlatformData.GENDER.FEMALE); //optional
////            } else if (sex == 0) {
////                platformData.setGender(UMPlatformData.GENDER.MALE); //optional
////            }
////            MobclickAgent.onSocialEvent(DawuhanApplication.getInstance(), platformData);
////            //========
//
//            if (resultCallback != null) resultCallback.onSuccess();
//            if (shareListener != null) {
//                shareListener.onResult(platform);
//                return;
//            }
//            com.umeng.socialize.utils.Log.d(TAG, "platform" + platform);
//            if (platform.name().equals("WEIXIN_FAVORITE")) {
////                ToastUtils.showShort("收藏成功啦");
//            } else {
////                ToastUtils.showShort("分享成功啦");
//                LogUtils.loge("分享成功");
//            }
//
//            // 自家的分享记录
////            DWHNetworkManager.recordShares(smd.getSid(), smd.getSuserid(), smd.getType(), shareMode, null);
//        }

//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            if (resultCallback != null) resultCallback.onFailed();
//            if (shareListener != null) {
//                shareListener.onError(platform, t);
//                return;
//            }
////            ToastUtils.showShort("分享失败啦");
//            if (t != null) {
//                com.umeng.socialize.utils.Log.d(TAG, "throw:" + t.getMessage());
////                if (!BuildConfig.IS_RELEASE) Log.d(TAG, Log.getStackTraceString(t));
//            }
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            if (resultCallback != null) resultCallback.onFailed();
//            if (shareListener != null) {
//                shareListener.onCancel(platform);
//                return;
//            }
////            ToastUtils.showShort("分享取消了");
//        }
//
//        @Override
//        public void onStart(SHARE_MEDIA share_media) {
//        }
//    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pyq:
                startShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                mPopWindow.dismiss();
                if (resultCallback != null) resultCallback.onFailed();
                break;
            case R.id.ll_wx:
                startShare(SHARE_MEDIA.WEIXIN);
                mPopWindow.dismiss();
                if (resultCallback != null) resultCallback.onFailed();
                break;
            case R.id.ll_wb:
                startShare(SHARE_MEDIA.SINA);
                mPopWindow.dismiss();
                if (resultCallback != null) resultCallback.onFailed();
                break;
            case R.id.ll_kj:
                startShare(SHARE_MEDIA.QZONE);
                mPopWindow.dismiss();
                if (resultCallback != null) resultCallback.onFailed();
                break;
            case R.id.ll_qq:
                startShare(SHARE_MEDIA.QQ);
                mPopWindow.dismiss();
                if (resultCallback != null) resultCallback.onFailed();
                break;
            case R.id.tv_cancel:
                mPopWindow.dismiss();
                if (resultCallback != null) resultCallback.onFailed();
                break;
        }
    }

    // 开始分享
    public void startShare(SHARE_MEDIA type) {
        boolean isinstall = ThirdPartLoginControler.isInstall(type, true);
        LogUtils.loge("type：" + type + ",安装状态是：" + isinstall);

        if (!isinstall) {
            return;
        }


        saction.setPlatform(type)
//                .setCallback(umShareListener)
                .setCallback(umShareListener)
                .withText(smd.getText());

        final UMWeb web = new UMWeb(smd.getShareUrl());
        web.setTitle(smd.getTitle());//标题
        web.setDescription(smd.getText());//描述

        //缩略图
        Object imgUrl = smd.getImgUrl();
        if (imgUrl instanceof String) {
            ImageLoader.getInstance().loadImage((String) imgUrl, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    UMImage thumb = new UMImage(activity, imageUri);
                    thumb.compressFormat = Bitmap.CompressFormat.JPEG;
                    showShare(saction, web, thumb);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    UMImage thumb = new UMImage(activity, loadedImage);
                    thumb.compressFormat = Bitmap.CompressFormat.JPEG;
                    showShare(saction, web, thumb);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    UMImage thumb = new UMImage(activity, imageUri);
                    thumb.compressFormat = Bitmap.CompressFormat.JPEG;
                    showShare(saction, web, thumb);
                }
            });
        } else if (imgUrl instanceof Integer) {
            UMImage thumb = new UMImage(activity, (int) imgUrl);
            thumb.compressFormat = Bitmap.CompressFormat.PNG;

            showShare(saction, web, thumb);
        }
    }

    private void showShare(ShareAction saction, UMWeb web, UMImage thumb) {
        if (thumb != null) {
            thumb.compressStyle = UMImage.CompressStyle.QUALITY;
            web.setThumb(thumb);
        }

        saction.withMedia(web);
        saction.share();
    }

    public static class ShareModle {
        private String shareUrl;
        private Object imgUrl;
        private String title;
        private String text;

        // 分享记录时使用
        private String sid = ""; //type=4,5,7时 sid传空或0
        private String suserid = ""; //type=6,7时 suserid传空或0
        private int type = -1; //1:直播 2:回放 3:位视图片 4:个人主页 5:商户主页 6:广告 7:分享位视

        public void setShareContent(String shareUrl, Object imgUrl, String title, String text) {
            this.shareUrl = shareUrl;
            this.imgUrl = imgUrl;
            this.title = title;
            this.text = text;
        }

        public void setShareRecord(String sid, String suserid, int type) {
            this.sid = sid;
            this.suserid = suserid;
            this.type = type;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public Object getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(Object imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSuserid() {
            return suserid;
        }

        public void setSuserid(String suserid) {
            this.suserid = suserid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
