package music.dexterous.com.dexterousmusic.database.music;

import android.content.Context;

import java.util.ArrayList;

import de.greenrobot.dao.internal.DaoConfig;
import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.database.MusicLibraryTable;
import music.dexterous.com.dexterousmusic.database.MusicLibraryTableDao;

/**
 * Created by Honey on 7/31/2016.
 */
public class MyMusicLibraryTableDao {

    public static void saveAll(Context context, ArrayList<MusicLibraryTable> musicLibraryTables) {
        ((GlobalApplication) context).getSession().getMusicLibraryTableDao().insertInTx(musicLibraryTables);
    }

}
