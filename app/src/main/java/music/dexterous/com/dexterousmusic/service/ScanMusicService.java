package music.dexterous.com.dexterousmusic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import hugo.weaving.DebugLog;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.music.ScanningMusic;

public class ScanMusicService extends IntentService {

    public static void startService(Context context) {
        Intent intent = new Intent(context, ScanMusicService.class);
        context.startService(intent);
    }

    public ScanMusicService() {
        super("ScanMusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            getSongsFromMediaStore();
        }
    }

    @DebugLog
    private void getSongsFromMediaStore() {
        List<Music> musicList = new ScanningMusic().getAllMusicEntities(this);
        PrettyLogger.d("List size is :" + musicList.size());
        DataManager.getInstance(this).saveAllMusic(musicList);
    }
}
