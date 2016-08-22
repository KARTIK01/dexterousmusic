package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import music.dexterous.com.dexterousmusic.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlbumFragmentFragment extends BaseFragment {

    public AlbumFragmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album_fragment, container, false);
    }
}
