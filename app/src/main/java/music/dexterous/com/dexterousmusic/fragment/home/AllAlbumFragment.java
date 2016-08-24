package music.dexterous.com.dexterousmusic.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.AllAlbumsAdapter;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.fragment.AlbumFragment;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.models.AlbumModel;

/**
 * Created by Kartik on 8/9/2016.
 */

public class AllAlbumFragment extends BaseFragment {

    List<AlbumModel> albumModels;

    RecyclerView mRecyclerView;
    AllAlbumsAdapter allAlbumsAdapter;

    public static AllAlbumFragment newInstance() {
        AllAlbumFragment fragment = new AllAlbumFragment();
        Bundle info = new Bundle();
        fragment.setArguments(info);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_album_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseData();
        initialiseUI(view);
    }

    protected void initialiseData() {

        //All songs List
        List<Music> allSongsList = DataManager.getInstance(getActivity()).getAllMusic();
        List<AlbumModel> albums = DataManager.getInstance(getActivity()).getAlbums();

        albumModels = AlbumModel.getModel(allSongsList, albums);
    }

    protected void initialiseUI(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(allAlbumsAdapter = new AllAlbumsAdapter(albumModels, getActivity()));

        allAlbumsAdapter.setOnItemClickListener((view1, position) -> {
            AlbumModel albumModel = albumModels.get(position);
            AlbumFragment albumFragmentFragment = AlbumFragment.newInstance(albumModel);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootHomeContainerUpper, albumFragmentFragment, AlbumFragment.TAG)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();

        });
    }

}
