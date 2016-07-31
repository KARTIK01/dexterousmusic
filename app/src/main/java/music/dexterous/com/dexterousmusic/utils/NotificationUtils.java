package music.dexterous.com.dexterousmusic.utils;//package com.brightness.screen.nightmode.utils;
//
//import android.annotation.TargetApi;
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Looper;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.content.ContextCompat;
//import android.widget.RemoteViews;
//
//import com.brightness.screen.nightmode.R;
//import com.brightness.screen.nightmode.activity.MainActivity;
//
///**
// * Created by Honey on 7/13/2016.
// */
//public class NotificationUtils {
//
//    private static final Handler mHandler = new Handler(Looper.getMainLooper());
//
//    /**
//     * notification icon
//     * TODO replace this icon with proper small icon
//     */
//    private static final int smallIcon = R.mipmap.ic_launcher;
//
//    private static final int largeIcon = R.mipmap.ic_launcher;
//
//    private Context mContext;
//
//    public NotificationUtils(Context mContext) {
//        this.mContext = mContext.getApplicationContext();
//    }
//
//
//    public Notification getNotificationForAutoPush() {
//        return null;
//    }
//
//
//    public Notification getNotificationForNightModeForeground() {
//
//        String title = mContext.getString(R.string.foreground_notification_tittle);
//
//        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
//
//        Notification mNotification;
//        String packageName = mContext.getPackageName();
//        RemoteViews smallContentView = new RemoteViews(packageName, R.layout.notification_small);
//        smallContentView.setTextViewText(R.id.notificationTitle, title);
//
//        Intent notificationIntent = new Intent(mContext, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
//
//        mBuilder.setContentIntent(pendingIntent)
//                .setSmallIcon(getNotificationIcon())
//                .setWhen(System.currentTimeMillis())
//                .setColor(ContextCompat.getColor(mContext, R.color.colorAccent))
//                .setAutoCancel(true)
//                .setContent(smallContentView)
//                .setContentText(title)
//                .setDefaults(Notification.DEFAULT_ALL);
//
//        mNotification = mBuilder.build();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            RemoteViews bigContentView = new RemoteViews(packageName, R.layout.notification_big);
//            bigContentView.setTextViewText(R.id.bigNotificationTitle, title);
//
//            mNotification.priority = Notification.PRIORITY_MAX;
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mNotification.visibility = Notification.VISIBILITY_PUBLIC;
//        }
//
//        return mNotification;
//    }
//
//    /**
//     * return icon to be used in notification with either color filters set or unset
//     */
//    public static int getNotificationIcon() {
//        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
//        return useWhiteIcon ? smallIcon : R.mipmap.ic_launcher;
//    }
//
//}
