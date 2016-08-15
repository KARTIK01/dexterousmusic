package music.dexterous.com.dexterousmusic.adapters.list.viewholder;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.AllSongsAdapter;
import music.dexterous.com.dexterousmusic.customeviews.FontTextView;

public class AllSongsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private AllSongsAdapter.OnAllSongsItemClickListener mOnClickListener;

    public FontTextView mSongAlbum;
    public FontTextView mSongName;
    public FontTextView mSongArtist;
    public FontTextView mSongDuration;
    public ImageView mOverflowIcon;


    public AllSongsViewHolder(View itemView, AllSongsAdapter.OnAllSongsItemClickListener onClickListener) {
        super(itemView);
        mSongName = (FontTextView) itemView.findViewById(R.id.song_name);
        mSongAlbum = (FontTextView) itemView.findViewById(R.id.song_album);
        mSongArtist = (FontTextView) itemView.findViewById(R.id.song_artist);
        mSongDuration = (FontTextView) itemView.findViewById(R.id.song_duration);
        mOverflowIcon = (ImageView) itemView.findViewById(R.id.showPopup);

        this.mOnClickListener = onClickListener;

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
                    break;
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
