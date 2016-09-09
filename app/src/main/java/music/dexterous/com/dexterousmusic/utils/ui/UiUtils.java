package music.dexterous.com.dexterousmusic.utils.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.concurrent.CountDownLatch;

import music.dexterous.com.dexterousmusic.R;

/**
 * Created by Kartik on 06/01/16.
 * <p>
 * Helper methods for UI operations
 */
public class UiUtils {

    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    static public String[] mHomeTabHeaderTittle;
    static public Drawable ic_pause_vector;
    static public Drawable ic_play_vector;
    static public Drawable ic_skip_next_vector;
    static public Drawable ic_skip_previous_vector;
    static public Drawable ic_shuffle_off_vector;
    static public Drawable ic_shuffle_on_vector;
    static public Drawable ic_shuffle_white_vector;
    static public Drawable ic_repeat_all_vector;
    static public Drawable ic_repeat_off_vector;
    static public Drawable ic_repeat_one_vector;
    static public Drawable ic_dots_vertical_vector;
    /**
     * Screen dimensions in pixels
     */
    public static int screenWidth;
    public static int screenHeight;
    private static float densityScale;

    /**
     * load data which are required for {@link music.dexterous.com.dexterousmusic.GlobalApplication}
     *
     * @param context
     */
    public static void initialize(Context context) {
        Resources resources = context.getResources();
        densityScale = resources.getDisplayMetrics().density;
    }


    /**
     * Create bitmap of the view to be used in share. This method may be called in background
     * thread, hence make sure to do the ui operation in ui thread
     */
    public static Bitmap renderViewOnBitmapForShare(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.RGB_565);
        Canvas         canvas         = new Canvas(bitmap);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        mHandler.post(() -> {
            canvas.drawColor(Color.WHITE);
            view.draw(canvas);
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static int dpToPixel(float dp) {
        return Math.round(dp * densityScale);
    }

    /**
     * Load data which are required for {@link music.dexterous.com.dexterousmusic.activity.HomeActivity}
     * or its fragment
     *
     * @param context
     */
    static public void loadHomeActivitySpecificData(Context context) {
        loadStrings(context);
        loadIcons(context);
    }

    static private void loadStrings(Context context) {
        Resources resources = context.getResources();

        String albums    = resources.getString(R.string.albums);
        String artist    = resources.getString(R.string.artist);
        String all_songs = resources.getString(R.string.all_songs);
//        String recent_played = resources.getString(R.string.recent_played);
        mHomeTabHeaderTittle = new String[]{all_songs, artist, albums};
    }

    static private void loadIcons(Context context) {

        ic_pause_vector = ContextCompat.getDrawable(context, R.drawable.ic_pause_vector);
        ic_play_vector = ContextCompat.getDrawable(context, R.drawable.ic_play_vector);

        ic_skip_next_vector = ContextCompat.getDrawable(context, R.drawable.ic_skip_next_vector);
        ic_skip_previous_vector = ContextCompat.getDrawable(context, R.drawable.ic_skip_previous_vector);

        ic_dots_vertical_vector = ContextCompat.getDrawable(context, R.drawable.ic_dots_vertical_vector);

        //shuffle icons
        ic_shuffle_off_vector = ContextCompat.getDrawable(context, R.drawable.ic_shuffle_off_vector);
        ic_shuffle_on_vector = ContextCompat.getDrawable(context, R.drawable.ic_shuffle_on_vector);
        ic_shuffle_white_vector = ContextCompat.getDrawable(context, R.drawable.ic_shuffle_white_vector);

        //repeat icons
        ic_repeat_all_vector = ContextCompat.getDrawable(context, R.drawable.ic_repeat_all_vector);
        ic_repeat_one_vector = ContextCompat.getDrawable(context, R.drawable.ic_repeat_one_vector);
        ic_repeat_off_vector = ContextCompat.getDrawable(context, R.drawable.ic_repeat_off_vector);

    }

}
