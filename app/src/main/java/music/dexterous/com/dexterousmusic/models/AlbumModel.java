package music.dexterous.com.dexterousmusic.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.dexterous.com.dexterousmusic.database.Music;

/**
 * Created by Honey on 8/11/2016.
 */
public class AlbumModel implements Parcelable {

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

    static public List<AlbumModel> getModel(List<Music> musicList, List<AlbumModel> albumModelList) {
        Map<String, AlbumModel> albumModelMap = new HashMap<>(albumModelList.size());

        for (int i = 0; i < albumModelList.size(); i++) {
            albumModelMap.put(albumModelList.get(i).albumName, albumModelList.get(i));
        }

        for (int i = 0; i < musicList.size(); i++) {
            AlbumModel albumModel = albumModelMap.get(musicList.get(i).getSONG_ALBUM());
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.albumName);
        dest.writeString(this.albumArtPath);
        dest.writeTypedList(this.musicArrayList);
    }

    public AlbumModel() {
    }

    protected AlbumModel(Parcel in) {
        this.albumName = in.readString();
        this.albumArtPath = in.readString();
        this.musicArrayList = in.createTypedArrayList(Music.CREATOR);
    }

    public static final Creator<AlbumModel> CREATOR = new Creator<AlbumModel>() {
        @Override
        public AlbumModel createFromParcel(Parcel source) {
            return new AlbumModel(source);
        }

        @Override
        public AlbumModel[] newArray(int size) {
            return new AlbumModel[size];
        }
    };

    @Override
    public String toString() {
        return "AlbumModel{" +
                "albumName='" + albumName + '\'' +
                ", albumArtPath='" + albumArtPath + '\'' +
                ", musicArrayList=" + musicArrayList +
                '}';
    }
}
