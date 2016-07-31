package music.dexterous.com.dexterousmusic.services;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.database.MusicLibraryTable;
import music.dexterous.com.dexterousmusic.database.music.MyMusicLibraryTableDao;
import music.dexterous.com.dexterousmusic.utils.music.ScanningMusic;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ScanMusicService extends IntentService {
    GlobalApplication mApp;
    private String mMediaStoreSelection = null;

    public ScanMusicService() {
        super("ScanMusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getSongsFromMediaStore();
    }

    private void getSongsFromMediaStore() {
        List<MusicLibraryTable> musicLibraryTables = ScanningMusic.getAllMusicEntities();
        MyMusicLibraryTableDao.saveAll(getApplicationContext(), musicLibraryTables);
    }


}
