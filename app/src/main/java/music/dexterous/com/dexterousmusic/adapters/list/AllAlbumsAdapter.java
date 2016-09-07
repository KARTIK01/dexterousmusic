package music.dexterous.com.dexterousmusic.adapters.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.widget.FontTextView;
import music.dexterous.com.dexterousmusic.models.AlbumModel;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;

/**
 * Created by Dubey's on 06-08-2016.
 */
public class AllAlbumsAdapter extends RecyclerView.Adapter<AllAlbumsAdapter.ViewHolder> {

    private static final String TAG = "AllAlbumsAdapter";
    /**
     * Used to load images asynchronously on a background thread.
     */
    ImageLoader mImageLoader;
    Context context;
    private List<AlbumModel> mDataArray;
    private OnAlbumItemClickListener mOnClickListener;

    public AllAlbumsAdapter(List<AlbumModel> dataset, Context context) {
        this.context = context;
        mDataArray = dataset;
        mImageLoader = new ImageLoader(context, R.drawable.bg_1);
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public AllAlbumsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_layout_albums, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String albumArtPath = mDataArray.get(position).getAlbumArtPath();
        holder.albumName.setText(mDataArray.get(position).getAlbumName());
        new ImageLoader(context).loadImage(context, albumArtPath, holder.mTextView);
    }

    public void setOnItemClickListener(OnAlbumItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnAlbumItemClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView    mTextView;
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
                    int position = getAdapterPosition();
                    mOnClickListener.onClick(view, position);
            }
        }
    }


}
