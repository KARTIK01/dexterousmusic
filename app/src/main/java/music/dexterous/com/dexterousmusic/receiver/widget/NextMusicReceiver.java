package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

public class NextMusicReceiver extends BroadcastReceiver {

    public NextMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent nextMusic = new Intent(DexterousPlayMusicService.NEXT_MUSIC);
        nextMusic.setClass(context, DexterousPlayMusicService.class);
        context.startService(nextMusic);
    }
}
