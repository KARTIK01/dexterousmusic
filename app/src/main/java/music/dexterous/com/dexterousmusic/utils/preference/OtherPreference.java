package music.dexterous.com.dexterousmusic.utils.preference;

/**
 * Created by Honey on 8/11/2016.
 */
public class OtherPreference {

    private static final String KEY_IS_FIRST_TIME      = "isFirstTime";
    private static final String KEY_CURRENT_SONG_INDEX = "lastSongIndex";

    /**
     * @return if the user is first time user
     */
    public static boolean isFirstTime() {
        return AppPreference.getBoolean(KEY_IS_FIRST_TIME, true);
    }

    /**
     * @return if the user is first time user
     */
    public static void setFirstTime(boolean isFirstTime) {
        AppPreference.putBoolean(KEY_IS_FIRST_TIME, isFirstTime);
    }

    /**
     * @return if the user is first time user
     */
    public static int getCurrentSongIndex() {
        return AppPreference.getInt(KEY_CURRENT_SONG_INDEX, -1);
    }

    /**
     * @return if the user is first time user
     */
    public static void setCurrentSongIndex(int lastSongIndex) {
        AppPreference.putInt(KEY_CURRENT_SONG_INDEX, lastSongIndex);
    }


}
