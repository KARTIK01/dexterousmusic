package music.dexterous.com.dexterousmusic.utils.music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.database.MusicLibraryTable;
import music.dexterous.com.dexterousmusic.misc.PrettyLogger;

/**
 * Created by Dubey's on 31-07-2016.
 */
public class ScanningMusic {
    private boolean scanningSongs;
    private boolean scannedSongs;
    String GENRE_ID = MediaStore.Audio.Genres._ID;
    String GENRE_NAME = MediaStore.Audio.Genres.NAME;
    String SONG_ID = android.provider.MediaStore.Audio.Media._ID;
    String SONG_TITLE = android.provider.MediaStore.Audio.Media.TITLE;
    String SONG_ARTIST = android.provider.MediaStore.Audio.Media.ARTIST;
    String SONG_ALBUM = android.provider.MediaStore.Audio.Media.ALBUM;
    String SONG_YEAR = android.provider.MediaStore.Audio.Media.YEAR;
    String SONG_TRACK_NO = android.provider.MediaStore.Audio.Media.TRACK;
    String SONG_FILEPATH = android.provider.MediaStore.Audio.Media.DATA;
    String SONG_DURATION = android.provider.MediaStore.Audio.Media.DURATION;
    List<MusicLibraryTable> musicLibraryTables = new ArrayList<>();


    public boolean isScanning() {
        return scanningSongs;
    }

    public boolean isInitialized() {
        return scannedSongs;
    }

    public List<MusicLibraryTable> getAllMusicEntities(Context c) {

        if (scanningSongs) {
            return null;
        }
        scanFromInternal("internal", c);


        scanningSongs = true;


        return musicLibraryTables;
    }

    public void scanFromInternal(String fromWhere, Context c) {
        Uri musicUri = ((fromWhere == "internal") ?
                android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI :
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Uri genreUri = ((fromWhere == "internal") ?
                android.provider.MediaStore.Audio.Genres.INTERNAL_CONTENT_URI :
                android.provider.MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI);
        Uri playlistUri = ((fromWhere == "internal") ?
                android.provider.MediaStore.Audio.Playlists.INTERNAL_CONTENT_URI :
                android.provider.MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI);
        ContentResolver resolver = c.getContentResolver();
        Cursor cursor;
        final String musicsOnly = MediaStore.Audio.Media.IS_MUSIC + "=1";


        String[] columns = {
                SONG_ID,
                SONG_TITLE,
                SONG_ARTIST,
                SONG_ALBUM,
                SONG_YEAR,
                SONG_TRACK_NO,
                SONG_FILEPATH,
                SONG_DURATION,
        };
        cursor = resolver.query(musicUri, null, musicsOnly, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // NOTE: I tried to use MediaMetadataRetriever, but it was too slow.
            //       Even with 10 songs, it took like 13 seconds,
            //       No way I'm releasing it this way - I have like 4.260 songs!
            int i = 1;

            do {
                // Creating a song from the values on the row
                MusicLibraryTable song = new MusicLibraryTable();


                song.setSONG_ID("" + cursor.getInt(cursor.getColumnIndex(SONG_ID)));
                song.setSONG_FILE_PATH(cursor.getString(cursor.getColumnIndex(SONG_FILEPATH)));
                song.setSONG_TITLE(cursor.getString(cursor.getColumnIndex(SONG_TITLE)));
                song.setSONG_ARTIST(cursor.getString(cursor.getColumnIndex(SONG_ARTIST)));
                song.setSONG_ALBUM(cursor.getString(cursor.getColumnIndex(SONG_ALBUM)));
                song.setSONG_YEAR("" + cursor.getInt(cursor.getColumnIndex(SONG_YEAR)));
                song.setSONG_TRACK_NUMBER("" + cursor.getInt(cursor.getColumnIndex(SONG_TRACK_NO)));
                song.setSONG_DURATION("" + cursor.getInt(cursor.getColumnIndex(SONG_DURATION)));

                musicLibraryTables.add(song);
            }
            while (cursor.moveToNext());
        }
       /* PrettyLogger.e(Arrays.toString(musicLibraryTables.toArray()));
        PrettyLogger.e("" + musicLibraryTables.size());
*/

        scanFromExternal("external", c);


    }

    public void scanFromExternal(String fromWhere, Context c) {
        Uri musicUri = ((fromWhere == "internal") ?
                android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI :
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Uri genreUri = ((fromWhere == "internal") ?
                android.provider.MediaStore.Audio.Genres.INTERNAL_CONTENT_URI :
                android.provider.MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI);
        Uri playlistUri = ((fromWhere == "internal") ?
                android.provider.MediaStore.Audio.Playlists.INTERNAL_CONTENT_URI :
                android.provider.MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI);
        ContentResolver resolver = c.getContentResolver();
        Cursor cursor;
        final String musicsOnly = MediaStore.Audio.Media.IS_MUSIC + "=1";


        String[] columns = {
                SONG_ID,
                SONG_TITLE,
                SONG_ARTIST,
                SONG_ALBUM,
                SONG_YEAR,
                SONG_TRACK_NO,
                SONG_FILEPATH,
                SONG_DURATION
        };
        cursor = resolver.query(musicUri, columns, musicsOnly, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // NOTE: I tried to use MediaMetadataRetriever, but it was too slow.
            //       Even with 10 songs, it took like 13 seconds,
            //       No way I'm releasing it this way - I have like 4.260 songs!

            do {
                // Creating a song from the values on the row
                MusicLibraryTable song = new MusicLibraryTable();


                song.setSONG_ID("" + cursor.getInt(cursor.getColumnIndex(SONG_ID)));
                song.setSONG_FILE_PATH(cursor.getString(cursor.getColumnIndex(SONG_FILEPATH)));
                song.setSONG_TITLE(cursor.getString(cursor.getColumnIndex(SONG_TITLE)));
                song.setSONG_ARTIST(cursor.getString(cursor.getColumnIndex(SONG_ARTIST)));
                song.setSONG_ALBUM(cursor.getString(cursor.getColumnIndex(SONG_ALBUM)));
                song.setSONG_YEAR("" + cursor.getInt(cursor.getColumnIndex(SONG_YEAR)));
                song.setSONG_TRACK_NUMBER("" + cursor.getInt(cursor.getColumnIndex(SONG_TRACK_NO)));
                song.setSONG_DURATION("" + cursor.getInt(cursor.getColumnIndex(SONG_DURATION)));

                musicLibraryTables.add(song);
                PrettyLogger.e(cursor.getString(cursor.getColumnIndex(SONG_FILEPATH)));

            }
            while (cursor.moveToNext());
        }
        PrettyLogger.e(Arrays.toString(musicLibraryTables.toArray()));
        PrettyLogger.e("" + musicLibraryTables.size());


    }


}
