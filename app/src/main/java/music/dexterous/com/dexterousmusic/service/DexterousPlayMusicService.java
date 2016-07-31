package music.dexterous.com.dexterousmusic.service;

import android.app.IntentService;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;

/**
 * This is very import class and it needs to take care with proper guidance
 * <p>
 * Service that makes the music play
 */

public class DexterousPlayMusicService extends IntentService {

    /**
     * Dexterous Media Player - we control it in here.
     */
    DexterousMediaPlayer dexterousMediaPlayer;


    public DexterousPlayMusicService() {
        super("DexterousPlayMusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
    }

}
