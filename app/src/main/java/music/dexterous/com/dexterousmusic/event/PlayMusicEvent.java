package music.dexterous.com.dexterousmusic.event;

import music.dexterous.com.dexterousmusic.database.Music;

/**
 * Created by Honey on 8/15/2016.
 */
public class PlayMusicEvent {

    public Music music;

    public PlayMusicEvent(Music music) {
        this.music = music;
    }
}
