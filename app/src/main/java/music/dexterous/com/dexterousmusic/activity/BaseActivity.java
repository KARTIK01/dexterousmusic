package music.dexterous.com.dexterousmusic.activity;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import music.dexterous.com.dexterousmusic.GlobalApplication;

/**
 * Created by Honey on 8/7/2016.
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * @return Event bus for listening to events
     */
    public EventBus getBus() {
        return GlobalApplication.getBus();
    }

    /**
     * safely registers the activity
     */
    public synchronized void safeRegister() {
        if (!getBus().isRegistered(this)) getBus().register(this);
    }

    /**
     * safely registers the activity
     */
    public synchronized void unregister() {
        if (getBus().isRegistered(this)) getBus().unregister(this);
    }
}
