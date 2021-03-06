package music.dexterous.com.dexterousmusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import music.dexterous.com.dexterousmusic.service.ScanMusicService;

public class MediaScannerCompletedReceiver extends BroadcastReceiver {

    public MediaScannerCompletedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            switch (intent.getAction()) {
                case Intent.ACTION_MEDIA_SCANNER_FINISHED:
                    Uri newMediaInsertedUri = intent.getData();

                    //TODO check if music is adedd in app then show notificaton for more songs added in app
                    // it can be done in 2 ways
                    //1 . check uri returned in abpve url
                    //2. check out database state then show notification_big


                    //TODO check if we has permission for access
                    ScanMusicService.startService(context.getApplicationContext());
                    break;

            }
        }
    }
}
