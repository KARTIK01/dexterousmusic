package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

/**
 * Called when user clicks the "skip" button on the on-going system Notification.
 */
public class CloseMusicReceiver extends BroadcastReceiver {


    public static final String ACTION = "action";


    public static final String ACTION_TYPE_CLOSE = "action_type_close";

    public CloseMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        DexterousPlayMusicService.startService(context, DexterousPlayMusicService.STOP_MUSIC);
    }
}
