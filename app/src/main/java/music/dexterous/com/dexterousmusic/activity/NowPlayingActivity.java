package music.dexterous.com.dexterousmusic.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import music.dexterous.com.dexterousmusic.R;

public class NowPlayingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_layout);
    }
}
