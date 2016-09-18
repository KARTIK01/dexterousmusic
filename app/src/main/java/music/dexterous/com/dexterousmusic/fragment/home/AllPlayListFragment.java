package music.dexterous.com.dexterousmusic.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.AllPlayListAdapter;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.fragment.ArtistFragment;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.fragment.PlayListFragment;
import music.dexterous.com.dexterousmusic.models.PlaylistModel;

/**
 * Created by Kartik on 8/9/2016.
 */

public class AllPlayListFragment extends BaseFragment {

    List<PlaylistModel> playlistModels;

    RecyclerView mRecyclerView;

    DragScrollBar dragScrollBar;
    AllPlayListAdapter allArtistAdapter;

    public static AllPlayListFragment newInstance() {
        AllPlayListFragment fragment = new AllPlayListFragment();
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
        return inflater.inflate(R.layout.all_artist_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseData();
        initialiseUI(view);
    }

    protected void initialiseData() {
        playlistModels = DataManager.getInstance(getContext()).getAllPlayList();
    }

    protected void initialiseUI(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        dragScrollBar = (DragScrollBar) view.findViewById(R.id.dragScrollBar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(allArtistAdapter = new AllPlayListAdapter(playlistModels, getActivity()));
//
        dragScrollBar.setRecyclerView(mRecyclerView);
        dragScrollBar.setIndicator(new AlphabetIndicator(getActivity()), true);

        allArtistAdapter.setOnItemClickListener((view1, position) -> {
            PlaylistModel artistModel = playlistModels.get(position);
            PlayListFragment playListFragment = PlayListFragment.newInstance(artistModel);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootHomeContainerUpper, playListFragment, ArtistFragment.TAG)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();

        });
    }

}
