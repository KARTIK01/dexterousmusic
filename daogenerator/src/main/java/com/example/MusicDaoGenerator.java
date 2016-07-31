package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MusicDaoGenerator {
    public static final int SCHEMA_VERSION = 0;

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(SCHEMA_VERSION, "music.dexterous.com.dexterousmusic.database");

        addMusicLibrary(schema);


        schema.enableKeepSectionsByDefault();
        String generatedModelPath = "app/src/main/java";
        new DaoGenerator().generateAll(schema, generatedModelPath);
    }

    /**
     * Music library table.
     */
    private static Entity addMusicLibrary(Schema schema) {
        Entity newsItemEntity = schema.addEntity("MusicLibraryTable");

        newsItemEntity.addLongProperty("id").primaryKey();

        newsItemEntity.addStringProperty("SONG_ID");
        newsItemEntity.addStringProperty("SONG_TITLE");
        newsItemEntity.addStringProperty("SONG_ARTIST");
        newsItemEntity.addStringProperty("SONG_ALBUM");
        newsItemEntity.addStringProperty("SONG_ALBUM_ARTIST");
        newsItemEntity.addStringProperty("SONG_DURATION");
        newsItemEntity.addStringProperty("SONG_FILE_PATH");

        //TODO Check why we require this
        newsItemEntity.addStringProperty("SONG_TRACK_NUMBER");

        newsItemEntity.addStringProperty("SONG_GENRE");
        newsItemEntity.addStringProperty("SONG_PLAY_COUNT");
        newsItemEntity.addStringProperty("SONG_YEAR");

        //TODO Check why we require this
        newsItemEntity.addStringProperty("ALBUMS_COUNT");

        newsItemEntity.addStringProperty("SONGS_COUNT");
        newsItemEntity.addStringProperty("GENRES_SONG_COUNT");
        newsItemEntity.addStringProperty("SONG_LAST_MODIFIED");
        newsItemEntity.addStringProperty("SONG_SCANNED");
        newsItemEntity.addStringProperty("BLACKLIST_STATUS");
        newsItemEntity.addStringProperty("ADDED_TIMESTAMP");
        newsItemEntity.addStringProperty("RATING");
        newsItemEntity.addStringProperty("LAST_PLAYED_TIMESTAMP");
        newsItemEntity.addStringProperty("SONG_SOURCE");
        newsItemEntity.addStringProperty("SONG_ALBUM_ART_PATH");
        newsItemEntity.addStringProperty("SONG_DELETED");
        newsItemEntity.addStringProperty("ARTIST_ART_LOCATION");
        newsItemEntity.addStringProperty("ALBUM_ID");
        newsItemEntity.addStringProperty("ARTIST_ID");
        newsItemEntity.addStringProperty("GENRE_ID");
        newsItemEntity.addStringProperty("GENRE_SONG_COUNT");
        newsItemEntity.addStringProperty("LOCAL_COPY_PATH");
        newsItemEntity.addStringProperty("LIBRARIES");
        newsItemEntity.addStringProperty("SAVED_POSITION");

        newsItemEntity.implementsInterface("Parcelable");

        return newsItemEntity;
    }

}
