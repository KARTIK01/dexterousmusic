package music.dexterous.com.dexterousmusic.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.animations.transformation.ABaseTransformer;
import music.dexterous.com.dexterousmusic.animations.transformation.DepthPageTransformer;
import music.dexterous.com.dexterousmusic.customeviews.slidinguppannel.BottomPanelSlideListener;
import music.dexterous.com.dexterousmusic.customeviews.slidinguppannel.SlidingUpPanelLayout;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;

/**
 * Created by Kartik on 8/9/2016.
 */

public class HomeRootFragment extends BaseFragment {

    /**
     * tag for fragment transactions
     */
    public static final String FRAGMENT_TAG = HomeRootFragment.class.getSimpleName();
    private static final int NUM_PAGES_CACHED = 3;
    private static final String INTITIAL_PAGE = "initial_page";
    /**
     * transformer for the viewpager
     */
    private final ABaseTransformer PAGE_TRANSFORMER = new DepthPageTransformer();
    FragmentManager mFragmentManager;
    SlidingUpPanelLayout     mSlidingUpPanelLayout;
    BottomPanelSlideListener mBottomPanelSlideListener;
    FrameLayout mRootHomeContainerBottom;
    FrameLayout mRootHomeContainerUpper;
    NowPlayingFragment mNowPlayingFragment;
    HomeFragment       homeFragment;
    /**
     * Flag to determine if on resume has been called from oncreate or not
     */
    private boolean mIsComingFromOnCreate = false;
    private              int    initialPage   = 0;


    public static HomeRootFragment newInstance(int initialPage) {
        HomeRootFragment fragment = new HomeRootFragment();
        Bundle           info     = new Bundle();
        info.putInt(INTITIAL_PAGE, initialPage);
        fragment.setArguments(info);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

        }
        mIsComingFromOnCreate = true;

        initialPage = getArguments().getInt(INTITIAL_PAGE, 0);
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
        homeFragment = HomeFragment.newInstance(initialPage);

//        if (true)
//            mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

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
