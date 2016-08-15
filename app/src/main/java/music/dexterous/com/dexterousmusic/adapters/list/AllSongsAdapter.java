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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_layout, parent, false);
        return new AllSongsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AllSongsViewHolder holder, int position) {
        holder.mTextView.setText(mDataArray.get(position).getSONG_TITLE());
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

    public class AllSongsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FontTextView mTextView;

        public AllSongsViewHolder(View itemView) {
            super(itemView);
            mTextView = (FontTextView) itemView.findViewById(R.id.tv_alphabet);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) {
                switch (view.getId()) {
                    default:
                        int position = getAdapterPosition();
                        mOnClickListener.onClick(view, position);
                }
            }
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
