package music.dexterous.com.dexterousmusic.customeviews;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by anand on 26/10/15.
 */
public class RecyclerGridDivider extends RecyclerView.ItemDecoration {

    private int space;

    public RecyclerGridDivider(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;
    }
}
