package com.cloudTop.starshare.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConfig;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.QRCodeUtil;
import com.cloudTop.starshare.utils.ThirdPartLoginControler;
import com.cloudTop.starshare.utils.ToastUtils;
import com.netease.nimlib.jsbridge.util.LogUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.cloudTop.starshare.R.id.img_zxing;

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
    private String starName;
    private String starWork;
    private String describe;
    private String text;
    private String imageurl;
    private ImageView imageView;
    private LinearLayout ll_main;
    private FrameLayout fl_qr_code_main;
    private ImageView iv_icon;
    private TextView tv_name;
    private TextView star_work;

    public ShareControlerView(Activity activity, Context context, UMShareListener umShareListener) {
        this.umShareListener = umShareListener;
        this.context = context;
        this.activity = activity;
        initPop();
    }
    private String expendLine = "";
    private Bitmap bitmap;
    private void initPop() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_shares, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);
        LinearLayout wx = (LinearLayout) contentView.findViewById(R.id.ll_wx);
        LinearLayout wb = (LinearLayout) contentView.findViewById(R.id.ll_wb);
        LinearLayout kj = (LinearLayout) contentView.findViewById(R.id.ll_kj);
        LinearLayout qq = (LinearLayout) contentView.findViewById(R.id.ll_qq);
        LinearLayout ll_pyq = (LinearLayout) contentView.findViewById(R.id.ll_pyq);
        LinearLayout ll_qr_code = (LinearLayout) contentView.findViewById(R.id.ll_qr_code);
        ImageView  iv_cancel= (ImageView) contentView.findViewById(R.id.iv_cancel);
        iv_icon = (ImageView) contentView.findViewById(R.id.iv_icon);
        tv_name = (TextView) contentView.findViewById(R.id.tv_name);
        star_work = (TextView) contentView.findViewById(R.id.star_work);
        ll_main = (LinearLayout) contentView.findViewById(R.id.ll_main);
        fl_qr_code_main = (FrameLayout) contentView.findViewById(R.id.fl_qr_code_main);
        iv_cancel.setOnClickListener(this);
        wx.setOnClickListener(this);
        wb.setOnClickListener(this);
        kj.setOnClickListener(this);
        qq.setOnClickListener(this);
        ll_pyq.setOnClickListener(this);
        ll_qr_code.setOnClickListener(this);
        mPopWindow.setFocusable(false);
        imageView = (ImageView) contentView.findViewById(img_zxing);
        final ImageView close = (ImageView) contentView.findViewById(R.id.close);
        LogUtils.loge("expendLine:"+expendLine);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveImageToGallery(context, bitmap);
                return false;
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });

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
            case R.id.iv_cancel:
                mPopWindow.dismiss();
                break;
            case R.id.ll_qr_code:
                createQrCode();
                break;
        }
    }

    private void createQrCode() {
        Glide.with(context).load(imageurl)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.user_default_head)
                .error(R.drawable.user_default_head)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ll_main.setVisibility(View.GONE);
                        fl_qr_code_main.setVisibility(View.VISIBLE);
                        if (bitmap==null){
                            bitmap = QRCodeUtil.createQRCodeWithLogo(expendLine, resource);
                        }
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
        expendLine = webUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setStarName(String starName) {
        this.starName = starName;
        tv_name.setText(starName);
    }
    public void setStarWork(String starWork) {
        this.starWork = starWork;
        star_work.setText(starWork);
    }


    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = AppConfig.QI_NIU_PIC_ADRESS+imageurl;
        ImageLoaderUtils.displaySmallPhoto(context,iv_icon,imageurl);
    }

    public void showShareView(View parent) {
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }
    public boolean isOpen() {
        if (mPopWindow.isShowing()==true){
            return true;
        }else {
            return false;
        }

    }
    public void closeShareView(){
        this.umShareListener = null;
        this.context = null;
        this.activity = null;
        this.webUrl = "";
        this.title = "";
        this.starName = "";
        this.starWork = "";
        this.describe = "";
        this.text = "";
        this.imageurl = "";
        mPopWindow.dismiss();
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
    private boolean isFirstSave = false ;
    /**
     * 保存图片到图库
     *
     * @param context
     * @param bmp
     */
    public  void saveImageToGallery(Context context, Bitmap bmp) {
        if (isFirstSave){
            ToastUtils.showShort("二维码已保存");
            return;
        }
        isFirstSave = true;
        File appDir = new File(Environment.getExternalStorageDirectory(), "XingXiang");   // 首先保存图片
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.e("二维码已保存至:" + Environment.getExternalStorageDirectory() + "/XingXiang/" + "目录文件夹下");
        ToastUtils.showShort("二维码已保存");
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

}
