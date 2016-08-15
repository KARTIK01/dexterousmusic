package music.dexterous.com.dexterousmusic.adapters.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viethoa.RecyclerViewFastScroller;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.viewholder.AllSongsViewHolder;
import music.dexterous.com.dexterousmusic.database.Music;

/**
 * Created by Dubey's on 06-08-2016.
 */
public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsViewHolder>
        implements RecyclerViewFastScroller.BubbleTextGetter {

    private List<Music> mDataArray;

    public AllSongsAdapter(List<Music> dataset) {
        mDataArray = dataset;
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public AllSongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_songs_list_item, parent, false);
        return new AllSongsViewHolder(v, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(AllSongsViewHolder holder, int position) {
        holder.mSongAlbum.setText(mDataArray.get(position).getSONG_TITLE());
        holder.mSongAlbum.setText(mDataArray.get(position).getSONG_ALBUM());
        holder.mSongDuration.setText(mDataArray.get(position).getSONG_DURATION());
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


    private OnAllSongsItemClickListener mOnClickListener;

    public void setOnItemClickListener(OnAllSongsItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnAllSongsItemClickListener {
        void onClick(View view, int position);
    }
}
