package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.ShortToast;
import music.dexterous.com.dexterousmusic.models.AlbumModel;
import music.dexterous.com.dexterousmusic.utils.image.HomeActivtyBgImageHelper;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlbumFragmentFragment extends BaseFragment {

    public static final String TAG = AlbumFragmentFragment.class.getName();
    public static final String EXTRA_ALBUM = "EXTRA_ALBUM";

    ImageView album_fragment_album_art;
    RecyclerView album_fragment_recycler_view;

    AlbumModel albumModel;

    public static AlbumFragmentFragment newInstance(AlbumModel albumModel) {
        AlbumFragmentFragment fragment = new AlbumFragmentFragment();
        Bundle info = new Bundle();
        info.putParcelable(EXTRA_ALBUM, albumModel);
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

        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        albumModel = (AlbumModel) args
                .getParcelable(EXTRA_ALBUM);


    }
}
