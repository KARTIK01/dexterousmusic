package music.dexterous.com.dexterousmusic.utils.other;

import java.util.concurrent.TimeUnit;

/**
 * Created by Honey on 8/15/2016.
 */
public class TimeConvert {

    //// TODO: 8/15/2016  make this for hours also
    public static String songDurationToDisplay(long time) {
        long min = TimeUnit.MILLISECONDS.toMinutes(time);
        long sec = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(min);
        return String.format("%d.%02d", min
                , sec);
    }
}
