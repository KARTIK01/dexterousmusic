package music.dexterous.com.dexterousmusic.models;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import music.dexterous.com.dexterousmusic.database.Music;

/**
 * Created by Honey on 8/11/2016.
 */
public class AlbumModel {

    private String albumName;
    private Uri albumArtPath;
    private ArrayList<Music> musicArrayList;

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Uri getAlbumArtPath() {
        return albumArtPath;
    }

    public void setAlbumArtPath(Uri albumArtPath) {
        this.albumArtPath = albumArtPath;
    }

    public ArrayList<Music> getMusicArrayList() {
        return musicArrayList;
    }

    public void setMusicArrayList(ArrayList<Music> musicArrayList) {
        this.musicArrayList = musicArrayList;
    }

    @Override
    public String toString() {
        return "AlbumModel{" +
                "albumName='" + albumName + '\'' +
                ", albumArtPath=" + albumArtPath +
                ", musicArrayList=" + musicArrayList +
                '}';
    }

    public Set<AlbumModel> getModel(List<Music> musicList) {
        Set<AlbumModel> albumModelSet = new HashSet<>();

        for (int i = 0; i < musicList.size(); i++) {
            AlbumModel albumModel = new AlbumModel();
//TODO
            albumModelSet.add(albumModel);
        }

        return albumModelSet;
    }

    @Override
    public int hashCode() {
        return albumName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        AlbumModel albumModel = (AlbumModel) obj;
        if (albumModel.albumName.equals(this.albumName)) {
            return true;
        }
        return false;
    }
}
