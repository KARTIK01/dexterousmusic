package music.dexterous.com.dexterousmusic.misc;

import com.brightness.screen.nightmode.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by anand on 26/09/15.
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

    public static void e(String msg) {
        if (DEBUG) Logger.e(msg);
    }

    public static void d(String msg) {
        if (DEBUG) Logger.d(msg);
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
