package music.dexterous.com.dexterousmusic.fragment;

import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;

import music.dexterous.com.dexterousmusic.GlobalApplication;

/**
 * Created by Kartik on 8/9/2016.
 */

public class BaseFragment extends Fragment {


    // safely registers the fragment
    public synchronized void safeRegister() {
        if (!getBus().isRegistered(this)) getBus().register(this);
    }

    public synchronized void unregister() {
        if (getBus().isRegistered(this)) getBus().unregister(this);
    }

    /**
     * @return Event bus
     */
    public EventBus getBus() {
        return GlobalApplication.getBus();
    }
}
