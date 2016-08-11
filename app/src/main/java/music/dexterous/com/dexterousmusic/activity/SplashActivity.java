package music.dexterous.com.dexterousmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.service.ScanMusicService;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.preference.OtherPreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFirstTime = OtherPreference.isFirstTime();
        bootstrapStartupTasks();

        if (isFirstTime) {
            //TODO app intro OR scanning music
//            setContentView(R.layout.layout_intro);
            ScanMusicService.startService(getApplicationContext());
            OtherPreference.setFirstTime(false);
        } else {
            //TODO user Rx java when data of songs and album is ready then open it
            Intent homeActivityIntent = HomeActivity.getIntent(SplashActivity.this);
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
