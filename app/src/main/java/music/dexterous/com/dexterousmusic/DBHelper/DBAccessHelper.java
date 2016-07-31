/*
 * Copyright (C) 2014 Saravan Pantham
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package music.dexterous.com.dexterousmusic.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import music.dexterous.com.dexterousmusic.R;

import java.util.HashSet;

import music.dexterous.com.dexterousmusic.GlobalApplication;

/**
 * SQLite database implementation. Note that this class
 * only contains methods that access Jams' private
 * database. For methods that access Android's
 * MediaStore database, see MediaStoreAccessHelper.
 *
 * @author Saravan Pantham
 */
public class DBAccessHelper extends SQLiteOpenHelper {

    //Database instance. Will last for the lifetime of the application.
    private static DBAccessHelper sInstance;

    //Writable database instance.
    private SQLiteDatabase mDatabase;

    //Commmon utils object.
    private GlobalApplication mApp;

    //Database Version.
    private static final int DATABASE_VERSION = 1;

    //Database Name.
    private static final String DATABASE_NAME = "Dextro.db";

    //Common fields.
    public static final String _ID = "_id";
    public static final String SONG_ID = "song_id";
    public static final String EQ_50_HZ = "eq_50_hz";
    public static final String EQ_130_HZ = "eq_130_hz";
    public static final String EQ_320_HZ = "eq_320_hz";
    public static final String EQ_800_HZ = "eq_800_hz";
    public static final String EQ_2000_HZ = "eq_2000_hz";
    public static final String EQ_5000_HZ = "eq_5000_hz";
    public static final String EQ_12500_HZ = "eq_12500_hz";
    public static final String VIRTUALIZER = "eq_virtualizer";
    public static final String BASS_BOOST = "eq_bass_boost";
    public static final String REVERB = "eq_reverb";

    //Music folders table.
    public static final String MUSIC_FOLDERS_TABLE = "MusicFoldersTable";
    public static final String FOLDER_PATH = "folder_path";
    public static final String INCLUDE = "include";

    //Equalizer settings table for individual songs.
    public static final String EQUALIZER_TABLE = "EqualizerTable";

    //Equalizer presets table.
    public static final String EQUALIZER_PRESETS_TABLE = "EqualizerPresetsTable";
    public static final String PRESET_NAME = "preset_name";

    //Custom libraries table.
    public static final String LIBRARIES_TABLE = "LibrariesTable";
    public static final String LIBRARY_NAME = "library_name";
    public static final String LIBRARY_TAG = "library_tag";

    //Music library table.
    public static final String MUSIC_LIBRARY_TABLE = "MusicLibraryTable";
    public static final String SONG_TITLE = "title";
    public static final String SONG_ARTIST = "artist";
    public static final String SONG_ALBUM = "album";
    public static final String SONG_ALBUM_ARTIST = "album_artist";
    public static final String SONG_DURATION = "duration";
    public static final String SONG_FILE_PATH = "file_path";
    public static final String SONG_TRACK_NUMBER = "track_number";
    public static final String SONG_GENRE = "genre";
    public static final String SONG_PLAY_COUNT = "play_count";
    public static final String SONG_YEAR = "year";
    public static final String SONG_LAST_MODIFIED = "last_modified";
    public static final String SONG_SCANNED = "scanned";
    public static final String SONG_RATING = "rating";
    public static final String BLACKLIST_STATUS = "blacklist_status";
    public static final String ADDED_TIMESTAMP = "added_timestamp";
    public static final String RATING = "rating";
    public static final String LAST_PLAYED_TIMESTAMP = "last_played_timestamp";
    public static final String SONG_SOURCE = "source";
    public static final String SONG_ALBUM_ART_PATH = "album_art_path";
    public static final String SONG_DELETED = "deleted";
    public static final String ARTIST_ART_LOCATION = "artist_art_location";
    public static final String ALBUM_ID = "album_id";
    public static final String ARTIST_ID = "artist_id";
    public static final String GENRE_ID = "genre_id";
    public static final String GENRE_SONG_COUNT = "genre_song_count";
    public static final String LOCAL_COPY_PATH = "local_copy_path";
    public static final String LIBRARIES = "libraries";
    public static final String SAVED_POSITION = "saved_position";
    public static final String ALBUMS_COUNT = "albums_count";
    public static final String SONGS_COUNT = "songs_count";
    public static final String GENRES_SONG_COUNT = "genres_song_count";

