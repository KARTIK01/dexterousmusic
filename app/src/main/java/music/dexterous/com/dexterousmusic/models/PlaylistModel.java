package music.dexterous.com.dexterousmusic.models;

import java.util.ArrayList;

import music.dexterous.com.dexterousmusic.database.Music;

public class PlaylistModel {

    private long   id;
    private String name;

    private ArrayList<Music> songs = new ArrayList<>();

}
