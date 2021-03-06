package music.dexterous.com.dexterousmusic.adapters.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viethoa.RecyclerViewFastScroller;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.viewholder.AlbumSongsViewHolder;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.fragment.AlbumFragment;
import music.dexterous.com.dexterousmusic.musicutils.HumanReadableTime;

/**
 * This is adapter for list of songs of {@link AlbumFragment}
 */
public class AlbumSongsAdapter extends RecyclerView.Adapter<AlbumSongsViewHolder>
        implements RecyclerViewFastScroller.BubbleTextGetter {

    private List<Music> mDataArray;
    private OnAlbumSongsItemClickListener mOnClickListener;

    public AlbumSongsAdapter(List<Music> dataset) {
        mDataArray = dataset;
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public AlbumSongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_songs_list_item, parent, false);
        return new AlbumSongsViewHolder(v, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(AlbumSongsViewHolder holder, int position) {
        holder.mSongName.setText(mDataArray.get(position).getSONG_TITLE());
        holder.mSongAlbum.setText(mDataArray.get(position).getSONG_ALBUM());
        holder.mSongDuration.setText(HumanReadableTime.getSongsDuration(mDataArray.get(position)));
        holder.mSongArtist.setText(mDataArray.get(position).getSONG_ARTIST());

    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= mDataArray.size())
            return null;

        String name = mDataArray.get(pos).getSONG_TITLE();
        if (name == null || name.length() < 1)
            return null;

        return mDataArray.get(pos).getSONG_TITLE().substring(0, 1);
    }

    public void setOnItemClickListener(OnAlbumSongsItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnAlbumSongsItemClickListener {
        void onItemClick(View view, int position);
    }
}
