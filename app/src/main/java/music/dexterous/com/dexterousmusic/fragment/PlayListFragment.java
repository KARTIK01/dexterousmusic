package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.PlayListAdapter;
import music.dexterous.com.dexterousmusic.customeviews.bounce.BounceBackSwipeRecyclerView;
import music.dexterous.com.dexterousmusic.event.PlayMusicEvent;
import music.dexterous.com.dexterousmusic.models.PlaylistModel;

/**
 * Created by Kartik on 8/9/2016.
 */

public class PlayListFragment extends BaseFragment {

    public static final String TAG = AlbumFragment.class.getName();
    public static final String EXTRA_PLAY_LIST = "EXTRA_PLAYLIST";

    BounceBackSwipeRecyclerView mRecyclerView;
    private PlayListAdapter playListAdapter;
    PlaylistModel playlistModel;

    public static PlayListFragment newInstance(PlaylistModel playlistModel) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle info = new Bundle();
        info.putParcelable(EXTRA_PLAY_LIST, playlistModel);
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
        safeRegister();
        initialiseData();
        initialiseUI(view);
    }

    protected void initialiseData() {
        Bundle args = getArguments();

        playlistModel = args != null ? (playlistModel = (PlaylistModel) args
                .getParcelable(EXTRA_PLAY_LIST)) : null;
        if (playlistModel == null) {
            return;
        }
    }

    protected void initialiseUI(View view) {
        mRecyclerView = (BounceBackSwipeRecyclerView) view.findViewById(R.id.play_list_recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(playListAdapter = new PlayListAdapter(playlistModel.getSongs()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshViewsOnSongChange(PlayMusicEvent playMusicEvent) {
//        playListSongList = NowPlayingList.getInstance().getList();
//        playListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        safeRegister();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregister();
    }
}
