package music.dexterous.com.dexterousmusic.databaseutils;

import android.content.Context;

import java.util.List;

import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.database.MusicDao;

/**
 * Created by Honey on 7/31/2016.
 */
public class MyMusicLibraryTableDao {

    public static void saveAllMusic(Context context, List<Music> musicList) {
        ((GlobalApplication) context).getSession().getMusicDao().insertInTx(musicList);
    }

    public static List<Music> getAllMusic(Context context) {
        return ((GlobalApplication) context).getSession().getMusicDao().loadAll();
    }

    public static List<Music> getAllMusic(Context context, String searchQuery) {
        return ((GlobalApplication) context)
                .getSession()
                .getMusicDao()
                .queryBuilder()
                .where(MusicDao.Properties.SONG_ARTIST.eq("Bilal Saeed"))
                .list();
    }
}
