# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in d:\tools\androidsdk/tools/proguard/proguard-android.txt
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

# bugly configuration
-keep public class com.tencent.bugly.** { public *; }

#-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

##---------------Begin: proguard configuration for EventBus  ----------
## GreenRobot EventBus specific rules ##
# http://greenrobot.org/eventbus/documentation/proguard/

-keepattributes *Annotation*

-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keepclassmembers class ** {
    public void onEvent*(**);
}
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.event.util.ThrowableFailureEvent {
    public <init>(java.lang.Throwable);
}

##---------------Begin: proguard configuration for GreenDAO  ----------
-keepclassmembers class * extends org.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
##---------------End: proguard configuration for GreenDAO  ----------
##---------------End: proguard configuration for EventBus  ----------

##---------------Begin: proguard configuration for facebook  ----------
-dontwarn com.facebook.**
-keep public class com.facebook.** { public *; }
##---------------End: proguard configuration for facebook  ----------

##---------------Begin: proguard configuration for google-play-services-lib  ----------
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
##---------------End: proguard configuration for google-play-services-lib  ----------



##---------------Begin: proguard configuration for android.support.v4  ----------
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

# Allow obfuscation of android.support.v7.internal.view.menu.**
# to avoid problem on Samsung 4.2.2 devices with appcompat v21
# see https://code.google.com/p/android/issues/detail?id=78377
-keep class !android.support.v7.internal.view.menu.**,android.support.** {*;}
##---------------End: proguard configuration for android.support.v7  ----------

-keep class java.lang.reflect.**

-dontwarn org.slf4j.impl.*

-keep public class org.jbox2d.** { public *; }

-keep public class android.support.design.widget.** { public *; }

# junk clean interface
-keep public class android.content.pm.** { public *; }

##---------------Begin: proguard configuration for AppsFlyer  ----------
-keep public class com.appsflyer.** { public *; }
-dontwarn com.appsflyer.**
##---------------End: proguard configuration for AppsFlyer  ----------



# gson
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }


# for dl provided depandency
-keep class com.google.gson.Gson {
    public *;
}
-keep class com.google.gson.reflect.TypeToken { *; }
##---------------End: proguard configuration for Gson  ----------

#-keep class mobi.wifi.adlibrary.config.GsonModel.** { *; }


# unrar
-dontwarn de.innosystec.unrar.**

-keep class android.renderscript.** { *; }


-keep public class android.webkit.JavascriptInterface {}



-keep class ScrollAnimBehavior { public *; }




#-keepattributes Signature
-keepattributes Exceptions

# okio
-dontwarn okio.**


-ignorewarnings
