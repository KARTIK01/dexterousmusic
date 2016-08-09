package music.dexterous.com.dexterousmusic.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import music.dexterous.com.dexterousmusic.fragment.AllSongsFragment;

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
            case 1:
            case 2:
            case 3:
                return AllSongsFragment.newInstance();
        }
        return AllSongsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return NO_OF_PAGES;
    }
}
