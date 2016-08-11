package music.dexterous.com.dexterousmusic.databaseutils;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.database.DaoSession;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.database.MusicDao;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by Honey on 7/31/2016.
 */
public class DataManager extends MediaDao {

    private static DataManager sInstance;
    private Context mContext;


    /**
     * Dao classes for db queries
     */
    private MusicDao mMusicDao;

    private DataManager(Context context) {
        mContext = context;
        DaoSession mDaoSession = ((GlobalApplication) context).getSession();
        /* getting dao */
        mMusicDao = mDaoSession.getMusicDao();
    }

    public static DataManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DataManager.class) {
                if (sInstance == null) {
                    sInstance = new DataManager(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public void saveAllMusic(List<Music> musicList) {
        mMusicDao.insertOrReplaceInTx(musicList);
    }

    public List<Music> getAllMusic() {
        return mMusicDao.loadAll();
    }

    public List<Music> getAllMusic(String searchQuery) {
        return mMusicDao
                .queryBuilder()
                .where(MusicDao.Properties.SONG_TITLE.eq("%" + searchQuery + "%"))
                .list();
    }

    public Music getMusic(String index) {
        return mMusicDao.load(index);
    }


    public static ArrayList<Music> getAllAlbum(Context context) {
        return null;
    }

}
