package music.dexterous.com.dexterousmusic.customeviews.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

/**
 * Created by Honey on 7/31/2016.
 */
public class ShortToast {
    public static final  int     yOffset  = UiUtils.dpToPixel(52);
    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    public static void displayToast(Context context, String textToShow, long duration) {
        if (!TextUtils.isEmpty(textToShow) && context != null) {
            if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
                duration = 300 * (duration + 1);
            }

            duration = (duration > 400) ? duration : 1000;

            LayoutInflater inflater    = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View           toastLayout = inflater.inflate(R.layout.layout_simple_toast, null);

            final TextView text = (TextView) toastLayout.findViewById(R.id.toastMessage);

            final long finalDuration = duration;
            sHandler.post(() -> {
                final Toast toast = new Toast(context);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, yOffset);
                toast.setDuration(Toast.LENGTH_SHORT);

                text.setText(textToShow);
                toast.setView(toastLayout);
                toast.show();

                sHandler.postDelayed(toast::cancel, finalDuration);
            });
        }
    }
}
