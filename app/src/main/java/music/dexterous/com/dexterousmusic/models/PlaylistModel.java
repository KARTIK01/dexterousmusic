package music.dexterous.com.dexterousmusic.models;

import java.util.ArrayList;

import music.dexterous.com.dexterousmusic.database.Music;

public class PlaylistModel {

    private long id;
    private String name;

    private ArrayList<Music> songs = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Music> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Music> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "PlaylistModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", songs=" + songs +
                '}';
    }
}
