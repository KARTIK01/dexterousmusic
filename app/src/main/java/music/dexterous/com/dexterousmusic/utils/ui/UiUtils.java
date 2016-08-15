package music.dexterous.com.dexterousmusic.utils.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.util.concurrent.CountDownLatch;

import music.dexterous.com.dexterousmusic.R;
import okhttp3.Response;

/**
 * Created by Kartik on 06/01/16.
 * <p>
 * Helper methods for UI operations
 */
public class UiUtils {

    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    private static float densityScale;

    static public String[] mHomeTabHeaderTittle;

    /**
     * Screen dimensions in pixels
     */
    public static int screenWidth;
    public static int screenHeight;


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
     * Create bitmap of the view to be used in share.
     * This method may be called in background thread, hence make sure
     * to do the ui operation in ui thread
     */
    public static Bitmap renderViewOnBitmapForShare(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
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
     * Load data which are required for {@link music.dexterous.com.dexterousmusic.activity.HomeActivity} or its fragment
     *
     * @param context
     */
    static public void loadHomeActivitySpecificData(Context context) {
        loadStrings(context);
    }

    static private void loadStrings(Context context) {
        Resources resources = context.getResources();

        String albums = resources.getString(R.string.albums);
        String artist = resources.getString(R.string.artist);
        String all_songs = resources.getString(R.string.all_songs);
        String recent_played = resources.getString(R.string.recent_played);
        mHomeTabHeaderTittle = new String[]{recent_played, all_songs, artist, albums};
    }

}
