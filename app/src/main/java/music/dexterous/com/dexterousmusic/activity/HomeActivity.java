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

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.event.UpDateHomeActivityEvent;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.fragment.NowPlayingFragment;
import music.dexterous.com.dexterousmusic.fragment.home.HomeFragment;
import music.dexterous.com.dexterousmusic.models.AlbumModel;
import music.dexterous.com.dexterousmusic.utils.image.BlurBuilder;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;
import music.dexterous.com.dexterousmusic.utils.image.transformation.BlurTransformation;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

/**
 * Created by Kartik on 8/9/2016.
 */
public class HomeActivity extends BaseActivity {

    FragmentManager mFragmentManager;

    HomeFragment mHomeFragment;
    NowPlayingFragment mNowPlayingFragment;

    FrameLayout mRootHomeContainer;
    FrameLayout mRootHomeContainerBottom;

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
        mRootHomeContainerBottom = (FrameLayout) findViewById(R.id.rootHomeContainerBottom);

        imageView = (ImageView) findViewById(R.id.home_activity_backgroud_image_view);
        mFragmentManager = getSupportFragmentManager();

        mHomeFragment = HomeFragment.newInstance();
        mNowPlayingFragment = NowPlayingFragment.newInstance();

        DataManager.getInstance(this).loadHomeActivitySpecificData();
        UiUtils.loadHomeActivitySpecificData(HomeActivity.this);

        openHomeFragment();

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

            mFragmentManager.beginTransaction()
                    .replace(R.id.rootHomeContainerBottom, mNowPlayingFragment, NowPlayingFragment.FRAGMENT_TAG)
                    .commitAllowingStateLoss();

        }
    }

    /**
     * updates user HomeScreen Background
     * with current playing song album art
     *
     * @param upDateHomeActivityEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshViewsOnSongChange(UpDateHomeActivityEvent upDateHomeActivityEvent) {
        String albumArtPath = "";

        String songAlbum = upDateHomeActivityEvent.music.getSONG_ALBUM();
        AlbumModel albumModel = DataManager.getAlbumsMap().get(songAlbum);
        if (albumModel != null) {
            albumArtPath = albumModel.getAlbumArtPath();
        }

        if (!TextUtils.isEmpty(albumArtPath)) {
            Bitmap songCoverImage = BitmapFactory.decodeFile(albumArtPath);
            //TODO make blur via glide and {@link BlurTransformation}
            new ImageLoader(this, new BlurTransformation(this))
                    .loadImage(this, BlurBuilder.blur(this, songCoverImage), imageView);
        } else {
            //show palceholder
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister();
    }
}
