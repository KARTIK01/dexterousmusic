package music.dexterous.com.dexterousmusic.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;

/**
 * Created by Kartik on 8/9/2016.
 */

public class HomeFragment extends BaseFragment {

    private static final int NUM_PAGES_CACHED = 3;

    /**
     * tag for fragment transactions
     */
    public static final String FRAGMENT_TAG = HomeFragment.class.getSimpleName();

    /**
     * Flag to determine if on resume has been called from oncreate or not
     */
    private boolean mIsComingFromOnCreate = false;

    FragmentManager mFragmentManager;

    ViewPager mHomeViewPager;
    TabLayout mHomeTabHeader;

    MusicViewPageAdapter mMusicViewPageAdapter;

    SlidingUpPanelLayout mSlidingUpPanelLayout;
    FrameLayout mRootHomeContainerBottom;

    NowPlayingFragment mNowPlayingFragment;

    BottomPanelSlideListener mBottomPanelSlideListener;

    /**
     * transformer for the viewpager
     */
    private final ABaseTransformer PAGE_TRANSFORMER = new DepthPageTransformer();


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * fragment views
         */
        mHomeViewPager = (ViewPager) view.findViewById(R.id.home_view_pager);
        mHomeTabHeader = (TabLayout) view.findViewById(R.id.home_tab_header);

        mSlidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        mRootHomeContainerBottom = (FrameLayout) view.findViewById(R.id.rootHomeContainerBottom);


        mFragmentManager = getActivity().getSupportFragmentManager();


        mNowPlayingFragment = NowPlayingFragment.newInstance();

        mMusicViewPageAdapter = new MusicViewPageAdapter(getChildFragmentManager());

        mHomeViewPager.setAdapter(mMusicViewPageAdapter);
        mHomeViewPager.setOffscreenPageLimit(NUM_PAGES_CACHED);
        mHomeViewPager.setPageTransformer(true, PAGE_TRANSFORMER);


        mHomeTabHeader.setupWithViewPager(mHomeViewPager);

        if (mBottomPanelSlideListener == null)
            mSlidingUpPanelLayout.addPanelSlideListener(mBottomPanelSlideListener = new BottomPanelSlideListener());


        mFragmentManager.beginTransaction()
                .replace(R.id.rootHomeContainerBottom, mNowPlayingFragment, NowPlayingFragment.FRAGMENT_TAG)
                .commitAllowingStateLoss();
    }

}
