package music.dexterous.com.dexterousmusic.utils.preference;

/**
 * Created by Honey on 8/3/2016.
 */
public class UsersAppPreference {

    private static final String HIDE_SMALL_CLIPS_DURATIONS = "hide_small_clips_durations";
    private static final String MUSIC_PLAY_ON_INSET_HEADSET = "music_play_on_inset_headset";
    private static final String MUSIC_REPEATE_MODE_TYPE = "music_repeate_mode_type";
    private static final String MUSIC_SHUFFLE_MODE = "music_shuffle_mode";


    /**
     * Set the duration in mill-seconds to hide songs clips which are less than that duration
     *
     * @param millsec
     */
    public static void setHideSmallClipsDurations(long millsec) {
        AppPreference.putLong(HIDE_SMALL_CLIPS_DURATIONS, millsec);
    }

    public static long getHideSmallClipsDurations() {
        return AppPreference.getLong(HIDE_SMALL_CLIPS_DURATIONS, 60000 * 4); //60 * 3secs
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

    /**
     * Set value to repeat current song (0) {@link #, RepeatModeContants.REPEAT_CURRENT_SONG}
     * or repeat playlist (1)  {@link #, RepeatModeContants.REPEAT_CURRENT_PLAYLIST}
     * or end when all songs played (2) {@link #, RepeatModeContants.REPEAT_MODE_OFF}
     * <p>
     * default is repeat playlist (1)
     *
     * @param autoStart
     */
    public static void setMusicRepeatModeSetting(int autoStart) {
        AppPreference.putInt(MUSIC_REPEATE_MODE_TYPE, autoStart);
    }

    /**
     * @return true when user wants to auto start his music on insert headset
     * false otherwise
     */
    public static int getMusicRepeatModeSetting() {
        return AppPreference.getInt(MUSIC_REPEATE_MODE_TYPE, RepeatModeContants.REPEAT_CURRENT_PLAYLIST);
    }


    public static boolean isMusicShuffle() {
        return AppPreference.getBoolean(MUSIC_SHUFFLE_MODE, true);
    }

    public static void setMusicShuffleMode(boolean isShuffle) {
        AppPreference.putBoolean(MUSIC_REPEATE_MODE_TYPE, isShuffle);
    }

    static public class RepeatModeContants {
        public static final int REPEAT_CURRENT_SONG = 0;
        public static final int REPEAT_CURRENT_PLAYLIST = 1;
        public static final int REPEAT_MODE_OFF = 2;
    }

}
