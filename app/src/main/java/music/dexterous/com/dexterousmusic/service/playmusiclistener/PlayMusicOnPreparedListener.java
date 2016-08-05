package music.dexterous.com.dexterousmusic.service.playmusiclistener;

import android.media.MediaPlayer;

import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;

/**
 * Created by Honey on 7/31/2016.
 */
public class PlayMusicOnPreparedListener implements DexterousMediaPlayer.OnPreparedListener {


    private MediaPlayer mDexterousMediaPlayer;

    public PlayMusicOnPreparedListener(MediaPlayer dexterousMediaPlayer) {
        mDexterousMediaPlayer = dexterousMediaPlayer;
    }

    /**
     * Called when the music is ready for playback.
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // Start playback
        mDexterousMediaPlayer.start();
    }
}
