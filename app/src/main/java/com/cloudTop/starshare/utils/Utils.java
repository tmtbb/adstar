package com.cloudTop.starshare.utils;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cloudTop.starshare.app.AppApplication;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * 复制到剪切板
     *
     * @param context
     * @param src
     * @return
     */
    public static boolean copyCode(Context context, String src) {
        return copyCode(context, src, "已复制礼包码");
    }

    public static boolean copyCode(Context context, String src, String tips) {
        try {
            if (TextUtils.isEmpty(src)) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 8) {
                ClipboardManager clip = (ClipboardManager) AppApplication.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(src);
                ToastUtils.showShort(tips);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 动态计算listview高度
     *
     * @param listView 需要计算的listview
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String sign(String string) {
        try {
            return MD5(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkPermission(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        int p = pm.checkPermission(permission, context.getPackageName());
        if (p == PackageManager.PERMISSION_DENIED) {
            return false;
        } else {
            return true;
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isMiuiFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            return checkOp(context, 24);
        } else {
            if ((context.getApplicationInfo().flags & 1 << 27) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class<AppOpsManager> clazz = AppOpsManager.class;
                Method dispatchMethod = clazz.getMethod("checkOp", new Class[]{int.class, int.class, String.class});
                int mode = (Integer) dispatchMethod.invoke(manager, new Object[]{op, Binder.getCallingUid(), context.getApplicationContext().getPackageName()});
                if (mode == AppOpsManager.MODE_ALLOWED) {
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    public static void openAppDetailActivity(Context context) {
        //       Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//        intent.setData(uri);
//        context.startActivity(intent);
//
        Intent intent = new Intent();
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());

        context.startActivity(intent);
    }

    public static void scrollToReplyLocation(View view, ListView listView, Context context) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int y = location[1] - DisplayUtil.getScreenHeight(context) / 2 + view.getHeight() + DisplayUtil.dip2px(10);
        Log.d("TAG", location[1] + "-->" + view.getHeight() + "--->" + y);
        scrollListBy(listView, "trackMotionScroll", new Object[]{-y, -y}, new Class[]{int.class, int.class});
    }

    private static Object scrollListBy(Object object, String methodName, Object[] params, Class[] paramTypes) {
        Object returnObj = null;
        if (object == null) {
            return null;
        }
        Class cls = object.getClass();
        Method method = null;
        for (; cls != Object.class; cls = cls.getSuperclass()) {
            try {
                method = cls.getDeclaredMethod(methodName, paramTypes);
                break;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        if (method != null) {
            method.setAccessible(true);
            try {
                returnObj = method.invoke(object, params);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return returnObj;
    }


    public static String getVersion() {
        try {
            PackageInfo packageInfo = AppApplication.getApplication().getPackageManager().getPackageInfo(AppApplication.getApplication().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getPackageName() {
        return AppApplication.getApplication().getPackageName();
    }


    public static String getSdkVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertGreyImg(Bitmap img) {
        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    public static String getFormatNumToString(long num) {
        String resault;
        if (num >= 10000 && num <= 99999999) {
            float temp = num / 10000f;
            resault = String.format("%.1f", temp) + "万";
        } else if (num > 99999999) {
            float temp = num / 100000000f;
            resault = String.format("%.2f", temp) + "亿";
        } else {
            resault = String.valueOf(num);
        }
        return resault;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void setAlpha(float alpha, View... view) {
        for (int i = 0; i < view.length; i++) {
            view[i].setAlpha(alpha);
        }
    }

    public static String getUniquePsuedoID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }


    public static void setChannel(Context context) {

        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (applicationInfo != null) {
            String channel = applicationInfo.metaData.getString("channel");
            LogUtils.logi("channel:" + channel);
            applicationInfo.metaData.putString("channel", ChannelUtil.getChannel(context));
            channel = applicationInfo.metaData.getString("channel");
            LogUtils.logi("channel:" + channel);
        }
    }

    public static String getChannel(Application context) {
        String channel = null;

        channel = ChannelUtil.getChannel(context);

        LogUtils.logi("channel:" + channel);

        return channel;
    }

    public static String getUrlForURLencode(String resUrl) {
        String url = resUrl;
        try {
            if (!TextUtils.isEmpty(url)) {
                int lastIndex = url.lastIndexOf("/");
                if (lastIndex != -1) {
                    String filename = url.substring(url.lastIndexOf("/") + 1);
                    String encodeName = URLEncoder.encode(filename, "UTF-8");
                    url = url.substring(0, url.lastIndexOf("/")) + "/" + encodeName;
                }
                //参数转换
                url = url.replaceAll("%3F", "?").replace("%3D", "=").replaceAll("%26", "&");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static boolean isSystemApp(ApplicationInfo pInfo) {
        return ((pInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public static boolean isSystemUpdateApp(ApplicationInfo pInfo) {
        return ((pInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }

    public static boolean isUserApp(ApplicationInfo pInfo) {
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));
    }
    public static boolean isMobile(String mobiles) {
        //   Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,3,5-9]))\\d{8}$");
        //     Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(147)|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static void closeSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//		if(imm.isActive(v)){
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//		}
    }
}