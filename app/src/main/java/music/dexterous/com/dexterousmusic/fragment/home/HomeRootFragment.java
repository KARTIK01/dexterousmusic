package music.dexterous.com.dexterousmusic.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.viewpager.MusicViewPageAdapter;
import music.dexterous.com.dexterousmusic.animations.transformation.ABaseTransformer;
import music.dexterous.com.dexterousmusic.animations.transformation.DepthPageTransformer;
import music.dexterous.com.dexterousmusic.customeviews.slidinguppannel.BottomPanelSlideListener;
import music.dexterous.com.dexterousmusic.customeviews.slidinguppannel.SlidingUpPanelLayout;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.fragment.home.listener.OnHomeViewPagerChangeListener;
import music.dexterous.com.dexterousmusic.musicutils.ShuffleAllSongs;

/**
 * Created by Kartik on 8/9/2016.
 */

public class HomeRootFragment extends BaseFragment {

    private static final int NUM_PAGES_CACHED = 3;

    /**
     * tag for fragment transactions
     */
    public static final String FRAGMENT_TAG = HomeRootFragment.class.getSimpleName();

    /**
     * Flag to determine if on resume has been called from oncreate or not
     */
    private boolean mIsComingFromOnCreate = false;

    FragmentManager mFragmentManager;

    SlidingUpPanelLayout mSlidingUpPanelLayout;
    BottomPanelSlideListener mBottomPanelSlideListener;

    FrameLayout mRootHomeContainerBottom;
    FrameLayout mRootHomeContainerUpper;

    NowPlayingFragment mNowPlayingFragment;
    HomeFragment homeFragment;


    /**
     * transformer for the viewpager
     */
    private final ABaseTransformer PAGE_TRANSFORMER = new DepthPageTransformer();


    public static HomeRootFragment newInstance() {
        HomeRootFragment fragment = new HomeRootFragment();
        Bundle info = new Bundle();
        fragment.setArguments(info);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

        }
        mIsComingFromOnCreate = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_root_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSlidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);

        mRootHomeContainerUpper = (FrameLayout) view.findViewById(R.id.rootHomeContainerUpper);
        mRootHomeContainerBottom = (FrameLayout) view.findViewById(R.id.rootHomeContainerBottom);

        mFragmentManager = getActivity().getSupportFragmentManager();

        mNowPlayingFragment = NowPlayingFragment.newInstance();
        homeFragment = HomeFragment.newInstance();


        if (mBottomPanelSlideListener == null)
            mSlidingUpPanelLayout.addPanelSlideListener(mBottomPanelSlideListener = new BottomPanelSlideListener());


        mFragmentManager.beginTransaction()
                .replace(R.id.rootHomeContainerBottom, mNowPlayingFragment, NowPlayingFragment.FRAGMENT_TAG)
                .commitAllowingStateLoss();


        mFragmentManager.beginTransaction()
                .replace(R.id.rootHomeContainerUpper, homeFragment, HomeFragment.FRAGMENT_TAG)
                .commitAllowingStateLoss();
    }

}