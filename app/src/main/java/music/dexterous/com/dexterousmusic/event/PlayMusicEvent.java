package music.dexterous.com.dexterousmusic.event;

import music.dexterous.com.dexterousmusic.database.Music;

/**
 * This event is responsible for all UI chagnes
 * Fire this when app opens and when music changes
 */
public class PlayMusicEvent {

    public Music music;

    public PlayMusicEvent(Music music) {
        this.music = music;
    }

    @Override
    public String toString() {
        return "PlayMusicEvent{" +
                "music=" + music +
                '}';
    }
}
