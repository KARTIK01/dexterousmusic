package music.dexterous.com.dexterousmusic.service.musiccontrol;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.utils.preference.OtherPreference;

/**
 * This class is responsible for all music which are added to now playing queue
 */
public class NowPlayingList {

    static private NowPlayingList nowPlayingList;

    /**
     * Index of the current song we're playing on the `songs` list.
     */
    private int currentSongPosition;

    /**
     * List of songs we're  currently playing. or in playList
     */
    private List<Music> list;

    private NowPlayingList() {
        list = new ArrayList<>();
    }

    public static NowPlayingList getInstance() {
        if (nowPlayingList == null) {
            synchronized (NowPlayingList.class) {
                if (nowPlayingList == null) {
                    nowPlayingList = new NowPlayingList();
                }
            }
        }
        return nowPlayingList;
    }

    /**
     * Appends a song to the end of the currently playing queue.
     *
     * @param music New song to put at the end.
     */
    public void add(Music music) {
        list.add(music);
    }

    /**
     * Returns the song on the Now Playing List at `position`.
     */
    public Music getSong(int position) {
        return list.get(position);
    }

    public int getCurrentSongPosition() {
        return currentSongPosition;
    }

    /**
     * Sets a specific song, already within internal Now Playing List.
     *
     * @param songIndex Index of the song inside the Now Playing List.
     */
    public void setCurrentSongPosition(int songIndex) {
        if (songIndex < 0 || songIndex >= list.size())
            this.currentSongPosition = 0;
        else
            currentSongPosition = songIndex;
        OtherPreference.setCurrentSongIndex(currentSongPosition);
    }

    public List<Music> getList() {
        return list;
    }

    /**
     * Sets the "Now Playing List"
     *
     * @param musicList Songs list that will play from now on.
     *
     * @note Make sure to call {@link #playMusic()} after this.
     */
    public void setList(List<Music> musicList) {
        list = musicList;
    }
}
