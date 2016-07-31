package music.dexterous.com.dexterousmusic.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Kartik on 06/01/16.
 * <p>
 * Helper methods for UI operations
 */
public class UiUtils {

    private static final Handler mHandler = new Handler(Looper.getMainLooper());


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


}
