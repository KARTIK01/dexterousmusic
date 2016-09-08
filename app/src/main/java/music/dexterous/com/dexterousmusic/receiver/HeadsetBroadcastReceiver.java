package music.dexterous.com.dexterousmusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Toast;

import music.dexterous.com.dexterousmusic.customeviews.widget.ShortToast;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;

//TODO register it with service it cannot br registed with manifest

/**
 * refer https://developer.android.com/reference/android/media/AudioManager.html#ACTION_HEADSET_PLUG
 */

/**
 * Receives external Broadcasts and gives our MusicService orders based on them.
 * <p>
 * It is the bridge between our application and the external world. It receives Broadcasts and
 * launches Internal Broadcasts.
 * <p>
 * It acts on music events (such as disconnecting headphone) and music controls (the lockscreen
 * widget).
 *
 * @note This class works because we are declaring it in a `receiver` tag in `AndroidManifest.xml`.
 */
public class HeadsetBroadcastReceiver extends BroadcastReceiver {
    String TAG = "Receiver";


    static public BroadcastReceiver gerReciver(Context context) {
        return new HeadsetBroadcastReceiver();
    }

    public HeadsetBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null)
            return;

        String action = intent.getAction();

        switch (action) {
            case Intent.ACTION_HEADSET_PLUG:
                handleHeadSet(intent, context);
                break;
            case Intent.ACTION_MEDIA_BUTTON:
                // Which media key was pressed
                KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
                handleKeyPressed(keyEvent);
                break;
            case android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                break;
        }
    }

    private void handleHeadSet(Intent intent, Context context) {
        int state      = intent.getIntExtra("state", -1);
        int microPhone = intent.getIntExtra("microphone", -1); // 1 when has microphone else 0
        switch (state) {
            case 0:
                PrettyLogger.d("Headset is unplugged");
                DexterousPlayMusicService.startService(context, DexterousPlayMusicService.PAUSE_MUSIC);
                break;
            case 1:
                PrettyLogger.d("Headset is plugged");
                if (UsersAppPreference.getMusicPlayONInsetHeadSet()) {
                    DexterousPlayMusicService.startService(context, DexterousPlayMusicService.PLAY_MUSIC);
                }
                break;
            default:
                ShortToast.displayToast(context, "Received", Toast.LENGTH_SHORT);
                PrettyLogger.d("I have no idea what the headset state is");
        }
    }

    /**
     * TODO Its not working, check why
     *
     * @param keyEvent
     */
    private void handleKeyPressed(KeyEvent keyEvent) {

        // Not interested on anything other than pressed keys.
        if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
            return;

        switch (keyEvent.getKeyCode()) {

            case KeyEvent.KEYCODE_HEADSETHOOK:
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
//                intentValue = ServicePlayMusic.BROADCAST_ORDER_TOGGLE_PLAYBACK;
                break;

            case KeyEvent.KEYCODE_MEDIA_PLAY:
//                intentValue = ServicePlayMusic.BROADCAST_ORDER_PLAY;
                break;

            case KeyEvent.KEYCODE_MEDIA_PAUSE:
//                intentValue = ServicePlayMusic.BROADCAST_ORDER_PAUSE;
                break;

            case KeyEvent.KEYCODE_MEDIA_NEXT:
//                intentValue = ServicePlayMusic.BROADCAST_ORDER_SKIP;
                break;

            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                // TODO: ensure that doing this in rapid succession actually plays the
                // previous song
//                intentValue = ServicePlayMusic.BROADCAST_ORDER_REWIND;
                break;
        }
    }
}


