package music.dexterous.com.dexterousmusic;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;


import com.cleveroad.audiowidget.AudioWidget;

import org.greenrobot.eventbus.EventBus;

import hugo.weaving.DebugLog;
import music.dexterous.com.dexterousmusic.customeviews.AudioWidgetController;
import music.dexterous.com.dexterousmusic.database.DaoMaster;
import music.dexterous.com.dexterousmusic.database.DaoSession;
import music.dexterous.com.dexterousmusic.database.update.UpgradeDpHelper;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.Constants;
import music.dexterous.com.dexterousmusic.utils.preference.OtherPreference;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;
import music.dexterous.com.dexterousmusic.utils.other.StrictModeUtil;
import music.dexterous.com.dexterousmusic.utils.preference.AppPreference;

/**
 * Created by Honey on 7/13/2016.
 * <p>
 * Add this file in root package folder i.e music.dexterous.com.dexterousmusic
 */
public class GlobalApplication extends Application {


    //TODO remove theses line when share code
    /*******************************************************************************/
    /**
     * Floating widget
     */
    private AudioWidget audioWidget;
    private AudioWidgetController audioWidgetController;
    /*******************************************************************************/

    /**
     * lock object for serialized access of dao session
     */
    private final Object mDaoLock = new Object();

    /**
     * session for mSQLiteDatabase
     */
    private DaoSession daoSession;


    @Override
    @DebugLog
    public void onCreate() {
        super.onCreate();

        /** Initialize the Preference class */
        new AppPreference.Builder().setContext(GlobalApplication.this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        /** Initialize the DataManager class */
        DataManager.getInstance(GlobalApplication.this);

        /** load the ui defaults */
        UiUtils.initialize(GlobalApplication.this);

        if (BuildConfig.DEBUG) {
            StrictModeUtil.enableStrictMode();
        }

        /** initialize the analytics */
        TaskExecutor.getInstance().executeTask(() -> {
            //TODO
//            GoogleAnalyticsHelper.prepareAnalytics(GlobalApplication.this);
        });

        startMusicService();


        //TODO remove theses line when share code
        /************************************/
        setupFloatingAudioWidget();
        /*************************************/
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

    /**
     * It start the music service and initializes all required components
     */
    private void startMusicService() {
        DexterousPlayMusicService.startService(this, DexterousPlayMusicService.INITIALIZE);
    }


    //TODO remove theses line when share code

    /*************************************************************************************************************************************/
    private void setupFloatingAudioWidget() {
        //TODO check preference
        if (audioWidget == null) {
            audioWidget = new AudioWidget.Builder(this).build();
            if (audioWidgetController == null) {
                audioWidget.controller().onControlsClickListener(audioWidgetController = new AudioWidgetController(this));
                audioWidget.controller().onWidgetStateChangedListener(audioWidgetController);
            }
            //TODO show when first song play
            audioWidget.show(100, 100);
        }
    }

    public AudioWidget getAudioWidget() {
        return audioWidget;
    }
    /*********************************************************************************************************/


    /**
     * @return App wide event bus
     */

    public static EventBus getBus() {
        return EventBus.getDefault();
    }
}
