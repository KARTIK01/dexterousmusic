package music.dexterous.com.dexterousmusic.utils;

import android.os.Build;

public class AppUtils {

    public static boolean isCompatWith(int versionCode) {
        return Build.VERSION.SDK_INT >= versionCode;
    }

    public static boolean isCompatWithMarshMallow() {
        return isCompatWith(Build.VERSION_CODES.M);
    }

}
