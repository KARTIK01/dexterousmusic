package music.dexterous.com.dexterousmusic.service.musiccontrol;

import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;

import java.io.IOException;

import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.event.PlayMusicEvent;
import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.notification.NotificationMusic;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnCompletionListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnErrorListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnPreparedListener;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.other.RandomNumberGeneratorForMusic;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;

/**
 * Created by Honey on 8/4/2016.
 */
public abstract class AbstractMusicControlService extends Service implements MusicControl {

    /**
     * Dexterous Media Player - we control it in here.
     */
    private MediaPlayer mDexterousMediaPlayer;

    /**
     * Spawns an on-going notification with our current
     * playing song.
     */
    private NotificationMusic notification = null;

    private NowPlayingList nowPlayingList;


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

    public AbstractMusicControlService() {
        super();
    }

    public AbstractMusicControlService(String name) {
        super();
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
        nowPlayingList = NowPlayingList.getInstance();
        int musicPosition = nowPlayingList.getCurrentSongPosition();
        Music musicToPlay = nowPlayingList.getSong(musicPosition);
        musicToPlay.setSONG_IS_PLAYING(true);

        currentMusic = musicToPlay;


/********************************This is mess please improve this, please *************************************/
// Append the external URI with our songs'
//        Uri songToPlayURI = ContentUris.withAppendedId
//                (android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                        musicToPlay.getId());
        try {
            mDexterousMediaPlayer.setDataSource(this, Uri.parse(musicToPlay.getSONG_FILE_PATH()));
        } catch (IOException io) {
            PrettyLogger.e("IOException: couldn't change the song : " + io.getMessage(), io);
            destroySelf();
        } catch (Exception e) {
            PrettyLogger.e("Error when changing the song", e);
            destroySelf();
        }
/********************************Mess Ends here please improve this, please *************************************/

        // Prepare the MusicPlayer asynchronously.
        // When finished, will call `onPrepare`
        mDexterousMediaPlayer.prepareAsync();


//        serviceState = ServiceState.Preparing;
//
//        broadcastState(ServicePlayMusic.BROADCAST_EXTRA_PLAYING);
//
//        updateLockScreenWidget(currentSong, RemoteControlClient.PLAYSTATE_PLAYING);

        /**
         * update {@link music.dexterous.com.dexterousmusic.activity.HomeActivity} UI
         */
        GlobalApplication.getBus().post(new PlayMusicEvent(musicToPlay));


    }

    /**
     * Jumps to the next song on the list.
     *
     * @note Remember to call {@link #playMusic} to make the MusicPlayer
     * actually play the music.
     */
    @Override
    public void playNextMusic(boolean isUserSkipped) {
        if (isUserSkipped) {
            //TODO
        }

        //TODO Updates Lock-Screen Widget

        int currentSongPosition = nowPlayingList.getCurrentSongPosition();

        if (UsersAppPreference.isMusicShuffle()) {
            nowPlayingList.setCurrentSongPosition(RandomNumberGeneratorForMusic.nextInt(currentSongPosition, nowPlayingList.getList().size()));
            return;
        }

        nowPlayingList.setCurrentSongPosition(currentSongPosition + 1);

        if (currentSongPosition >= nowPlayingList.getList().size())
            nowPlayingList.setCurrentSongPosition(0);
    }

    @Override
    public void playPreviousMusic(boolean isUserSkipped) {
        if (isUserSkipped) {
            //TODO
        }

        //TODO Updates Lock-Screen Widget

        int currentSongPosition = nowPlayingList.getCurrentSongPosition();

        currentSongPosition--;

        if (currentSongPosition < 0)
            currentSongPosition = nowPlayingList.getList().size() - 1;

        nowPlayingList.setCurrentSongPosition(currentSongPosition);
    }

    @Override
    public void pauseMusic() {
        mDexterousMediaPlayer.pause();

        currentMusic.setSONG_IS_PLAYING(false);

        if (notification != null)
            notification.notifyPaused(true);
        //TODO Updates Lock-Screen Widget
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
            mDexterousMediaPlayer.setOnPreparedListener(mPlayMusicOnPreparedListener = new PlayMusicOnPreparedListener(this, mDexterousMediaPlayer)); // player initialized
        }
        if (mPlayMusicOnCompletionListener == null) {
            mDexterousMediaPlayer.setOnCompletionListener(mPlayMusicOnCompletionListener = new PlayMusicOnCompletionListener(this)); // song completed
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


    /**
     * Displays a notification on the status bar with the
     * current song and some nice buttons.
     */
    @Override
    public void notifyCurrentSong() {
        if (!UsersAppPreference.isMusicNotificationToDisplay())
            return;
        if (currentMusic == null)
            return;

        if (notification == null)
            notification = new NotificationMusic();

        notification.notifySong(this, this, currentMusic);
    }

    @Override
    public void unPauseMusic() {
        //restart music
        mDexterousMediaPlayer.start();

        currentMusic.setSONG_IS_PLAYING(true);

        //show on notification
        if (notification != null)
            notification.notifyPaused(false);

        //TODO Updates Lock-Screen Widget
        /**
         * Controller that communicates with the lock screen,
         * providing that fancy widget.
         */
//        RemoteControlClientCompat lockscreenController = null;
//        if (lockscreenController != null)
//            lockscreenController.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);

    }

    protected boolean isPlaying() {
        return mDexterousMediaPlayer.isPlaying();
    }
}
