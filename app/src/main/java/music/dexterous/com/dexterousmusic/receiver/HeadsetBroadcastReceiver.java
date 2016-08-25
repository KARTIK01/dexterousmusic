package music.dexterous.com.dexterousmusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

//TODO
public class HeadsetBroadcastReceiver extends BroadcastReceiver {
    String TAG = "Receiver";

    public HeadsetBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();

            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Headset is unplugged");
                    break;
                case 1:
                    Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Headset is plugged");
                    break;
                default:
                    Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "I have no idea what the headset state is");
            }
        }
    }


}