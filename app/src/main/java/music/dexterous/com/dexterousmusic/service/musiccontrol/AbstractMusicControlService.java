package music.dexterous.com.dexterousmusic.service.musiccontrol;

import android.app.IntentService;
import android.media.AudioManager;
import android.os.PowerManager;

import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnCompletionListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnErrorListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnPreparedListener;

/**
 * Created by Honey on 8/4/2016.
 */
public abstract class AbstractMusicControlService extends IntentService implements MusicControl {

    /**
     * Dexterous Media Player - we control it in here.
     */
    DexterousMediaPlayer mDexterousMediaPlayer;


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
    protected AudioManager audioManager;

    PlayMusicOnPreparedListener mPlayMusicOnPreparedListener;
    PlayMusicOnCompletionListener mPlayMusicOnCompletionListener;
    PlayMusicOnErrorListener mPlayMusicOnErrorListener;


    public AbstractMusicControlService(String name) {
        super(name);
    }

    @Override
    public void initMusicPlayer() {
        if (mDexterousMediaPlayer == null) {
            mDexterousMediaPlayer = new DexterousMediaPlayer();
        }

        // Assures the CPU continues running this service
        // even when the device is sleeping.
        mDexterousMediaPlayer.setWakeMode(this,
                PowerManager.PARTIAL_WAKE_LOCK);

        mDexterousMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // These are the events that will "wake us up"
        if (mPlayMusicOnPreparedListener == null) {
            mDexterousMediaPlayer.setOnPreparedListener(mPlayMusicOnPreparedListener = new PlayMusicOnPreparedListener(mDexterousMediaPlayer)); // player initialized
        }
        if (mPlayMusicOnCompletionListener == null) {
            mDexterousMediaPlayer.setOnCompletionListener(mPlayMusicOnCompletionListener = new PlayMusicOnCompletionListener()); // song completed
        }
        if (mPlayMusicOnErrorListener == null) {
            mDexterousMediaPlayer.setOnErrorListener(mPlayMusicOnErrorListener = new PlayMusicOnErrorListener());
        }
    }

    @Override
    public void playMusic() {

    }

    @Override
    public void playNextMusic() {

    }

    @Override
    public void playPreviousMusic() {

    }

    @Override
    public void pauseMusic() {

    }

    @Override
    public void stopMusicPlayer() {
        if (mDexterousMediaPlayer == null)
            return;

        mDexterousMediaPlayer.stop();
        mDexterousMediaPlayer.release();
        mDexterousMediaPlayer = null;
    }
}
