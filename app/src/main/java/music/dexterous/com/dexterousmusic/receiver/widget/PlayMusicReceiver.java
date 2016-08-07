package music.dexterous.com.dexterousmusic.receiver.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;


/**
 * Called when user clicks the "play/pause" button on the on-going system Notification.
 */
public class PlayMusicReceiver extends BroadcastReceiver {


    public static final String ACTION = "action";


    public static final String ACTION_TYPE_TOGGLE = "action_type_toggle";

    public PlayMusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO set currentSongPosition on {@link #,MusicList} first

        Intent nextMusic = new Intent(DexterousPlayMusicService.PLAY_MUSIC);
        nextMusic.setClass(context, DexterousPlayMusicService.class);
        context.startService(nextMusic);


        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            int state = bundle.getInt(ACTION);
            switch (state) {
//                kMP.musicService.togglePlayback();
            }
        }

    }
}
