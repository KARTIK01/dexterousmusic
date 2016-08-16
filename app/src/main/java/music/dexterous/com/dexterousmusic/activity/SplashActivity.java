package music.dexterous.com.dexterousmusic.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.ShortToast;
import music.dexterous.com.dexterousmusic.service.ScanMusicService;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.preference.OtherPreference;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFirstTime = OtherPreference.isFirstTime();
        bootstrapStartupTasks();

        if (isFirstTime) {
            //TODO app intro OR scanning music and RumTimePermissions
//            setContentView(R.layout.layout_intro);
            ScanMusicService.startService(getApplicationContext());
            OtherPreference.setFirstTime(false);
            showStoragePermission();
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

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showStoragePermission() {
        ScanMusicService.startService(getApplicationContext());
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForStorage(PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_storage_rationale)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.button_deny, (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showDeniedForStorage() {
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showNeverAskForStorage() {
        ShortToast.displayToast(this, getResources().getString(R.string.permission_storage_never_ask), Toast.LENGTH_SHORT);
    }

}
