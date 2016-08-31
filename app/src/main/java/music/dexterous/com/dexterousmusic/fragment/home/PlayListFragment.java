package music.dexterous.com.dexterousmusic.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.PlayListAdapter;
import music.dexterous.com.dexterousmusic.customeviews.bounce.BounceBackSwipeRecyclerView;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.service.musiccontrol.NowPlayingList;

/**
 * Created by Kartik on 8/9/2016.
 */

public class PlayListFragment extends BaseFragment {

    List<Music> playListSongList;
    public static final String FRAGMENT_TAG = PlayListFragment.class.getName();

    BounceBackSwipeRecyclerView mRecyclerView;

    public static PlayListFragment newInstance() {
        PlayListFragment fragment = new PlayListFragment();
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
        return inflater.inflate(R.layout.playlist_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseData();
        initialiseUI(view);
    }

    protected void initialiseData() {
        playListSongList = NowPlayingList.getInstance().getList();
    }

    protected void initialiseUI(View view) {
        mRecyclerView = (BounceBackSwipeRecyclerView) view.findViewById(R.id.play_list_recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new PlayListAdapter(playListSongList));
    }

}
