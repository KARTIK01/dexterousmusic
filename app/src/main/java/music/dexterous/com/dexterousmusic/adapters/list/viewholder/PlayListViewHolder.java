package music.dexterous.com.dexterousmusic.adapters.list.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.widget.FontTextView;

public class PlayListViewHolder extends RecyclerView.ViewHolder {

    public FontTextView mSongAlbum;
    public FontTextView mSongName;
    public FontTextView mSongArtist;
    public FontTextView mSongDuration;
    public ImageView    mOverflowIcon;


    public PlayListViewHolder(View itemView) {
        super(itemView);
        mSongName = (FontTextView) itemView.findViewById(R.id.song_name);
        mSongAlbum = (FontTextView) itemView.findViewById(R.id.song_album);
        mSongArtist = (FontTextView) itemView.findViewById(R.id.song_artist);
        mSongDuration = (FontTextView) itemView.findViewById(R.id.song_duration);
    }


}
