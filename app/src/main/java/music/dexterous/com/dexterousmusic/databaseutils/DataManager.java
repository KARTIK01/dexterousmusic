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
import music.dexterous.com.dexterousmusic.models.AlbumModel;
import music.dexterous.com.dexterousmusic.models.ArtistModel;
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
    private static final List<AlbumModel> albums = new ArrayList<>();
    private static final List<Music> allMusic = new ArrayList<>();
    private static final List<ArtistModel> artist = new ArrayList<>();

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
        loadArtist();
    }

    private void loadAlbums() {
        synchronized (albums) {
            List<AlbumModel> returnListOfAlbumNames = new ArrayList<>();
            Uri albumUri = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
            Cursor cursor = mContext.getContentResolver().query(albumUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    AlbumModel albumModel = new AlbumModel();
                    albumModel.setAlbumArtPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                    albumModel.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
                    returnListOfAlbumNames.add(albumModel);
                } while (cursor.moveToNext());
            }
            albums.clear();
            albums.addAll(returnListOfAlbumNames);
        }
    }

    private void loadAllMusic() {
        synchronized (allMusic) {
            List<Music> returnMusicList = mMusicDao.loadAll();
            allMusic.clear();
            allMusic.addAll(returnMusicList);
        }
    }

    private void loadArtist() {
        synchronized (artist) {
            List<ArtistModel> returnListOfAlbumNames = new ArrayList<>();
            Uri artistUri = android.provider.MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
            Cursor cursor = mContext.getContentResolver().query(artistUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ArtistModel albumModel = new ArtistModel();
                    albumModel.setArtistName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
//                    albumModel.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.)));
                    returnListOfAlbumNames.add(albumModel);
                } while (cursor.moveToNext());
            }
            artist.clear();
            artist.addAll(returnListOfAlbumNames);
        }
    }


    public void saveAllMusic(List<Music> musicList) {
        mMusicDao.insertOrReplaceInTx(musicList);
    }


    public List<Music> getAllMusic(String searchQuery) {
        return mMusicDao
                .queryBuilder()
                .where(MusicDao.Properties.SONG_TITLE.eq("%" + searchQuery + "%"))
                .list();
    }


    public static List<AlbumModel> getAlbums() {
        return albums;
    }

    public static List<ArtistModel> getArtist() {
        return artist;
    }


    public static List<Music> getAllMusic() {
        return allMusic;
    }


}
