package music.dexterous.com.dexterousmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.preference.OtherPreference;

public class SplashActivity extends AppCompatActivity {

    public static final int INTIAL_PAGE_INDEX = 2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFirstTime = OtherPreference.isFirstTime();
        bootstrapStartupTasks();

        if (isFirstTime) {
            //TODO app intro OR scanning music and RumTimePermissions

            OtherPreference.setFirstTime(false);
            Intent mainActivityIntent = MainActivity.getIntent(SplashActivity.this);
            startActivity(mainActivityIntent);

        } else {
            //TODO user Rx java when data of songs and album is ready then open it
            Intent homeActivityIntent = HomeActivity.getIntent(SplashActivity.this, INTIAL_PAGE_INDEX);
            startActivity(homeActivityIntent);
            finish();
        }
    }

    /**
     * Set of tasks to be run on each startup will go here
     */
    private void bootstrapStartupTasks() {
        TaskExecutor.getInstance().executeTask(() -> {

        });
    }
}
