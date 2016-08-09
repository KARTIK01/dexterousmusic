package music.dexterous.com.dexterousmusic.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.RecyclerViewAdapter;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.databaseutils.MyMusicLibraryTableDao;
import music.dexterous.com.dexterousmusic.musicutils.ShuffleAllSongs;

public class SongListActivity extends BaseActivity {

    //All songs List
    List<Music> allSongsList;

    //Alphabet list
    private List<AlphabetItem> mAlphabetItems;

    RecyclerView mRecyclerView;
    RecyclerViewFastScroller fastScroller;

    Button shuffle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        initialiseData();
        initialiseUI();
    }

    protected void initialiseData() {
        //All songs
        allSongsList = MyMusicLibraryTableDao.getAllMusic(getApplicationContext());

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

    protected void initialiseUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        fastScroller = (RecyclerViewFastScroller) findViewById(R.id.fast_scroller);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(allSongsList));

        fastScroller.setRecyclerView(mRecyclerView);
        fastScroller.setUpAlphabet(mAlphabetItems);
        shuffle = (Button) findViewById(R.id.shuffle);
        shuffle.setOnClickListener(view -> ShuffleAllSongs.shuffleAllSongs(getApplicationContext(), allSongsList));
    }


}
