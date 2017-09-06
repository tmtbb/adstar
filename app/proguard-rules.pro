# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android_studio\studio_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#LRecyclerview_library
-keep class com.sun.jna.**{*;}
-dontwarn com.github.jdsjlzx.**
-keep class com.github.jdsjlzx.**{*;}
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-dontwarn com.netease.**
-dontwarn io.netty.**
-keep class com.netease.** {*;}
#如果 netty 使用的官方版本，它中间用到了反射，因此需要 keep。如果使用的是我们提供的版本，则不需要 keep
-keep class io.netty.** {*;}

#如果你使用全文检索插件，需要加入
-dontwarn org.apache.lucene.**
-keep class org.apache.lucene.** {*;}
#微信登录
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}


#fastjson


-dontwarn com.alibaba.fastjson.**
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses

-keep class com.alibaba.fastjson.**{*;}
-keep class * implements java.io.Serializable { *; }

-keepattributes *Annotation
-keepattributes Signature

#soket
-keep class org.apache.http.** { *; }
-keep class android.net.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.**



#高德地图2d
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
#定位
     -keep class com.amap.api.location.**{*;}
     -keep class com.amap.api.fence.**{*;}
     -keep class android.util.FloatMath.**{*;}
     -keep class com.autonavi.aps.amapapi.model.**{*;}
     -keep class com.amap.api.mapcore2d.MapMessage.**{*;}

-dontwarn android.util.FloatMath.**
-dontwarn com.amap.api.mapcore2d.**



#高德搜索
     -keep   class com.amap.api.services.**{*;}
     -keepattributes EnclosingMethod


-dontwarn Android.support.**
-dontwarn com.alibaba.fastjson.**


-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses

-keep class com.baidu.** { *; }

-keepclassmembers class * {
public <methods>;
}

-keepclassmembers class * implements Java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
}
-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#支付宝
# -libraryjars libs/alipaySingle-20170510.jar


-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

-keep class  com.alipay.share.sdk.** {
   *;
}

-dontwarn com.alipay.**
-keep class com.alipay.**{*;}

-dontwarn com.ta.utdid2.**
-keep class com.ta.utdid2.**{*;}

-dontwarn com.ut.device.**
-keep class com.ut.device.**{*;}

#友盟分享
-dontusemixedcaseclassnames
    -dontshrink
    -dontoptimize
    -dontwarn com.google.android.maps.**
    -dontwarn android.webkit.WebView
    -dontwarn com.umeng.**
    -dontwarn com.tencent.weibo.sdk.**
    -dontwarn com.facebook.**
    -keep public class javax.**
    -keep public class android.webkit.**
    -dontwarn android.support.v4.**
    -keep enum com.facebook.**
    -keepattributes Exceptions,InnerClasses,Signature
    -keepattributes *Annotation*
    -keepattributes SourceFile,LineNumberTable

    -keep public interface com.facebook.**
    -keep public interface com.tencent.**
    -keep public interface com.umeng.socialize.**
    -keep public interface com.umeng.socialize.sensor.**
    -keep public interface com.umeng.scrshot.**
    -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
    -keep public class com.umeng.socialize.* {*;}


    -keep class com.facebook.**
    -keep class com.facebook.** { *; }
    -keep class com.umeng.scrshot.**
    -keep public class com.tencent.** {*;}
    -keep class com.umeng.socialize.sensor.**
    -keep class com.umeng.socialize.handler.**
    -keep class com.umeng.socialize.handler.*
    -keep class com.umeng.weixin.handler.**
    -keep class com.umeng.weixin.handler.*
    -keep class com.umeng.qq.handler.**
    -keep class com.umeng.qq.handler.*
    -keep class UMMoreHandler{*;}
    -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
    -keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
    -keep class im.yixin.sdk.api.YXMessage {*;}
    -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
    -keep class com.tencent.mm.sdk.** {
     *;
    }
    -keep class com.tencent.mm.opensdk.** {
   *;
    }
    -dontwarn twitter4j.**
    -keep class twitter4j.** { *; }

    -keep class com.tencent.** {*;}
    -dontwarn com.tencent.**
    -keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
    }
    -keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
        }
    -keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    }

    -keep class com.tencent.open.TDialog$*
    -keep class com.tencent.open.TDialog$* {*;}
    -keep class com.tencent.open.PKDialog
    -keep class com.tencent.open.PKDialog {*;}
    -keep class com.tencent.open.PKDialog$*
    -keep class com.tencent.open.PKDialog$* {*;}

    -keep class com.sina.** {*;}
    -dontwarn com.sina.**
    -keep class  com.alipay.share.sdk.** {
       *;
    }
    -keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
    }

    -keep class com.linkedin.** { *; }
    -keepattributes Signature
    #bugout
    -dontwarn com.qamaster.android.**
    -dontwarn com.testin.agent.**
    -keepattributes InnerClasses
    -keep class com.testin.agent.** { *; }

    -dontwarn com.igexin.**
    -keep class com.igexin.** { *; }
    -keep class org.json.** { *; }
    #greendao
    #greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
 -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
 public static java.lang.String TABLENAME;
 }
 -keep class **$Properties

 # If you do not use SQLCipher:
 -dontwarn org.greenrobot.greendao.database.**
 # If you do not use Rx:
 -dontwarn rx.**
 #友盟统计
 -keepclassmembers class * {
    public <init> (org.json.JSONObject);
 }
 -keep class com.pili.pldroid.player.** { *; }
 -keep class com.qiniu.qplayer.mediaEngine.MediaPlayer{*;}
 -keep class com.qiniu.**{*;}
 -keep class com.qiniu.**{public <init>();}
 -ignorewarnings

 -keep class tv.danmaku.ijk.** { *; }
 -dontwarn tv.danmaku.ijk.**
 -keep class com.shuyu.gsyvideoplayer.** { *; }
 -dontwarn com.shuyu.gsyvideoplayer.**

