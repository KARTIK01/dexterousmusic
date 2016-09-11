package music.dexterous.com.dexterousmusic.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import music.dexterous.com.dexterousmusic.fragment.home.AllAlbumFragment;
import music.dexterous.com.dexterousmusic.fragment.home.AllArtistFragment;
import music.dexterous.com.dexterousmusic.fragment.home.AllSongsFragment;
import music.dexterous.com.dexterousmusic.fragment.home.SettingFragment;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;
/**
 * Created by Kartik on 8/9/2016.
 */

public class MusicViewPageAdapter extends FragmentStatePagerAdapter {

    //    public static final int RECENT_PLAYED = 0;
    public static final int ALL_SONGS  = 0;
    public static final int ALL_ARTIST = 1;
    public static final int ALL_ALBUM  = 2;
    public static final int SETTINGS = 3;

    protected static final String[] titles = UiUtils.mHomeTabHeaderTittle;

    public MusicViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
//            case RECENT_PLAYED:
//                return RecentPlayedFragment.newInstance();
            case ALL_SONGS:
                return AllSongsFragment.newInstance();
            case ALL_ARTIST:
                return AllArtistFragment.newInstance();
            case ALL_ALBUM:
                return AllAlbumFragment.newInstance();
            case SETTINGS :
                return SettingFragment.newInstance();
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
