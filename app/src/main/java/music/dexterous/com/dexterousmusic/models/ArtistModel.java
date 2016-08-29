package music.dexterous.com.dexterousmusic.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.database.Music;

/**
 * Created by Honey on 8/11/2016.
 */
public class ArtistModel implements Parcelable {

    private String artistName;
    private List<Music> musicArrayList = new ArrayList<>();

    public String getArtistName() {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.artistName);
        dest.writeTypedList(this.musicArrayList);
    }

    public ArtistModel() {
    }

    protected ArtistModel(Parcel in) {
        this.artistName = in.readString();
        this.musicArrayList = in.createTypedArrayList(Music.CREATOR);
    }

    public static final Creator<ArtistModel> CREATOR = new Creator<ArtistModel>() {
        @Override
        public ArtistModel createFromParcel(Parcel source) {
            return new ArtistModel(source);
        }

        @Override
        public ArtistModel[] newArray(int size) {
            return new ArtistModel[size];
        }
    };
}
