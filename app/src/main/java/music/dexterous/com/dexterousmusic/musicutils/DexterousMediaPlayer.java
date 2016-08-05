package music.dexterous.com.dexterousmusic.musicutils;

import android.media.MediaPlayer;

/**
 * Created by Honey on 7/31/2016.
 */
public class DexterousMediaPlayer extends MediaPlayer {

    @Override
    public void start() throws IllegalStateException {
        super.start();

        // If the user clicks on the notification, let's spawn the
        // Now Playing screen.
//        notifyCurrentSong();
    }
}
