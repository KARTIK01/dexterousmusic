package music.dexterous.com.dexterousmusic.service.musiccontrol;

import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;

import java.io.IOException;

import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.event.MusicPaused;
import music.dexterous.com.dexterousmusic.event.MusicStop;
import music.dexterous.com.dexterousmusic.event.MusicUnPaused;
import music.dexterous.com.dexterousmusic.event.PlayMusicEvent;
import music.dexterous.com.dexterousmusic.event.ShowWidget;
import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.musicutils.PlayCurrentSong;
import music.dexterous.com.dexterousmusic.notification.NotificationMusic;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnCompletionListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnErrorListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnPreparedListener;
import music.dexterous.com.dexterousmusic.utils.android.AppState;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.other.RandomNumberGeneratorForMusic;
import music.dexterous.com.dexterousmusic.utils.preference.OtherPreference;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;

/**
 * Created by Honey on 8/4/2016.
 */
public abstract class AbstractMusicControlService extends Service implements MusicControl {

    /**
     * Dexterous Media Player - we control it in here.
     */
    static public MediaPlayer mDexterousMediaPlayer;

    /**
     * Spawns an on-going notification_big with our current playing song.
     */
    static public NotificationMusic notification = null;

    private NowPlayingList nowPlayingList;


    /**
     * Copy of the current song being played (or paused).
     * <p>
     * Use it to get info from the current song.
     */
    private Music currentMusic;


    /**
     * AudioManager provides access to volume and ringer mode control.
     * <p>
     * Use this to get audio focus:
     * <p>
     * 1. Making sure other music apps don't play at the same time; 2. Guaranteeing the lock screen
     * widget will be controlled by us;
     */
    private AudioManager audioManager;


    private PlayMusicOnPreparedListener   mPlayMusicOnPreparedListener;
    private PlayMusicOnCompletionListener mPlayMusicOnCompletionListener;
    private PlayMusicOnErrorListener      mPlayMusicOnErrorListener;

    public AbstractMusicControlService() {
        super();
    }

    public AbstractMusicControlService(String name) {
        super();
    }

    @Override
    public void initMusicPlayer() {

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void playMusic() {
        if (mDexterousMediaPlayer == null) {
            initMusicPlayer();
        }
        mDexterousMediaPlayer.reset();

        // Get the song ID from the list, extract the ID and
        // get an URL based on it
        if (nowPlayingList == null)
            nowPlayingList = NowPlayingList.getInstance();

        int musicPosition = nowPlayingList.getCurrentSongPosition();
        if (musicPosition < 0 || musicPosition >= nowPlayingList.getList().size())
            return;
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
            //TODO fix what to do next
        } catch (Exception e) {
            PrettyLogger.e("Error when changing the song :" + e.getMessage(), e);
            destroySelf();
            //TODO fix what to do next
        }
/********************************Mess Ends here please improve this, please *************************************/

        // Prepare the MusicPlayer asynchronously.
        // When finished, will call `onPrepare`
        //TODO fix  IllegalStateException
        try {
            mDexterousMediaPlayer.prepareAsync();
        } catch (Exception e) {
            PrettyLogger.e("" + e.getMessage(), e);
        }

//        serviceState = ServiceState.Preparing;
//
//        broadcastState(ServicePlayMusic.BROADCAST_EXTRA_PLAYING);
//
//        updateLockScreenWidget(currentSong, RemoteControlClient.PLAYSTATE_PLAYING);

        /**
         * update {@link music.dexterous.com.dexterousmusic.activity.HomeActivity} UI
         */
        GlobalApplication.getBus().post(new PlayMusicEvent(musicToPlay));
        GlobalApplication.getBus().post(new ShowWidget(musicToPlay));


    }

    /**
     * Jumps to the next song on the list.
     *
     * @note Remember to call {@link #playMusic} to make the MusicPlayer actually play the music.
     */
    @Override
    public void playNextMusic(boolean isUserSkipped) {
        if (isUserSkipped) {
            //TODO
        }

        if (nowPlayingList == null)
            nowPlayingList = NowPlayingList.getInstance();

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

        if (nowPlayingList == null)
            nowPlayingList = NowPlayingList.getInstance();

        //TODO Updates Lock-Screen Widget

        int currentSongPosition = nowPlayingList.getCurrentSongPosition();

        currentSongPosition--;

        if (currentSongPosition < 0)
            currentSongPosition = nowPlayingList.getList().size() - 1;

        nowPlayingList.setCurrentSongPosition(currentSongPosition);
    }

    @Override
    public void pauseMusic() {
        if (mDexterousMediaPlayer == null) {
            initMusicPlayer();
        }

        if (mDexterousMediaPlayer.isPlaying()) {
            mDexterousMediaPlayer.pause();

            currentMusic.setSONG_IS_PLAYING(false);

            if (notification != null)
                notification.notifyPaused(true);
            //TODO Updates Lock-Screen Widget


            GlobalApplication.getBus().post(new MusicPaused());
        }
    }

    @Override
    public void stopMusicPlayer() {
        if (mDexterousMediaPlayer == null)
            return;

        int songIndex = OtherPreference.getCurrentSongIndex();
        if (DataManager.getInstance(this).getAllMusic().size() > songIndex)
            DataManager.getInstance(this).getAllMusic().get(songIndex).setSONG_IS_PLAYING(false);

        mDexterousMediaPlayer.stop();
        currentMusic = null;
        //if app is not in foreground then only cancel all these as they are initilise in global application
        if (AppState.isAppIsInBackground(this)) {
            mDexterousMediaPlayer.release();
            mDexterousMediaPlayer = null;
        }
        notification.cancel();

        GlobalApplication.getBus().post(new MusicStop());
    }


    @Override
    public void destroySelf() {
        stopSelf();
        currentMusic = null;
    }


    /**
     * Displays a notification_big on the status bar with the current song and some nice buttons.
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
        if (mDexterousMediaPlayer == null) {
            initMusicPlayer();
        }
        /**
         * restart music when it is paused only else play song which is stored in preference
         * {@link music.dexterous.com.dexterousmusic.utils.preference.OtherPreference.KEY_CURRENT_SONG_INDEX}
         */
        if (currentMusic != null) {
            mDexterousMediaPlayer.start();

            currentMusic.setSONG_IS_PLAYING(true);

            //show on notification_big
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
        } else if (OtherPreference.getCurrentSongIndex() != -1) {
            //This will call when user opens app (not first time) and toggle song
            //TODO pass his playList in second parameter
            PlayCurrentSong.playCurrentSong(this, DataManager.getInstance(this).getAllMusic(), OtherPreference.getCurrentSongIndex());
        } else {
            //when user opens app first time and press toogel button then play song of index zero whoese UI is set in default
            PlayCurrentSong.playCurrentSong(this, DataManager.getInstance(this).getAllMusic(), 0);
        }

        GlobalApplication.getBus().post(new MusicUnPaused());

    }

    protected boolean isPlaying() {
        if (mDexterousMediaPlayer != null)
            return mDexterousMediaPlayer.isPlaying();
        else
            return false;
    }
}
