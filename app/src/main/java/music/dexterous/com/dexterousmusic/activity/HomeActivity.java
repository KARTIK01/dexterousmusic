package music.dexterous.com.dexterousmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.event.UpDateHomeActivityEvent;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.fragment.home.HomeFragment;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;
import music.dexterous.com.dexterousmusic.utils.image.transformation.BlurTransformation;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

/**
 * Created by Kartik on 8/9/2016.
 */
public class HomeActivity extends BaseActivity {

    HomeFragment mHomeFragment;
    FragmentManager mFragmentManager;
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
        imageView = (ImageView) findViewById(R.id.imageView);
        mFragmentManager = getSupportFragmentManager();
        mHomeFragment = HomeFragment.newInstance();

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
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshViewsOnSongChange(UpDateHomeActivityEvent upDateHomeActivityEvent) {
        //TODO album art
//        String songAlbum = upDateHomeActivityEvent.music.getSONG_ALBUM();
//        String albumArt = songAlbum;
//
//        final public static Uri sArtworkUri = Uri
//                .parse("content://media/external/audio/albumart");
//
//        Uri uri = ContentUris.withAppendedId(PlayerConstants.sArtworkUri,
//                listOfAlbums.get(position).getAlbumID());
//
//        Bitmap songCoverImage = BitmapFactory.decodeFile(albumArt);
//        ImageLoader.loadBlurImage(HomeActivity.this, songCoverImage, imageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister();
    }
}
