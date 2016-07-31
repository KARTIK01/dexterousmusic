package music.dexterous.com.dexterousmusic.utils;

import com.brightness.screen.nightmode.misc.AppPreference;

/**
 * Created by Honey on 7/13/2016.
 */
public class NightModeUtils {

    private static String KEY_TRANSPARENCY = "transparency";

    static public void setTransparency(int transparency) {
        AppPreference.putInt(KEY_TRANSPARENCY, transparency);
    }

    static public int getTransparency() {
        return AppPreference.getInt(KEY_TRANSPARENCY, 50);
    }

}
