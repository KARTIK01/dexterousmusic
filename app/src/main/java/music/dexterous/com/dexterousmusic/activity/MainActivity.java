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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.ShortToast;
import music.dexterous.com.dexterousmusic.misc.PrettyLogger;
import music.dexterous.com.dexterousmusic.services.ScanMusicService;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    protected Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scan = (Button) findViewById(R.id.scan_music);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStoragePermission();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        PrettyLogger.d("hello this is log");
    }


    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showStoragePermission() {
        Intent intent = new Intent(getApplicationContext(), ScanMusicService.class);
        startService(intent);

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
