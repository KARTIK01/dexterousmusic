package music.dexterous.com.dexterousmusic.service.musiccontrol;

import android.app.IntentService;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;

import java.io.IOException;

import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnCompletionListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnErrorListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnPreparedListener;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by Honey on 8/4/2016.
 */
public abstract class AbstractMusicControlService extends IntentService implements MusicControl {

    /**
     * Dexterous Media Player - we control it in here.
     */
    MediaPlayer mDexterousMediaPlayer;


    MusicList musicList;


    /**
     * Copy of the current song being played (or paused).
     * <p>
     * Use it to get info from the current song.
     */
    Music currentMusic;


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

        initListener();
    }

    @Override
    public void playMusic() {

        mDexterousMediaPlayer.reset();

        // Get the song ID from the list, extract the ID and
        // get an URL based on it
        musicList = MusicList.getInstance();
        int musicPosition = musicList.getCurrentSongPosition();
        Music musicToPlay = musicList.getSong(musicPosition);

        currentMusic = musicToPlay;

        // Append the external URI with our songs'
        Uri songToPlayURI = ContentUris.withAppendedId
                (android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        musicToPlay.getId());
        try {
            mDexterousMediaPlayer.setDataSource(getApplicationContext(), songToPlayURI);
        } catch (IOException io) {
            PrettyLogger.e("IOException: couldn't change the song" + io.getMessage(), io);
            destroySelf();
        } catch (Exception e) {
            PrettyLogger.e("Error when changing the song", e);
            destroySelf();
        }

        // Prepare the MusicPlayer asynchronously.
        // When finished, will call `onPrepare`
        mDexterousMediaPlayer.prepareAsync();
//        serviceState = ServiceState.Preparing;
//
//        broadcastState(ServicePlayMusic.BROADCAST_EXTRA_PLAYING);
//
//        updateLockScreenWidget(currentSong, RemoteControlClient.PLAYSTATE_PLAYING);

        PrettyLogger.d("playing music");

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


    private void initListener() {
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
    public void destroySelf() {
        stopSelf();
        currentMusic = null;
    }
}
