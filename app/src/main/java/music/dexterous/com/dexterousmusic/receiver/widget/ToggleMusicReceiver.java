package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;


/**
 * Called when user clicks the "play/pause" button on the on-going system Notification.
 */
public class ToggleMusicReceiver extends BroadcastReceiver {


    public static final String ACTION = "action";


    public static final String ACTION_TYPE_TOGGLE = "action_type_toggle";

    public ToggleMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        DexterousPlayMusicService.startService(context, DexterousPlayMusicService.TOGGLE_MUSIC);
    }
}
