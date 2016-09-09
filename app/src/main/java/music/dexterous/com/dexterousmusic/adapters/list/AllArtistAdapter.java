package music.dexterous.com.dexterousmusic.adapters.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turingtechnologies.materialscrollbar.INameableAdapter;
import com.viethoa.RecyclerViewFastScroller;

import java.util.List;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.adapters.list.viewholder.AllArtistViewHolder;
import music.dexterous.com.dexterousmusic.models.ArtistModel;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;

/**
 * Created by Dubey's on 06-08-2016.
 */
public class AllArtistAdapter extends RecyclerView.Adapter<AllArtistViewHolder>
        implements RecyclerViewFastScroller.BubbleTextGetter, INameableAdapter {

    /**
     * Used to load images asynchronously on a background thread.
     */
    ImageLoader mImageLoader;
    Context context;
    private List<ArtistModel> artistModels;
    private OnAlbumItemClickListener mOnClickListener;

    public AllArtistAdapter(List<ArtistModel> artistModels, Context context) {
        this.context = context;
        this.artistModels = artistModels;
        mImageLoader = new ImageLoader(context, R.drawable.bg_1);
    }

    @Override
    public int getItemCount() {
        if (artistModels == null)
            return 0;
        return artistModels.size();
    }

    @Override
    public AllArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_layout_artist, parent, false);
        return new AllArtistViewHolder(v, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(AllArtistViewHolder holder, int position) {

//        Bitmap bitmap = BitmapFactory.decodeFile(
//                mDataArray
//                        .get(position)
//                        .getAlbumArtPath());

//        PrettyLogger.d(mDataArray.get(position).toString());

//        mImageLoader.loadImage(context,bitmap, holder.mTextView);/
        holder.albumName.setText(artistModels.get(position).getArtistName());
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= artistModels.size())
            return null;

        String name = artistModels.get(pos).getArtistName();
        if (name == null || name.length() < 1)
            return null;

        return artistModels.get(pos).getArtistName().substring(0, 1);
    }

    public void setOnItemClickListener(OnAlbumItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public Character getCharacterForElement(int element) {
        Character c = artistModels.get(element).getArtistName().charAt(0);
        if (Character.isDigit(c)) {
            c = '#';
        }
        return c;
    }

    public interface OnAlbumItemClickListener {
        void onClick(View view, int position);
    }

}
