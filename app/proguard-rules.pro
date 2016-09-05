# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\java\MOBILE_TECH\Android\SDK\sdk/tools/proguard/proguard-android.txt
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

#Alternatively, you can add the @Keep annotation to the code you want to keep.
#Adding @Keep on a class keeps the entire class as-is.
#Adding it on a method or field will keep the method/field (and it's name) as well as the class name intact.
#Note that this annotation is available only when using the

# app specific rules

-optimizationpasses 1
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService


-keepclasseswithmembernames class * {
    native <methods>;
}


-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}


-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable

## rules for support libraries
################## for support library appcompat v7 ############################################

-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

## for crash fix on samsung 4.4.2
-keep class !android.support.v7.internal.view.menu.*MenuBuilder*

###################### for cardview #############################################################

# http://stackoverflow.com/questions/29679177/cardview-shadow-not-appearing-in-lollipop-after-obfuscate-with-proguard/29698051
-keep class android.support.v7.widget.RoundRectDrawable { *; }

######################################## for design support ######################################

-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

################################################## okhttp ########################################
# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

########################################## okhttp config ends here #################################

############################################ for event bus ########################################

-keepattributes *Annotation*

-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}

# Don't warn for missing support classes
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment

#################################### event-bus  config ends here #################################

################################ Begin: proguard configuration for Gson  ####################
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class music.dexterous.com.dexterousmusic.models.** { *; }

##---------------End: proguard configuration for Gson  ----------

######################### Leak canary config ends here ################################

-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }

## to prevent leak canary warnings for using removed method setLatestEventInfo (Android M)
-dontwarn com.squareup.leakcanary.**

###################################### for glide modules ########################################
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}



################################# for lambda in JAVA 8 ######################################
-dontwarn java.lang.invoke.*



################################################# Crashlytics 2.+ For easy debugging

-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile, LineNumberTable, *Annotation*

# If you are using custom exceptions, add this line so that custom exception types are skipped during obfuscation:
-keep public class * extends java.lang.Exception

# For Fabric to properly de-obfuscate your crash reports, you need to remove this line from your ProGuard config:
# -printmapping mapping.txt

######################################### rules for reactive java ########################
-dontwarn sun.misc.**

-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}



# The rxjava library depends on sun.misc.Unsafe, which is unavailable on Android
# The rxjava team is aware of this, and mention in the docs that they only use
# the unsafe functionality if the platform supports it.
#  - https://github.com/ReactiveX/RxJava/issues/1415#issuecomment-48390883
#  - https://github.com/ReactiveX/RxJava/blob/1.x/src/main/java/rx/internal/util/unsafe/UnsafeAccess.java#L23
-dontwarn rx.internal.util.**

## prevent proguard from obfuscating pacelable
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}


-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

#// If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
#// If you do not use Rx:
#-dontwarn org.greenrobot.greendao.rx.**

#ok http
-dontwarn okio.**