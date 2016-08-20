package music.dexterous.com.dexterousmusic.adapters.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turingtechnologies.materialscrollbar.INameableAdapter;
import com.viethoa.RecyclerViewFastScroller;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.viewholder.AllSongsViewHolder;
import music.dexterous.com.dexterousmusic.adapters.list.viewholder.PlayListViewHolder;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.utils.other.TimeConvert;

/**
 * Created by Dubey's on 06-08-2016.
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListViewHolder> {

    private List<Music> playListSongList;

    public PlayListAdapter(List<Music> playListSongList) {
        this.playListSongList = playListSongList;
    }

    @Override
    public int getItemCount() {
        if (playListSongList == null)
            return 0;
        return playListSongList.size();
    }

    @Override
    public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_songs_list_item, parent, false);
        return new PlayListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlayListViewHolder holder, int position) {
        holder.mSongName.setText(playListSongList.get(position).getSONG_TITLE());
        holder.mSongAlbum.setText(playListSongList.get(position).getSONG_ALBUM());
        holder.mSongDuration.setText(
                TimeConvert.songDurationToDisplay(Long.parseLong(playListSongList.get(position).getSONG_DURATION())));
        holder.mSongArtist.setText(playListSongList.get(position).getSONG_ARTIST());

    }
}
