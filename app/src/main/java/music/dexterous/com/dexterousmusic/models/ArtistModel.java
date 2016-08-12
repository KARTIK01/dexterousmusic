package music.dexterous.com.dexterousmusic.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.dexterous.com.dexterousmusic.database.Music;

/**
 * Created by Honey on 8/11/2016.
 */
public class ArtistModel {

    private String artistName;
    private List<Music> musicArrayList = new ArrayList<>();

    public String getAlbumName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }


    public List<Music> getMusicArrayList() {
        return musicArrayList;
    }

    public void setMusicArrayList(List<Music> musicArrayList) {
        this.musicArrayList = musicArrayList;
    }

    @Override
    public String toString() {
        return "\nArtistModel{" +
                ", artistName='" + artistName + '\'' +
                ", musicArrayList=" + musicArrayList +
                '}';
    }

    static public List<ArtistModel> getModel(List<Music> musicList, List<ArtistModel> albumModelList) {
        Map<String, ArtistModel> artistModelMap = new HashMap<>(albumModelList.size());

        for (int i = 0; i < albumModelList.size(); i++) {
            artistModelMap.put(albumModelList.get(i).artistName, albumModelList.get(i));
        }

        for (int i = 0; i < musicList.size(); i++) {
            ArtistModel albumModel = artistModelMap.get(musicList.get(i).getSONG_ARTIST());
            List<Music> musicList1 = albumModel.getMusicArrayList();
            musicList1.add(musicList.get(i));
            albumModel.setMusicArrayList(musicList1);
        }
        return new ArrayList<ArtistModel>(artistModelMap.values());
    }

    @Override
    public int hashCode() {
        return artistName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        ArtistModel albumModel = (ArtistModel) obj;
        if (albumModel.artistName.equals(this.artistName)) {
            return true;
        }
        return false;
    }
}
