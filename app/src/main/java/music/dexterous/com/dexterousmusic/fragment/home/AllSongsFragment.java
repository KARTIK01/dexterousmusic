package music.dexterous.com.dexterousmusic.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.AllSongsAdapter;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.musicutils.PlayCurrentSong;
import music.dexterous.com.dexterousmusic.musicutils.ShuffleAllSongs;

/**
 * Created by Kartik on 8/9/2016.
 */

public class AllSongsFragment extends BaseFragment {

    //All songs List
    List<Music> allSongsList;

    //Alphabet list
    private List<AlphabetItem> mAlphabetItems;

    RecyclerView mRecyclerView;
    RecyclerViewFastScroller fastScroller;
    AllSongsAdapter recyclerViewAdapterAllSongs;

    Button shuffle;

    public static AllSongsFragment newInstance() {
        AllSongsFragment fragment = new AllSongsFragment();
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

        //Alphabet fast scroller data
        mAlphabetItems = new ArrayList<>();

        List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < allSongsList.size(); i++) {
            String tittle = allSongsList.get(i).getSONG_TITLE();
            if (tittle == null || tittle.trim().isEmpty())
                continue;
            String word = tittle.substring(0, 1);
            if (!strAlphabets.contains(word)) {
                strAlphabets.add(word);
                mAlphabetItems.add(new AlphabetItem(i, word, false));
            }
        }
    }

    protected void initialiseUI(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        fastScroller = (RecyclerViewFastScroller) view.findViewById(R.id.fast_scroller);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(recyclerViewAdapterAllSongs = new AllSongsAdapter(allSongsList));

        fastScroller.setRecyclerView(mRecyclerView);
        fastScroller.setUpAlphabet(mAlphabetItems);
        shuffle = (Button) view.findViewById(R.id.shuffle);
        shuffle.setOnClickListener(view2 -> ShuffleAllSongs.shuffleAllSongs(getActivity(), allSongsList));

        recyclerViewAdapterAllSongs.setOnItemClickListener((view1, position) -> {
            PlayCurrentSong.playCurrentSong(getActivity().getApplicationContext(), allSongsList, position);
        });
    }

}
