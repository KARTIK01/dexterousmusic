package music.dexterous.com.dexterousmusic.service.playmusiclistener;

import android.media.MediaPlayer;

import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;

/**
 * Created by Honey on 7/31/2016.
 */
public class PlayMusicOnErrorListener implements DexterousMediaPlayer.OnErrorListener{

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }
}
