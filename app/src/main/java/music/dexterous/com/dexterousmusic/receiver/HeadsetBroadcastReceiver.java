package music.dexterous.com.dexterousmusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;

//TODO
public class HeadsetBroadcastReceiver extends BroadcastReceiver {
    public HeadsetBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(Intent.ACTION_HEADSET_PLUG) && UsersAppPreference.getMusicPlayONInsetHeadSet()) {
            PrettyLogger.d("HeadPhone Inserted");
            /**
             * Implement this one day
             *
             */
        }
    }
}
