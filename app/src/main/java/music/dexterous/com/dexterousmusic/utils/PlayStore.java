package music.dexterous.com.dexterousmusic.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

import music.dexterous.com.dexterousmusic.BuildConfig;

/**
 * Created by Honey on 8/17/2016.
 */
public class PlayStore {

    /**
     * Opens app in play store for rating and download new version
     */
    public static void openAppRating(Context context) {
        String packageName = context.getPackageName();
        //TODO update package name
        if (BuildConfig.DEBUG) packageName = "com.lockscreen.zuppit";

        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        boolean marketFound = false;

        /**  find all applications able to handle our rateIntent */
        final List<ResolveInfo> otherApps = context.getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            /** look for Google Play application */
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                rateIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_NO_HISTORY);
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;
            }
        }

        /**  if GooglePlay not present on device, open web browser */
        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            context.startActivity(webIntent);
        }
    }
}
