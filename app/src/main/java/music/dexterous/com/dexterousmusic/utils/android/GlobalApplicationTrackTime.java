package music.dexterous.com.dexterousmusic.utils.android;

import music.dexterous.com.dexterousmusic.BuildConfig;
import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by Honey on 8/5/2016.
 */
public class GlobalApplicationTrackTime {

    static long startTime;

    static public void startTimeTrack(GlobalApplication globalApplication) {
        startTime = System.currentTimeMillis();
    }

    static public void endTimeTrack(GlobalApplication globalApplication, boolean willPrint) {
        long endTime = System.currentTimeMillis();
        if (willPrint)
            if (endTime > startTime)
                PrettyLogger.i("GlobalApplication took total " + (endTime - startTime) + " milliseconds to execute");
            else
                PrettyLogger.e("GlobalApplication took total -1 milliseconds to execute", new Exception("Start time can't be greater than end time"));
    }

}