    //Playlist fields.
    public static final String PLAYLIST_ID = "playlist_id";
    public static final String PLAYLIST_NAME = "playlist_name";
    public static final String PLAYLIST_SOURCE = "playlist_source";
    public static final String PLAYLIST_FILE_PATH = "playlist_file_path";
    public static final String PLAYLIST_FOLDER_PATH = "playlist_folder_path";
    public static final String PLAYLIST_SONG_ENTRY_ID = "song_entry_id";
    public static final String PLAYLIST_ORDER = "order";

    //Song source values.
    public static final String GMUSIC = "gmusic";
    public static final String LOCAL = "local";

    public DBAccessHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mApp = (GlobalApplication) context.getApplicationContext();

    }

    /**
     * Returns a singleton instance for the database.
     *
     * @param context
     * @return
     */
    public static synchronized DBAccessHelper getInstance(Context context) {
        if (sInstance == null)
            sInstance = new DBAccessHelper(context.getApplicationContext());

        return sInstance;
    }

    /**
     * Returns a writable instance of the database. Provides an additional
     * null check for additional stability.
     */
    private synchronized SQLiteDatabase getDatabase() {
        if (mDatabase == null)
            mDatabase = getWritableDatabase();

        return mDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Music folders table.
        String[] musicFoldersTableCols = {FOLDER_PATH, INCLUDE};
        String[] musicFoldersTableColTypes = {"TEXT", "TEXT"};
        String createMusicFoldersTable = buildCreateStatement(MUSIC_FOLDERS_TABLE,
                musicFoldersTableCols,
                musicFoldersTableColTypes);

        //Equalizer table.
        String[] equalizerTableCols = {SONG_ID, EQ_50_HZ, EQ_130_HZ,
                EQ_320_HZ, EQ_800_HZ, EQ_2000_HZ,
                EQ_5000_HZ, EQ_12500_HZ, VIRTUALIZER,
                BASS_BOOST, REVERB};

        String[] equalizerTableColTypes = {"TEXT", "TEXT", "TEXT",
                "TEXT", "TEXT", "TEXT",
                "TEXT", "TEXT", "TEXT",
                "TEXT", "TEXT"};

        String createEqualizerTable = buildCreateStatement(EQUALIZER_TABLE,
                equalizerTableCols,
                equalizerTableColTypes);

        //Equalizer presets table.
        String[] equalizerPresetsTableCols = {PRESET_NAME, EQ_50_HZ, EQ_130_HZ,
                EQ_320_HZ, EQ_800_HZ, EQ_2000_HZ,
                EQ_5000_HZ, EQ_12500_HZ, VIRTUALIZER,
                BASS_BOOST, REVERB};

        String[] equalizerPresetsTableColTypes = {"TEXT", "TEXT", "TEXT",
                "TEXT", "TEXT", "TEXT",
                "TEXT", "TEXT", "TEXT",
                "TEXT", "TEXT"};

        String createEqualizerPresetsTable = buildCreateStatement(EQUALIZER_PRESETS_TABLE,
                equalizerPresetsTableCols,
                equalizerPresetsTableColTypes);

        //Custom libraries table.
        String[] librariesTableCols = {LIBRARY_NAME, LIBRARY_TAG, SONG_ID};
        String[] librariesTableColTypes = {"TEXT", "TEXT", "TEXT"};
        String createLibrariesTable = buildCreateStatement(LIBRARIES_TABLE,
                librariesTableCols,
                librariesTableColTypes);

        //Music library table.
        String[] musicLibraryTableCols = {SONG_ID, SONG_TITLE, SONG_ARTIST,
                SONG_ALBUM, SONG_ALBUM_ARTIST,
                SONG_DURATION, SONG_FILE_PATH,
                SONG_TRACK_NUMBER, SONG_GENRE,
                SONG_PLAY_COUNT, SONG_YEAR, ALBUMS_COUNT,
                SONGS_COUNT, GENRES_SONG_COUNT, SONG_LAST_MODIFIED, SONG_SCANNED,
                BLACKLIST_STATUS, ADDED_TIMESTAMP, RATING,
                LAST_PLAYED_TIMESTAMP, SONG_SOURCE, SONG_ALBUM_ART_PATH,
                SONG_DELETED, ARTIST_ART_LOCATION, ALBUM_ID,
                ARTIST_ID, GENRE_ID, GENRE_SONG_COUNT,
                LOCAL_COPY_PATH, LIBRARIES, SAVED_POSITION};

        String[] musicLibraryTableColTypes = new String[musicLibraryTableCols.length];
        for (int i = 0; i < musicLibraryTableCols.length; i++)
            musicLibraryTableColTypes[i] = "TEXT";

        String createMusicLibraryTable = buildCreateStatement(MUSIC_LIBRARY_TABLE,
                musicLibraryTableCols,
                musicLibraryTableColTypes);

        //Execute the CREATE statements.
        db.execSQL(createMusicFoldersTable);
        db.execSQL(createEqualizerTable);
        db.execSQL(createEqualizerPresetsTable);
        db.execSQL(createLibrariesTable);
        db.execSQL(createMusicLibraryTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void finalize() {
        try {
            getDatabase().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Constructs a fully formed CREATE statement using the input
     * parameters.
     */
    private String buildCreateStatement(String tableName, String[] columnNames, String[] columnTypes) {
        String createStatement = "";
        if (columnNames.length == columnTypes.length) {
            createStatement += "CREATE TABLE IF NOT EXISTS " + tableName + "("
                    + _ID + " INTEGER PRIMARY KEY, ";

            for (int i = 0; i < columnNames.length; i++) {

                if (i == columnNames.length - 1) {
                    createStatement += columnNames[i]
                            + " "
                            + columnTypes[i]
                            + ")";
                } else {
                    createStatement += columnNames[i]
                            + " "
                            + columnTypes[i]
                            + ", ";
                }

            }

        }

        return createStatement;
    }

    /***********************************************************
     * MUSIC FOLDERS TABLE METHODS.
     ***********************************************************/

    /**
     * Adds a music folder to the table.
     */
    public void addMusicFolderPath(String folderPath) {
        //Escape any rogue apostrophes.
        if (folderPath.contains("'")) {
            folderPath = folderPath.replace("'", "''");
        }

        ContentValues values = new ContentValues();
        values.put(FOLDER_PATH, folderPath);

        getDatabase().insert(MUSIC_FOLDERS_TABLE, null, values);

    }


    /**
     * Deletes the specified music folder from the table.
     */
    public void deleteMusicFolderPath(String folderPath) {
        String condition = FOLDER_PATH + " = '" + folderPath + "'";
        getDatabase().delete(MUSIC_FOLDERS_TABLE, condition, null);
    }

    /**
     * Deletes all music folders from the table.
     */
    public void deleteAllMusicFolderPaths() {
        getDatabase().delete(MUSIC_FOLDERS_TABLE, null, null);
    }

    /**
     * Returns a cursor with all music folder paths in the table.
     */
    public Cursor getAllMusicFolderPaths() {
        String selectQuery = "SELECT  * FROM " + MUSIC_FOLDERS_TABLE
                + " ORDER BY " + INCLUDE + "*1 DESC";

        return getDatabase().rawQuery(selectQuery, null);
    }

    /***********************************************************
     * EQUALIZER TABLE METHODS.
     ***********************************************************/

    /**
     * Returns an integer array with EQ values for the specified song.
     * The final array index (10) indicates whether the specified song
     * has any saved EQ values (0 for false, 1 for true).
     *
     * @param songId The id of the song to retrieve EQ values for.
     */
    public int[] getSongEQValues(String songId) {

        String condition = SONG_ID + "=" + "'" + songId + "'";
        String[] columnsToReturn = {_ID, EQ_50_HZ, EQ_130_HZ, EQ_320_HZ,
                EQ_800_HZ, EQ_2000_HZ, EQ_5000_HZ,
                EQ_12500_HZ, VIRTUALIZER, BASS_BOOST, REVERB};

        Cursor cursor = getDatabase().query(EQUALIZER_TABLE, columnsToReturn, condition, null, null, null, null);
        int[] eqValues = new int[11];

        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            eqValues[0] = cursor.getInt(cursor.getColumnIndex(EQ_50_HZ));
            eqValues[1] = cursor.getInt(cursor.getColumnIndex(EQ_130_HZ));
            eqValues[2] = cursor.getInt(cursor.getColumnIndex(EQ_320_HZ));
            eqValues[3] = cursor.getInt(cursor.getColumnIndex(EQ_800_HZ));
            eqValues[4] = cursor.getInt(cursor.getColumnIndex(EQ_2000_HZ));
            eqValues[5] = cursor.getInt(cursor.getColumnIndex(EQ_5000_HZ));
            eqValues[6] = cursor.getInt(cursor.getColumnIndex(EQ_12500_HZ));
            eqValues[7] = cursor.getInt(cursor.getColumnIndex(VIRTUALIZER));
            eqValues[8] = cursor.getInt(cursor.getColumnIndex(BASS_BOOST));
            eqValues[9] = cursor.getInt(cursor.getColumnIndex(REVERB));
            eqValues[10] = 1; //The song id exists in the EQ table.

            cursor.close();

        } else {
            eqValues[0] = 16;
            eqValues[1] = 16;
            eqValues[2] = 16;
            eqValues[3] = 16;
            eqValues[4] = 16;
            eqValues[5] = 16;
            eqValues[6] = 16;
            eqValues[7] = 0;
            eqValues[8] = 0;
            eqValues[9] = 0;
            eqValues[10] = 0; //The song id doesn't exist in the EQ table.

        }

        return eqValues;
    }

    /**
     * Saves a song's equalizer/audio effect settings to the database.
     */
    public void addSongEQValues(String songId,
                                int fiftyHertz,
                                int oneThirtyHertz,
                                int threeTwentyHertz,
                                int eightHundredHertz,
                                int twoKilohertz,
                                int fiveKilohertz,
                                int twelvePointFiveKilohertz,
                                int virtualizer,
                                int bassBoost,
                                int reverb) {

        ContentValues values = new ContentValues();
        values.put(SONG_ID, songId);
        values.put(EQ_50_HZ, fiftyHertz);
        values.put(EQ_130_HZ, threeTwentyHertz);
        values.put(EQ_320_HZ, threeTwentyHertz);
        values.put(EQ_800_HZ, eightHundredHertz);
        values.put(EQ_2000_HZ, twoKilohertz);
        values.put(EQ_5000_HZ, fiveKilohertz);
        values.put(EQ_12500_HZ, twelvePointFiveKilohertz);
        values.put(VIRTUALIZER, virtualizer);
        values.put(BASS_BOOST, bassBoost);
        values.put(REVERB, reverb);

        getDatabase().insert(EQUALIZER_TABLE, null, values);

    }

    /**
     * Checks if equalizer settings already exist for the given song.
     */
    public boolean hasEqualizerSettings(String songId) {

        String where = SONG_ID + "=" + "'" + songId + "'";
        Cursor cursor = getDatabase().query(EQUALIZER_TABLE,
                new String[]{SONG_ID},
                where,
                null,
                null,
                null,
                null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }

        } else {
            return false;
        }

    }

    /**
     * Updates the equalizer/audio effects for the specified song.
     */
    public void updateSongEQValues(String songId,
                                   int fiftyHertz,
                                   int oneThirtyHertz,
                                   int threeTwentyHertz,
                                   int eightHundredHertz,
                                   int twoKilohertz,
                                   int fiveKilohertz,
                                   int twelvePointFiveKilohertz,
                                   int virtualizer,
                                   int bassBoost,
                                   int reverb) {

        ContentValues values = new ContentValues();
        values.put(EQ_50_HZ, fiftyHertz);
        values.put(EQ_130_HZ, threeTwentyHertz);
        values.put(EQ_320_HZ, threeTwentyHertz);
        values.put(EQ_800_HZ, eightHundredHertz);
        values.put(EQ_2000_HZ, twoKilohertz);
        values.put(EQ_5000_HZ, fiveKilohertz);
        values.put(EQ_12500_HZ, twelvePointFiveKilohertz);
        values.put(VIRTUALIZER, virtualizer);
        values.put(BASS_BOOST, bassBoost);
        values.put(REVERB, reverb);

        String condition = SONG_ID + " = " + "'" + songId + "'";
        getDatabase().update(EQUALIZER_TABLE, values, condition, null);

    }

    /***********************************************************
     * EQUALIZER PRESETS TABLE METHODS.
     ***********************************************************/

    /**
     * Adds a new EQ preset to the table.
     */
    public void addNewEQPreset(String presetName,
                               int fiftyHertz,
                               int oneThirtyHertz,
                               int threeTwentyHertz,
                               int eightHundredHertz,
                               int twoKilohertz,
                               int fiveKilohertz,
                               int twelvePointFiveKilohertz,
                               short virtualizer,
                               short bassBoost,
                               short reverb) {

        ContentValues values = new ContentValues();
        values.put(PRESET_NAME, presetName);
        values.put(EQ_50_HZ, fiftyHertz);
        values.put(EQ_130_HZ, threeTwentyHertz);
        values.put(EQ_320_HZ, threeTwentyHertz);
        values.put(EQ_800_HZ, eightHundredHertz);
        values.put(EQ_2000_HZ, twoKilohertz);
        values.put(EQ_5000_HZ, fiveKilohertz);
        values.put(EQ_12500_HZ, twelvePointFiveKilohertz);
        values.put(VIRTUALIZER, virtualizer);
        values.put(BASS_BOOST, bassBoost);
        values.put(REVERB, reverb);

        getDatabase().insert(EQUALIZER_PRESETS_TABLE, null, values);

    }

    /**
     * This method returns the specified eq preset.
     */
    public int[] getPresetEQValues(String presetName) {

        String condition = PRESET_NAME + "=" + "'" + presetName.replace("'", "''") + "'";
        String[] columnsToReturn = {_ID, EQ_50_HZ, EQ_130_HZ, EQ_320_HZ,
                EQ_800_HZ, EQ_2000_HZ, EQ_5000_HZ,
                EQ_12500_HZ, VIRTUALIZER, BASS_BOOST, REVERB};

        Cursor cursor = getDatabase().query(EQUALIZER_PRESETS_TABLE, columnsToReturn, condition, null, null, null, null);
        int[] eqValues = new int[10];

        if (cursor != null && cursor.getCount() != 0) {
            eqValues[0] = cursor.getInt(cursor.getColumnIndex(EQ_50_HZ));
            eqValues[1] = cursor.getInt(cursor.getColumnIndex(EQ_130_HZ));
            eqValues[2] = cursor.getInt(cursor.getColumnIndex(EQ_320_HZ));
            eqValues[3] = cursor.getInt(cursor.getColumnIndex(EQ_800_HZ));
            eqValues[4] = cursor.getInt(cursor.getColumnIndex(EQ_2000_HZ));
            eqValues[5] = cursor.getInt(cursor.getColumnIndex(EQ_5000_HZ));
            eqValues[6] = cursor.getInt(cursor.getColumnIndex(EQ_12500_HZ));
            eqValues[7] = cursor.getInt(cursor.getColumnIndex(VIRTUALIZER));
            eqValues[8] = cursor.getInt(cursor.getColumnIndex(BASS_BOOST));
            eqValues[9] = cursor.getInt(cursor.getColumnIndex(REVERB));

            cursor.close();

        } else {
            eqValues[0] = 16;
            eqValues[1] = 16;
            eqValues[2] = 16;
            eqValues[3] = 16;
            eqValues[4] = 16;
            eqValues[5] = 16;
            eqValues[6] = 16;
            eqValues[7] = 16;
            eqValues[8] = 16;
            eqValues[9] = 16;

        }

        return eqValues;
    }

    /**
     * This method updates the specified EQ preset.
     */
    public void updateEQPreset(String presetName,
                               int fiftyHertz,
                               int oneThirtyHertz,
                               int threeTwentyHertz,
                               int eightHundredHertz,
                               int twoKilohertz,
                               int fiveKilohertz,
                               int twelvePointFiveKilohertz,
                               short virtualizer,
                               short bassBoost,
                               short reverb) {

        //Escape any rogue apostrophes.
        if (presetName != null) {

            if (presetName.contains("'")) {
                presetName = presetName.replace("'", "''");
            }

        }

        ContentValues values = new ContentValues();
        values.put(EQ_50_HZ, fiftyHertz);
        values.put(EQ_130_HZ, threeTwentyHertz);
        values.put(EQ_320_HZ, threeTwentyHertz);
        values.put(EQ_800_HZ, eightHundredHertz);
        values.put(EQ_2000_HZ, twoKilohertz);
        values.put(EQ_5000_HZ, fiveKilohertz);
        values.put(EQ_12500_HZ, twelvePointFiveKilohertz);
        values.put(VIRTUALIZER, virtualizer);
        values.put(BASS_BOOST, bassBoost);
        values.put(REVERB, reverb);

        String condition = PRESET_NAME + " = " + "'" + presetName + "'";
        getDatabase().update(EQUALIZER_PRESETS_TABLE, values, condition, null);

    }

    /**
     * Returns a cursor with all EQ presets in the table.
     */
    public Cursor getAllEQPresets() {
        String query = "SELECT * FROM " + EQUALIZER_PRESETS_TABLE;
        return getDatabase().rawQuery(query, null);

    }

    //Deletes the specified preset.
    public void deletePreset(String presetName) {
        String condition = PRESET_NAME + " = " + "'" + presetName.replace("'", "''") + "'";
        getDatabase().delete(EQUALIZER_PRESETS_TABLE, condition, null);

    }

    /***********************************************************
     * LIBRARIES TABLE METHODS.
     ***********************************************************/

    /**
     * Returns a cursor with a list of all unique libraries within the database.
     *
     * @return
     */
    public Cursor getAllUniqueLibraries() {
        String rawQuery = "SELECT DISTINCT(" + LIBRARY_NAME + "), " +
                _ID + ", " + LIBRARY_TAG +
                " FROM " + LIBRARIES_TABLE + " GROUP BY " +
                LIBRARY_NAME + " ORDER BY " + _ID
                + " ASC";

        Cursor cursor = getDatabase().rawQuery(rawQuery, null);
        return cursor;
    }

    /**
     * Deletes the specified library by its name and tag.
     */
    public void deleteLibrary(String libraryName, String tag) {

        //Escape any rogue apostrophes.
        libraryName = libraryName.replace("'", "''");
        tag = tag.replace("'", "''");

        //Perform the delete operation.
        String where = LIBRARY_NAME + "=" + "'" + libraryName + "'" + " AND "
                + LIBRARY_TAG + "=" + "'" + tag + "'";

        getDatabase().delete(LIBRARIES_TABLE, where, null);
    }

    public HashSet<String> getAllSongIdsInLibrary(String libraryName, String tag) {
        HashSet<String> songIdsHashSet = new HashSet<String>();

        libraryName = libraryName.replace("'", "''");
        tag = tag.replace("'", "''");

        String where = LIBRARY_NAME + "=" + "'" + libraryName + "'" + " AND "
                + LIBRARY_TAG + "=" + "'" + tag + "'";

        Cursor cursor = getDatabase().query(LIBRARIES_TABLE, null, where, null, null, null, SONG_ID);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                songIdsHashSet.add(cursor.getString(cursor.getColumnIndex(SONG_ID)));
            }

        }

        if (cursor != null) {
            cursor.close();
            cursor = null;
        }

        return songIdsHashSet;
    }

}
