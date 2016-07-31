package music.dexterous.com.dexterousmusic.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.os.PowerManager;

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

    /**
     * AudioManager provides access to volume and ringer mode control.
     * <p>
     * Use this to get audio focus:
     * <p>
     * 1. Making sure other music apps don't play
     * at the same time;
     * 2. Guaranteeing the lock screen widget will
     * be controlled by us;
     */
    AudioManager audioManager;


    public DexterousPlayMusicService() {
        super("DexterousPlayMusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            initMusicPlayer();

        }
    }

    /**
     * Initializes the Android's internal MediaPlayer.
     *
     * @note We might call this function several times without
     * necessarily calling {@link #stopMusicPlayer()}.
     */
    public void initMusicPlayer() {
        if (dexterousMediaPlayer == null) {
            dexterousMediaPlayer = new DexterousMediaPlayer();
        }

        // Assures the CPU continues running this service
        // even when the device is sleeping.
        dexterousMediaPlayer.setWakeMode(this,
                PowerManager.PARTIAL_WAKE_LOCK);

        dexterousMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // These are the events that will "wake us up"
        dexterousMediaPlayer.setOnPreparedListener(this); // player initialized
        dexterousMediaPlayer.setOnCompletionListener(this); // song completed
        dexterousMediaPlayer.setOnErrorListener(this);
    }


    public void stopMusicPlayer() {
        if (dexterousMediaPlayer == null)
            return;

        dexterousMediaPlayer.stop();
        dexterousMediaPlayer.release();
    }
}
