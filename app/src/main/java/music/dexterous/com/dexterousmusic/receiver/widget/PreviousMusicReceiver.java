package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

public class PreviousMusicReceiver extends BroadcastReceiver {

    public static final String ACTION = "action";


    public static final String ACTION_TYPE_SKIP = "action_type_skip";


    public PreviousMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        DexterousPlayMusicService.startService(context, DexterousPlayMusicService.PREVIOUS_MUSIC);
    }
}
