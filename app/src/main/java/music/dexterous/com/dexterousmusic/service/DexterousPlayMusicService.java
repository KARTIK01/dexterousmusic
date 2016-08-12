package music.dexterous.com.dexterousmusic.service;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.musicutils.DexterousMediaPlayer;
import music.dexterous.com.dexterousmusic.service.musiccontrol.AbstractMusicControlService;
import music.dexterous.com.dexterousmusic.service.musiccontrol.MusicList;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnCompletionListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnErrorListener;
import music.dexterous.com.dexterousmusic.service.playmusiclistener.PlayMusicOnPreparedListener;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;

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

    public static void startService(Context context, String type) {
        Intent nextMusic = new Intent(type);
        nextMusic.setClass(context, DexterousPlayMusicService.class);
        context.startService(nextMusic);
    }

    public DexterousPlayMusicService() {
        super("DexterousPlayMusicService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TaskExecutor.getInstance().executeTask(() -> {
            if (intent != null) {
                if (intent.getExtras() != null) {
                    Bundle intentExtras = intent.getExtras();
                }
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
                        playNextMusic(true);
                        break;
                    case PREVIOUS_MUSIC:
                        playPreviousMusic();
                        break;
                }
            }
        });
        return START_STICKY;
    }

}
