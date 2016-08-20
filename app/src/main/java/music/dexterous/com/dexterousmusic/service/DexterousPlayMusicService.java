package music.dexterous.com.dexterousmusic.service;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import music.dexterous.com.dexterousmusic.service.musiccontrol.AbstractMusicControlService;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;

/**
 * This is very import class and it needs to take care with proper guidance
 * <p>
 * Service that makes the music play
 */

public class DexterousPlayMusicService extends AbstractMusicControlService {

    public static final String PLAY_MUSIC = "play_music";
    public static final String PAUSE_MUSIC = "pause_music";
    public static final String UNPAUSE_MUSIC = "unpause_music";
    public static final String TOGGLE_MUSIC = "toggle_music";
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
                        playMusic();
                        break;
                    case PREVIOUS_MUSIC:
                        playPreviousMusic(true);
                        playMusic();
                        break;
                    case TOGGLE_MUSIC:
                        if (isPlaying())
                            pauseMusic();
                        else
                            unPauseMusic();
                        break;
                    case UNPAUSE_MUSIC:
                        unPauseMusic();
                }
            }
        });
        return START_STICKY;
    }

}
