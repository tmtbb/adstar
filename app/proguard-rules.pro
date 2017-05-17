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



