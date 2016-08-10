package music.dexterous.com.dexterousmusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import music.dexterous.com.dexterousmusic.service.ScanMusicService;
import music.dexterous.com.dexterousmusic.utils.preference.AppPreference;

//TODO check this is not working
public class MediaScannerCompletedReceiver extends BroadcastReceiver {

    public MediaScannerCompletedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            switch (intent.getAction()) {
                case Intent.ACTION_MEDIA_SCANNER_FINISHED:
                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(new long[]{0, 2000, 1000}, 0);
                    AppPreference.putString("ACTION_MEDIA_SCANNER_FINISHED", intent.getData().toString());

                    //TODO check if music is adedd in app then show notificaton for more songs added in app


                    //TODO check if we has permission for access
                    Intent scanIntent = new Intent(context, ScanMusicService.class);
                    context.startService(scanIntent);
                    break;

            }
        }
    }
}
