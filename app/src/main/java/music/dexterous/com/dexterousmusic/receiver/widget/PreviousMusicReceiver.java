package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

public class PreviousMusicReceiver extends BroadcastReceiver {
    public PreviousMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent nextMusic = new Intent(DexterousPlayMusicService.PREVIOUS_MUSIC);
        nextMusic.setClass(context, DexterousPlayMusicService.class);
        context.startService(nextMusic);
    }
}
