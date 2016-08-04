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
import music.dexterous.com.dexterousmusic.service.musiccontrol.MusicList;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnCompletionListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnErrorListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnPreparedListener;

/**
 * This is very import class and it needs to take care with proper guidance
 * <p>
 * Service that makes the music play
 */

public class DexterousPlayMusicService extends AbstractMusicControlService {


    MusicList musicList;


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

}
