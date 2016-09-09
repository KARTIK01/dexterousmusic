package music.dexterous.com.dexterousmusic.utils.preference;

/**
 * Created by Honey on 8/3/2016.
 */
public class UsersAppPreference {

    private static final String HIDE_SMALL_CLIPS_DURATIONS  = "hide_small_clips_durations";
    private static final String MUSIC_PLAY_ON_INSET_HEADSET = "music_play_on_inset_headset";
    private static final String MUSIC_REPEAT_MODE_TYPE      = "music_repeat_mode_type";
    private static final String MUSIC_SHUFFLE_MODE          = "music_shuffle_mode";
    private static final String MUSIC_NOTIFICATION          = "music_notification";

    public static long getHideSmallClipsDurations() {
        return AppPreference.getLong(HIDE_SMALL_CLIPS_DURATIONS, DefaultPreference.HIDE_SMALL_CLIPS_DURATIONS_DEFAULT);
    }

    /**
     * Set the duration in mill-seconds to hide songs clips which are less than that duration
     *
     * @param millsec
     */
    public static void setHideSmallClipsDurations(long millsec) {
        AppPreference.putLong(HIDE_SMALL_CLIPS_DURATIONS, millsec);
    }

    /**
     * @return true when user wants to auto start his music on insert headset false otherwise
     */
    public static boolean getMusicPlayONInsetHeadSet() {
        return AppPreference.getBoolean(MUSIC_PLAY_ON_INSET_HEADSET, DefaultPreference.MUSIC_PLAY_ON_INSET_HEADSET_DEFAULT);
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
     * @return true when user wants to auto start his music on insert headset false otherwise
     */
    public static int getMusicRepeatModeSetting() {
        return AppPreference.getInt(MUSIC_REPEAT_MODE_TYPE, RepeatModeContants.REPEAT_CURRENT_PLAYLIST);
    }

    /**
     * Set value to repeat current song (0) {@link #, RepeatModeContants.REPEAT_CURRENT_SONG} or
     * repeat playlist (1)  {@link #, RepeatModeContants.REPEAT_CURRENT_PLAYLIST} or end when all
     * songs played (2) {@link #, RepeatModeContants.REPEAT_MODE_OFF}
     * <p>
     * default is repeat playlist (1)
     *
     * @param autoStart
     */
    public static void setMusicRepeatModeSetting(int autoStart) {
        AppPreference.putInt(MUSIC_REPEAT_MODE_TYPE, autoStart);
    }

    public static boolean isMusicShuffle() {
        return AppPreference.getBoolean(MUSIC_SHUFFLE_MODE, DefaultPreference.MUSIC_SHUFFLE_MODE_DEFAULT);
    }

    public static void setMusicShuffleMode(boolean isShuffle) {
        AppPreference.putBoolean(MUSIC_SHUFFLE_MODE, isShuffle);
    }

    public static boolean isMusicNotificationToDisplay() {
        return AppPreference.getBoolean(MUSIC_NOTIFICATION, DefaultPreference.MUSIC_NOTIFICATION_DEFAULT);
    }

    public static void setMusicNotificationToDisplat(boolean isShuffle) {
        AppPreference.putBoolean(MUSIC_NOTIFICATION, isShuffle);
    }


    static public class RepeatModeContants {
        public static final int REPEAT_CURRENT_SONG     = 0;
        public static final int REPEAT_CURRENT_PLAYLIST = 1;
        public static final int REPEAT_MODE_OFF         = 2;
    }

    static public class DefaultPreference {

        public static boolean MUSIC_NOTIFICATION_DEFAULT          = true;
        public static boolean MUSIC_SHUFFLE_MODE_DEFAULT          = true;
        public static boolean MUSIC_PLAY_ON_INSET_HEADSET_DEFAULT = true;
        public static int     HIDE_SMALL_CLIPS_DURATIONS_DEFAULT  = 100 * 1000; // 100 seconds


        /**
         * reset all user app preference to default values
         */
        static public void restAll() {
            setMusicNotificationToDisplat(MUSIC_NOTIFICATION_DEFAULT);
            setMusicShuffleMode(MUSIC_SHUFFLE_MODE_DEFAULT);
            setMusicRepeatModeSetting(RepeatModeContants.REPEAT_CURRENT_PLAYLIST);
            setMusicPlayONInsetHeadSet(MUSIC_PLAY_ON_INSET_HEADSET_DEFAULT);
            setHideSmallClipsDurations(HIDE_SMALL_CLIPS_DURATIONS_DEFAULT);
        }
    }

}
