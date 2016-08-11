package music.dexterous.com.dexterousmusic.databaseutils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

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


    /**
     * list of all Albums present
     */
    private static final List<String> albums = new ArrayList<>();
    private static final List<Music> allMusic = new ArrayList<>();

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

    public void loadActivitySpecificData() {
        loadAllMusic();
        loadAlbums();
    }

    private void loadAlbums() {
        synchronized (albums) {
            List<String> returnListOfAlbumNames = new ArrayList<>();
            Uri albumUri = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
            Cursor cursor = mContext.getContentResolver().query(albumUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    returnListOfAlbumNames.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
                } while (cursor.moveToNext());
            }
            albums.clear();
            albums.addAll(returnListOfAlbumNames);
        }
    }

    public void saveAllMusic(List<Music> musicList) {
        mMusicDao.insertOrReplaceInTx(musicList);
    }

    private void loadAllMusic() {
        synchronized (allMusic) {
            List<Music> returnMusicList = mMusicDao.loadAll();
            allMusic.clear();
            allMusic.addAll(returnMusicList);
        }
    }


    public List<Music> getAllMusic(String searchQuery) {
        return mMusicDao
                .queryBuilder()
                .where(MusicDao.Properties.SONG_TITLE.eq("%" + searchQuery + "%"))
                .list();
    }


    public static List<String> getAlbums() {
        return albums;
    }

    public static List<Music> getAllMusic() {
        return allMusic;
    }


}
