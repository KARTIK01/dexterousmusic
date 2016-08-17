package music.dexterous.com.dexterousmusic.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import music.dexterous.com.dexterousmusic.fragment.home.AllAlbumFragment;
import music.dexterous.com.dexterousmusic.fragment.home.AllArtistFragment;
import music.dexterous.com.dexterousmusic.fragment.home.AllSongsFragment;
import music.dexterous.com.dexterousmusic.fragment.home.RecentPlayedFragment;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

/**
 * Created by Kartik on 8/9/2016.
 */

public class MusicViewPageAdapter extends FragmentStatePagerAdapter {

    protected static final String[] titles = UiUtils.mHomeTabHeaderTittle;

    public MusicViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RecentPlayedFragment.newInstance();
            case 1:
                return AllSongsFragment.newInstance();
            case 2:
                return AllArtistFragment.newInstance();
            case 3:
                return AllAlbumFragment.newInstance();
        }
        return AllSongsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
