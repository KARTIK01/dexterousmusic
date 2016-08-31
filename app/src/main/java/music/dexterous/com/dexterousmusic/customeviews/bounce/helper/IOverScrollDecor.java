package music.dexterous.com.dexterousmusic.customeviews.bounce.helper;

import android.view.View;

/**
 * @author amit
 */
public interface IOverScrollDecor {
    View getView();
    void setOverScrollStateListener(IOverScrollStateListener listener);
    void setOverScrollUpdateListener(IOverScrollUpdateListener listener);
}
