package com.cloudTop.starshare.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.ThirdPartLoginControler;

/**
 * Created by sll on 2017/6/13.
 */

public class ShareControlerView implements View.OnClickListener {
    //    private volatile static ShareControlerView instance;
    private UMShareListener umShareListener;
    private Context context;
    private Activity activity;
    private PopupWindow mPopWindow;
    private String webUrl;
    private String title;
    private String describe;
    private String text;
    private String imageurl;

    public ShareControlerView(Activity activity, Context context, UMShareListener umShareListener) {
        this.umShareListener = umShareListener;
        this.context = context;
        this.activity = activity;
        initPop();
    }

    private void initPop() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_live_share, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        TextView pyq = (TextView) contentView.findViewById(R.id.tv_cancel);
        LinearLayout wx = (LinearLayout) contentView.findViewById(R.id.ll_wx);
        LinearLayout wb = (LinearLayout) contentView.findViewById(R.id.ll_wb);
        LinearLayout kj = (LinearLayout) contentView.findViewById(R.id.ll_kj);
        LinearLayout qq = (LinearLayout) contentView.findViewById(R.id.ll_qq);
        LinearLayout ll_pyq = (LinearLayout) contentView.findViewById(R.id.ll_pyq);
        pyq.setOnClickListener(this);
        wx.setOnClickListener(this);
        wb.setOnClickListener(this);
        kj.setOnClickListener(this);
        qq.setOnClickListener(this);
        ll_pyq.setOnClickListener(this);
        mPopWindow.setFocusable(false);
        /*mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(false);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pyq:
                startShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                mPopWindow.dismiss();
                break;
            case R.id.ll_wx:
                startShare(SHARE_MEDIA.WEIXIN);
                mPopWindow.dismiss();
                break;
            case R.id.ll_wb:
                startShare(SHARE_MEDIA.SINA);
                mPopWindow.dismiss();
                break;
            case R.id.ll_kj:
                startShare(SHARE_MEDIA.QZONE);
                mPopWindow.dismiss();
                break;
            case R.id.ll_qq:
                startShare(SHARE_MEDIA.QQ);
                mPopWindow.dismiss();

                break;
            case R.id.tv_cancel:
                mPopWindow.dismiss();
                break;
        }
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void showShareView(View parent) {
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    private void startShare(SHARE_MEDIA type) {
        boolean isinstall = ThirdPartLoginControler.isInstall(type, true);
        LogUtils.loge("type：" + type + ",安装状态是：" + isinstall);
        if (!isinstall) {
            return;
        }

        UMWeb web = new UMWeb(webUrl);
        web.setTitle(title);
        web.setThumb(new UMImage(context, imageurl));
        web.setDescription(describe);
        new ShareAction(activity)
                .withText(text)
                .withMedia(web)
                .setPlatform(type)
                .setCallback(umShareListener).share();
    }

}
