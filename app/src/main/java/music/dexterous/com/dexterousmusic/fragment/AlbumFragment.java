package music.dexterous.com.dexterousmusic.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.AlbumSongsAdapter;
import music.dexterous.com.dexterousmusic.customeviews.FontTextView;
import music.dexterous.com.dexterousmusic.models.AlbumModel;
import music.dexterous.com.dexterousmusic.musicutils.PlayCurrentSong;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlbumFragment extends BaseFragment {

    public static final String TAG = AlbumFragment.class.getName();
    public static final String EXTRA_ALBUM = "EXTRA_ALBUM";

    ImageView album_fragment_album_art;
    RecyclerView album_fragment_recycler_view;
    FontTextView total_songs;

    ImageLoader mImageLoader;
    AlbumModel albumModel;

    AlbumSongsAdapter albumSongsAdapter;

    public static AlbumFragment newInstance(AlbumModel albumModel) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle info = new Bundle();
        info.putParcelable(EXTRA_ALBUM, albumModel);
        fragment.setArguments(info);
        return fragment;
    }


    public AlbumFragment() {
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
        total_songs = (FontTextView) view.findViewById(R.id.total_songs);

        mImageLoader = new ImageLoader(getActivity());

        Bundle args = getArguments();

        albumModel = args != null ? (albumModel = (AlbumModel) args
                .getParcelable(EXTRA_ALBUM)) : null;
        if (albumModel == null) {
            return;
        }

        String albumArtPath = albumModel.getAlbumArtPath();
        Bitmap bitmap = null;

        if (!TextUtils.isEmpty(albumArtPath)) {
            bitmap = BitmapFactory.decodeFile(albumArtPath);
        }

        if (bitmap != null)
            mImageLoader.loadImage(getActivity(), bitmap, album_fragment_album_art);
        else
            mImageLoader.loadImage(getActivity(), R.drawable.bg_1, album_fragment_album_art);


        total_songs.setText("" + albumModel.getMusicArrayList().size());

        album_fragment_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        album_fragment_recycler_view.setAdapter(albumSongsAdapter = new AlbumSongsAdapter(albumModel.getMusicArrayList()));

        albumSongsAdapter.setOnItemClickListener((view1, position) -> {
            if (albumModel == null && albumModel.getMusicArrayList() != null) {
                PlayCurrentSong.playCurrentSong(getActivity().getApplicationContext(), albumModel.getMusicArrayList(), position);
            }
        });

    }

}
