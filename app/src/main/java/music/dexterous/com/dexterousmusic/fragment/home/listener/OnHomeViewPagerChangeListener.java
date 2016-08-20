package music.dexterous.com.dexterousmusic.fragment.home.listener;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;

import music.dexterous.com.dexterousmusic.adapters.viewpager.MusicViewPageAdapter;

/**
 * Created by Honey on 8/20/2016.
 */
public class OnHomeViewPagerChangeListener implements ViewPager.OnPageChangeListener {

    private FloatingActionButton mFloatingActionButton;

    public OnHomeViewPagerChangeListener(FloatingActionButton floatingActionButton) {
        super();
        mFloatingActionButton = floatingActionButton;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case MusicViewPageAdapter.RECENT_PLAYED:
                mFloatingActionButton.show();
                break;
            case MusicViewPageAdapter.ALL_SONGS:
                mFloatingActionButton.show();
                break;
            case MusicViewPageAdapter.ALL_ARTIST:
                mFloatingActionButton.show();
                break;
            case MusicViewPageAdapter.ALL_ALBUM:
                mFloatingActionButton.hide();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
