package music.dexterous.com.dexterousmusic.adapters.list;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.viethoa.RecyclerViewFastScroller;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.FontTextView;
import music.dexterous.com.dexterousmusic.database.Music;

/**
 * Created by Dubey's on 06-08-2016.
 */
public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.AllSongsViewHolder>
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
        return new AllSongsViewHolder(v);
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

    public class AllSongsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        FontTextView mSongAlbum;
        FontTextView mSongName;
        FontTextView mSongArtist;
        FontTextView mSongDuration;
        ImageView mOverflowIcon;


        public AllSongsViewHolder(View itemView) {
            super(itemView);
            mSongName = (FontTextView) itemView.findViewById(R.id.song_name);
            mSongAlbum = (FontTextView) itemView.findViewById(R.id.song_album);
            mSongArtist = (FontTextView) itemView.findViewById(R.id.song_artist);
            mSongDuration = (FontTextView) itemView.findViewById(R.id.song_duration);
            mOverflowIcon = (ImageView) itemView.findViewById(R.id.showPopup);

            mOverflowIcon.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) {
                switch (view.getId()) {
                    case R.id.showPopup:
                        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                        popupMenu.inflate(R.menu.all_songs__item_menu);
                        popupMenu.setOnMenuItemClickListener(this);
                        popupMenu.show();

                    default:
                        int position = getAdapterPosition();
                        mOnClickListener.onClick(view, position);
                }
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Toast.makeText(mOverflowIcon.getContext(), "DO SOME STUFF HERE", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    private OnAllSongsItemClickListener mOnClickListener;

    public void setOnItemClickListener(OnAllSongsItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnAllSongsItemClickListener {
        void onClick(View view, int position);
    }
}
