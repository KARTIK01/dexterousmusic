package music.dexterous.com.dexterousmusic.models;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import music.dexterous.com.dexterousmusic.database.Music;

/**
 * Created by Honey on 8/11/2016.
 */
public class AlbumModel {

    private String albumName;
    private String albumArtPath;
    private List<Music> musicArrayList = new ArrayList<>();

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumArtPath() {
        return albumArtPath;
    }

    public void setAlbumArtPath(String albumArtPath) {
        this.albumArtPath = albumArtPath;
    }

    public List<Music> getMusicArrayList() {
        return musicArrayList;
    }

    public void setMusicArrayList(List<Music> musicArrayList) {
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

    static public List<AlbumModel> getModel(List<Music> musicList, List<String> albumsNames) {
        Map<String, AlbumModel> albumModelMap = new HashMap<>(albumsNames.size());

        for (int i = 0; i < albumsNames.size(); i++) {
            AlbumModel albumModel = new AlbumModel();
            albumModel.setAlbumName(albumsNames.get(i));
            albumModelMap.put(albumsNames.get(i), albumModel);
        }

        for (int i = 0; i < musicList.size(); i++) {
            AlbumModel albumModel = albumModelMap.get(musicList.get(i).getSONG_ALBUM());
            albumModel.setAlbumArtPath(musicList.get(i).getSONG_ALBUM_ART_PATH());
            List<Music> musicList1 = albumModel.getMusicArrayList();
            musicList1.add(musicList.get(i));
            albumModel.setMusicArrayList(musicList1);
        }
        return new ArrayList<AlbumModel>(albumModelMap.values());
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
