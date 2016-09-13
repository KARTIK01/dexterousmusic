package music.dexterous.com.dexterousmusic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;

import java.util.Locale;

import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

/**
 * Created by Honey on 4/8/2016.
 */
public class MailUtill {

    static public void sendFeedbackMail(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("android.intent.extra.EMAIL", new String[]{"info@zuppit.in"});
            intent.putExtra("android.intent.extra.SUBJECT", "Feedback for Zuppit android app");

            String str = "";
            StringBuilder stringBuilder = new StringBuilder();
            ArrayMap<String, String> f = getUserDetails(activity);

            stringBuilder.append("Device Id: ");
            //TODO
//            stringBuilder.append(OtherPreference.getDeviceId());

            stringBuilder.append("\nDevice Name: ");
            stringBuilder.append(f.get("deviceName"));

            stringBuilder.append("\nAndroid Version: ");
            stringBuilder.append(f.get("androidVersion"));

            stringBuilder.append("\nApp Version: ");
            stringBuilder.append(f.get("appVersion"));

            stringBuilder.append("\nApp Version Code: ");
            stringBuilder.append(f.get("appVersionCode"));

            if (f.containsKey("PlayServiceVersion")) {
                stringBuilder.append("\nPlay Service Version: ");
                stringBuilder.append(f.get("PlayServiceVersion"));
            }

            stringBuilder.append("\nScreen Width (in pixels): " + UiUtils.getScreenWidth(activity));
            stringBuilder.append("\nScreen Height (in pixels): " + UiUtils.getScreenHeight(activity));

            if (str != null) {
                stringBuilder.append("\nLanguage: " + str);
            }
            stringBuilder.append("\n--- Kindly do not edit the information above this line to help us serve you better ---\n\n");
            intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
            intent.setType("text/plain");

            ResolveInfo resolveInfoFinal = null;
            for (ResolveInfo resolveInfo : activity.getPackageManager().queryIntentActivities(intent, 0)) {
                if (resolveInfo.activityInfo.packageName.toLowerCase(Locale.US).equals("com.google.android.gm")) {
                    resolveInfoFinal = resolveInfo;
                    break;
                }
            }

            if (resolveInfoFinal != null) {
                intent.setClassName(resolveInfoFinal.activityInfo.packageName, resolveInfoFinal.activityInfo.name);
            }
            activity.startActivity(intent);
        } catch (Throwable e) {
            Crashlytics.logException(e);
        }
    }

    static public ArrayMap<String, String> getUserDetails(Context context) throws Exception {
        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
//        arrayMap.put("deviceId", UserPreference.getDeviceId());
        arrayMap.put("deviceName", MailUtill.getDeviceName());
        arrayMap.put("androidVersion", "" + Build.VERSION.SDK_INT);
        arrayMap.put("androidSdkVersion", "" + Build.VERSION.RELEASE);
        arrayMap.put("appVersion", pInfo.versionName);
        arrayMap.put("appVersionCode", "" + pInfo.versionCode);
        try {
            PackageInfo gInfo = context.getPackageManager().getPackageInfo("com.google.android.gms", 0);
            arrayMap.put("PlayServiceVersion", "" + gInfo.versionCode);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        return arrayMap;
    }

    static public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    /**
     * @param
     * @return
     */
    static private String capitalize(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

}
