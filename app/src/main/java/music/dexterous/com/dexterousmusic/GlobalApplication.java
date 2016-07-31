package music.dexterous.com.dexterousmusic;

import android.app.Application;
import android.content.ContextWrapper;

import music.dexterous.com.dexterousmusic.misc.AppPreference;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.Utils;

/**
 * Created by Honey on 7/13/2016.
 */
public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /** Initialize the Preference class */
        new AppPreference.Builder().setContext(GlobalApplication.this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        if (BuildConfig.DEBUG) {
            Utils.enableStrictMode();
        }

        /** initialize the analytics */
        TaskExecutor.getInstance().executeTask(() -> {
            //TODO
//            GoogleAnalyticsHelper.prepareAnalytics(GlobalApplication.this);
        });
    }

}
