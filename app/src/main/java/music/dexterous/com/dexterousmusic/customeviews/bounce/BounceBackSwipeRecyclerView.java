package music.dexterous.com.dexterousmusic.customeviews.bounce;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import music.dexterous.com.dexterousmusic.customeviews.bounce.helper.OverScrollDecoratorHelper;

/**
 * Created by kartik on 30/08/16.
 */
public class BounceBackSwipeRecyclerView extends RecyclerView {

    public BounceBackSwipeRecyclerView(Context context) {
        super(context);
    }

    public BounceBackSwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceBackSwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
         
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        OverScrollDecoratorHelper.setUpOverScroll(this, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }
    
}
