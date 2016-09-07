package music.dexterous.com.dexterousmusic.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.AllSongsAdapter;
import music.dexterous.com.dexterousmusic.customeviews.bounce.BounceBackRecyclerView;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.musicutils.PlayCurrentSong;

/**
 * Created by Kartik on 8/9/2016.
 */

public class AllSongsFragment extends BaseFragment {

    //All songs List
    List<Music> allSongsList;

    BounceBackRecyclerView mRecyclerView;
    DragScrollBar          dragScrollBar;
    AllSongsAdapter        recyclerViewAdapterAllSongs;

    public static AllSongsFragment newInstance() {
        AllSongsFragment fragment = new AllSongsFragment();
        Bundle           info     = new Bundle();
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
        return inflater.inflate(R.layout.all_song_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseData();
        initialiseUI(view);
    }

    protected void initialiseData() {
        //All songs
        allSongsList = DataManager.getInstance(getActivity()).getAllMusic();
    }

    protected void initialiseUI(View view) {
        mRecyclerView = (BounceBackRecyclerView) view.findViewById(R.id.my_recycler_view);
        dragScrollBar = (DragScrollBar) view.findViewById(R.id.dragScrollBar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(recyclerViewAdapterAllSongs = new AllSongsAdapter(allSongsList));

        dragScrollBar.setRecyclerView(mRecyclerView);
        dragScrollBar.addIndicator(new AlphabetIndicator(getActivity()), true);

        recyclerViewAdapterAllSongs.setOnItemClickListener((view1, position) -> {
            PlayCurrentSong.playCurrentSong(getActivity().getApplicationContext(), allSongsList, position);
        });
    }
}
