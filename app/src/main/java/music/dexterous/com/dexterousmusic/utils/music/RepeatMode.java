package music.dexterous.com.dexterousmusic.utils.music;

import android.content.Context;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

/**
 * Created by naren on 25/8/16.
 */
public class RepeatMode {

    public static void changeRepeteMode(Context context, ImageView imageView) {
        int mode = UsersAppPreference.getMusicRepeatModeSetting();
        if (mode == UsersAppPreference.RepeatModeContants.REPEAT_CURRENT_SONG)
            UsersAppPreference.setMusicRepeatModeSetting(UsersAppPreference.RepeatModeContants.REPEAT_CURRENT_PLAYLIST);
        else if (mode == UsersAppPreference.RepeatModeContants.REPEAT_CURRENT_PLAYLIST)
            UsersAppPreference.setMusicRepeatModeSetting(UsersAppPreference.RepeatModeContants.REPEAT_MODE_OFF);
        else
            UsersAppPreference.setMusicRepeatModeSetting(UsersAppPreference.RepeatModeContants.REPEAT_CURRENT_SONG);

        showRepeatIcon(context, imageView);
    }

    public static void showRepeatIcon(Context context, ImageView widget_shuffel) {
        int mode = UsersAppPreference.getMusicRepeatModeSetting();
        if (mode == UsersAppPreference.RepeatModeContants.REPEAT_CURRENT_SONG)
            new ImageLoader(context).loadImage(context, UiUtils.ic_repeat_one_vector, widget_shuffel);
        else if (mode == UsersAppPreference.RepeatModeContants.REPEAT_CURRENT_PLAYLIST)
            new ImageLoader(context).loadImage(context, UiUtils.ic_repeat_all_vector, widget_shuffel);
        else
            new ImageLoader(context).loadImage(context, UiUtils.ic_repeat_off_vector, widget_shuffel);
    }
}
