package music.dexterous.com.dexterousmusic.musicutils;

import android.content.Context;

import java.util.List;

import music.dexterous.com.dexterousmusic.customeviews.widget.ShortToast;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;
import music.dexterous.com.dexterousmusic.service.musiccontrol.NowPlayingList;
import music.dexterous.com.dexterousmusic.utils.other.RandomNumberGeneratorForMusic;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;

/**
 * Created by Honey on 8/9/2016.
 */

public class ShuffleAllSongs {

    public static void shuffleAllSongs(Context context, List<Music> allSongsList) {

        //Set mode to shuffle in SharedPreferences
        UsersAppPreference.setMusicShuffleMode(true);

        NowPlayingList.getInstance().setList(allSongsList);
        int currentSongPoistion = RandomNumberGeneratorForMusic.nextInt(-1, allSongsList.size());
        if (currentSongPoistion == -1) {
            ShortToast.displayToast(context, "Please Select At Least One song to play", 2000);
            return;
        } else {
            NowPlayingList.getInstance().setCurrentSongPosition(currentSongPoistion);
            DexterousPlayMusicService.startService(context, DexterousPlayMusicService.PLAY_MUSIC);
        }
    }
}
