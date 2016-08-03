package music.dexterous.com.dexterousmusic.utils.preference;

/**
 * Created by Honey on 8/3/2016.
 */
public class UsersAppPreference {

    private static final String HIDE_SMALL_CLIPS_DURATIONS = "hide_small_clips_durations";


    public static void setHideSmallClipsDurations(long millsec) {
        AppPreference.putLong(HIDE_SMALL_CLIPS_DURATIONS, millsec);
    }

    public static long getHideSmallClipsDurations() {
        return AppPreference.getLong(HIDE_SMALL_CLIPS_DURATIONS, 10000);
    }
    
}
