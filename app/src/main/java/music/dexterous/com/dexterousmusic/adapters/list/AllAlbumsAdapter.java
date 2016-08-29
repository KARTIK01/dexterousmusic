package music.dexterous.com.dexterousmusic.adapters.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.FontTextView;
import music.dexterous.com.dexterousmusic.models.AlbumModel;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;

/**
 * Created by Dubey's on 06-08-2016.
 */
public class AllAlbumsAdapter extends RecyclerView.Adapter<AllAlbumsAdapter.ViewHolder> {

    /**
     * Used to load images asynchronously on a background thread.
     */
    ImageLoader mImageLoader;

    private List<AlbumModel> mDataArray;
    Context context;


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
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String albumArtPath = mDataArray.get(position).getAlbumArtPath();
        Bitmap bitmap = null;

        if (!TextUtils.isEmpty(albumArtPath)) {
            bitmap = BitmapFactory.decodeFile(albumArtPath);
        }

        if (bitmap != null)
            mImageLoader.loadImage(context, bitmap, holder.mTextView);
        else
            mImageLoader.loadImage(context, R.drawable.bg_1, holder.mTextView);


        holder.albumName.setText(mDataArray.get(position).getAlbumName());

    }

    public interface OnAlbumItemClickListener {
        void onClick(View view, int position);
    }

    private OnAlbumItemClickListener mOnClickListener;

    public void setOnItemClickListener(OnAlbumItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
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
                    int position = getAdapterPosition();
                    mOnClickListener.onClick(view, position);
            }
        }
    }


}
