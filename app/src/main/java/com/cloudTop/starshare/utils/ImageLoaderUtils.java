package com.cloudTop.starshare.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudTop.starshare.R;

import java.io.File;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.ic_empty_picture)
                .crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .crossFade().into(imageView);
    }

    /**
     * 小方图，专用头像
     * @param context
     * @param imageView
     * @param url
     * .transform(new GlideRoundTransform(context, 10))
     */
    public static void displaySmallPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.star_default_icon)
                .error(R.drawable.star_default_icon)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

//    /**
//     * 小方图，圆角
//     * @param context
//     * @param imageView
//     * @param url
//     * .transform(new GlideRoundTransform(context, 10))
//     */
//    public static void displaySmallPhotoCircular(Context context, ImageView imageView, String url,int rund) {
//        if (imageView == null) {
//            throw new IllegalArgumentException("argument error");
//        }
//        Glide.with(context).load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.star_default_icon)
//                .error(R.drawable.star_default_icon)
//                .transform(new GlideRoundTransform(context, rund))
//                .centerCrop()
//                .crossFade()
//                .into(imageView);
//    }

    /**
     * 小圆图,专用头像
     * @param context
     * @param imageView
     * @param url
     */
    public static void displaySmallPhotoRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.user_default_head)
                .error(R.drawable.user_default_head)
                .centerCrop()
                .crossFade()
                .transform(new GlideRoundTransformUtil(context))
                .into(imageView);
    }
    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .into(imageView);
    }
    public static void display(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .crossFade().into(imageView);
    }
    public static void displayRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.toux2)
                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }
    public static void displayRound(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.toux2)
                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }

    public static void displayWithDefaultImg(Context context, ImageView imageView, String url,int resurce) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.edit_cursor)
                .error(resurce)
                .crossFade().into(imageView);
    }
    public static Bitmap getDefaultBitmap(Bitmap mDefauleBitmap,int rotateRotationAngle,float BITMAP_WIDTH,float BITMAP_HEIGHT) {
       // Bitmap bitmap = BitmapFactory.decodeResource(AppApplication.getAppContext().getResources(), drawableId);
            int width = mDefauleBitmap.getWidth();
            int height = mDefauleBitmap.getHeight();
            Matrix matrix = new Matrix();
            //matrix.postRotate(rotateRotationAngle);
            //matrix.setRotate(rotateRotationAngle,((float) BITMAP_WIDTH) / width,((float) BITMAP_HEIGHT) / height);
            matrix.postScale(((float) BITMAP_WIDTH) / width, ((float) BITMAP_HEIGHT) / height);
            mDefauleBitmap = Bitmap.createBitmap(mDefauleBitmap, 0, 0, width, height, matrix, true);
            return mDefauleBitmap;
    }

    //预览图
    public static void displayWithPreviewImg(Context context, final ImageView imageView, String url,int resurce) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.edit_cursor)
                .error(resurce)
                .into(imageView);
    }



}
