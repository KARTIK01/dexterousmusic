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

    public static void saveAllMusic(Context context, List<Music> musicLibraryTables) {
        ((GlobalApplication) context).getSession().getMusicDao().insertInTx(musicLibraryTables);
    }

    public static List<Music> getAllMusic(Context context) {
        return ((GlobalApplication) context).getSession().getMusicDao().loadAll();
    }

    public static List<Music> getAllMusic(Context context, String searchQuery) {
        return ((GlobalApplication) context)
                .getSession()
                .getMusicDao()
                .queryBuilder()
                .where(MusicDao.Properties.SONG_TITLE.like("%" + searchQuery + "%"))
                .list();
    }

}
