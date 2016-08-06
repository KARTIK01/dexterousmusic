package music.dexterous.com.dexterousmusic.service.playmusiclistener;

import android.media.MediaPlayer;

import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.service.musiccontrol.MusicControl;

/**
 * Created by Honey on 7/31/2016.
 */
public class PlayMusicOnCompletionListener implements DexterousMediaPlayer.OnCompletionListener {


    private MusicControl musicControl;

    public PlayMusicOnCompletionListener(MusicControl musicControl) {
        this.musicControl = musicControl;
    }

    /**
     * Will be called when the music completes - either when the
     * user presses 'next' or when the music ends or when the user
     * selects another track.
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        // Repeating current song if desired
        if (true) { //TODO replace with repeat mode var
            musicControl.playMusic();
            return;
        }

    }
}
