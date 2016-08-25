package music.dexterous.com.dexterousmusic.utils.logger;

import com.crashlytics.android.Crashlytics;
import com.orhanobut.logger.Logger;

import music.dexterous.com.dexterousmusic.BuildConfig;

/**
 * Created by Kartik on 26/09/15.
 * <p>
 * Logger helper file for pretty logging
 */
public class PrettyLogger {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    public PrettyLogger() {
    }

    static {
        Logger.init("PrettyLogger");
    }

    public static void e(String msg, Exception e) {
        if (DEBUG) {
            Logger.e(msg);
            e.printStackTrace();
        } else {
            //TODO send exception
        }
        Crashlytics.logException(e);
    }

    public static void d(String msg) {
        if (DEBUG) Logger.d(msg);
    }

    public static void d(Object msg) {
        if (DEBUG) Logger.d(msg.toString());
    }

    public static void i(String msg) {
        if (DEBUG) Logger.i(msg);
    }

    public static void w(String msg) {
        if (DEBUG) Logger.w(msg);
    }

    public static void v(String msg) {
        if (DEBUG) Logger.v(msg);
    }

    public static void wtf(String msg) {
        if (DEBUG) Logger.wtf(msg);
    }
}
