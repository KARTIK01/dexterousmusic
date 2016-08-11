package music.dexterous.com.dexterousmusic.service;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.music.ScanningMusic;

public class ScanMusicService extends IntentService {

    private String mMediaStoreSelection = null;

    public ScanMusicService() {
        super("ScanMusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            getSongsFromMediaStore();
        }
    }

    private void getSongsFromMediaStore() {
        List<Music> musicList = new ScanningMusic().getAllMusicEntities(this);
        PrettyLogger.d("List size is :" + musicList.size());
        DataManager.getInstance(this).saveAllMusic(musicList);
    }


}
