package music.dexterous.com.dexterousmusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import music.dexterous.com.dexterousmusic.customeviews.ShortToast;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

//TODO register it with service it cannot br registed with manifest

/**
 * refer https://developer.android.com/reference/android/media/AudioManager.html#ACTION_HEADSET_PLUG
 */
public class HeadsetBroadcastReceiver extends BroadcastReceiver {
    String TAG = "Receiver";

    public HeadsetBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            ShortToast.displayToast(context, "Received", Toast.LENGTH_SHORT);

            int state = intent.getIntExtra("state", -1);
            int microPhone = intent.getIntExtra("microphone", -1); // 1 when has microphone else 0
            switch (state) {
                case 0:
                    ShortToast.displayToast(context, "Received", Toast.LENGTH_SHORT);
                    PrettyLogger.d("Headset is unplugged");
                    break;
                case 1:
                    ShortToast.displayToast(context, "Received", Toast.LENGTH_SHORT);
                    PrettyLogger.d("Headset is plugged");
                    break;
                default:
                    ShortToast.displayToast(context, "Received", Toast.LENGTH_SHORT);
                    PrettyLogger.d("I have no idea what the headset state is");
            }
        }
    }


}