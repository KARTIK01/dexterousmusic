package music.dexterous.com.dexterousmusic.musicutils;

import android.content.Context;

import java.util.List;

import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;
import music.dexterous.com.dexterousmusic.service.musiccontrol.NowPlayingList;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;

/**
 * Created by Honey on 8/12/2016.
 */
public class PlayCurrentSong {

    /**
     * Makes playlist from current song without shuffle mode
     *
     * @param context
     * @param allSongsList
     */
    public static void playCurrentSong(Context context, List<Music> allSongsList, int position) {

        //Set mode not to shuffle in SharedPreferences
        UsersAppPreference.setMusicShuffleMode(false);

        NowPlayingList.getInstance().setList(allSongsList);
        int currentSongPoistion = position;
        NowPlayingList.getInstance().setCurrentSongPosition(currentSongPoistion);
        DexterousPlayMusicService.startService(context, DexterousPlayMusicService.PLAY_MUSIC);
    }

}
