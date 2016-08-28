package music.dexterous.com.dexterousmusic.musicutils;

import android.media.MediaPlayer;

/**
 * Created by Honey on 7/31/2016.
 */
public class DexterousMediaPlayer extends MediaPlayer {

    @Override
    public void start() throws IllegalStateException {
        super.start();

        // If the user clicks on the notification_big, let's spawn the
        // Now Playing screen.
//        notifyCurrentSong();
    }
//
//    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
//        throw new RuntimeException("Stub!");
//    }
//
//    public interface OnErrorListener {
//        boolean onError(DexterousMediaPlayer var1, int var2, int var3);
//    }
}
