package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

public class PauseMusicReceiver extends BroadcastReceiver {
    public PauseMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent nextMusic = new Intent(DexterousPlayMusicService.PAUSE_MUSIC);
        nextMusic.setClass(context, DexterousPlayMusicService.class);
        context.startService(nextMusic);
    }
}
