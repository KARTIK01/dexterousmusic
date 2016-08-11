package music.dexterous.com.dexterousmusic.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import music.dexterous.com.dexterousmusic.fragment.AllAlbumFragment;
import music.dexterous.com.dexterousmusic.fragment.AllSongsFragment;
import music.dexterous.com.dexterousmusic.fragment.NowPlayingFragment;

/**
 * Created by Honey on 8/9/2016.
 */

public class MusicViewPageAdapter extends FragmentStatePagerAdapter {

    private static final int NO_OF_PAGES = 4;

    public MusicViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AllSongsFragment.newInstance();
            case 1:
                return NowPlayingFragment.newInstance();
            case 2:
                return AllSongsFragment.newInstance();
            case 3:
                return AllAlbumFragment.newInstance();
        }
        return AllSongsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return NO_OF_PAGES;
    }
}
