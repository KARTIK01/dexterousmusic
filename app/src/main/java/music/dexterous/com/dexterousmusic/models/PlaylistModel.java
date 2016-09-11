package music.dexterous.com.dexterousmusic.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import music.dexterous.com.dexterousmusic.database.Music;

public class PlaylistModel implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.songs);
    }

    public PlaylistModel() {
    }

    protected PlaylistModel(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.songs = in.createTypedArrayList(Music.CREATOR);
    }

    public static final Parcelable.Creator<PlaylistModel> CREATOR = new Parcelable.Creator<PlaylistModel>() {
        @Override
        public PlaylistModel createFromParcel(Parcel source) {
            return new PlaylistModel(source);
        }

        @Override
        public PlaylistModel[] newArray(int size) {
            return new PlaylistModel[size];
        }
    };
}
