package music.dexterous.com.dexterousmusic.adapters.list.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.AllArtistAdapter;
import music.dexterous.com.dexterousmusic.customeviews.widget.FontTextView;

public class AllArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView    mTextView;
    public FontTextView albumName;
    private AllArtistAdapter.OnAlbumItemClickListener mOnClickListener;


    public AllArtistViewHolder(View itemView, AllArtistAdapter.OnAlbumItemClickListener onClickListener) {
        super(itemView);
        mTextView = (ImageView) itemView.findViewById(R.id.albumImage);
        albumName = (FontTextView) itemView.findViewById(R.id.albumName);

        this.mOnClickListener = onClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener != null) {
            switch (view.getId()) {
                default:
                    mOnClickListener.onClick(view, getAdapterPosition());
            }
        }
    }

}
