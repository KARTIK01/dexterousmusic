package music.dexterous.com.dexterousmusic.customeviews.bounce;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import music.dexterous.com.dexterousmusic.customeviews.bounce.helper.OverScrollDecoratorHelper;

/**
 * Created by kartik on 31/08/16.
 */
public class BounceBackViewPager extends ViewPager{

    public BounceBackViewPager(Context context) {
        super(context);
    }

    public BounceBackViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        OverScrollDecoratorHelper.setUpOverScroll(this);
    }
}
