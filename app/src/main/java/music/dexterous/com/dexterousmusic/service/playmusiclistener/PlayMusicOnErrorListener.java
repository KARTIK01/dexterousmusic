package music.dexterous.com.dexterousmusic.service.playmusiclistener;

import android.media.MediaPlayer;

import com.crashlytics.android.Crashlytics;

import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by Honey on 7/31/2016.
 */
public class PlayMusicOnErrorListener implements DexterousMediaPlayer.OnErrorListener {

    /**
     * If something wrong happens with the MusicPlayer. Called to indicate an error.
     *
     * @param mediaPlayer the MediaPlayer the error pertains to
     * @param what        the type of error that has occurred: <ul> <li>{@link #MEDIA_ERROR_UNKNOWN}
     *                    1 Unspecified media player error. <li>{@link #MEDIA_ERROR_SERVER_DIED} 100
     *                    Media server died. </ul>
     * @param extra       an extra code, specific to the error. Typically implementation dependent.
     *                    <ul> <li>{@link #MEDIA_ERROR_IO} 1004 File or network related operation
     *                    errors. <li>{@link #MEDIA_ERROR_MALFORMED} 1007 Bitstream is not
     *                    conforming to the related coding standard or file spec. <li>{@link
     *                    #MEDIA_ERROR_UNSUPPORTED} 1010  Bitstream is conforming to the related
     *                    coding standard or file spec, but the media framework does not support the
     *                    feature. <li>{@link #MEDIA_ERROR_TIMED_OUT} 110 Some operation takes too
     *                    long to complete, usually more than 3-5 seconds. </ul>
     *
     * @return True if the method handled the error, false if it didn't. Returning false, or not
     * having an OnErrorListener at all, will cause the OnCompletionListener to be called.
     */
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        //Reset media player
        mediaPlayer.reset();
        Crashlytics.setString("What Extra :", "" + what + ", " + extra);
        PrettyLogger.e("Error in onError PlayMusicOnErrorListener", new PlayMusicOnError(what, extra));
        return false;
    }

    class PlayMusicOnError extends Exception {

        private int what;
        private int extra;

        PlayMusicOnError(int what, int extra) {
            super("Error in onError PlayMusicOnErrorListener  what : " + what + " extra : " + extra);
            this.what = what;
            this.extra = extra;
        }

        @Override
        public String toString() {
            return ("Error in onError PlayMusicOnErrorListener  what : " + what + " extra : " + extra);
        }
    }
}
