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
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.viewpager.MusicViewPageAdapter;
import music.dexterous.com.dexterousmusic.animations.transformation.ABaseTransformer;
import music.dexterous.com.dexterousmusic.animations.transformation.DepthPageTransformer;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.fragment.SettingFragment;
import music.dexterous.com.dexterousmusic.fragment.home.listener.OnHomeViewPagerChangeListener;
import music.dexterous.com.dexterousmusic.musicutils.ShuffleAllSongs;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;

/**
 * Created by Kartik on 8/9/2016.
 */

public class HomeFragment extends BaseFragment {

    /**
     * tag for fragment transactions
     */
    public static final String FRAGMENT_TAG = HomeFragment.class.getSimpleName();
    private static final int NUM_PAGES_CACHED = 3;
    private static final String INTITIAL_PAGE = "initial_page";
    /**
     * transformer for the viewpager
     */
    private final ABaseTransformer PAGE_TRANSFORMER = new DepthPageTransformer();

    FragmentManager mFragmentManager;
    SettingFragment mSettingFragment;

    ViewPager mHomeViewPager;
    TabLayout mHomeTabHeader;
    ImageView setting_icon;

    MusicViewPageAdapter mMusicViewPageAdapter;

    FloatingActionButton shuffelAllSongs;
    /**
     * Flag to determine if on resume has been called from oncreate or not
     */
    private boolean mIsComingFromOnCreate = false;
    /**
     * initial page index which you want to show -1 is index for bottom fragment or SlidingUp
     * pannel
     */
    private int initialPage;


    public static HomeFragment newInstance(int initialPage) {
        HomeFragment fragment = new HomeFragment();
        Bundle info = new Bundle();
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

        Bundle bundle = getArguments();
        initialPage = bundle.getInt(INTITIAL_PAGE, 0);

        TaskExecutor.getInstance().executeTask(() -> {
            loadDeferredUI();
        });
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

        setting_icon = (ImageView) view.findViewById(R.id.setting_icon);
        shuffelAllSongs = (FloatingActionButton) view.findViewById(R.id.shuffel_all_songs_fab);

        mFragmentManager = getActivity().getSupportFragmentManager();
        mMusicViewPageAdapter = new MusicViewPageAdapter(getChildFragmentManager());


        mHomeViewPager.setAdapter(mMusicViewPageAdapter);
        mHomeViewPager.setOffscreenPageLimit(NUM_PAGES_CACHED);
        mHomeViewPager.setPageTransformer(true, PAGE_TRANSFORMER);
        mHomeViewPager.setCurrentItem(initialPage);
        mHomeViewPager.addOnPageChangeListener(new OnHomeViewPagerChangeListener(shuffelAllSongs));
        mHomeTabHeader.setupWithViewPager(mHomeViewPager);


        shuffelAllSongs.setOnClickListener(view1 -> {
            ShuffleAllSongs.shuffleAllSongs(getActivity(), DataManager.getInstance(getActivity()).getAllMusic());
        });

        setting_icon.setOnClickListener(v -> {
            toggleSettingFragemnt();
        });
    }


    /**
     * Initialize variables when settings is shown
     */
    private void loadDeferredUI() {
        mSettingFragment = SettingFragment.newInstance();
    }

    private void toggleSettingFragemnt() {
        SettingFragment settingsFragment = (SettingFragment) mFragmentManager.findFragmentByTag(SettingFragment.FRAGMENT_TAG);
        if (settingsFragment == null) {
            openSettingsPopup();
        } else
            closeSettings();
    }


    private void openSettingsPopup() {
        mFragmentManager.beginTransaction()
//                .setCustomAnimations(R.anim.animate_top_to_bottom, R.anim.animate__bottom_to_top,
//                        R.anim.animate_top_to_bottom, R.anim.animate__bottom_to_top)
                .replace(R.id.rootHomeContainer, mSettingFragment, SettingFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void closeSettings() {
        mFragmentManager.popBackStack();
    }
}

