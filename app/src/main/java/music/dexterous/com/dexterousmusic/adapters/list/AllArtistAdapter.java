package music.dexterous.com.dexterousmusic.adapters.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.viethoa.RecyclerViewFastScroller;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.FontTextView;
import music.dexterous.com.dexterousmusic.models.ArtistModel;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;

/**
 * Created by Dubey's on 06-08-2016.
 */
public class AllArtistAdapter extends RecyclerView.Adapter<AllArtistAdapter.ViewHolder>
        implements RecyclerViewFastScroller.BubbleTextGetter {

    /**
     * Used to load images asynchronously on a background thread.
     */
    ImageLoader mImageLoader;

    private List<ArtistModel> mDataArray;
    Context context;


    public AllArtistAdapter(List<ArtistModel> dataset, Context context) {
        this.context = context;
        mDataArray = dataset;
        mImageLoader = new ImageLoader(context, R.drawable.dishoom);
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public AllArtistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_layout_artist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        Bitmap bitmap = BitmapFactory.decodeFile(
//                mDataArray
//                        .get(position)
//                        .getAlbumArtPath());

//        PrettyLogger.d(mDataArray.get(position).toString());

//        mImageLoader.loadImage(context,bitmap, holder.mTextView);/
        holder.albumName.setText(mDataArray.get(position).getAlbumName());
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= mDataArray.size())
            return null;

        String name = mDataArray.get(pos).getAlbumName();
        if (name == null || name.length() < 1)
            return null;

        return mDataArray.get(pos).getAlbumName().substring(0, 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mTextView;
        FontTextView albumName;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (ImageView) itemView.findViewById(R.id.albumImage);
            albumName = (FontTextView) itemView.findViewById(R.id.albumName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                    mOnClickListener.onClick(view, getAdapterPosition());
            }
        }
    }


    public interface OnAlbumItemClickListener {
        void onClick(View view, int position);
    }

    private OnAlbumItemClickListener mOnClickListener;

    public void setOnItemClickListener(OnAlbumItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

}
