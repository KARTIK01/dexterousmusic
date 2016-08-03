package music.dexterous.com.dexterousmusic.utils.preference;

/**
 * Created by Honey on 8/3/2016.
 */
public class UsersAppPreference {

    private static final String HIDE_SMALL_CLIPS_DURATIONS = "hide_small_clips_durations";
    private static final String MUSIC_PLAY_ON_INSET_HEADSET = "music_play_on_inset_headset";


    /**
     * Set the duration in mill-seconds to hide songs clips which are less than that duration
     *
     * @param millsec
     */
    public static void setHideSmallClipsDurations(long millsec) {
        AppPreference.putLong(HIDE_SMALL_CLIPS_DURATIONS, millsec);
    }

    public static long getHideSmallClipsDurations() {
        return AppPreference.getLong(HIDE_SMALL_CLIPS_DURATIONS, 10000);
    }


    /**
     * Set true when user wants to auto start his music when he insert his headset
     *
     * @param autoStart
     */
    public static void setMusicPlayONInsetHeadSet(boolean autoStart) {
        AppPreference.putBoolean(MUSIC_PLAY_ON_INSET_HEADSET, autoStart);
    }

    /**
     * @return true when user wants to auto start his music on insert headset
     * false otherwise
     */
    public static boolean getMusicPlayONInsetHeadSet() {
        return AppPreference.getBoolean(MUSIC_PLAY_ON_INSET_HEADSET, true);
    }


}
