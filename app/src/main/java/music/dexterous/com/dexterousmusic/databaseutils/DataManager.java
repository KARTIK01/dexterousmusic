package music.dexterous.com.dexterousmusic.databaseutils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.database.DaoSession;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.database.MusicDao;
import music.dexterous.com.dexterousmusic.models.AlbumModel;
import music.dexterous.com.dexterousmusic.models.ArtistModel;
import music.dexterous.com.dexterousmusic.models.PlaylistModel;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by Honey on 7/31/2016.
 */
public class DataManager extends MediaDao {

    /**
     * list of all Albums present
     */
    private static final Map<String, AlbumModel> albumHash = new HashMap<>();
    private static final Map<String, ArtistModel> artist = new HashMap<>();
    private static final Map<String, PlaylistModel> playList = new HashMap<>();
    /**
     * All music in user phone
     */
    private static final List<Music> allMusic = new ArrayList<>();
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

    public void loadHomeActivitySpecificData() {
        loadAllMusic();
        loadAlbums();
        loadArtist();
        loadPlayList();
    }

    private void loadAllMusic() {
        synchronized (allMusic) {
            List<Music> returnMusicList = mMusicDao.queryBuilder().orderAsc(MusicDao.Properties.SONG_TITLE).list();
            allMusic.clear();
            allMusic.addAll(returnMusicList);
        }
    }

    private void loadAlbums() {
        synchronized (albumHash) {
            Map<String, AlbumModel> returnListOfAlbumNames = new HashMap<>();
            Uri albumUri = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
            Cursor cursor = mContext.getContentResolver().query(albumUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    AlbumModel albumModel = new AlbumModel();
                    albumModel.setAlbumArtPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                    String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                    albumModel.setAlbumName(albumName);
                    returnListOfAlbumNames.put(albumName, albumModel);
                } while (cursor.moveToNext());
            }

            for (int i = 0; i < allMusic.size(); i++) {
                AlbumModel albumModel = returnListOfAlbumNames.get(allMusic.get(i).getSONG_ALBUM());
                List<Music> albumMusicList = albumModel.getMusicArrayList();
                albumMusicList.add(allMusic.get(i));
            }

            albumHash.clear();
            albumHash.putAll(returnListOfAlbumNames);
        }
    }

    private void loadArtist() {
        synchronized (artist) {
            Map<String, ArtistModel> returnListOfArtistNames = new HashMap<>();
            Uri artistUri = android.provider.MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
            Cursor cursor = mContext.getContentResolver().query(artistUri, null, null, null, MediaStore.Audio.Artists.ARTIST);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ArtistModel albumModel = new ArtistModel();
                    String artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                    albumModel.setArtistName(artistName);
                    returnListOfArtistNames.put(artistName, albumModel);
                } while (cursor.moveToNext());
            }

            for (int i = 0; i < allMusic.size(); i++) {
                ArtistModel artistModel = returnListOfArtistNames.get(allMusic.get(i).getSONG_ARTIST());
                List<Music> musicList1 = artistModel.getMusicArrayList();
                musicList1.add(allMusic.get(i));
            }

            artist.clear();
            artist.putAll(returnListOfArtistNames);
        }
    }

    private void loadPlayList() {
        synchronized (playList) {
            Map<String, PlaylistModel> returnListOfPlayList = new HashMap<>();
            Uri playListUri = android.provider.MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
            Cursor cursor = mContext.getContentResolver().query(playListUri, null, null, null, MediaStore.Audio.Playlists.NAME);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PlaylistModel playlistModel = new PlaylistModel();
                    String playListName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
                    long playListID = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
                    playlistModel.setName(playListName);
                    playlistModel.setId(playListID);

                    //TODO imporve this
                    // For each playlist, get all song IDs
                    Uri currentUri = MediaStore.Audio.Playlists.Members.getContentUri("external", playListID);

                    Cursor cursor2 = mContext.getContentResolver().query(currentUri, null, null, null, null);

                    // Adding each song's ID to it
                    for (cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()) {
                        long songId = cursor2.getLong(cursor2.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID));
                        if (allMusic.contains(songId)) {
                            playlistModel.getSongs().add(allMusic.get((int) songId));
                        }
                    }
                    returnListOfPlayList.put(playListName, playlistModel);
                } while (cursor.moveToNext());
            }

//            for (int i = 0; i < allMusic.size(); i++) {
//                PlaylistModel playlistModel = returnListOfPlayList.get(allMusic.get(i).getSONG_ARTIST());
//                List<Music> musicList1 = artistModel.getMusicArrayList();
//                musicList1.add(allMusic.get(i));
//            }

            PrettyLogger.d(returnListOfPlayList);
            playList.clear();
            playList.putAll(returnListOfPlayList);
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


    public List<AlbumModel> getAlbums() {
        return new ArrayList<AlbumModel>(albumHash.values());
    }

    public List<ArtistModel> getArtist() {
        return new ArrayList<ArtistModel>(artist.values());
    }

    public Map<String, AlbumModel> getAlbumsMap() {
        return albumHash;
    }

    public Map<String, ArtistModel> getArtistMap() {
        return artist;
    }

    public List<Music> getAllMusic() {
        return allMusic;
    }


}
