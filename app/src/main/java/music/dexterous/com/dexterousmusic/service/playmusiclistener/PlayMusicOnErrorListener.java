package music.dexterous.com.dexterousmusic.service.playmusiclistener;

import android.media.MediaPlayer;

import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by Honey on 7/31/2016.
 */
public class PlayMusicOnErrorListener implements DexterousMediaPlayer.OnErrorListener {

    /**
     * If something wrong happens with the MusicPlayer.
     */
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        //Reset media player
        //TODO send report
        PrettyLogger.e("Error in onError");
        return false;
    }
}
