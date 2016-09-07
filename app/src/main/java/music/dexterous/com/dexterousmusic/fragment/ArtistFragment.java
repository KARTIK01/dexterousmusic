package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.AlbumSongsAdapter;
import music.dexterous.com.dexterousmusic.customeviews.widget.FontTextView;
import music.dexterous.com.dexterousmusic.models.ArtistModel;
import music.dexterous.com.dexterousmusic.musicutils.HumanReadableTime;
import music.dexterous.com.dexterousmusic.musicutils.PlayCurrentSong;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.image.HomeActivtyBgImageHelper;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistFragment extends BaseFragment {

    public static final String TAG         = ArtistFragment.class.getName();
    public static final String EXTRA_ALBUM = "EXTRA_ALBUM";

    ImageView    album_fragment_album_art;
    RecyclerView album_fragment_recycler_view;
    FontTextView total_songs;
    FontTextView total_songs_duration;
    ImageLoader  mImageLoader;
    ArtistModel  artistModel;

    AlbumSongsAdapter artistSongsAdapter;

    public ArtistFragment() {
    }

    public static ArtistFragment newInstance(ArtistModel albumModel) {
        ArtistFragment fragment = new ArtistFragment();
        Bundle         info     = new Bundle();
        info.putParcelable(EXTRA_ALBUM, albumModel);
        fragment.setArguments(info);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImageLoader = new ImageLoader(getActivity());
        setUpComponent(view);
        showData();


        artistSongsAdapter.setOnItemClickListener((view1, position) -> {
            if (artistModel != null && artistModel.getMusicArrayList() != null)
                PlayCurrentSong.playCurrentSong(getActivity().getApplicationContext(), artistModel.getMusicArrayList(), position);
        });
    }


    private void setUpComponent(View view) {
        album_fragment_album_art = (ImageView) view.findViewById(R.id.artist_fragment_album_art);
        album_fragment_recycler_view = (RecyclerView) view.findViewById(R.id.artist_fragment_recycler_view);
        total_songs = (FontTextView) view.findViewById(R.id.artist_fragment_total_songs);
        total_songs_duration = (FontTextView) view.findViewById(R.id.artist_fragment_total_songs_duration);
    }

    private void showData() {
        Bundle args = getArguments();

        artistModel = args != null ? (artistModel = (ArtistModel) args
                .getParcelable(EXTRA_ALBUM)) : null;
        if (artistModel == null) {
            return;
        }

        //TODO get form bundle when it comes
        Observable.fromCallable(() -> HumanReadableTime.getSongsDuration(artistModel.getMusicArrayList()))
                .subscribeOn(Schedulers.from(TaskExecutor.threadPoolExecutor))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(duration -> {
                    total_songs_duration.setText(duration);
                });

        total_songs.setText("" + artistModel.getMusicArrayList().size());

        String albumArtPath = artistModel.getMusicArrayList().get(0).getSONG_ALBUM_ART_PATH();

        HomeActivtyBgImageHelper.setImage(getActivity(), albumArtPath, album_fragment_album_art, false);
        album_fragment_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        album_fragment_recycler_view.setAdapter(artistSongsAdapter = new AlbumSongsAdapter(artistModel.getMusicArrayList()));

    }
}
