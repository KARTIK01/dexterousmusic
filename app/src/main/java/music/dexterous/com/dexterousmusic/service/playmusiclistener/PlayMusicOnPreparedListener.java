package music.dexterous.com.dexterousmusic.service.playmusiclistener;

import android.media.MediaPlayer;

import music.dexterous.com.dexterousmusic.BuildConfig;
import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.service.musiccontrol.MusicControl;

/**
 * Created by Honey on 7/31/2016.
 */
public class PlayMusicOnPreparedListener implements DexterousMediaPlayer.OnPreparedListener {


    private MusicControl mMusicControl;
    private MediaPlayer mDexterousMediaPlayer;

    public PlayMusicOnPreparedListener(MusicControl musicControl, MediaPlayer dexterousMediaPlayer) {
        mMusicControl = musicControl;
        mDexterousMediaPlayer = dexterousMediaPlayer;
    }

    /**
     * Called when the music is ready for playback.
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // Start playback
        mDexterousMediaPlayer.start();
//        if (BuildConfig.DEBUG)
//            mDexterousMediaPlayer.seekTo(mDexterousMediaPlayer.getDuration() - 1000 * 10);
        // If the user clicks on the notification_big, let's spawn the
        // Now Playing screen.
        mMusicControl.notifyCurrentSong();
    }
}
