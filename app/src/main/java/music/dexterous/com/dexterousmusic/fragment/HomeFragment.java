package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.viewpager.MusicViewPageAdapter;
import music.dexterous.com.dexterousmusic.animations.transformation.ABaseTransformer;
import music.dexterous.com.dexterousmusic.animations.transformation.DepthPageTransformer;

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

    ViewPager mMusicViewPage;
    MusicViewPageAdapter mMusicViewPageAdapter;

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
        mMusicViewPage = (ViewPager) view.findViewById(R.id.musicViewPage);

        mMusicViewPageAdapter = new MusicViewPageAdapter(getChildFragmentManager());
        mMusicViewPage.setAdapter(mMusicViewPageAdapter);
        mMusicViewPage.setOffscreenPageLimit(NUM_PAGES_CACHED);
        mMusicViewPage.setPageTransformer(true, PAGE_TRANSFORMER);
    }

}
