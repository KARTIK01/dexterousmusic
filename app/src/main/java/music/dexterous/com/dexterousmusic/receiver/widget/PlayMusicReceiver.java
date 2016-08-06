package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

public class PlayMusicReceiver extends BroadcastReceiver {
    public PlayMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO set currentSongPosition on {@link #,MusicList} first

        Intent nextMusic = new Intent(DexterousPlayMusicService.PLAY_MUSIC);
        nextMusic.setClass(context, DexterousPlayMusicService.class);
        context.startService(nextMusic);

    }
}
