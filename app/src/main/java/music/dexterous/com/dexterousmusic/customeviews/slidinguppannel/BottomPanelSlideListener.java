package music.dexterous.com.dexterousmusic.customeviews.slidinguppannel;

import android.view.View;

import music.dexterous.com.dexterousmusic.R;

/**
 * Created by Kartik on 8/18/2016.
 */
public class BottomPanelSlideListener implements SlidingUpPanelLayout.PanelSlideListener {

    static float previousSlideOffSet = 0;

    /**
     * Called when a sliding pane's position changes.
     *
     * @param panel       The child view that was moved
     * @param slideOffset The new offset of this sliding pane within its range, from 0-1
     */
    @Override
    public void onPanelSlide(View panel, float slideOffset) {

        panel.findViewById(R.id.bottom_bar).setAlpha(1 - slideOffset);
        panel.findViewById(R.id.now_plaiing_innerview).setAlpha(slideOffset);

        if (previousSlideOffSet == 0) {
            panel.findViewById(R.id.bottom_bar).setVisibility(View.VISIBLE);
            panel.findViewById(R.id.now_plaiing_innerview).setVisibility(View.VISIBLE);
        }
        previousSlideOffSet = slideOffset;
    }

    /**
     * Called when a sliding panel state changes
     *
     * @param panel The child view that was slid to an collapsed position
     */
    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

        switch (newState) {
            case EXPANDED:
                panel.findViewById(R.id.bottom_bar).setVisibility(View.GONE);
                panel.findViewById(R.id.now_plaiing_innerview).setVisibility(View.VISIBLE);
                previousSlideOffSet = 0;
                break;
            case COLLAPSED:
                panel.findViewById(R.id.bottom_bar).setVisibility(View.VISIBLE);
                panel.findViewById(R.id.now_plaiing_innerview).setVisibility(View.GONE);
                previousSlideOffSet = 0;
                break;
            case ANCHORED:
                break;
            case HIDDEN:
                break;
            case DRAGGING:
                break;
        }

    }
}
