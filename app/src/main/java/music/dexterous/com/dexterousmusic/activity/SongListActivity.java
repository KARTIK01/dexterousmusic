package music.dexterous.com.dexterousmusic.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.RecyclerViewAdapter;
import music.dexterous.com.dexterousmusic.databaseutils.MyMusicLibraryTableDao;

public class SongListActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    RecyclerViewFastScroller fastScroller;
    ArrayList<String> arrayList = new ArrayList<>();
    private List<AlphabetItem> mAlphabetItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        initialiseData();
        initialiseUI();
    }

    protected void initialiseData() {
        //Recycler view data
        arrayList = MyMusicLibraryTableDao.getAllSongNames(getApplicationContext());

        //Alphabet fast scroller data
        mAlphabetItems = new ArrayList<>();
        List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            String name = arrayList.get(i);
            if (name == null || name.trim().isEmpty())
                continue;

            String word = name.substring(0, 1);
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
        mRecyclerView.setAdapter(new RecyclerViewAdapter(arrayList));

        fastScroller.setRecyclerView(mRecyclerView);
        fastScroller.setUpAlphabet(mAlphabetItems);
    }


}
