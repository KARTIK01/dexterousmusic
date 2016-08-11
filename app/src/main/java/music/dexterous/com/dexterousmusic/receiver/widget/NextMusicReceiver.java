package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

/**
 * Called when user clicks the "skip" button on the on-going system Notification.
 */
public class NextMusicReceiver extends BroadcastReceiver {


    public static final String ACTION = "action";


    public static final String ACTION_TYPE_SKIP = "action_type_skip";

    public NextMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        DexterousPlayMusicService.startService(context, DexterousPlayMusicService.NEXT_MUSIC);

//        kMP.musicService.next(true);
//        kMP.musicService.playSong();
    }
}
