package music.dexterous.com.dexterousmusic.service.playmusiclistener;

import android.media.MediaPlayer;

import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.service.musiccontrol.AbstractMusicControlService;
import music.dexterous.com.dexterousmusic.service.musiccontrol.MusicControl;
import music.dexterous.com.dexterousmusic.service.musiccontrol.MusicList;
import music.dexterous.com.dexterousmusic.utils.preference.AppPreference;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;

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

        //TODO send report song completed

        // Repeating current song if desired
        if (UsersAppPreference.getMusicRepeatModeSetting() ==
                UsersAppPreference.RepeatModeContants.REPEAT_CURRENT_SONG) {
            musicControl.playMusic();
            return;
        }

        // Remember that by calling next(), if played
        // the last song on the list, will reset to the
        // first one.
        musicControl.playNextMusic(false);


        // Reached the end, should we restart playing
        // from the first song or simply stop?
        if (MusicList.getInstance().getCurrentSongPosition() == 0) {
            if (UsersAppPreference.getMusicRepeatModeSetting() ==
                    UsersAppPreference.RepeatModeContants.REPEAT_CURRENT_PLAYLIST)
                musicControl.playMusic();
            else
                musicControl.destroySelf();
            return;
        }
        // Common case - skipped a track or anything
        musicControl.playMusic();

    }
}
