package music.dexterous.com.dexterousmusic;

import android.app.Application;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;

import music.dexterous.com.dexterousmusic.database.DaoMaster;
import music.dexterous.com.dexterousmusic.database.DaoSession;
import music.dexterous.com.dexterousmusic.database.update.UpgradeDpHelper;
import music.dexterous.com.dexterousmusic.utils.preference.AppPreference;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.Constants;
import music.dexterous.com.dexterousmusic.utils.UiUtils;
import music.dexterous.com.dexterousmusic.utils.Utils;

/**
 * Created by Honey on 7/13/2016.
 */
public class GlobalApplication extends Application {


    /**
     * lock object for serialized access of dao session
     */
    private final Object mDaoLock = new Object();

    /**
     * session for mSQLiteDatabase
     */
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();

        /** Initialize the Preference class */
        new AppPreference.Builder().setContext(GlobalApplication.this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        /** load the ui defaults */
        UiUtils.initialize(GlobalApplication.this);


        if (BuildConfig.DEBUG) {
            Utils.enableStrictMode();
        }

        /** initialize the analytics */
        TaskExecutor.getInstance().executeTask(() -> {
            //TODO
//            GoogleAnalyticsHelper.prepareAnalytics(GlobalApplication.this);
        });
    }


//    public static CurrentState getCurrentState(Context context) {
//        GlobalApplication application = (GlobalApplication) context.getApplicationContext();
//        return application.mCurrentState;
//    }


    /**
     * Get session object for making mSQLiteDatabase queries
     */
    public DaoSession getSession() {
        synchronized (mDaoLock) {
            if (daoSession == null) {
                UpgradeDpHelper helper = new UpgradeDpHelper(GlobalApplication.this,
                        Constants.DB_NAME, null);
                SQLiteDatabase mSQLiteDatabase = helper.getWritableDatabase();
                DaoMaster daoMaster = new DaoMaster(mSQLiteDatabase);
                daoSession = daoMaster.newSession();
            }
            return daoSession;
        }
    }

}
