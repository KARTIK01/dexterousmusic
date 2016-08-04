package music.dexterous.com.dexterousmusic.service;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.service.musiccontrol.AbstractMusicControlService;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnCompletionListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnErrorListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnPreparedListener;

/**
 * This is very import class and it needs to take care with proper guidance
 * <p>
 * Service that makes the music play
 */

public class DexterousPlayMusicService extends AbstractMusicControlService {

    public static final String PLAY_MUSIC = "play_music";
    public static final String PAUSE_MUSIC = "pause_music";
    public static final String NEXT_MUSIC = "next_music";
    public static final String PREVIOUS_MUSIC = "previous_music";
    public static final String INITIALIZE = "initialize";


    public DexterousPlayMusicService() {
        super("DexterousPlayMusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            switch (intent.getAction()) {
                case INITIALIZE:
                    audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    initMusicPlayer();
                    break;
                case PLAY_MUSIC:
                    playMusic();
                    break;
                case PAUSE_MUSIC:
                    pauseMusic();
                    break;
                case NEXT_MUSIC:
                    playNextMusic();
                    break;
                case PREVIOUS_MUSIC:
                    playPreviousMusic();
                    break;
            }
        }
    }

    /**
     * Index of the current song we're playing on the `songs` list.
     */
    public int currentSongPosition;

    /**
     * List of songs we're  currently playing.
     */
    List<Music> mMusicList;

    /**
     * Sets the "Now Playing List"
     *
     * @param musicList Songs list that will play from now on.
     * @note Make sure to call {@link #playMusic()} after this.
     */
    public void setList(ArrayList<Music> musicList) {
        mMusicList = musicList;
    }

    /**
     * Appends a song to the end of the currently playing queue.
     *
     * @param music New song to put at the end.
     */
    public void add(Music music) {
        mMusicList.add(music);
    }


    /**
     * Returns the song on the Now Playing List at `position`.
     */
    public Music getSong(int position) {
        return mMusicList.get(position);
    }

    /**
     * Sets a specific song, already within internal Now Playing List.
     *
     * @param songIndex Index of the song inside the Now Playing List.
     */
    public void setSong(int songIndex) {
        if (songIndex < 0 || songIndex >= mMusicList.size())
            currentSongPosition = 0;
        else
            currentSongPosition = songIndex;
    }


}
