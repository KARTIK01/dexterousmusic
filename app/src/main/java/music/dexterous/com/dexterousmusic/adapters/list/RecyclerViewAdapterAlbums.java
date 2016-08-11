package music.dexterous.com.dexterousmusic.adapters.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viethoa.RecyclerViewFastScroller;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.FontTextView;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.models.AlbumModel;

/**
 * Created by Dubey's on 06-08-2016.
 */
public class RecyclerViewAdapterAlbums extends RecyclerView.Adapter<RecyclerViewAdapterAlbums.ViewHolder>
        implements RecyclerViewFastScroller.BubbleTextGetter {


    private List<AlbumModel> mDataArray;

    public RecyclerViewAdapterAlbums(List<AlbumModel> dataset) {
        mDataArray = dataset;
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public RecyclerViewAdapterAlbums.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataArray.get(position).getAlbumName());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        FontTextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (FontTextView) itemView.findViewById(R.id.tv_alphabet);
        }
    }


}
