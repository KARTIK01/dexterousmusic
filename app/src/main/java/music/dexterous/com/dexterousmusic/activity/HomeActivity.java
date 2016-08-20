package music.dexterous.com.dexterousmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.event.PlayMusicEvent;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.fragment.home.HomeFragment;
import music.dexterous.com.dexterousmusic.models.AlbumModel;
import music.dexterous.com.dexterousmusic.service.musiccontrol.NowPlayingList;
import music.dexterous.com.dexterousmusic.utils.image.BlurBuilder;
import music.dexterous.com.dexterousmusic.utils.image.HomeActivtyBgImageHelper;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;
import music.dexterous.com.dexterousmusic.utils.other.RandomNumberGeneratorForMusic;
import music.dexterous.com.dexterousmusic.utils.preference.OtherPreference;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

/**
 * Created by Kartik on 8/9/2016.
 */
public class HomeActivity extends BaseActivity {

    FragmentManager mFragmentManager;

    HomeFragment mHomeFragment;

    FrameLayout mRootHomeContainer;

    ImageView imageView;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRootHomeContainer = (FrameLayout) findViewById(R.id.rootHomeContainer);

        imageView = (ImageView) findViewById(R.id.home_activity_backgroud_image_view);
        mFragmentManager = getSupportFragmentManager();

        mHomeFragment = HomeFragment.newInstance();


        DataManager.getInstance(this).loadHomeActivitySpecificData();
        UiUtils.loadHomeActivitySpecificData(HomeActivity.this);

        openHomeFragment();

        safeRegister();

        setUpUiForCurrentSong();
    }

    private void setUpUiForCurrentSong() {
        Music musicToPlay;
        List<Music> musicList = DataManager.getInstance(this).getAllMusic();
        if (OtherPreference.getCurrentSongIndex() != -1) {
            musicToPlay = musicList.get(OtherPreference.getCurrentSongIndex());
        } else {
            musicToPlay = musicList.get(RandomNumberGeneratorForMusic.nextInt(-1, DataManager.getInstance(this).getAllMusic().size()));
        }

        GlobalApplication.getBus().postSticky(new PlayMusicEvent(musicToPlay));
    }

    @Override
    protected void onResume() {
        super.onResume();
        safeRegister();
    }


    /**
     * fragment containing the main content for news
     */
    public void openHomeFragment() {
        BaseFragment fragment = (BaseFragment) mFragmentManager.findFragmentByTag(HomeFragment.FRAGMENT_TAG);
        if (fragment == null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.rootHomeContainer, mHomeFragment, HomeFragment.FRAGMENT_TAG)
                    .commitAllowingStateLoss();
        }
    }

    /**
     * updates user HomeScreen Background
     * with current playing song album art
     *
     * @param playMusicEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshViewsOnSongChange(PlayMusicEvent playMusicEvent) {
        HomeActivtyBgImageHelper.setImage(playMusicEvent, getApplicationContext(), imageView, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }
}
