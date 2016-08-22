package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.models.AlbumModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlbumFragmentFragment extends BaseFragment {

    ImageView album_fragment_album_art;
    RecyclerView album_fragment_recycler_view;

    public static AlbumFragmentFragment newInstance(AlbumModel albumModel) {
        AlbumFragmentFragment fragment = new AlbumFragmentFragment();
        Bundle info = new Bundle();
        fragment.setArguments(info);
        return fragment;
    }


    public AlbumFragmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        album_fragment_album_art = (ImageView) view.findViewById(R.id.album_fragment_album_art);
        album_fragment_recycler_view = (RecyclerView) view.findViewById(R.id.album_fragment_recycler_view);

    }
}
