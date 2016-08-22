package music.dexterous.com.dexterousmusic.utils.other;

import android.os.Build;
import android.os.StrictMode;

import music.dexterous.com.dexterousmusic.utils.AppUtils;

/**
 * Created by Honey on 8/6/2016.
 */
public class StrictModeUtil {
    /**
     * Enables strict mode for debugging.
     * Beware that instance count is buggy, so ignore it (as of April 12, 2016 )
     */
    public static void enableStrictMode() {
        if (AppUtils.isCompatWith(Build.VERSION_CODES.GINGERBREAD)) {

            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog();

            StrictMode.VmPolicy.Builder vmPolicyBuilder =
                    new StrictMode.VmPolicy.Builder()
                            .detectActivityLeaks()
//                            .detectLeakedClosableObjects()
                            .detectLeakedRegistrationObjects()
                            .detectLeakedSqlLiteObjects()
//                            .setClassInstanceLimit(HomeRootFragment.class, 1)
//                            .setClassInstanceLimit(HomeActivity.class, 1)
                            .penaltyDeath()
                            .penaltyLog();

            threadPolicyBuilder.penaltyFlashScreen();

            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }
}
